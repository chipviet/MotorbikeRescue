import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ConnectionUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.connection.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  createAtInput: ElementFinder = element(by.css('input#connection-createAt'));
  longitudeInput: ElementFinder = element(by.css('input#connection-longitude'));
  latitudeInput: ElementFinder = element(by.css('input#connection-latitude'));
  statusSelect: ElementFinder = element(by.css('select#connection-status'));
  userSelect: ElementFinder = element(by.css('select#connection-user'));
  requestSelect: ElementFinder = element(by.css('select#connection-request'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCreateAtInput(createAt) {
    await this.createAtInput.sendKeys(createAt);
  }

  async getCreateAtInput() {
    return this.createAtInput.getAttribute('value');
  }

  async setLongitudeInput(longitude) {
    await this.longitudeInput.sendKeys(longitude);
  }

  async getLongitudeInput() {
    return this.longitudeInput.getAttribute('value');
  }

  async setLatitudeInput(latitude) {
    await this.latitudeInput.sendKeys(latitude);
  }

  async getLatitudeInput() {
    return this.latitudeInput.getAttribute('value');
  }

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }
  async userSelectLastOption() {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
  }

  async requestSelectLastOption() {
    await this.requestSelect.all(by.tagName('option')).last().click();
  }

  async requestSelectOption(option) {
    await this.requestSelect.sendKeys(option);
  }

  getRequestSelect() {
    return this.requestSelect;
  }

  async getRequestSelectedOption() {
    return this.requestSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setCreateAtInput('createAt');
    await waitUntilDisplayed(this.saveButton);
    await this.setLongitudeInput('longitude');
    await waitUntilDisplayed(this.saveButton);
    await this.setLatitudeInput('latitude');
    await waitUntilDisplayed(this.saveButton);
    await this.statusSelectLastOption();
    await this.userSelectLastOption();
    await this.requestSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
