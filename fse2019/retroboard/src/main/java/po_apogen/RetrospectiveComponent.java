package po_apogen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import po_utils.BasePageObject;
import po_utils.PageComponent;

import java.util.List;

public class RetrospectiveComponent extends BasePageObject implements PageComponent {

	/**
	 * Page Object for NewRetrospective (state7) --> RetrospectiveContainerPage
	 */
	public RetrospectiveComponent(WebDriver driver) {
		super(driver);
	}

	// divided in 4 methods instead of 1
//	public RetrospectiveComponent retrospective(String args0, String args1, String args2, String args3) {
//		span_1.sendKeys(args3);
//		input_1.sendKeys(args0);
//		input_2.sendKeys(args1);
//		input_3.sendKeys(args2);
//		return this;
//	}

	// openMenu
	public void goToMenu() {
		this.clickOpenMenu();
	}

	/*----- added */

	public void clickOpenMenu(){
		this.clickOn(By.xpath("//button[@id=\"crawljax-menu\"]"));
	}

	public void renameBoard(String newBoardName){
		this.clickOn(By.xpath("//span[contains(@class,\"SessionName__editIcon___AOR2M\")]"));
		WebElement inputElement = this.findElementSafely(By.xpath("//div[@class=\"SessionName__sessionName___10R1C\"]//div[@data-react-toolbox=\"input\"]/input[@type=\"text\"]"));
		this.type(inputElement, newBoardName);
		this.pressKeyboardEnter(inputElement);
	}

	public void typeWentWellPost(String wentWell){
		WebElement wentWellPostInputBox = this.findElementSafely(By.xpath("(//div[contains(@class,\"PostBoard__column___2nAoB\")])[1]//input[@type=\"input\"]"));
		this.createPost(wentWellPostInputBox, wentWell);
	}

	public void typeNotWentWellPost(String notWentWell){
		WebElement notWentWellPostInputBox = this.findElementSafely(By.xpath("(//div[contains(@class,\"PostBoard__column___2nAoB\")])[2]//input[@type=\"input\"]"));
		this.createPost(notWentWellPostInputBox, notWentWell);
	}

	public void typeIdeasPost(String idea){
		WebElement ideasPostInputBox = this.findElementSafely(By.xpath("(//div[contains(@class,\"PostBoard__column___2nAoB\")])[3]//input[@type=\"input\"]"));
		this.createPost(ideasPostInputBox, idea);
	}

	public boolean isSummaryModeOn(){
		return this.isElementPresentOnPage(By.xpath("//div[contains(@class,\"SummaryBoard__summary___1QuND\")]"));
	}

	public void deletePost(WebElement post){
		WebElement deleteButton = this.findElementStartingFrom(post, By.xpath(".//button[contains(@class,\"Post__deleteButton___2cGsP\")]"));
		this.clickOn(deleteButton);
	}

	public void createPost(WebElement inputBox, String content){
		this.type(inputBox, content);
		this.pressKeyboardEnter(inputBox);
		this.waitForTimeoutExpires(200); //be sure that the element is created
	}

	public boolean isPostShared(WebElement post){
		return this.findElementsStartingFrom(post, By.xpath(".//button[contains(@class,\"Post__like___2T9cH\")]")).size() == 1
				|| this.findElementsStartingFrom(post, By.xpath(".//button[contains(@class,\"Post__dislike___aLKuy\")]")).size() == 1;
	}

	public boolean isPostPresent(int id, List<WebElement> list){
		if(list.size() > 0 && id <= list.size()){
			return true;
		}
		return false;
	}

	public List<WebElement> getWentWellPosts(){
		return this.findElements(By.xpath("//div[contains(@class,\"Post__well___1WcRv\")]"));
	}

	public List<WebElement> getNotWentWellPosts(){
		return this.findElements(By.xpath("//div[contains(@class,\"Post__notWell___37RjH\")]"));
	}

	public List<WebElement> getIdeasPosts(){
		return this.findElements(By.xpath("//div[contains(@class,\"Post__ideas___1lntq\")]"));
	}

	public void deleteWentWellPost(int id){
		List<WebElement> wentWellPosts = this.getWentWellPosts();
		WebElement wentWellPost = wentWellPosts.get(id - 1);
		this.deletePost(wentWellPost);
	}

	public void deleteNotWentWellPost(int id){
		List<WebElement> notWentWellPosts = this.getNotWentWellPosts();
		WebElement notWentWellPost = notWentWellPosts.get(id - 1);
		this.deletePost(notWentWellPost);
	}

	public void deleteIdeasPost(int id){
		List<WebElement> ideasPosts = this.getIdeasPosts();
		WebElement ideasPost = ideasPosts.get(id - 1);
		this.deletePost(ideasPost);
	}

	public boolean isWentWellPostPresent(int id){
		List<WebElement> wentWellPosts = this.getWentWellPosts();
		return this.isPostPresent(id, wentWellPosts);
	}

	public boolean isNotWentWellPostPresent(int id){
		List<WebElement> notWentWellPosts = this.getNotWentWellPosts();
		return this.isPostPresent(id, notWentWellPosts);
	}

	public boolean isIdeasPostPresent(int id){
		List<WebElement> ideasPosts = this.getIdeasPosts();
		return this.isPostPresent(id, ideasPosts);
	}

	public boolean isWentWellPostShared(int id){
		List<WebElement> wentWellPosts = this.getWentWellPosts();
		WebElement wentWellPost = wentWellPosts.get(id - 1);
		return this.isPostShared(wentWellPost);
	}

	public boolean isNotWentWellPostShared(int id){
		List<WebElement> notWentWellPosts = this.getNotWentWellPosts();
		WebElement notWentWellPost = notWentWellPosts.get(id - 1);
		return this.isPostShared(notWentWellPost);
	}

	public boolean isIdeasPostShared(int id){
		List<WebElement> ideasPosts = this.getIdeasPosts();
		WebElement ideasPost = ideasPosts.get(id - 1);
		return this.isPostShared(ideasPost);
	}
}
