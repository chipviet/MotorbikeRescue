import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RequestComponentsPage from './request.page-object';
import RequestUpdatePage from './request-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Request e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let requestComponentsPage: RequestComponentsPage;
  let requestUpdatePage: RequestUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    requestComponentsPage = new RequestComponentsPage();
    requestComponentsPage = await requestComponentsPage.goToPage(navBarPage);
  });

  it('should load Requests', async () => {
    expect(await requestComponentsPage.title.getText()).to.match(/Requests/);
    expect(await requestComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Requests', async () => {
    const beforeRecordsCount = (await isVisible(requestComponentsPage.noRecords)) ? 0 : await getRecordsCount(requestComponentsPage.table);
    requestUpdatePage = await requestComponentsPage.goToCreateRequest();
    await requestUpdatePage.enterData();
    expect(await isVisible(requestUpdatePage.saveButton)).to.be.false;

    expect(await requestComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(requestComponentsPage.table);
    await waitUntilCount(requestComponentsPage.records, beforeRecordsCount + 1);
    expect(await requestComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await requestComponentsPage.deleteRequest();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(requestComponentsPage.records, beforeRecordsCount);
      expect(await requestComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(requestComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
