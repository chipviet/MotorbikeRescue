import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import RequestUpdatePage from './request-update.page-object';

const expect = chai.expect;
export class RequestDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('mrProjectApp.request.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-request'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class RequestComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('request-heading'));
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
    await navBarPage.getEntityPage('request');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateRequest() {
    await this.createButton.click();
    return new RequestUpdatePage();
  }

  async deleteRequest() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const requestDeleteDialog = new RequestDeleteDialog();
    await waitUntilDisplayed(requestDeleteDialog.deleteModal);
    expect(await requestDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/mrProjectApp.request.delete.question/);
    await requestDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(requestDeleteDialog.deleteModal);

    expect(await isVisible(requestDeleteDialog.deleteModal)).to.be.false;
  }
}
