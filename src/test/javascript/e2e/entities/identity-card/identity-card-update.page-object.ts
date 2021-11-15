import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class IdentityCardUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.identityCard.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  cardIDInput: ElementFinder = element(by.css('input#identity-card-cardID'));
  nameInput: ElementFinder = element(by.css('input#identity-card-name'));
  dobInput: ElementFinder = element(by.css('input#identity-card-dob'));
  homeInput: ElementFinder = element(by.css('input#identity-card-home'));
  addressInput: ElementFinder = element(by.css('input#identity-card-address'));
  sexInput: ElementFinder = element(by.css('input#identity-card-sex'));
  nationalityInput: ElementFinder = element(by.css('input#identity-card-nationality'));
  doeInput: ElementFinder = element(by.css('input#identity-card-doe'));
  photoInput: ElementFinder = element(by.css('input#identity-card-photo'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCardIDInput(cardID) {
    await this.cardIDInput.sendKeys(cardID);
  }

  async getCardIDInput() {
    return this.cardIDInput.getAttribute('value');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setDobInput(dob) {
    await this.dobInput.sendKeys(dob);
  }

  async getDobInput() {
    return this.dobInput.getAttribute('value');
  }

  async setHomeInput(home) {
    await this.homeInput.sendKeys(home);
  }

  async getHomeInput() {
    return this.homeInput.getAttribute('value');
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setSexInput(sex) {
    await this.sexInput.sendKeys(sex);
  }

  async getSexInput() {
    return this.sexInput.getAttribute('value');
  }

  async setNationalityInput(nationality) {
    await this.nationalityInput.sendKeys(nationality);
  }

  async getNationalityInput() {
    return this.nationalityInput.getAttribute('value');
  }

  async setDoeInput(doe) {
    await this.doeInput.sendKeys(doe);
  }

  async getDoeInput() {
    return this.doeInput.getAttribute('value');
  }

  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return this.photoInput.getAttribute('value');
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
    await this.setCardIDInput('cardID');
    await waitUntilDisplayed(this.saveButton);
    await this.setNameInput('name');
    await waitUntilDisplayed(this.saveButton);
    await this.setDobInput('dob');
    await waitUntilDisplayed(this.saveButton);
    await this.setHomeInput('home');
    await waitUntilDisplayed(this.saveButton);
    await this.setAddressInput('address');
    await waitUntilDisplayed(this.saveButton);
    await this.setSexInput('sex');
    await waitUntilDisplayed(this.saveButton);
    await this.setNationalityInput('nationality');
    await waitUntilDisplayed(this.saveButton);
    await this.setDoeInput('doe');
    await waitUntilDisplayed(this.saveButton);
    await this.setPhotoInput('photo');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
