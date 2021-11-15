import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import RatingUpdatePage from './rating-update.page-object';

const expect = chai.expect;
export class RatingDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('mrProjectApp.rating.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-rating'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class RatingComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('rating-heading'));
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
    await navBarPage.getEntityPage('rating');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateRating() {
    await this.createButton.click();
    return new RatingUpdatePage();
  }

  async deleteRating() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const ratingDeleteDialog = new RatingDeleteDialog();
    await waitUntilDisplayed(ratingDeleteDialog.deleteModal);
    expect(await ratingDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/mrProjectApp.rating.delete.question/);
    await ratingDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(ratingDeleteDialog.deleteModal);

    expect(await isVisible(ratingDeleteDialog.deleteModal)).to.be.false;
  }
}
