import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class DeviceUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.device.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#device-name'));
  deviceUuidInput: ElementFinder = element(by.css('input#device-deviceUuid'));
  platformInput: ElementFinder = element(by.css('input#device-platform'));
  versionInput: ElementFinder = element(by.css('input#device-version'));
  userSelect: ElementFinder = element(by.css('select#device-user'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setDeviceUuidInput(deviceUuid) {
    await this.deviceUuidInput.sendKeys(deviceUuid);
  }

  async getDeviceUuidInput() {
    return this.deviceUuidInput.getAttribute('value');
  }

  async setPlatformInput(platform) {
    await this.platformInput.sendKeys(platform);
  }

  async getPlatformInput() {
    return this.platformInput.getAttribute('value');
  }

  async setVersionInput(version) {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput() {
    return this.versionInput.getAttribute('value');
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
    await this.setNameInput('name');
    await waitUntilDisplayed(this.saveButton);
    await this.setDeviceUuidInput('deviceUuid');
    await waitUntilDisplayed(this.saveButton);
    await this.setPlatformInput('platform');
    await waitUntilDisplayed(this.saveButton);
    await this.setVersionInput('version');
    await this.userSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
