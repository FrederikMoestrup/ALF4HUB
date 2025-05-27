import { Given, When, Then } from "@cucumber/cucumber";
import { expect } from "@playwright/test";

Given('the user is on the Forum page', async function () {
  await this.page.goto('http://localhost:5173/blog/forum');
});

When('the page loads', async function () {
  await this.page.waitForLoadState('networkidle');
});

Then('there should be a visible button with the text "See your drafts"', async function () {
  const button = await this.page.locator('button:has-text("Se dine kladder")');
  await expect(button).toBeVisible();
});

Given('the user is logged in and on the Forum page', async function () {
  await this.page.goto('http://localhost:5173');

  await this.page.evaluate(() => {
    localStorage.setItem('jwtToken', 'mock-valid-token');
  });

  await this.page.goto('http://localhost:5173/blog/forum');
  await this.page.waitForLoadState('networkidle');
});

When('the user clicks the button "See your drafts"', async function () {
  await this.page.click('button:has-text("Se dine kladder")');
});

Then('the user should be taken to a page with their saved drafts', async function () {
  await expect(this.page).toHaveURL('http://localhost:5173/blog/drafts');
});

Then('each draft should display at least a title and a creation or last modified date', async function () {
  const titles = await this.page.locator('h3');
  const dates = await this.page.locator('small');

  await expect(titles.first()).toBeVisible();
  await expect(dates.first()).toContainText(/Saved on/i);
});
