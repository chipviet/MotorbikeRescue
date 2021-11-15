import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ConnectionComponentsPage from './connection.page-object';
import ConnectionUpdatePage from './connection-update.page-object';
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

describe('Connection e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let connectionComponentsPage: ConnectionComponentsPage;
  let connectionUpdatePage: ConnectionUpdatePage;
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
    connectionComponentsPage = new ConnectionComponentsPage();
    connectionComponentsPage = await connectionComponentsPage.goToPage(navBarPage);
  });

  it('should load Connections', async () => {
    expect(await connectionComponentsPage.title.getText()).to.match(/Connections/);
    expect(await connectionComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Connections', async () => {
    const beforeRecordsCount = (await isVisible(connectionComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(connectionComponentsPage.table);
    connectionUpdatePage = await connectionComponentsPage.goToCreateConnection();
    await connectionUpdatePage.enterData();
    expect(await isVisible(connectionUpdatePage.saveButton)).to.be.false;

    expect(await connectionComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(connectionComponentsPage.table);
    await waitUntilCount(connectionComponentsPage.records, beforeRecordsCount + 1);
    expect(await connectionComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await connectionComponentsPage.deleteConnection();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(connectionComponentsPage.records, beforeRecordsCount);
      expect(await connectionComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(connectionComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
