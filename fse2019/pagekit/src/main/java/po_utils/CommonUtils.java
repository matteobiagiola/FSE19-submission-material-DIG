package po_utils;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommonUtils {

    private CommonUtils() {

    }

    public static void appendTextToFile(String filePath, String textToWrite) {
        try {
            FileWriter newFile = new FileWriter(filePath, true);
            BufferedWriter out = new BufferedWriter(newFile);
            out.write(textToWrite);
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            //throw new TestEnvInitFailedException();
        }
    }

    /**
     * delete directory by path
     */
    public static void deleteDirectory(String dirName) {
        try {
            FileUtils.deleteDirectory(new File(dirName));
        } catch (IOException e) {
            PageObjectLogging.logInfo("deleteDirectory: " + e);
        }
    }

    /**
     * creates directory based on given path
     */
    public static void createDirectory(String fileName) {
        try {
            new File(fileName).mkdir();
            System.out.println("directory " + fileName + " created");
        } catch (SecurityException e) {
            PageObjectLogging.logInfo("createDirectory SecurityException: " + e);
            //throw new TestEnvInitFailedException();
        }
    }

    public static String getAbsolutePathForFile(String relativePath) {
        File fileCheck = new File(relativePath);
        if (!fileCheck.isFile()) {
            PageObjectLogging.logInfo("getAbsolutePathForFile: file " + relativePath + " doesn't exists");
            //throw new TestEnvInitFailedException("file " + relativePath + " doesn't exists");
        }
        return fileCheck.getAbsolutePath();
    }

    /*public static String sendPost(String apiUrl, String[][] param) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);
            List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();

            for (int i = 0; i < param.length; i++) {
                paramPairs.add(new BasicNameValuePair(param[i][0], param[i][1]));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(paramPairs));

            HttpResponse response = null;
            response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            PageObjectLogging.logInfo("sendPost", e, false);
            return null;
        } catch (ClientProtocolException e) {
            PageObjectLogging.logInfo("sendPost", e, false);
            return null;
        } catch (IOException e) {
            PageObjectLogging.logInfo("sendPost", e, false);
            return null;
        }
    }*/
}
