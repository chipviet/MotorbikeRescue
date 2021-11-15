import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RescueTeamComponentsPage from './rescue-team.page-object';
import RescueTeamUpdatePage from './rescue-team-update.page-object';
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

describe('RescueTeam e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rescueTeamComponentsPage: RescueTeamComponentsPage;
  let rescueTeamUpdatePage: RescueTeamUpdatePage;
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
    rescueTeamComponentsPage = new RescueTeamComponentsPage();
    rescueTeamComponentsPage = await rescueTeamComponentsPage.goToPage(navBarPage);
  });

  it('should load RescueTeams', async () => {
    expect(await rescueTeamComponentsPage.title.getText()).to.match(/Rescue Teams/);
    expect(await rescueTeamComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete RescueTeams', async () => {
    const beforeRecordsCount = (await isVisible(rescueTeamComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(rescueTeamComponentsPage.table);
    rescueTeamUpdatePage = await rescueTeamComponentsPage.goToCreateRescueTeam();
    await rescueTeamUpdatePage.enterData();
    expect(await isVisible(rescueTeamUpdatePage.saveButton)).to.be.false;

    expect(await rescueTeamComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(rescueTeamComponentsPage.table);
    await waitUntilCount(rescueTeamComponentsPage.records, beforeRecordsCount + 1);
    expect(await rescueTeamComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await rescueTeamComponentsPage.deleteRescueTeam();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(rescueTeamComponentsPage.records, beforeRecordsCount);
      expect(await rescueTeamComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(rescueTeamComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
