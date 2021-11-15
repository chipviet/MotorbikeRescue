import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class RescueTeamUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.rescueTeam.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#rescue-team-name'));
  phoneNumberInput: ElementFinder = element(by.css('input#rescue-team-phoneNumber'));
  longitudeInput: ElementFinder = element(by.css('input#rescue-team-longitude'));
  latitudeInput: ElementFinder = element(by.css('input#rescue-team-latitude'));
  addressInput: ElementFinder = element(by.css('input#rescue-team-address'));
  createAtInput: ElementFinder = element(by.css('input#rescue-team-createAt'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber) {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput() {
    return this.phoneNumberInput.getAttribute('value');
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

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setCreateAtInput(createAt) {
    await this.createAtInput.sendKeys(createAt);
  }

  async getCreateAtInput() {
    return this.createAtInput.getAttribute('value');
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
    await this.setPhoneNumberInput('phoneNumber');
    await waitUntilDisplayed(this.saveButton);
    await this.setLongitudeInput('longitude');
    await waitUntilDisplayed(this.saveButton);
    await this.setLatitudeInput('latitude');
    await waitUntilDisplayed(this.saveButton);
    await this.setAddressInput('address');
    await waitUntilDisplayed(this.saveButton);
    await this.setCreateAtInput('createAt');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
