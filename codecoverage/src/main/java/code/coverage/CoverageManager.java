package code.coverage;

import code.utils.MyProperties;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CoverageManager {

    public CoverageManager(){
    }

    public Object getCoverageObject(WebDriver driver){
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        Object coverage = javascriptExecutor.executeScript("return window.__coverage__;");
        if(coverage == null){
            throw new IllegalStateException("Be sure that the application source code has been instrumented with \' istanbul instrument \'");
        }
        return coverage;
    }

    public void sendCoverageObjectToExpressServer(Object coverage){
        try {
            HttpRequestFactory requestFactory
                    = new NetHttpTransport().createRequestFactory();
            JsonHttpContent jsonHttpContent = new JsonHttpContent(new JacksonFactory(), coverage);
            HttpRequest request = requestFactory.buildPostRequest(
                    new GenericUrl("http://localhost:"
                            + MyProperties.getInstance().getExpressServerPort()
                            + "/coverage/client"), jsonHttpContent);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("application/json");
            request.setHeaders(headers);
            HttpResponse httpResponse = request.execute();
            this.parseOkResponse(httpResponse);
        } catch (IOException e) {
            System.out.println("[CodeCoverage] Be sure that the express server is running.");
            e.printStackTrace();
        }
    }

    public String getCoverageReportFromExpressServer(){
        try {
            HttpRequestFactory requestFactory
                    = new NetHttpTransport().createRequestFactory();
            HttpRequest request = requestFactory.buildGetRequest(
                    new GenericUrl("http://localhost:"
                            + MyProperties.getInstance().getExpressServerPort()
                            + "/coverage"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("text/html");
            request.setHeaders(headers);
            String htmlResponse = request.execute().parseAsString();
            return htmlResponse;
        } catch (IOException e) {
            System.out.println("[CodeCoverage] Be sure that the express server is running or that you post the coverage info through the client (sendCoverageObjectToExpressServer).");
            e.printStackTrace();
            return "";
        }
    }

    public void resetCoverageStats(){
        try {
            HttpRequestFactory requestFactory
                    = new NetHttpTransport().createRequestFactory();
            JsonObject jsonObject = new JsonObject();
            JsonHttpContent jsonHttpContent = new JsonHttpContent(new JacksonFactory(), jsonObject);
            HttpRequest request = requestFactory.buildPostRequest(
                    new GenericUrl("http://localhost:"
                            + MyProperties.getInstance().getExpressServerPort()
                            + "/coverage/reset"), jsonHttpContent);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("application/json");
            request.setHeaders(headers);
            HttpResponse httpResponse = request.execute();
            this.parseOkResponse(httpResponse);
        } catch (IOException e) {
            System.out.println("[CodeCoverage] Be sure that the express server is running.");
            e.printStackTrace();
        }
    }

    public List<CoverageInfo> parseHTMLResponse(String html){
        Document doc = Jsoup.parse(html);
        Element coverageSummaryContainer = doc.select("div.clearfix").first();
        Elements children = coverageSummaryContainer.children();
        List<CoverageInfo> coverageInfos = children.stream()
                .map(element -> {
                    CoverageInfo coverageInfo = new CoverageInfo();
                    for (int i = 0; i < element.children().size(); i++) {
                        Element span = element.child(i);
                        if(span.hasText()){
                            if(i == 0){ //percentage
                                String percentage = span.text();
                                String doublePercentage = percentage.replace("%", "").trim(); //trim remove white spaces before and after the string
                                coverageInfo.setPercentage(Double.valueOf(doublePercentage));
                            }else if(i == 1){ //name
                                String name = span.text();
                                coverageInfo.setName(name.trim());
                            }else if(i == 2){ //fraction
                                String fraction = span.text().trim();
                                String numDen[] = fraction.split("/");
                                String num = numDen[0];
                                String den = numDen[1];
                                coverageInfo.setFraction(fraction);
                                coverageInfo.setNumerator(Integer.valueOf(num));
                                coverageInfo.setDenominator(Integer.valueOf(den));
                            }
                        }
                    }
                    return coverageInfo;
                })
                .collect(Collectors.toList());
        return coverageInfos;
    }

    public void writeCoverageReport(List<CoverageInfo> coverageInfos, String pathToESTestSuite){
        try {
            int counter = 0;
            File resultFile = new File(pathToESTestSuite + "/coverage-report" + counter + ".txt");
            while(resultFile.exists()){
                resultFile = new File(pathToESTestSuite + "/coverage-report" + counter + ".txt");
                counter++;
            }
            Writer writer = new PrintWriter(resultFile);
            writer.write(coverageInfos.stream().map(String::valueOf).collect(Collectors.joining("\n")));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCoverageReport(List<CoverageInfo> coverageInfos, String pathToESTestSuite, int indexOfCoverageReport){
        try {
            Writer writer = new PrintWriter(new File(pathToESTestSuite) + "/coverage-report" + indexOfCoverageReport + ".txt");
            writer.write(coverageInfos.stream().map(String::valueOf).collect(Collectors.joining("\n")));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseOkResponse(HttpResponse httpResponse) throws IOException {
        try{
            String rawResponse = httpResponse.parseAsString();
            Type okMessageType = new TypeToken<OkMessage>() {}.getType();
            Gson gson = new Gson();
            OkMessage okMessage = gson.fromJson(rawResponse, okMessageType);
            if(!okMessage.getOk().equals("true")){
                throw new IllegalStateException("Response message is not as expected! Expected \'{\"ok\":true}\' found " + rawResponse);
            }
        }finally {
            httpResponse.disconnect();
        }
    }

    private static class OkMessage{

        private String ok;

        public String getOk() {
            return ok;
        }
    }
}
