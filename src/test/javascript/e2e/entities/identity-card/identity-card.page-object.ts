import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import IdentityCardUpdatePage from './identity-card-update.page-object';

const expect = chai.expect;
export class IdentityCardDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('mrProjectApp.identityCard.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-identityCard'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class IdentityCardComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('identity-card-heading'));
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
    await navBarPage.getEntityPage('identity-card');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateIdentityCard() {
    await this.createButton.click();
    return new IdentityCardUpdatePage();
  }

  async deleteIdentityCard() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const identityCardDeleteDialog = new IdentityCardDeleteDialog();
    await waitUntilDisplayed(identityCardDeleteDialog.deleteModal);
    expect(await identityCardDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/mrProjectApp.identityCard.delete.question/);
    await identityCardDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(identityCardDeleteDialog.deleteModal);

    expect(await isVisible(identityCardDeleteDialog.deleteModal)).to.be.false;
  }
}
