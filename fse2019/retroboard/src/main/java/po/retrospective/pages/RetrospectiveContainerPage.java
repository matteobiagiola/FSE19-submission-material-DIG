package po.retrospective.pages;

import custom_classes.BoardNames;
import custom_classes.Id;
import custom_classes.IdeasPosts;
import custom_classes.NotWentWellPosts;
import custom_classes.WentWellPosts;
import org.openqa.selenium.WebDriver;
import po.home.pages.HomeContainerPage;
import po.retrospective.components.RetrospectiveComponent;
import po.retrospective.pages.modals.MenuPage;
import po.shared.components.NavbarComponent;
import po_utils.ConstantLocators;
import po_utils.PageObject;

public class RetrospectiveContainerPage implements PageObject {

    public NavbarComponent navbarComponent;
    public RetrospectiveComponent retrospectiveComponent;

    public RetrospectiveContainerPage(WebDriver driver){
        this.navbarComponent = new NavbarComponent(driver);
        this.retrospectiveComponent = new RetrospectiveComponent(driver);
        if(!this.isPageLoaded()){
            throw new IllegalStateException("RetrospectiveContainerPage not loaded properly");
        }
    }

    //tested
    public HomeContainerPage goToHome(){
        this.navbarComponent.clickHomeLink();
        return new HomeContainerPage(this.navbarComponent.getDriver());
    }

    //tested
    public MenuPage openMenu(){
        this.navbarComponent.clickOpenMenu();
        return new MenuPage(this.navbarComponent.getDriver());
    }

    //tested
    public RetrospectiveContainerPage renameBoard(BoardNames newBoardName){
        this.retrospectiveComponent.renameBoard(newBoardName.value());
        return this;
    }

    //tested
    public RetrospectiveContainerPage createWentWellPost(WentWellPosts wentWellPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()){
            this.retrospectiveComponent.typeWentWellPost(wentWellPost.value());
            return this;
        }else{
            throw new IllegalStateException("createWentWellPost: summary mode is on");
        }
    }

    //tested
    public RetrospectiveContainerPage createNotWentWellPost(NotWentWellPosts notWentWellPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()){
            this.retrospectiveComponent.typeNotWentWellPost(notWentWellPost.value());
            return this;
        }else{
            throw new IllegalStateException("createNotWentWellPost: summary mode is on");
        }
    }

    //tested
    public RetrospectiveContainerPage createIdeasPost(IdeasPosts ideasPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()){
            this.retrospectiveComponent.typeIdeasPost(ideasPost.value());
            return this;
        }else{
            throw new IllegalStateException("createIdeasPost: summary mode is on");
        }
    }

    //tested
    public RetrospectiveContainerPage deleteWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isWentWellPostPresent(id.value)
                && !this.retrospectiveComponent.isWentWellPostShared(id.value)){
            this.retrospectiveComponent.deleteWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("deleteWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage deleteNotWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isNotWentWellPostPresent(id.value)
                && !this.retrospectiveComponent.isNotWentWellPostShared(id.value)){
            this.retrospectiveComponent.deleteNotWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("deleteNotWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage deleteIdeasPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isIdeasPostPresent(id.value)
                && !this.retrospectiveComponent.isIdeasPostShared(id.value)){
            this.retrospectiveComponent.deleteIdeasPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("deleteIdeasPost: summary mode is on or post with id " + id.value + " is not present or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage changeWentWellPostContent(Id id, WentWellPosts wentWellPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isWentWellPostPresent(id.value)
                && !this.retrospectiveComponent.isWentWellPostShared(id.value)){
            this.retrospectiveComponent.changeContentOfWentWellPost(id.value, wentWellPost.value());
            return this;
        }else{
            throw new IllegalStateException("changeWentWellPostContent: summary mode is on or post with id " + id.value + " is not present or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage changeNotWentWellPostContent(Id id, NotWentWellPosts notWentWellPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isNotWentWellPostPresent(id.value)
                && !this.retrospectiveComponent.isNotWentWellPostShared(id.value)){
            this.retrospectiveComponent.changeContentOfNotWentWellPost(id.value, notWentWellPost.value());
            return this;
        }else{
            throw new IllegalStateException("changeNotWentWellPostContent: summary mode is on or post with id " + id.value + " is not present post or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage changeIdeasPostContent(Id id, IdeasPosts ideasPost){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isIdeasPostPresent(id.value)
                && !this.retrospectiveComponent.isIdeasPostShared(id.value)){
            this.retrospectiveComponent.changeContentOfIdeasPost(id.value, ideasPost.value());
            return this;
        }else{
            throw new IllegalStateException("changeIdeasPostContent: summary mode is on or post with id " + id.value + " is not present or it is shared");
        }
    }

    //tested
    public RetrospectiveContainerPage likeWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isWentWellPostPresent(id.value)
                && this.retrospectiveComponent.isWentWellPostShared(id.value)
                && this.retrospectiveComponent.isWentWellPostEnabled(id.value)){
            this.retrospectiveComponent.likeWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("likeWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    //tested
    public RetrospectiveContainerPage likeNotWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isNotWentWellPostPresent(id.value)
                && this.retrospectiveComponent.isNotWentWellPostShared(id.value)
                && this.retrospectiveComponent.isNotWentWellPostEnabled(id.value)){
            this.retrospectiveComponent.likeNotWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("likeNotWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    //tested
    public RetrospectiveContainerPage likeIdeasPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isIdeasPostPresent(id.value)
                && this.retrospectiveComponent.isIdeasPostShared(id.value)
                && this.retrospectiveComponent.isIdeasPostEnabled(id.value)){
            this.retrospectiveComponent.likeIdeasPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("likeIdeasPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    //tested
    public RetrospectiveContainerPage dislikeWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isWentWellPostPresent(id.value)
                && this.retrospectiveComponent.isWentWellPostShared(id.value)
                && this.retrospectiveComponent.isWentWellPostEnabled(id.value)){
            this.retrospectiveComponent.dislikeWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("dislikeWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    //tested
    public RetrospectiveContainerPage dislikeNotWentWellPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isNotWentWellPostPresent(id.value)
                && this.retrospectiveComponent.isNotWentWellPostShared(id.value)
                && this.retrospectiveComponent.isNotWentWellPostEnabled(id.value)){
            this.retrospectiveComponent.dislikeNotWentWellPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("dislikeNotWentWellPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    //tested
    public RetrospectiveContainerPage dislikeIdeasPost(Id id){
        if(!this.retrospectiveComponent.isSummaryModeOn()
                && this.retrospectiveComponent.isIdeasPostPresent(id.value)
                && this.retrospectiveComponent.isIdeasPostShared(id.value)
                && this.retrospectiveComponent.isIdeasPostEnabled(id.value)){
            this.retrospectiveComponent.dislikeIdeasPost(id.value);
            return this;
        }else{
            throw new IllegalStateException("dislikeIdeasPost: summary mode is on or post with id " + id.value + " is not present or it is not shared or it is not enabled");
        }
    }

    @Override
    public boolean isPageLoaded() {
        if(this.retrospectiveComponent.waitForElementBeingPresentOnPage(ConstantLocators.RETROSPECTIVE.value())){
            return true;
        }
        return false;
    }
}
