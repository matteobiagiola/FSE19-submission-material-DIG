package main;

import org.openqa.selenium.WebDriver;
import po_utils.DriverProvider;
import custom_classes.*;

import po_utils.NotInTheRightPageObjectException;
import po_utils.NotTheRightInputValuesException;

public class ClassUnderTest {

	private Object currentPage = null;

	// BOOTSTRAP POINT
	public ClassUnderTest() {
		// start driver and browser
		WebDriver driver = new DriverProvider().getActiveDriver();
		po.signin.SignIn signIn = new po.signin.SignIn(driver);
		signIn.singIn(Username.ADMIN, UserPassword.ADMIN);
		this.currentPage = new po.dashboard.pages.DashboardContainerPage(driver);
	}

	// PO Name: SelectLinkPage
	public void cancelOperationSelectLinkPage() {
		if (this.currentPage instanceof po.shared.pages.modals.SelectLinkPage) {
			po.shared.pages.modals.SelectLinkPage page = (po.shared.pages.modals.SelectLinkPage) this.currentPage;
			if (page.poCallee.equals(page.userSettings)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
				this.currentPage = new po.users.pages.UserSettingsContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.addEditLink)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
				this.currentPage = new po.site.pages.AddEditLinkContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.addEditLogin)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("Unknown poCallee " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"cancelOperationSelectLinkPage: expected po.shared.pages.modals.SelectLinkPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectLinkPage
	public void selectLinkPageSelectLinkPage(
			custom_classes.Extension extension,
			custom_classes.SitePages sitePage) {
		if (this.currentPage instanceof po.shared.pages.modals.SelectLinkPage) {
			po.shared.pages.modals.SelectLinkPage page = (po.shared.pages.modals.SelectLinkPage) this.currentPage;
			if (((extension.value().equals("Page")) && (page
					.isOptionPagePresent()))
					&& (page.isOptionPresentInDropdown(
							org.openqa.selenium.By.id("form-link-page"),
							sitePage.value()))) {
				page.selectOptionInDropdown(
						org.openqa.selenium.By.id("form-link-page"),
						sitePage.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@type=\"submit\"]")));
				if (page.poCallee.equals(page.userSettings)) {
					this.currentPage = new po.users.pages.UserSettingsContainerPage(
							page.getDriver());
				} else if (page.poCallee.equals(page.addEditLink)) {
					boolean textExpected = false;
					java.lang.String text = "false";
					if (page.waitForElementThatChangesText(
							org.openqa.selenium.By
									.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"),
							textExpected, text)) {
						this.currentPage = new po.site.pages.AddEditLinkContainerPage(
								page.getDriver());
					} else {
						throw new NotTheRightInputValuesException(
								"update: text not handled properly");
					}
				} else if (page.poCallee.equals(page.addEditLogin)) {
					boolean textExpected = false;
					java.lang.String text = "false";
					if (page.waitForElementThatChangesText(
							page.locatorElementToWaitFor, textExpected, text)) {
						this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
								page.getDriver());
					} else {
						throw new NotTheRightInputValuesException(
								"update: text not handled properly");
					}
				} else {
					throw new NotTheRightInputValuesException(
							("selectLinkPage: unknown poCallee " + page.poCallee));
				}

			} else {
				throw new NotTheRightInputValuesException(
						(((("selectLinkPage: extension " + (extension.value())) + " is not Page or SitePage ") + (sitePage
								.value())) + " is not among the options or there are no options"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectLinkPageSelectLinkPage: expected po.shared.pages.modals.SelectLinkPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectLinkPage
	public void selectLinkStorageSelectLinkPage(
			custom_classes.Extension extension) {
		if (this.currentPage instanceof po.shared.pages.modals.SelectLinkPage) {
			po.shared.pages.modals.SelectLinkPage page = (po.shared.pages.modals.SelectLinkPage) this.currentPage;
			if (extension.value().equals("Storage")) {
				page.selectOptionInDropdown(
						org.openqa.selenium.By.id("form-style"),
						extension.value());
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-modal-dialog\"]//a[@class=\"pk-form-link-toggle pk-link-icon uk-flex-middle\"]"));
				java.lang.String currentPoCallee = "SelectLinkPage";
				this.currentPage = new po.shared.pages.modals.SelectImagePage(
						page.getDriver(), currentPoCallee, page.poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("selectLinkStorage: extension " + (extension.value())) + " is not Storage"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectLinkStorageSelectLinkPage: expected po.shared.pages.modals.SelectLinkPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectLinkPage
	public void selectLinkUserSelectLinkPage(
			custom_classes.Extension extension, custom_classes.View view) {
		if (this.currentPage instanceof po.shared.pages.modals.SelectLinkPage) {
			po.shared.pages.modals.SelectLinkPage page = (po.shared.pages.modals.SelectLinkPage) this.currentPage;
			if (extension.value().equals("User")) {
				page.selectOptionInDropdown(
						org.openqa.selenium.By.id("form-style"),
						extension.value());
				page.selectOptionInDropdown(
						org.openqa.selenium.By.id("form-link-user"),
						view.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@type=\"submit\"]")));
				if (page.poCallee.equals(page.userSettings)) {
					this.currentPage = new po.users.pages.UserSettingsContainerPage(
							page.getDriver());
				} else if (page.poCallee.equals(page.addEditLink)) {
					boolean textExpected = false;
					java.lang.String text = "false";
					if (page.waitForElementThatChangesText(
							org.openqa.selenium.By
									.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"),
							textExpected, text)) {
						this.currentPage = new po.site.pages.AddEditLinkContainerPage(
								page.getDriver());
					} else {
						throw new NotTheRightInputValuesException(
								"update: text not handled properly");
					}
				} else if (page.poCallee.equals(page.addEditLogin)) {
					boolean textExpected = false;
					java.lang.String text = "false";
					if (page.waitForElementThatChangesText(
							page.locatorElementToWaitFor, textExpected, text)) {
						this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
								page.getDriver());
					} else {
						throw new NotTheRightInputValuesException(
								"update: text not handled properly");
					}
				} else {
					throw new NotTheRightInputValuesException(
							("selectLinkUser: unknown poCallee " + page.poCallee));
				}

			} else {
				throw new NotTheRightInputValuesException(
						(("selectLinkUser: extension " + (extension.value())) + " is not User"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectLinkUserSelectLinkPage: expected po.shared.pages.modals.SelectLinkPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectLinkPage
	public void updateSelectLinkPage() {
		if (this.currentPage instanceof po.shared.pages.modals.SelectLinkPage) {
			po.shared.pages.modals.SelectLinkPage page = (po.shared.pages.modals.SelectLinkPage) this.currentPage;
			if (page.poCallee.equals(page.userSettings)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@type=\"submit\"]")));
				this.currentPage = new po.users.pages.UserSettingsContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.addEditLink)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@type=\"submit\"]")));
				boolean textExpected = false;
				java.lang.String text = "false";
				if (page.waitForElementThatChangesText(
						org.openqa.selenium.By
								.xpath("//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"]"),
						textExpected, text)) {
					this.currentPage = new po.site.pages.AddEditLinkContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"update: text not handled properly");
				}
			} else if (page.poCallee.equals(page.addEditLogin)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.prefix + "/button[@type=\"submit\"]")));
				boolean textExpected = false;
				java.lang.String text = "false";
				if (page.waitForElementThatChangesText(
						page.locatorElementToWaitFor, textExpected, text)) {
					this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"update: text not handled properly");
				}
			} else {
				throw new NotTheRightInputValuesException(
						("update: unknown poCallee " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"updateSelectLinkPage: expected po.shared.pages.modals.SelectLinkPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteItemPage
	public void cancelOperationDeleteItemPage() {
		if (this.currentPage instanceof po.shared.pages.modals.DeleteItemPage) {
			po.shared.pages.modals.DeleteItemPage page = (po.shared.pages.modals.DeleteItemPage) this.currentPage;
			if (page.poCallee.equals(page.dashboard)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.userList)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.users.pages.UserListContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.roles)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.users.pages.RolesContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.pages)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("Unknown PO callee name: " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"cancelOperationDeleteItemPage: expected po.shared.pages.modals.DeleteItemPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DeleteItemPage
	public void confirmOperationDeleteItemPage() {
		if (this.currentPage instanceof po.shared.pages.modals.DeleteItemPage) {
			po.shared.pages.modals.DeleteItemPage page = (po.shared.pages.modals.DeleteItemPage) this.currentPage;
			if (page.poCallee.equals(page.dashboard)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.getDriver());
			} else if ((page.poCallee.equals(page.userList))
					&& (page.expectingFailure.equals("default"))) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.users.pages.UserListContainerPage(
						page.getDriver());
			} else if ((page.poCallee.equals(page.userList))
					&& (page.expectingFailure.equals("Delete admin user"))) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
				if (page.waitForElementBeingPresentOnPage(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po.users.pages.UserListContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"ConfirmOperation: unable to delete yourself message not loaded properly");
				}
			} else if (page.poCallee.equals(page.roles)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.users.pages.RolesContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.pages)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//button[@class=\"uk-button uk-button-link js-modal-confirm\"]"));
				page.waitForTimeoutExpires(500);
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("Unknown PO callee name: " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"confirmOperationDeleteItemPage: expected po.shared.pages.modals.DeleteItemPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditItemPage
	public void addItemAddEditItemPage() {
		if (this.currentPage instanceof po.shared.pages.modals.AddEditItemPage) {
			po.shared.pages.modals.AddEditItemPage page = (po.shared.pages.modals.AddEditItemPage) this.currentPage;
			if ((page.poCallee.equals(page.roles))
					&& (page.expectingFailure.equals("Role already exists"))) {
				page.type(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//input")),
						page.formValueInput.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
				if (page.waitForElementBeingPresentOnPage(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po.users.pages.RolesContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"addItem: unable to add role message not loaded properly");
				}
			} else if ((page.poCallee.equals(page.roles))
					&& (page.expectingFailure.equals("default"))) {
				page.type(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//input")),
						page.formValueInput.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
				if ((page
						.waitForElementBeingPresentOnPage(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))
						&& (page.waitForElementBeingInvisibleOnPage(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message\"]")))) {
					this.currentPage = new po.users.pages.RolesContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"addItem: roles notification not handled properly");
				}
			} else if ((page.poCallee.equals(page.pages))
					&& (page.expectingFailure.equals("Menu already exists"))) {
				page.type(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//input")),
						page.formValueInput.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
				if (page.waitForElementBeingPresentOnPage(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po.site.pages.PagesContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"addItem: page notification not handled properly");
				}
			} else if ((page.poCallee.equals(page.pages))
					&& (page.expectingFailure.equals("default"))) {
				page.type(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//input")),
						page.formValueInput.value());
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link\"]")));
				if (page.waitForElementBeingPresentOnPage(org.openqa.selenium.By
						.xpath((("//ul[@class=\"uk-nav uk-nav-side\"]/li/a[text()=\"" + (page.formValueInput
								.value())) + "\"]")))) {
					this.currentPage = new po.site.pages.PagesContainerPage(
							page.getDriver());
				} else {
					throw new NotTheRightInputValuesException(
							"addItem: page notification not handled properly");
				}
			} else {
				throw new NotTheRightInputValuesException(
						("Unknown PO callee name: " + page.poCallee));
			}

		} else {
			throw new NotInTheRightPageObjectException(
					"addItemAddEditItemPage: expected po.shared.pages.modals.AddEditItemPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditItemPage
	public void cancelOperationAddEditItemPage() {
		if (this.currentPage instanceof po.shared.pages.modals.AddEditItemPage) {
			po.shared.pages.modals.AddEditItemPage page = (po.shared.pages.modals.AddEditItemPage) this.currentPage;
			if (page.poCallee.equals(page.roles)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
				this.currentPage = new po.users.pages.RolesContainerPage(
						page.getDriver());
			} else if (page.poCallee.equals(page.pages)) {
				page.clickOn(org.openqa.selenium.By
						.xpath((page.xpathPrefix + "//button[@class=\"uk-button uk-button-link uk-modal-close\"]")));
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						("Unknown PO callee name: " + page.poCallee));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelOperationAddEditItemPage: expected po.shared.pages.modals.AddEditItemPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectImagePage
	public void cancelOperationSelectImagePage() {
		if (this.currentPage instanceof po.shared.pages.modals.SelectImagePage) {
			po.shared.pages.modals.SelectImagePage page = (po.shared.pages.modals.SelectImagePage) this.currentPage;
			if ((page.poCallee.equals(page.linkPage))
					&& (!(page.previousPoCallee.equals("default")))) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-modal-dialog uk-modal-dialog-large\"]//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.shared.pages.modals.SelectLinkPage(
						page.getDriver(), page.previousPoCallee);
			} else if (page.poCallee.equals(page.addEditPage)) {
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-modal-dialog uk-modal-dialog-large\"]//button[@class=\"uk-button uk-button-link uk-modal-close\"]"));
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((("selectFile: unknown poCallee " + page.poCallee) + " or previousCallee is default ") + page.previousPoCallee));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"cancelOperationSelectImagePage: expected po.shared.pages.modals.SelectImagePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: SelectImagePage
	public void selectFileSelectImagePage(custom_classes.FileNames fileName) {
		if (this.currentPage instanceof po.shared.pages.modals.SelectImagePage) {
			po.shared.pages.modals.SelectImagePage page = (po.shared.pages.modals.SelectImagePage) this.currentPage;
			if ((page.poCallee.equals(page.linkPage))
					&& (!(page.previousPoCallee.equals("default")))) {
				java.util.List<org.openqa.selenium.WebElement> filesInTable = page
						.findElements(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-modal uk-open\"]//div[@class=\"uk-form\"]//table/tbody/tr"));
				org.openqa.selenium.WebElement inputCheckbox = page
						.getIndexMatchingName(filesInTable, fileName);
				page.clickOn(inputCheckbox);
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-modal uk-open\"]//button[@class=\"uk-button uk-button-primary\" and @type=\"button\"]"));
				this.currentPage = new po.shared.pages.modals.SelectLinkPage(
						page.getDriver(), page.previousPoCallee);
			} else if (page.poCallee.equals(page.addEditPage)) {
				java.util.List<org.openqa.selenium.WebElement> filesInTable = page
						.findElements(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-modal uk-open\"]//div[@class=\"uk-form\"]//table/tbody/tr"));
				org.openqa.selenium.WebElement inputCheckbox = page
						.getIndexMatchingName(filesInTable, fileName);
				page.clickOn(inputCheckbox);
				page.clickOn(org.openqa.selenium.By
						.xpath("//div[@class=\"uk-modal uk-open\"]//button[@class=\"uk-button uk-button-primary\" and @type=\"button\"]"));
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						((("selectFile: unknown poCallee " + page.poCallee) + " or previousCallee is default ") + page.previousPoCallee));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectFileSelectImagePage: expected po.shared.pages.modals.SelectImagePage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void addFeedWidgetDashboardContainerPage(
			custom_classes.WidgetFeedTitle widgetFeedTitle,
			custom_classes.WidgetFeedUrl widgetFeedUrl,
			custom_classes.WidgetFeedNumberOfPosts widgetFeedNumberOfPosts,
			custom_classes.WidgetFeedPostContent widgetFeedPostContent) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(Widgets.FEED);
			page.widgetComponent.addEditFeedWidget(widgetFeedTitle,
					widgetFeedUrl, widgetFeedNumberOfPosts,
					widgetFeedPostContent);
			this.currentPage = new po.dashboard.pages.DashboardContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addFeedWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void addLocationWidgetDashboardContainerPage(
			custom_classes.WidgetLocation widgetLocation,
			custom_classes.WidgetUnit widgetUnit) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(Widgets.LOCATION);
			page.widgetComponent.addEditLocationWidget(widgetLocation,
					widgetUnit);
			this.currentPage = new po.dashboard.pages.DashboardContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addLocationWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void addUserWidgetDashboardContainerPage(
			custom_classes.WidgetUserType widgetUserType,
			custom_classes.WidgetUserDisplay widgetUserDisplay,
			custom_classes.WidgetTotalUser widgetTotalUser,
			custom_classes.WidgetNumberOfUsers widgetNumberOfUsers) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(Widgets.USER);
			page.widgetComponent.addEditUserWidget(widgetUserType,
					widgetUserDisplay, widgetTotalUser, widgetNumberOfUsers);
			this.currentPage = new po.dashboard.pages.DashboardContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addUserWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void deleteLocationWidgetDashboardContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if ((((id.value) - 1) < (widgetsOnPage.size()))
					&& (page.widgetComponent.isLocationWidget(widgetsOnPage
							.get((id.value - 1))))) {
				page.widgetComponent.clickOnEditWidget(widgetsOnPage
						.get((id.value - 1)));
				page.widgetComponent.deleteWidget(widgetsOnPage
						.get((id.value - 1)));
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a widget"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteLocationWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void deleteUserOrFeedWidgetDashboardContainerPage(
			custom_classes.Id id) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if ((((id.value) - 1) < (widgetsOnPage.size()))
					&& (!(page.widgetComponent.isLocationWidget(widgetsOnPage
							.get((id.value - 1)))))) {
				page.widgetComponent.clickOnEditWidget(widgetsOnPage
						.get((id.value - 1)));
				page.widgetComponent.deleteWidget(widgetsOnPage
						.get((id.value - 1)));
				java.lang.String poCallee = "DashboardContainerPage";
				this.currentPage = new po.shared.pages.modals.DeleteItemPage(
						page.widgetComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a widget"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteUserOrFeedWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void editFeedWidgetDashboardContainerPage(custom_classes.Id id,
			custom_classes.WidgetFeedTitle widgetFeedTitle,
			custom_classes.WidgetFeedUrl widgetFeedUrl,
			custom_classes.WidgetFeedNumberOfPosts widgetFeedNumberOfPosts,
			custom_classes.WidgetFeedPostContent widgetFeedPostContent) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.widgetComponent
					.isFeedWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.widgetComponent.isWidgetFormOpen(widgetsOnPage
							.get((id.value - 1)))))) {
				page.widgetComponent.clickOnEditWidget(widgetsOnPage
						.get((id.value - 1)));
				page.widgetComponent.addEditFeedWidget(widgetFeedTitle,
						widgetFeedUrl, widgetFeedNumberOfPosts,
						widgetFeedPostContent);
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a feed widget or the widget form is open"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editFeedWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void editLocationWidgetDashboardContainerPage(custom_classes.Id id,
			custom_classes.WidgetLocation widgetLocation,
			custom_classes.WidgetUnit widgetUnit) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.widgetComponent
					.isLocationWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.widgetComponent.isWidgetFormOpen(widgetsOnPage
							.get((id.value - 1)))))) {
				page.widgetComponent.clickOnEditWidget(widgetsOnPage
						.get((id.value - 1)));
				page.widgetComponent.addEditLocationWidget(widgetLocation,
						widgetUnit);
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a location widget or the widget form is open"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editLocationWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void editUserWidgetDashboardContainerPage(custom_classes.Id id,
			custom_classes.WidgetUserType widgetUserType,
			custom_classes.WidgetUserDisplay widgetUserDisplay,
			custom_classes.WidgetTotalUser widgetTotalUser,
			custom_classes.WidgetNumberOfUsers widgetNumberOfUsers) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if (((((id.value) - 1) < (widgetsOnPage.size())) && (page.widgetComponent
					.isUserWidget(widgetsOnPage.get((id.value - 1)))))
					&& (!(page.widgetComponent.isWidgetFormOpen(widgetsOnPage
							.get((id.value - 1)))))) {
				page.widgetComponent.clickOnEditWidget(widgetsOnPage
						.get((id.value - 1)));
				page.widgetComponent
						.addEditUserWidget(widgetUserType, widgetUserDisplay,
								widgetTotalUser, widgetNumberOfUsers);
				this.currentPage = new po.dashboard.pages.DashboardContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a user widget or the widget form is open"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editUserWidgetDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void goToDashboardDashboardContainerPage() {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void goToEditCurrentUserDashboardContainerPage() {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void goToEditUserDashboardContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> widgetsOnPage = page.widgetsListComponent
					.getWidgetsOnPage();
			if ((((id.value) - 1) < (widgetsOnPage.size()))
					&& (page.widgetComponent.isUserWidget(widgetsOnPage
							.get((id.value - 1))))) {
				page.widgetComponent.clickOnEditUserLink(widgetsOnPage
						.get((id.value - 1)));
				this.currentPage = new po.users.pages.AddEditUserContainerPage(
						page.widgetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("WebElement with id " + id.value) + " is not a user widget"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditUserDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void goToSiteDashboardContainerPage() {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: DashboardContainerPage
	public void goToUsersDashboardContainerPage() {
		if (this.currentPage instanceof po.dashboard.pages.DashboardContainerPage) {
			po.dashboard.pages.DashboardContainerPage page = (po.dashboard.pages.DashboardContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersDashboardContainerPage: expected po.dashboard.pages.DashboardContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void activateUserUserListContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> users = page.userListComponent
					.getUsersInList();
			if ((((id.value) - 1) < (users.size()))
					&& (!(page.userListComponent.isUserSelected(users
							.get((id.value - 1)))))) {
				page.userListComponent.activateUser(users.get((id.value - 1)));
				this.currentPage = new po.users.pages.UserListContainerPage(
						page.userListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("User with id " + id.value) + " is not a valid user"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"activateUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void addUserUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			page.userListComponent.clickOn(org.openqa.selenium.By
					.xpath("//a[@class=\"uk-button uk-button-primary\"]"));
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.userListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void blockUserUserListContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> users = page.userListComponent
					.getUsersInList();
			if ((((id.value) - 1) < (users.size()))
					&& (!(page.userListComponent.isUserSelected(users
							.get((id.value - 1)))))) {
				page.userListComponent.blockUser(users.get((id.value - 1)));
				if (page.userListComponent
						.waitForElementBeingPresentOnPage(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po.users.pages.UserListContainerPage(
							page.userListComponent.getDriver());
				} else {
					this.currentPage = new po.users.pages.UserListContainerPage(
							page.userListComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						(("User with id " + id.value) + " is not a valid user"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"blockUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void deleteAdminUserUserListContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> users = page.userListComponent
					.getUsersInList();
			if (((((id.value) - 1) < (users.size())) && (!(page.userListComponent
					.isUserSelected(users.get((id.value - 1))))))
					&& (page.userListComponent.isAdminUser(users
							.get((id.value - 1))))) {
				page.userListComponent.deleteUser(users.get((id.value - 1)));
				java.lang.String poCallee = "UserListContainerPage";
				java.lang.String expectingFailure = "Delete admin user";
				this.currentPage = new po.shared.pages.modals.DeleteItemPage(
						page.userListComponent.getDriver(), poCallee,
						expectingFailure);
			} else {
				throw new NotTheRightInputValuesException(
						(("User with id " + id.value) + " is not a valid user"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteAdminUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void deleteUserUserListContainerPage(custom_classes.Id id) {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			java.util.List<org.openqa.selenium.WebElement> users = page.userListComponent
					.getUsersInList();
			if ((((id.value) - 1) < (users.size()))
					&& (!(page.userListComponent.isUserSelected(users
							.get((id.value - 1)))))) {
				page.userListComponent.deleteUser(users.get((id.value - 1)));
				java.lang.String poCallee = "UserListContainerPage";
				this.currentPage = new po.shared.pages.modals.DeleteItemPage(
						page.userListComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("User with id " + id.value) + " is not a valid user"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToDashboardUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToEditCurrentUserUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToPermissionsUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToRolesUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToSettingsUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.USER_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToSiteUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToUserListUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.LIST);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserListUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserListContainerPage
	public void goToUsersUserListContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserListContainerPage) {
			po.users.pages.UserListContainerPage page = (po.users.pages.UserListContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersUserListContainerPage: expected po.users.pages.UserListContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void addExistingUserRoleRolesContainerPage(
			custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if (page.rolesSidebarComponent.isRolePresent(userRole)) {
				java.lang.String poCallee = "RolesContainerPage";
				java.lang.String expectingFailure = "Role already exists";
				page.rolesSidebarComponent.addRole();
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.rolesSidebarComponent.getDriver(), poCallee,
						userRole, expectingFailure);
			} else {
				throw new NotTheRightInputValuesException(
						(("addExistingUserRole: user role " + (userRole.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addExistingUserRoleRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void addUserRoleRolesContainerPage(custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if (!(page.rolesSidebarComponent.isRolePresent(userRole))) {
				page.rolesSidebarComponent.addRole();
				java.lang.String poCallee = "RolesContainerPage";
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.rolesSidebarComponent.getDriver(), poCallee,
						userRole);
			} else {
				throw new NotTheRightInputValuesException(
						(("addUserRole: user role " + (userRole.value())) + " already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addUserRoleRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void deleteUserRoleRolesContainerPage(
			custom_classes.UserRoles userRoleToDelete) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if ((page.rolesSidebarComponent.isRolePresent(userRoleToDelete))
					&& (page.rolesSidebarComponent
							.isRoleEditable(userRoleToDelete))) {
				page.rolesSidebarComponent.deleteRole(userRoleToDelete);
				java.lang.String poCallee = "RolesContainerPage";
				this.currentPage = new po.shared.pages.modals.DeleteItemPage(
						page.rolesSidebarComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteUserRole: user role " + (userRoleToDelete
								.value())) + " is not present or not editable"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteUserRoleRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void editUserRoleRolesContainerPage(
			custom_classes.UserRoles userRoleToEdit,
			custom_classes.UserRoles newUserRole) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if ((page.rolesSidebarComponent.isRolePresent(userRoleToEdit))
					&& (page.rolesSidebarComponent
							.isRoleEditable(userRoleToEdit))) {
				page.rolesSidebarComponent.editRole(userRoleToEdit);
				java.lang.String poCallee = "RolesContainerPage";
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.rolesMainComponent.getDriver(), poCallee,
						newUserRole);
			} else {
				throw new NotTheRightInputValuesException(
						(("editUserRole: user role " + (userRoleToEdit.value())) + " is not editable or it does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editUserRoleRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void editUserRoleWithExistingOneRolesContainerPage(
			custom_classes.UserRoles userRoleToEdit,
			custom_classes.UserRoles existingUserRole) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if (((page.rolesSidebarComponent.isRolePresent(userRoleToEdit)) && (page.rolesSidebarComponent
					.isRoleEditable(userRoleToEdit)))
					&& (page.rolesSidebarComponent
							.isRolePresent(existingUserRole))) {
				page.rolesSidebarComponent.editRole(userRoleToEdit);
				java.lang.String poCallee = "RolesContainerPage";
				java.lang.String expectingFailure = "Role already exists";
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.rolesMainComponent.getDriver(), poCallee,
						existingUserRole, expectingFailure);
			} else {
				throw new NotTheRightInputValuesException(
						(((("editUserRole: user role " + (userRoleToEdit
								.value())) + " is not editable or it does not exist or new user role ") + (existingUserRole
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editUserRoleWithExistingOneRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void giveOrRemoveAllPermissionsToUserRoleRolesContainerPage(
			custom_classes.UserRoles userRoles) {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			if ((page.rolesSidebarComponent.isRolePresent(userRoles))
					&& (!(page.rolesSidebarComponent.isRoleAdmin(userRoles)))) {
				page.rolesSidebarComponent.clickOnRole(userRoles);
				page.rolesMainComponent.giveOrRemoveAllPermissionsToUserRole();
				this.currentPage = new po.users.pages.RolesContainerPage(
						page.rolesSidebarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("giveOrRemoveAllPermissionsToUserRole: user role " + (userRoles
								.value())) + " is not present or it is an admin role"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"giveOrRemoveAllPermissionsToUserRoleRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToDashboardRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToEditCurrentUserRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToPermissionsRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToRolesRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToSettingsRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.USER_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToSiteRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToUserListRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.LIST);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserListRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: RolesContainerPage
	public void goToUsersRolesContainerPage() {
		if (this.currentPage instanceof po.users.pages.RolesContainerPage) {
			po.users.pages.RolesContainerPage page = (po.users.pages.RolesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersRolesContainerPage: expected po.users.pages.RolesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void addUserAddEditUserContainerPage(
			custom_classes.Username username, custom_classes.Name name,
			custom_classes.Email email,
			custom_classes.UserPassword userPassword,
			custom_classes.UserStatus userStatus) {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			if (!(page.addEditUserComponent.isEditUser())) {
				page.addEditUserComponent.addUser(username, name, email,
						userPassword, userStatus);
				if (page.addEditUserComponent
						.waitForElementBeingPresentOnPage(org.openqa.selenium.By
								.xpath("//div[@class=\"uk-notify uk-notify-top-center\"]/div[@class=\"uk-notify-message uk-notify-message-danger\"]"))) {
					this.currentPage = new po.users.pages.AddEditUserContainerPage(
							page.addEditUserComponent.getDriver());
				} else {
					this.currentPage = new po.users.pages.AddEditUserContainerPage(
							page.addEditUserComponent.getDriver());
				}
			} else {
				throw new NotTheRightInputValuesException(
						"addUser: you are in edit user mode");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addUserAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void closeOperationAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			page.addEditUserComponent.closeOperation();
			this.currentPage = new po.users.pages.UserListContainerPage(
					page.addEditUserComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"closeOperationAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void editUserAddEditUserContainerPage(
			custom_classes.Username username, custom_classes.Name name,
			custom_classes.Email email, custom_classes.UserPassword userPassword) {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			if (page.addEditUserComponent.isEditUser()) {
				page.addEditUserComponent.editUser(username, name, email,
						userPassword);
				this.currentPage = new po.users.pages.AddEditUserContainerPage(
						page.addEditUserComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"editUser: you are not in edit user mode");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editUserAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToDashboardAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToEditCurrentUserAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToPermissionsAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToRolesAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToSettingsAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.USER_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToSiteAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToUserListAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.LIST);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserListAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditUserContainerPage
	public void goToUsersAddEditUserContainerPage() {
		if (this.currentPage instanceof po.users.pages.AddEditUserContainerPage) {
			po.users.pages.AddEditUserContainerPage page = (po.users.pages.AddEditUserContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditUserContainerPage: expected po.users.pages.AddEditUserContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void giveOrRemoveAllPermissionsToUserRolePermissionsContainerPage(
			custom_classes.UserRoles userRoles) {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			if ((page.permissionsComponent.getUserRoles().contains(userRoles
					.value()))
					&& (!(page.permissionsComponent
							.isRoleAdministrator(userRoles)))) {
				page.permissionsComponent
						.giveOrRemoveAllPermissionsToUserRole(userRoles);
				this.currentPage = new po.users.pages.PermissionsContainerPage(
						page.permissionsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("giveOrRemoveAllPermissionsToUserRole: user role " + (userRoles
								.value())) + " does not exist or it is an administrator role."));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"giveOrRemoveAllPermissionsToUserRolePermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToDashboardPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToEditCurrentUserPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToPermissionsPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToRolesPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToSettingsPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.USER_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToSitePermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSitePermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToUserListPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.LIST);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserListPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PermissionsContainerPage
	public void goToUsersPermissionsContainerPage() {
		if (this.currentPage instanceof po.users.pages.PermissionsContainerPage) {
			po.users.pages.PermissionsContainerPage page = (po.users.pages.PermissionsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersPermissionsContainerPage: expected po.users.pages.PermissionsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void changeSettingsUserSettingsContainerPage(
			custom_classes.RegistrationUserSettings registrationUserSettings) {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			page.userSettingsComponent.changeSettings(registrationUserSettings);
			java.lang.String poCallee = "UserSettingsContainerPage";
			this.currentPage = new po.shared.pages.modals.SelectLinkPage(
					page.userSettingsComponent.getDriver(), poCallee);
		} else {
			throw new NotInTheRightPageObjectException(
					"changeSettingsUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToDashboardUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToEditCurrentUserUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToPermissionsUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPermissionsUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToRolesUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.PERMISSIONS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToRolesUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToSettingsUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent
					.goTo(NavbarActions.USER_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToSiteUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToUserListUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.LIST);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUserListUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void goToUsersUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: UserSettingsContainerPage
	public void saveSettingsUserSettingsContainerPage() {
		if (this.currentPage instanceof po.users.pages.UserSettingsContainerPage) {
			po.users.pages.UserSettingsContainerPage page = (po.users.pages.UserSettingsContainerPage) this.currentPage;
			page.userSettingsComponent
					.clickOn(org.openqa.selenium.By
							.xpath("//div[@id=\"settings\"]//button[@class=\"uk-button uk-button-primary\"]"));
			this.currentPage = new po.users.pages.UserSettingsContainerPage(
					page.userSettingsComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"saveSettingsUserSettingsContainerPage: expected po.users.pages.UserSettingsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void addExistingMenuPagesContainerPage(
			custom_classes.SiteMenus siteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if ((page.pagesSidebarComponent.isMenuPresent(siteMenu))
					&& (!(page.pagesSidebarComponent.isSpecialMenu(siteMenu)))) {
				page.pagesSidebarComponent.addMenu();
				java.lang.String poCallee = page.getClass().getSimpleName();
				java.lang.String expectingFailure = "Menu already exists";
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.pagesSidebarComponent.getDriver(), poCallee,
						siteMenu, expectingFailure);
			} else {
				throw new NotTheRightInputValuesException(
						(("addExistingMenu: menu " + (siteMenu.value())) + " is not present or it is a special menu."));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addExistingMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void addLinkPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (!(page.pagesSidebarComponent.isTrashMenuActive())) {
				page.pagesListComponent.addLinkOrPage(SiteAddPage.LINK);
				this.currentPage = new po.site.pages.AddEditLinkContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addLink: trash menu active, not possible to add page");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addLinkPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void addMenuPagesContainerPage(custom_classes.SiteMenus siteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if ((!(page.pagesSidebarComponent.isMenuPresent(siteMenu)))
					&& (!(page.pagesSidebarComponent.isSpecialMenu(siteMenu)))) {
				page.pagesSidebarComponent.addMenu();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.pagesSidebarComponent.getDriver(), poCallee,
						siteMenu);
			} else {
				throw new NotTheRightInputValuesException(
						(("addMenu: menu " + (siteMenu.value())) + " is present or it is a special menu."));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void addPagePagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (!(page.pagesSidebarComponent.isTrashMenuActive())) {
				page.pagesListComponent.addLinkOrPage(SiteAddPage.PAGE);
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addPage: trash menu active, not possible to add page");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addPagePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void deleteAllPagesAndLinksPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isOneElementPresent()) {
				page.pagesListComponent.selectAll();
				page.pagesListComponent.delete();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"deleteAllPagesAndLinks: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteAllPagesAndLinksPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void deleteLinkOrPagePagesContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage)) {
				page.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
				page.pagesListComponent.delete();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteLinkOrPage: siteLinkOrPage " + (siteLinkOrPage
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteLinkOrPagePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void editLinkPagesContainerPage(custom_classes.SiteLinks siteLink) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isLinkOrPagePresent(siteLink)) {
				page.pagesListComponent.editLinkOrPage(siteLink);
				this.currentPage = new po.site.pages.AddEditLinkContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editLink: siteLink " + (siteLink.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editLinkPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void editMenuPagesContainerPage(custom_classes.SiteMenus siteMenu,
			custom_classes.SiteMenus newNameSiteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if ((((page.pagesSidebarComponent.isMenuPresent(siteMenu)) && (page.pagesSidebarComponent
					.isMenuEditable(siteMenu))) && (!(page.pagesSidebarComponent
					.isSpecialMenu(newNameSiteMenu))))
					&& (!(page.pagesSidebarComponent
							.isMenuPresent(newNameSiteMenu)))) {
				page.pagesSidebarComponent.editMenu(siteMenu);
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.pagesSidebarComponent.getDriver(), poCallee,
						newNameSiteMenu);
			} else {
				throw new NotTheRightInputValuesException(
						(((("editMenu: menu " + (siteMenu.value())) + " not present or not editable. Or new menu ") + (newNameSiteMenu
								.value())) + " is a special menu or it already exists"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void editMenuWithExistingMenuPagesContainerPage(
			custom_classes.SiteMenus siteMenu,
			custom_classes.SiteMenus newNameSiteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (((((page.pagesSidebarComponent.isMenuPresent(siteMenu)) && (page.pagesSidebarComponent
					.isMenuEditable(siteMenu))) && (page.pagesSidebarComponent
					.isMenuPresent(newNameSiteMenu))) && (!(page.pagesSidebarComponent
					.isSpecialMenu(newNameSiteMenu))))
					&& (!(siteMenu.value().equals(newNameSiteMenu.value())))) {
				page.pagesSidebarComponent.editMenu(siteMenu);
				java.lang.String poCallee = page.getClass().getSimpleName();
				java.lang.String expectingFailure = "Menu already exists";
				this.currentPage = new po.shared.pages.modals.AddEditItemPage(
						page.pagesSidebarComponent.getDriver(), poCallee,
						newNameSiteMenu, expectingFailure);
			} else {
				throw new NotTheRightInputValuesException(
						(((("editMenuWithExistingMenu: menu " + (siteMenu
								.value())) + " not present or not editable. Or new menu ") + (newNameSiteMenu
								.value())) + " does not exist or it is a special menu. Or the two menus are the same"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editMenuWithExistingMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void editPagePagesContainerPage(custom_classes.SitePages sitePage) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isLinkOrPagePresent(sitePage)) {
				page.pagesListComponent.editLinkOrPage(sitePage);
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editPage: sitePage " + (sitePage.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editPagePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToDashboardPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToEditCurrentUserPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToMenuPagesContainerPage(custom_classes.SiteMenus siteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesSidebarComponent.isMenuPresent(siteMenu)) {
				page.pagesSidebarComponent.clickOnLink(siteMenu);
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesSidebarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToMenu: siteMenu " + (siteMenu.value())) + " not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToPagesPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToSitePagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSitePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToUsersPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void goToWidgetsPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void moveAllPagesAndLinksToMenuPagesContainerPage(
			custom_classes.SiteMenus siteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (((page.pagesSidebarComponent.isMenuPresent(siteMenu)) && (page.pagesListComponent
					.isOneElementPresent()))
					&& (!(page.pagesSidebarComponent.isTrashMenu(siteMenu)))) {
				page.pagesListComponent.selectAll();
				page.pagesListComponent.move(siteMenu);
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesSidebarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("moveAllPagesAndLinksToMenu: siteMenu " + (siteMenu
								.value())) + " does not exist or there are no links nor pages"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"moveAllPagesAndLinksToMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void moveLinkOrPageToMenuPagesContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage,
			custom_classes.SiteMenus siteMenu) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (((page.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage)) && (page.pagesSidebarComponent
					.isMenuPresent(siteMenu)))
					&& (!(page.pagesSidebarComponent.isTrashMenu(siteMenu)))) {
				page.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
				page.pagesListComponent.move(siteMenu);
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("moveLinkOrPageToMenu: siteLinkOrPage " + (siteLinkOrPage
								.value())) + " does not exist or siteMenu ") + (siteMenu
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"moveLinkOrPageToMenuPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void publishAllPagesAndLinksPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isOneElementPresent()) {
				page.pagesListComponent.selectAll();
				page.pagesListComponent.publish();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"publishAllPagesAndLinks: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"publishAllPagesAndLinksPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void publishLinkOrPagePagesContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if ((page.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage))
					&& (!(page.pagesListComponent
							.isStatusActive(siteLinkOrPage)))) {
				page.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
				page.pagesListComponent.publish();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("publishLinkOrPage: siteLinkOrPage " + (siteLinkOrPage
								.value())) + " does not exist or it is active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"publishLinkOrPagePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void unpublishAllPagesAndLinksPagesContainerPage() {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if (page.pagesListComponent.isOneElementPresent()) {
				page.pagesListComponent.selectAll();
				page.pagesListComponent.unpublish();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"unpublishAllPagesAndLinks: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"unpublishAllPagesAndLinksPagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: PagesContainerPage
	public void unpublishLinkOrPagePagesContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage) {
		if (this.currentPage instanceof po.site.pages.PagesContainerPage) {
			po.site.pages.PagesContainerPage page = (po.site.pages.PagesContainerPage) this.currentPage;
			if ((page.pagesListComponent.isLinkOrPagePresent(siteLinkOrPage))
					&& (page.pagesListComponent.isStatusActive(siteLinkOrPage))) {
				page.pagesListComponent.selectLinkOrPage(siteLinkOrPage);
				page.pagesListComponent.unpublish();
				this.currentPage = new po.site.pages.PagesContainerPage(
						page.pagesListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("unpublishLinkOrPage: siteLinkOrPage " + (siteLinkOrPage
								.value())) + " does not exist or it is not active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"unpublishLinkOrPagePagesContainerPage: expected po.site.pages.PagesContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void addEditMetaAddEditPageContainerPage(
			custom_classes.SitePages sitePage,
			custom_classes.MetaDescriptions metaDescription) {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			if (page.addEditNavbarComponent.isNavbarItemActive("Meta")) {
				page.metaComponent.typeTitle(sitePage.value());
				page.metaComponent.typeDescription(metaDescription.value());
				page.metaComponent.selectImage();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.modals.SelectImagePage(
						page.menuComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						"addEditMeta: navbar action active is not meta");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditMetaAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void addEditPageAddEditPageContainerPage(
			custom_classes.SitePages sitePage,
			custom_classes.HTMLSnippets htmlSnippet,
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.HideInMenu hideInMenu) {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			if (page.addEditNavbarComponent.isNavbarItemActive("Content")) {
				page.HTMLFormComponent.enterTitle(sitePage.value());
				page.HTMLFormComponent.writeIntoTextarea(htmlSnippet.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addEditPage: navbar action active is not page");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditPageAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void addEditPageRestrictAccessAddEditPageContainerPage(
			custom_classes.SitePages sitePage,
			custom_classes.HTMLSnippets htmlSnippet,
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.UserRoles userRole,
			custom_classes.HideInMenu hideInMenu) {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Content"))
					&& (page.pageLinkDetailsComponent.isRolePresent(userRole
							.value()))) {
				page.HTMLFormComponent.enterTitle(sitePage.value());
				page.HTMLFormComponent.writeIntoTextarea(htmlSnippet.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.clickOnRole(userRole.value());
				page.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditPageContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditPage: navbar action active is not page or user role " + (userRole
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditPageRestrictAccessAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void closeEditPageAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.close(AddEditNavbarPoCallees.PAGE_LINK);
		} else {
			throw new NotInTheRightPageObjectException(
					"closeEditPageAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToContentAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.PAGE_CONTENT);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToContentAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToDashboardAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToEditCurrentUserAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToMetaAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.PAGE_META);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToMetaAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToPagesAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToSiteAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToUsersAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditPageContainerPage
	public void goToWidgetsAddEditPageContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditPageContainerPage) {
			po.site.pages.AddEditPageContainerPage page = (po.site.pages.AddEditPageContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsAddEditPageContainerPage: expected po.site.pages.AddEditPageContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void addEditTextWidgetAddEditTextWidgetContainerPage(
			custom_classes.WidgetTextTitles widgetTextTitle,
			custom_classes.HTMLSnippets htmlSnippet,
			custom_classes.PageLinkStatus pageLinkStatus) {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			if (page.addEditNavbarComponent.isNavbarItemActive("Settings")) {
				page.htmlFormComponent.enterTitle(widgetTextTitle.value());
				page.htmlFormComponent.writeIntoTextarea(htmlSnippet.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addEditTextWidget: navbar item active is not Settings");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditTextWidgetAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void addEditTextWidgetRestrictAccessAddEditTextWidgetContainerPage(
			custom_classes.WidgetTextTitles widgetTextTitle,
			custom_classes.HTMLSnippets htmlSnippet,
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Settings"))
					&& (page.pageLinkDetailsComponent.isRolePresent(userRole
							.value()))) {
				page.htmlFormComponent.enterTitle(widgetTextTitle.value());
				page.htmlFormComponent.writeIntoTextarea(htmlSnippet.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.clickOnRole(userRole.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditTextWidget: navbar item active is not Settings or user role " + (userRole
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditTextWidgetRestrictAccessAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void addEditVisibilityToLinkOrPageAddEditTextWidgetContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage) {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Visibility"))
					&& (page.visibilityComponent
							.isLinkOrPagePresent(siteLinkOrPage.value()))) {
				page.visibilityComponent.clickOnPageInput(siteLinkOrPage
						.value());
				this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditVisibility: navbar item active is not Visibility or link or page " + (siteLinkOrPage
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditVisibilityToLinkOrPageAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void closeEditTextAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
		} else {
			throw new NotInTheRightPageObjectException(
					"closeEditTextAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToDashboardAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToEditCurrentUserAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToPagesAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToSettingsAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.TEXT_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToSiteAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToUsersAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToVisibilityAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.TEXT_VISIBILITY);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVisibilityAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void goToWidgetsAddEditTextWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditTextWidgetContainerPage
	public void restrictVisibilityAddEditTextWidgetContainerPage(
			custom_classes.SitePages sitePage) {
		if (this.currentPage instanceof po.site.pages.AddEditTextWidgetContainerPage) {
			po.site.pages.AddEditTextWidgetContainerPage page = (po.site.pages.AddEditTextWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Visibility"))
					&& (page.visibilityComponent.isLinkOrPagePresent(sitePage
							.value()))) {
				page.visibilityComponent.clickOnPageInput(sitePage.value());
				this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"restrictVisibility: navbar item active is not visibility");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"restrictVisibilityAddEditTextWidgetContainerPage: expected po.site.pages.AddEditTextWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void addEditMetaAddEditLinkContainerPage(
			custom_classes.SiteLinks siteLink,
			custom_classes.MetaDescriptions metaDescription) {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			if (page.addEditNavbarComponent.isNavbarItemActive("Meta")) {
				page.metaComponent.typeTitle(siteLink.value());
				page.metaComponent.typeDescription(metaDescription.value());
				page.metaComponent.selectImage();
				java.lang.String poCallee = page.getClass().getSimpleName();
				this.currentPage = new po.shared.pages.modals.SelectImagePage(
						page.menuComponent.getDriver(), poCallee);
			} else {
				throw new NotTheRightInputValuesException(
						"addEditMeta: navbar action active is not meta");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditMetaAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void addLinkAddEditLinkContainerPage(
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.LinkTypes linkType,
			custom_classes.SiteLinks siteLink,
			custom_classes.HideInMenu hideInMenu) {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			if (page.addEditLinkFormComponent.isUrlPresent()) {
				page.addEditLinkFormComponent.selectLinkType(linkType.value());
				page.pageLinkDetailsComponent.typeTitle(siteLink.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditLinkContainerPage(
						page.addEditLinkFormComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException("Url is not present");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addLinkAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void addLinkRestrictAccessAddEditLinkContainerPage(
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.LinkTypes linkType,
			custom_classes.UserRoles userRole,
			custom_classes.SiteLinks siteLink,
			custom_classes.HideInMenu hideInMenu) {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			if ((page.addEditLinkFormComponent.isUrlPresent())
					&& (page.pageLinkDetailsComponent.isRolePresent(userRole
							.value()))) {
				page.addEditLinkFormComponent.selectLinkType(linkType.value());
				page.pageLinkDetailsComponent.typeTitle(siteLink.value());
				page.pageLinkDetailsComponent.clickOnRole(userRole.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.hideOnMenu(hideInMenu.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditLinkContainerPage(
						page.addEditLinkFormComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("Url is not present or user role " + (userRole
								.value())) + " is not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addLinkRestrictAccessAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void closeEditLinkAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.close(AddEditNavbarPoCallees.PAGE_LINK);
		} else {
			throw new NotInTheRightPageObjectException(
					"closeEditLinkAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToDashboardAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToEditCurrentUserAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToMetaAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.LINK_META);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToMetaAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToPagesAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToSettingsAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.LINK_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToSiteAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToUsersAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void goToWidgetsAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLinkContainerPage
	public void selectUrlAddEditLinkContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLinkContainerPage) {
			po.site.pages.AddEditLinkContainerPage page = (po.site.pages.AddEditLinkContainerPage) this.currentPage;
			page.addEditLinkFormComponent.selectUrl();
			java.lang.String poCallee = page.getClass().getSimpleName();
			this.currentPage = new po.shared.pages.modals.SelectLinkPage(
					page.addEditLinkFormComponent.getDriver(), poCallee);
		} else {
			throw new NotInTheRightPageObjectException(
					"selectUrlAddEditLinkContainerPage: expected po.site.pages.AddEditLinkContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void addLoginWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(SiteAddWidget.LOGIN);
			this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addLoginWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void addMenuWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(SiteAddWidget.MENU);
			this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addMenuWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void addTextWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			page.widgetsListComponent.addWidget(SiteAddWidget.TEXT);
			this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
					page.widgetsListComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"addTextWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void copyAllWidgetsWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isOneElementPresent()) {
				page.widgetsListComponent.selectAll();
				page.widgetsListComponent.copy();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"copy: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"copyAllWidgetsWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void copyWidgetWidgetsContainerPage(custom_classes.Widget widget) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isWidgetPresent(widget)) {
				page.widgetsListComponent.selectWidget(widget);
				page.widgetsListComponent.copy();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("copy: widget " + (widget.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"copyWidgetWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void deleteAllWidgetsWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isOneElementPresent()) {
				page.widgetsListComponent.selectAll();
				page.widgetsListComponent.delete();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"deleteAllWidgets: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteAllWidgetsWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void deleteWidgetWidgetsContainerPage(custom_classes.Widget widget) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isWidgetPresent(widget)) {
				page.widgetsListComponent.selectWidget(widget);
				page.widgetsListComponent.delete();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("deleteWidget: widget " + (widget.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"deleteWidgetWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void editLoginWidgetsContainerPage(
			custom_classes.WidgetLoginTitles widgetLoginTitle) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isWidgetPresent(widgetLoginTitle)) {
				page.widgetsListComponent.editWidget(widgetLoginTitle);
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editLogin: widgetLoginTitle " + (widgetLoginTitle
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editLoginWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void editMenuWidgetsContainerPage(
			custom_classes.WidgetMenuTitles widgetMenuTitle) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isWidgetPresent(widgetMenuTitle)) {
				page.widgetsListComponent.editWidget(widgetMenuTitle);
				this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editMenu: widgetMenuTitle " + (widgetMenuTitle
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editMenuWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void editTextWidgetsContainerPage(
			custom_classes.WidgetTextTitles widgetTextTitle) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isWidgetPresent(widgetTextTitle)) {
				page.widgetsListComponent.editWidget(widgetTextTitle);
				this.currentPage = new po.site.pages.AddEditTextWidgetContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("editText: widgetTextTitle " + (widgetTextTitle
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"editTextWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToDashboardWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToEditCurrentUserWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToMenuWidgetsContainerPage(
			custom_classes.WidgetMenus widgetMenu) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsSidebarComponent.isMenuPresent(widgetMenu)) {
				page.widgetsSidebarComponent.clickOnLink(widgetMenu);
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsSidebarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("goToMenu: widgetMenu " + (widgetMenu.value())) + " not present"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"goToMenuWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToPagesWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToSiteWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToUsersWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void goToWidgetsWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void publishAllWidgetsWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isOneElementPresent()) {
				page.widgetsListComponent.selectAll();
				page.widgetsListComponent.publish();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"publishAllWidgets: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"publishAllWidgetsWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void publishWidgetWidgetsContainerPage(custom_classes.Widget widget) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if ((page.widgetsListComponent.isWidgetPresent(widget))
					&& (!(page.widgetsListComponent.isStatusActive(widget)))) {
				page.widgetsListComponent.selectWidget(widget);
				page.widgetsListComponent.publish();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("publishWidget: widget " + (widget.value())) + " does not exist or it is active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"publishWidgetWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void unpublishAllWidgetsWidgetsContainerPage() {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if (page.widgetsListComponent.isOneElementPresent()) {
				page.widgetsListComponent.selectAll();
				page.widgetsListComponent.unpublish();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"unpublishAllWidgets: there must be at least one element in the list");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"unpublishAllWidgetsWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: WidgetsContainerPage
	public void unpublishWidgetWidgetsContainerPage(custom_classes.Widget widget) {
		if (this.currentPage instanceof po.site.pages.WidgetsContainerPage) {
			po.site.pages.WidgetsContainerPage page = (po.site.pages.WidgetsContainerPage) this.currentPage;
			if ((page.widgetsListComponent.isWidgetPresent(widget))
					&& (page.widgetsListComponent.isStatusActive(widget))) {
				page.widgetsListComponent.selectWidget(widget);
				page.widgetsListComponent.unpublish();
				this.currentPage = new po.site.pages.WidgetsContainerPage(
						page.widgetsListComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("unpublishWidget: widget " + (widget.value())) + " does not exist or it is not active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"unpublishWidgetWidgetsContainerPage: expected po.site.pages.WidgetsContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void addEditLoginDetailsAddEditLoginWidgetContainerPage(
			custom_classes.WidgetLoginTitles widgetLoginTitle,
			custom_classes.PageLinkStatus pageLinkStatus) {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if (!(page.addEditNavbarComponent.isNavbarItemActive("Visibility"))) {
				page.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle
						.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addEditLoginDetails: visibility tab is active, cannot add login details");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditLoginDetailsAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void addEditLoginDetailsRestrictAccessAddEditLoginWidgetContainerPage(
			custom_classes.WidgetLoginTitles widgetLoginTitle,
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if ((page.pageLinkDetailsComponent.isRolePresent(userRole.value()))
					&& (!(page.addEditNavbarComponent
							.isNavbarItemActive("Visibility")))) {
				page.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle
						.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.pageLinkDetailsComponent.clickOnRole(userRole.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.pageLinkDetailsComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditLoginDetailsRestrictAccess: user role " + (userRole
								.value())) + " does not exist or visibility tab is active"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditLoginDetailsRestrictAccessAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void addEditLoginDetailsWithUrlAddEditLoginWidgetContainerPage(
			custom_classes.WidgetLoginTitles widgetLoginTitle,
			custom_classes.PageLinkStatus pageLinkStatus) {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if (((page.addEditLoginWidgetComponent.isUrlLoginPresent()) && (page.addEditLoginWidgetComponent
					.isUrlLogoutPresent()))
					&& (!(page.addEditNavbarComponent
							.isNavbarItemActive("Visibility")))) {
				page.addEditLoginWidgetComponent.typeTitle(widgetLoginTitle
						.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.addEditLoginWidgetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"addEditLoginDetailsWithUrl: url login or url logout is not present or visibility tab is active");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditLoginDetailsWithUrlAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void closeEditLoginAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
		} else {
			throw new NotInTheRightPageObjectException(
					"closeEditLoginAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToDashboardAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToEditCurrentUserAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToPagesAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToSettingsAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.LOGIN_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToSiteAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToUsersAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToVisibilityAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.LOGIN_VISIBILITY);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVisibilityAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void goToWidgetsAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void restrictVisibilityAddEditLoginWidgetContainerPage(
			custom_classes.SitePages sitePage) {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Visibility"))
					&& (page.visibilityComponent.isLinkOrPagePresent(sitePage
							.value()))) {
				page.visibilityComponent.clickOnPageInput(sitePage.value());
				this.currentPage = new po.site.pages.AddEditLoginWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"restrictVisibility: navbar item active is not visibility");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"restrictVisibilityAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void selectLoginRedirectLinkAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if (!(page.addEditNavbarComponent.isNavbarItemActive("Visibility"))) {
				page.addEditLoginWidgetComponent.selectLoginRedirect();
				java.lang.String poCallee = page.getClass().getSimpleName();
				org.openqa.selenium.By locatorToWaitFor = org.openqa.selenium.By
						.xpath("(//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"])[1]");
				this.currentPage = new po.shared.pages.modals.SelectLinkPage(
						page.addEditLoginWidgetComponent.getDriver(), poCallee,
						locatorToWaitFor);
			} else {
				throw new NotTheRightInputValuesException(
						"selectLoginRedirectLink: visibility tab is active, cannot select login redirect link");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectLoginRedirectLinkAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditLoginWidgetContainerPage
	public void selectLogoutRedirectLinkAddEditLoginWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditLoginWidgetContainerPage) {
			po.site.pages.AddEditLoginWidgetContainerPage page = (po.site.pages.AddEditLoginWidgetContainerPage) this.currentPage;
			if (!(page.addEditNavbarComponent.isNavbarItemActive("Visibility"))) {
				page.addEditLoginWidgetComponent.selectLogoutRedirect();
				java.lang.String poCallee = page.getClass().getSimpleName();
				org.openqa.selenium.By locatorToWaitFor = org.openqa.selenium.By
						.xpath("(//div[@class=\"uk-form-controls\"]/p[@class=\"uk-text-muted uk-margin-small-top uk-margin-bottom-remove\"])[2]");
				this.currentPage = new po.shared.pages.modals.SelectLinkPage(
						page.addEditLoginWidgetComponent.getDriver(), poCallee,
						locatorToWaitFor);
			} else {
				throw new NotTheRightInputValuesException(
						"selectLogoutRedirectLink: visibility tab is active, cannot select logout redirect link");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"selectLogoutRedirectLinkAddEditLoginWidgetContainerPage: expected po.site.pages.AddEditLoginWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void addEditMenuDetailsAddEditMenuWidgetContainerPage(
			custom_classes.WidgetMenuTitles widgetMenuTitle,
			custom_classes.SiteMenus siteMenu,
			custom_classes.StartLevel startLevel, custom_classes.Depth depth,
			custom_classes.MenuSubItems menuSubItem,
			custom_classes.PageLinkStatus pageLinkStatus) {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			if (page.addEditMenuWidgetComponent.isMenuPresent(siteMenu.value())) {
				page.addEditMenuWidgetComponent.typeTitle(widgetMenuTitle
						.value());
				page.addEditMenuWidgetComponent.selectMenu(siteMenu.value());
				page.addEditMenuWidgetComponent.selectLevel(startLevel.value);
				page.addEditMenuWidgetComponent.selectDepth(depth.value());
				page.addEditMenuWidgetComponent.selectSubItems(menuSubItem
						.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
						page.addEditMenuWidgetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditMenuDetails: siteMenu " + (siteMenu.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditMenuDetailsAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void addEditMenuDetailsRestrictAccessAddEditMenuWidgetContainerPage(
			custom_classes.WidgetMenuTitles widgetMenuTitle,
			custom_classes.SiteMenus siteMenu,
			custom_classes.StartLevel startLevel, custom_classes.Depth depth,
			custom_classes.MenuSubItems menuSubItem,
			custom_classes.PageLinkStatus pageLinkStatus,
			custom_classes.UserRoles userRole) {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			if ((page.addEditMenuWidgetComponent
					.isMenuPresent(siteMenu.value()))
					&& (page.pageLinkDetailsComponent.isRolePresent(userRole
							.value()))) {
				page.addEditMenuWidgetComponent.typeTitle(widgetMenuTitle
						.value());
				page.addEditMenuWidgetComponent.selectMenu(siteMenu.value());
				page.addEditMenuWidgetComponent.selectLevel(startLevel.value);
				page.addEditMenuWidgetComponent.selectDepth(depth.value());
				page.addEditMenuWidgetComponent.selectSubItems(menuSubItem
						.value());
				page.pageLinkDetailsComponent.clickOnRole(userRole.value());
				page.pageLinkDetailsComponent.selectStatus(pageLinkStatus
						.value());
				page.addEditNavbarComponent.save();
				this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
						page.addEditMenuWidgetComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(((("addEditMenuDetails: siteMenu " + (siteMenu.value())) + " does not exist or user role ") + (userRole
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditMenuDetailsRestrictAccessAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void addEditVisibilityToLinkOrPageAddEditMenuWidgetContainerPage(
			custom_classes.SiteLinkOrPage siteLinkOrPage) {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Visibility"))
					&& (page.visibilityComponent
							.isLinkOrPagePresent(siteLinkOrPage.value()))) {
				page.visibilityComponent.clickOnPageInput(siteLinkOrPage
						.value());
				this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						(("addEditVisibility: navbar item active is not Visibility or link or page " + (siteLinkOrPage
								.value())) + " does not exist"));
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"addEditVisibilityToLinkOrPageAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void closeEditMenuAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.close(AddEditNavbarPoCallees.MENU_TEXT_LOGIN);
		} else {
			throw new NotInTheRightPageObjectException(
					"closeEditMenuAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToDashboardAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.DASHBOARD);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToDashboardAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToEditCurrentUserAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			page.menuComponent.goToEditCurrentUser();
			this.currentPage = new po.users.pages.AddEditUserContainerPage(
					page.menuComponent.getDriver());
		} else {
			throw new NotInTheRightPageObjectException(
					"goToEditCurrentUserAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToPagesAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.PAGES);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToPagesAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToSettingsAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.MENU_SETTINGS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSettingsAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToSiteAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.SITE);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToSiteAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToUsersAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.menuComponent.goTo(MenuActions.USERS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToUsersAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToVisibilityAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.addEditNavbarComponent
					.goTo(AddEditNavbarActions.MENU_VISIBILITY);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToVisibilityAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void goToWidgetsAddEditMenuWidgetContainerPage() {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			this.currentPage = page.navbarComponent.goTo(NavbarActions.WIDGETS);
		} else {
			throw new NotInTheRightPageObjectException(
					"goToWidgetsAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

	// PO Name: AddEditMenuWidgetContainerPage
	public void restrictVisibilityAddEditMenuWidgetContainerPage(
			custom_classes.SitePages sitePage) {
		if (this.currentPage instanceof po.site.pages.AddEditMenuWidgetContainerPage) {
			po.site.pages.AddEditMenuWidgetContainerPage page = (po.site.pages.AddEditMenuWidgetContainerPage) this.currentPage;
			if ((page.addEditNavbarComponent.isNavbarItemActive("Visibility"))
					&& (page.visibilityComponent.isLinkOrPagePresent(sitePage
							.value()))) {
				page.visibilityComponent.clickOnPageInput(sitePage.value());
				this.currentPage = new po.site.pages.AddEditMenuWidgetContainerPage(
						page.addEditNavbarComponent.getDriver());
			} else {
				throw new NotTheRightInputValuesException(
						"restrictVisibility: navbar item active is not visibility");
			}
		} else {
			throw new NotInTheRightPageObjectException(
					"restrictVisibilityAddEditMenuWidgetContainerPage: expected po.site.pages.AddEditMenuWidgetContainerPage, found "
							+ this.currentPage.getClass().getSimpleName());
		}
	}

}
