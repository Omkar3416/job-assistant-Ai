const { chromium } = require("playwright");

async function searchNaukriJobs(
    email,
    password,
    keyword
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
                waitUntil: "domcontentloaded"
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

        console.log(
            "Logged into Naukri"
        );

        const formattedKeyword =
            keyword
                .trim()
                .toLowerCase()
                .replace(/\s+/g, "-");

        console.log(
            "SEARCH KEYWORD:",
            formattedKeyword
        );

        await page.goto(
            `https://www.naukri.com/${formattedKeyword}-jobs`,
            {
                waitUntil: "domcontentloaded"
            }
        );

        await page.waitForTimeout(
            3000
        );

        const jobs =
            await page.$$eval(
                ".srp-jobtuple-wrapper",
                cards =>
                    cards.map(card => ({
                        title:
                            card.querySelector(".title")
                                ?.innerText || "",

                        company:
                            card.querySelector(".comp-name")
                                ?.innerText || "",

                        url:
                            card.querySelector(".title")
                                ?.href || ""
                    }))
            );

        await browser.close();

        return jobs;

    } catch (error) {

        await browser.close();

        throw error;
    }
}

module.exports =
    searchNaukriJobs;