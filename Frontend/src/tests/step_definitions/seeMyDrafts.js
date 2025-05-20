import { Given, When, Then } from "@cucumber/cucumber";
import { expect } from "@playwright/test";
import { page } from "../support/world";

Given('brugeren befinder sig på Forum siden', async () => {
  await page.goto('http://localhost:5173/blog/forum');
});

When('siden indlæses', async () => {
  await page.waitForLoadState('networkidle');
});

Then('skal der være en synlig knap med teksten “Se dine kladder”', async () => {
  const button = await page.locator('button:has-text("Se dine kladder")');
  await expect(button).toBeVisible();
});

When('brugeren klikker på knappen “Se dine kladder”', async () => {
  await page.click('button:has-text("Se dine kladder")');
});

Then('skal brugeren føres til en side med deres egne gemte kladder', async () => {
  await expect(page).toHaveURL('http://localhost:5173/blog/drafts');
});

Then('hver kladde skal vise mindst en titel og en oprettelses-/sidst ændret dato', async () => {
  const titles = await page.locator('h3');
  const dates = await page.locator('small');

  await expect(titles.first()).toBeVisible();
  await expect(dates.first()).toContainText(/Saved on/);
});
