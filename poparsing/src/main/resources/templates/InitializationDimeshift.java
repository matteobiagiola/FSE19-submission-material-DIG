po.shared.components.NavbarComponent navbarComponent = new po.shared.components.NavbarComponent(driver);
navbarComponent.goToRegisterPage();
po.home.pages.RegisterPage registerPage = new po.home.pages.RegisterPage(driver);
registerPage.register(Username.ASD, Email.ASD, Password.ASD);