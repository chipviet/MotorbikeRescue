import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class RequestUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.request.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  longitudeInput: ElementFinder = element(by.css('input#request-longitude'));
  latitudeInput: ElementFinder = element(by.css('input#request-latitude'));
  createAtInput: ElementFinder = element(by.css('input#request-createAt'));
  messageInput: ElementFinder = element(by.css('input#request-message'));
  statusSelect: ElementFinder = element(by.css('select#request-status'));
  userSelect: ElementFinder = element(by.css('select#request-user'));

  getPageTitle() {
    return this.pageTitle;
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

  async setCreateAtInput(createAt) {
    await this.createAtInput.sendKeys(createAt);
  }

  async getCreateAtInput() {
    return this.createAtInput.getAttribute('value');
  }

  async setMessageInput(message) {
    await this.messageInput.sendKeys(message);
  }

  async getMessageInput() {
    return this.messageInput.getAttribute('value');
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
    await this.setLongitudeInput('longitude');
    await waitUntilDisplayed(this.saveButton);
    await this.setLatitudeInput('latitude');
    await waitUntilDisplayed(this.saveButton);
    await this.setCreateAtInput('createAt');
    await waitUntilDisplayed(this.saveButton);
    await this.setMessageInput('message');
    await waitUntilDisplayed(this.saveButton);
    await this.statusSelectLastOption();
    await this.userSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
