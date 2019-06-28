package custom_classes;

import po_utils.TestData;

public enum HTMLSnippets implements TestData {
    HOME ("<div class=\"uk-width-medium-3-4 uk-container-center\"><h3 class=\"uk-h1 uk-margin-large-bottom\">Uniting fresh design and clean code<br class=\"uk-hidden-small\"> to create beautiful websites.</h3><p class=\"uk-width-medium-4-6 uk-container-center\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.</p></div>"),
    IMAGE_TITLE ("<figure class=\"snip1585\"><img src=\"https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample70.jpg\" alt=\"sample70\" /><figcaption><h3>Ingredia <span>Nutrisha</span></h3></figcaption><a href=\"#\"></a></figure>"),
    IMAGE_TITLE_PRICE ("<figure class=\"snip1583\"><img src=\"https://s3-us-west-2.amazonaws.com/s.cdpn.io/331810/sample68.jpg\" alt=\"sample68\" /><div class=\"icons\"><a><i class=\"ion-android-cart\"></i></a><a><i class=\"ion-android-star\"></i></a><a><i class=\"ion-android-share-alt\"></i></a></div><figcaption><h3>Wisteria Ravenclaw</h3><div class=\"price\">$15.00</div></figcaption></figure>");

    private final String siteAddPage;

    HTMLSnippets(String siteAddPage){
        this.siteAddPage = siteAddPage;
    }

    public String value(){
        return this.siteAddPage;
    }
}
