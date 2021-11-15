import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class RatingUpdatePage {
  pageTitle: ElementFinder = element(by.id('mrProjectApp.rating.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  starInput: ElementFinder = element(by.css('input#rating-star'));
  commentInput: ElementFinder = element(by.css('input#rating-comment'));
  createAtInput: ElementFinder = element(by.css('input#rating-createAt'));
  requestSelect: ElementFinder = element(by.css('select#rating-request'));
  userSelect: ElementFinder = element(by.css('select#rating-user'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setStarInput(star) {
    await this.starInput.sendKeys(star);
  }

  async getStarInput() {
    return this.starInput.getAttribute('value');
  }

  async setCommentInput(comment) {
    await this.commentInput.sendKeys(comment);
  }

  async getCommentInput() {
    return this.commentInput.getAttribute('value');
  }

  async setCreateAtInput(createAt) {
    await this.createAtInput.sendKeys(createAt);
  }

  async getCreateAtInput() {
    return this.createAtInput.getAttribute('value');
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
    await this.setStarInput('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setCommentInput('comment');
    await waitUntilDisplayed(this.saveButton);
    await this.setCreateAtInput('createAt');
    await this.requestSelectLastOption();
    await this.userSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
