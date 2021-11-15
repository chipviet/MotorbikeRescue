import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import IdentityCardComponentsPage from './identity-card.page-object';
import IdentityCardUpdatePage from './identity-card-update.page-object';
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

describe('IdentityCard e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let identityCardComponentsPage: IdentityCardComponentsPage;
  let identityCardUpdatePage: IdentityCardUpdatePage;
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
    identityCardComponentsPage = new IdentityCardComponentsPage();
    identityCardComponentsPage = await identityCardComponentsPage.goToPage(navBarPage);
  });

  it('should load IdentityCards', async () => {
    expect(await identityCardComponentsPage.title.getText()).to.match(/Identity Cards/);
    expect(await identityCardComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete IdentityCards', async () => {
    const beforeRecordsCount = (await isVisible(identityCardComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(identityCardComponentsPage.table);
    identityCardUpdatePage = await identityCardComponentsPage.goToCreateIdentityCard();
    await identityCardUpdatePage.enterData();
    expect(await isVisible(identityCardUpdatePage.saveButton)).to.be.false;

    expect(await identityCardComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(identityCardComponentsPage.table);
    await waitUntilCount(identityCardComponentsPage.records, beforeRecordsCount + 1);
    expect(await identityCardComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await identityCardComponentsPage.deleteIdentityCard();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(identityCardComponentsPage.records, beforeRecordsCount);
      expect(await identityCardComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(identityCardComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
