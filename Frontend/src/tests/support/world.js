const { setWorldConstructor, Before, After } = require('@cucumber/cucumber');
const { chromium } = require('playwright');

let browser;
let context;
let currentPage;

class CustomWorld {
  async init() {
    browser = await chromium.launch();
    context = await browser.newContext();
    currentPage = await context.newPage();
  }

  getPage() {
    return currentPage;
  }

  async cleanup() {
    await browser.close();
  }
}

setWorldConstructor(CustomWorld);

Before(async function () {
  await this.init();
});

After(async function () {
  await this.cleanup();
});

module.exports.page = () => currentPage;
