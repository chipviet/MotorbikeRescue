import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import RescueTeamUpdatePage from './rescue-team-update.page-object';

const expect = chai.expect;
export class RescueTeamDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('mrProjectApp.rescueTeam.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-rescueTeam'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class RescueTeamComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('rescue-team-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('rescue-team');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateRescueTeam() {
    await this.createButton.click();
    return new RescueTeamUpdatePage();
  }

  async deleteRescueTeam() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const rescueTeamDeleteDialog = new RescueTeamDeleteDialog();
    await waitUntilDisplayed(rescueTeamDeleteDialog.deleteModal);
    expect(await rescueTeamDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/mrProjectApp.rescueTeam.delete.question/);
    await rescueTeamDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(rescueTeamDeleteDialog.deleteModal);

    expect(await isVisible(rescueTeamDeleteDialog.deleteModal)).to.be.false;
  }
}
