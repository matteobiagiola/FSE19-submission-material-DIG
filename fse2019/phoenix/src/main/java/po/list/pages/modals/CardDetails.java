package po.list.pages.modals;

import custom_classes.CardComment;
import custom_classes.CardDescription;
import custom_classes.CardTag;
import custom_classes.CardText;
import custom_classes.Email;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po.board.pages.BoardsContainerPage;
import po.list.pages.BoardListContainerPage;
import po_utils.BasePageObject;
import po_utils.ConstantLocators;
import po_utils.PageObject;
import po_utils.PageObjectLogging;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardDetails extends BasePageObject implements PageObject {

    public CardDetails(WebDriver driver) {
        super(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("CardDetails not loaded properly");
        }
    }

    //tested
    public BoardListContainerPage closeCardDetails(){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//a[@class=\"close\"]"));
        return new BoardListContainerPage(this.getDriver());
    }

    //tested
    public BoardListContainerPage deleteCard(){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//a[@class=\"delete\"]"));
        return new BoardListContainerPage(this.getDriver());
    }

    //tested
    public CardDetails openEditDescriptionForm(){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//a[text()=\"Edit\"]"));
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//form/a[text()=\"cancel\"]"));
        return this;
    }

    //tested
    public CardDetails editDescription(CardText cardText, CardDescription cardDescription){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//a[text()=\"Edit\"]"));
        this.type(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/input[@placeholder=\"Title\"]"), cardText.value());
        this.type(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/textarea[@placeholder=\"Description\"]"), cardDescription.value());
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/header/form/button[text()=\"Save card\"]"));
        return this;
    }

    //tested
    public CardDetails addComment(CardComment cardComment){
        this.type(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//textarea[@placeholder=\"Write a comment...\"]"), cardComment.value());
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]/div[@class=\"form-wrapper\"]/form//button[text()=\"Save comment\"]"));
        return this;
    }

    //tested
    public CardDetails addMemberToCard(Email email){
        if(this.isMemberPresent(email) && !this.isAlreadyMemberOfCard(email)){
            this.openMemberWindow();
            this.clickOnMember(email);
            this.closeMemberWindow();
            return this;
        }else{
            throw new IllegalArgumentException("addMemberToCard: member with email " + email.value() + " is already member of card or it does not exist");
        }
    }

    //tested
    public CardDetails removeMemberFromCard(Email email){
        if(this.isMemberPresent(email) && this.isAlreadyMemberOfCard(email)){
            this.openMemberWindow();
            this.clickOnMember(email);
            this.closeMemberWindow();
            return this;
        }else{
            throw new IllegalArgumentException("removeMemberFromCard: member with email " + email.value() + " is not a member of card or it does not exist");
        }
    }

    //tested
    public CardDetails addTagToCard(CardTag cardTag){
        if(!this.isTagAlreadyActive(cardTag)){
            this.openTagWindow();
            this.clickOnTag(cardTag);
            this.closeTagWindow();
            return this;
        }else{
            throw new IllegalArgumentException("addTagToCard: card tag " + cardTag.value() + " is already active on the card");
        }
    }

    //tested
    public CardDetails removeTagToCard(CardTag cardTag){
        if(this.isTagAlreadyActive(cardTag)){
            this.openTagWindow();
            this.clickOnTag(cardTag);
            this.closeTagWindow();
            return this;
        }else{
            throw new IllegalArgumentException("removeTagToCard: card tag " + cardTag.value() + " is not active on the card");
        }
    }

    public boolean isMemberPresent(Email email){
        this.openMemberWindow();
        try{
            WebElement memberLink = this.getMemberLinkByName(email);
            this.closeMemberWindow();
            return true;
        }catch (IllegalStateException ex){
            //PageObjectLogging.logInfo("isMemberPresent exception");
            this.closeMemberWindow();
            return false;
        }
    }

    public boolean isAlreadyMemberOfCard(Email email){
        this.openMemberWindow();
        WebElement memberLink = this.getMemberLinkByName(email);
        WebElement iTag = this.findElementJSByXPathStartingFrom(memberLink, "./i");
        String classAttribute = this.getAttribute(iTag, "class");
        if(classAttribute.equals("fa fa-check")){
            this.closeMemberWindow();
            return true;
        }
        this.closeMemberWindow();
        return false;
    }

    public void clickOnMember(Email email){
        WebElement memberLink = this.getMemberLinkByName(email);
        this.clickOn(memberLink);
    }

    public List<WebElement> getMemberLinks(){
        return this.findElements(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"options\"]/div[@class=\"members-selector\"]/ul/li/a"));
    }

    public WebElement getMemberLinkByName(Email email){
        List<WebElement> memberLinks = this.getMemberLinks();
        String regex = "([A-Za-z0-9+_.-]+@.+)";
        for(WebElement memberLink: memberLinks){
            WebElement imgTag = this.findElementJSByXPathStartingFrom(memberLink, "./img");
            String altText = this.getAttribute(imgTag, "alt");
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(altText);
            if(matcher.find()){
                String currentEmail = matcher.group(0);
                if(currentEmail.trim().equals(email.value())){
                    return memberLink;
                }
            }else{
                throw new IllegalStateException("getMemberLinkByName: problem with email regex");
            }
        }
        throw new IllegalStateException("getMemberLinkByName: could not find member link for email " + email.value());
    }

    public void openMemberWindow(){
        this.clickOn(By.xpath("(//div[@class=\"md-modal\"]//div[@class=\"options\"]/a[@class=\"button\"])[1]"));
    }

    public void closeMemberWindow(){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"options\"]/div[@class=\"members-selector\"]/header/a[@class=\"close\"]"));
    }

    public boolean isTagAlreadyActive(CardTag cardTag){
        return this.isElementPresentOnPage(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"info\"]//div[@class=\"items-wrapper\"]/div[@class=\"card-tags\"]/div[@class=\"" + cardTag.value() + "\"]"));
        //return this.isElementPresentOnPage(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"options\"]/div[@class=\"tags-selector\"]/ul/li/a[@class=" + "\"" + cardTag.value() + "selected\"]"));
    }

    public void clickOnTag(CardTag cardTag){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"options\"]/div[@class=\"tags-selector\"]/ul/li/a[contains(@class,\"" + cardTag.value() + "\")]"));
    }

    public void openTagWindow(){
        this.clickOn(By.xpath("(//div[@class=\"md-modal\"]//div[@class=\"options\"]/a[@class=\"button\"])[2]"));
    }

    public void closeTagWindow(){
        this.clickOn(By.xpath("//div[@class=\"md-modal\"]//div[@class=\"options\"]/div[@class=\"tags-selector\"]/header/a[@class=\"close\"]"));
    }

    @Override
    public boolean isPageLoaded() {
        if(this.waitForElementBeingPresentOnPage(ConstantLocators.CARD_DETAILS.value())){
            return true;
        }
        return false;
    }
}
