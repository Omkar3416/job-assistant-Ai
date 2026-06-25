const { chromium } =
    require("playwright");

async function loginToNaukri(
    email,
    password
) {

    const browser =
        await chromium.launch({
            headless: false
        });

    const page =
        await browser.newPage();

    try {

        await page.goto(
            "https://www.naukri.com/",
            {
                waitUntil: "networkidle"
            }
        );

        await page.click(
            'a[title="Jobseeker Login"]'
        );

        await page.waitForSelector(
            'input[placeholder="Enter your active Email ID / Username"]'
        );

        await page.fill(
            'input[placeholder="Enter your active Email ID / Username"]',
            email
        );

        await page.fill(
            'input[placeholder="Enter your password"]',
            password
        );

        await page.click(
            'button[type="submit"]'
        );

        await page.waitForTimeout(
            5000
        );

        const currentUrl =
            page.url();

        console.log(
            "Current URL:",
            currentUrl
        );

        return {
            success: true,
            currentUrl
        };

    } catch (error) {

        return {
            success: false,
            message: error.message
        };

    } finally {

        // keep browser open for testing

    }
}

module.exports =
    loginToNaukri;