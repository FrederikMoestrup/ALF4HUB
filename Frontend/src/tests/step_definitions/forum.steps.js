import { Given, When, Then } from '@cucumber/cucumber';
import { expect } from '@playwright/test';

Given('the user is on the homepage', async function () {
  await this.page.goto('http://localhost:5173/');
});

When('the user views the navigation menu', async function () {
    await this.page.waitForSelector('header.navbar');
});

Then('there should be a visible menu item with the text "Forum"', async function () {
  const forumLink = this.page.locator('text=Forum');
  await expect(forumLink).toBeVisible();
});

Given('the user sees a menu item "Forum"', async function () {
    await this.page.goto('http://localhost:5173');
    await this.page.waitForLoadState('networkidle');
    await this.page.waitForSelector('text=Forum', { timeout: 10000 });
    const forumLink = this.page.locator('text=Forum');
    await expect(forumLink).toBeVisible();
  });
  
  
When('the user clicks on the "Forum" menu item', async function () {
  await this.page.click('text=Forum');
});

Then('the user should be taken to a page showing an overview of existing blog posts', async function () {
  await expect(this.page).toHaveURL('http://localhost:5173/blog/forum');
});

Then('each blog post should display at least a title and a short preview', async function () {
  const blogTitles = await this.page.locator('h3');
  const blogPreviews = await this.page.locator('p');

  await expect(blogTitles.first()).toBeVisible();
  await expect(blogPreviews.first()).toBeVisible();
});
