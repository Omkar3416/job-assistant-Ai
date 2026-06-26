const { chromium } = require("playwright");

async function applyNaukriJob(
    email,
    password,
    jobUrl
) {

    const browser =
        await chromium.launch({
            headless: false
        });

    const page =
        await browser.newPage();

    try {

        await page.goto(
            "https://www.naukri.com/nlogin/login",
            {
                waitUntil: "domcontentloaded"
            }
        );

        await page.waitForSelector(
            'input[placeholder="Enter Email ID /Username"], input[placeholder="Enter Email ID / Username"]',
            {
                timeout:30000
            }
        );

        console.log(
            await page.locator("input").evaluateAll(
                els =>
                    els.map(e => ({
                        type: e.type,
                        name: e.name,
                        placeholder: e.placeholder
                    }))
            )
        );
        console.log("Current URL:", await page.url());

        await page.screenshot({
            path: "01-after-goto.png",
            fullPage: true
        });

        await page
            .getByPlaceholder("Enter Email ID / Username")
            .fill(email);

        await page.screenshot({
            path: `debug-${Date.now()}.png`,
            fullPage: true
        });

        console.log(await page.url());
        console.log(await page.content());
        await page.locator('input[type="password"]').fill(password);

        await page.click('button[type="submit"]');

        await page.waitForTimeout(8000);

        console.log("After Login URL:", page.url());

        await page.screenshot({
            path: "02-after-login.png",
            fullPage: true
        });

        if (page.url().includes("/nlogin/login")) {
            throw new Error("Login failed.");
        }


        await page.waitForTimeout(
            5000
        );

        await page.screenshot({
            path: "02-after-login.png",
            fullPage: true
        });

        console.log("After Login URL:", await page.url());

        await page.goto(jobUrl,{
            waitUntil:"domcontentloaded"
        });

        await page.waitForTimeout(5000);

        console.log("Current Job URL:", page.url());

        console.log(
            await page.locator("button").evaluateAll(btns =>
                btns.map(b => ({
                    text: b.innerText.trim(),
                    id: b.id,
                    class: b.className
                }))
            )
        );

// --------------------------------------------------
// Easy Apply
// --------------------------------------------------

        const easyApply = page.locator("#apply-button");

        if (await easyApply.count() > 0) {

            console.log("Easy Apply button found");

            await easyApply.first().scrollIntoViewIfNeeded();

            await easyApply.first().click();

            await page.waitForTimeout(3000);

            return {
                applied: true,
                externalApply: false,
                quotaReached: false,
                error: null
            };
        }

// --------------------------------------------------
// Apply Button (text)
// --------------------------------------------------

        const applyButton = page.getByRole("button", {
            name: /^Apply$/i
        });

        if (await applyButton.count() > 0) {

            console.log("Apply button found");

            await applyButton.first().scrollIntoViewIfNeeded();

            await applyButton.first().click();

            await page.waitForTimeout(3000);

            return {
                applied: true,
                externalApply: false,
                quotaReached: false,
                error: null
            };
        }

// --------------------------------------------------
// Apply on company site
// --------------------------------------------------

        const companyApply = page.getByRole("button", {
            name: /Apply on company site/i
        });

        if (await companyApply.count() > 0) {

            console.log("External Apply Job");

            return {
                applied: false,
                externalApply: true,
                quotaReached: false,
                error: "External company application"
            };
        }

// --------------------------------------------------
// Nothing matched
// --------------------------------------------------

        return {
            applied: false,
            externalApply: true,
            quotaReached: false,
            error: "No supported Apply button found"
        };


    } catch (error) {

        return {
            applied: false,
            quotaReached: false,
            externalApply: false,
            error: error.message
        };

    } finally {

        // await browser.close();
    }
}

module.exports = applyNaukriJob;