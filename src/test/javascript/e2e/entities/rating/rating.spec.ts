import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RatingComponentsPage from './rating.page-object';
import RatingUpdatePage from './rating-update.page-object';
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

describe('Rating e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ratingComponentsPage: RatingComponentsPage;
  let ratingUpdatePage: RatingUpdatePage;
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
    ratingComponentsPage = new RatingComponentsPage();
    ratingComponentsPage = await ratingComponentsPage.goToPage(navBarPage);
  });

  it('should load Ratings', async () => {
    expect(await ratingComponentsPage.title.getText()).to.match(/Ratings/);
    expect(await ratingComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Ratings', async () => {
    const beforeRecordsCount = (await isVisible(ratingComponentsPage.noRecords)) ? 0 : await getRecordsCount(ratingComponentsPage.table);
    ratingUpdatePage = await ratingComponentsPage.goToCreateRating();
    await ratingUpdatePage.enterData();
    expect(await isVisible(ratingUpdatePage.saveButton)).to.be.false;

    expect(await ratingComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(ratingComponentsPage.table);
    await waitUntilCount(ratingComponentsPage.records, beforeRecordsCount + 1);
    expect(await ratingComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await ratingComponentsPage.deleteRating();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(ratingComponentsPage.records, beforeRecordsCount);
      expect(await ratingComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(ratingComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
