import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ConnectionUpdatePage from './connection-update.page-object';

const expect = chai.expect;
export class ConnectionDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('mrProjectApp.connection.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-connection'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ConnectionComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('connection-heading'));
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
    await navBarPage.getEntityPage('connection');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateConnection() {
    await this.createButton.click();
    return new ConnectionUpdatePage();
  }

  async deleteConnection() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const connectionDeleteDialog = new ConnectionDeleteDialog();
    await waitUntilDisplayed(connectionDeleteDialog.deleteModal);
    expect(await connectionDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/mrProjectApp.connection.delete.question/);
    await connectionDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(connectionDeleteDialog.deleteModal);

    expect(await isVisible(connectionDeleteDialog.deleteModal)).to.be.false;
  }
}
