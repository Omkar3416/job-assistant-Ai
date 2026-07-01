const {
    getPage
} = require("./browserManager");

const {
    detectApplicationStatus
} = require("./applicationStatus");

const completeEasyApply =
    require("./completeEasyApply");

async function applyNaukriJob(
    email,
    password,
    jobUrl
) {

    const searchPage =
        await getPage(
            email,
            password
        );

    const jobPage =
        await searchPage.context().newPage();

    await jobPage.bringToFront();

    try {


        await jobPage.goto(
            jobUrl,
            {
                waitUntil: "domcontentloaded",
                timeout: 60000
            }
        );

        await jobPage.waitForTimeout(3000);


        console.log(
            "Current Job URL:",
            await jobPage.url()
        );
        // await page.goto(
        //     "https://www.naukri.com/nlogin/login",
        //     {
        //         waitUntil: "domcontentloaded"
        //     }
        // );
        //
        // await page.waitForSelector(
        //     'input[placeholder="Enter Email ID /Username"], input[placeholder="Enter Email ID / Username"]',
        //     {
        //         timeout:30000
        //     }
        // );
        //
        // console.log(
        //     await page.locator("input").evaluateAll(
        //         els =>
        //             els.map(e => ({
        //                 type: e.type,
        //                 name: e.name,
        //                 placeholder: e.placeholder
        //             }))
        //     )
        // );
        // console.log("Current URL:", await page.url());
        //
        // await page.screenshot({
        //     path: "01-after-goto.png",
        //     fullPage: true
        // });
        //
        // await page
        //     .getByPlaceholder("Enter Email ID / Username")
        //     .fill(email);
        //
        // await page.screenshot({
        //     path: `debug-${Date.now()}.png`,
        //     fullPage: true
        // });
        //
        // console.log(await page.url());
        // console.log(await page.content());
        // await page.locator('input[type="password"]').fill(password);
        //
        // await page.click('button[type="submit"]');
        //
        // await page.waitForTimeout(8000);
        //
        // console.log("After Login URL:", page.url());




        await jobPage.screenshot({
            path: "02-after-login.png",
            fullPage: true
        });

        if (jobPage.url().includes("/nlogin/login")) {
            throw new Error("Login failed.");
        }




        console.log(
            await jobPage.locator("button").evaluateAll(btns =>
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

        const easyApply = jobPage.locator("#apply-button");

        if (await easyApply.count() > 0) {

            console.log("Easy Apply button found");

            await easyApply.first().scrollIntoViewIfNeeded();

            await easyApply.first().click();

            await jobPage.waitForTimeout(2000);

            // ----------------------------------
// Complete Easy Apply form
// ----------------------------------

            await completeEasyApply(jobPage);

            return await detectApplicationStatus(jobPage);
        }

// --------------------------------------------------
// Apply Button (text)
// --------------------------------------------------

        const applyButton = jobPage.getByRole("button", {
            name: /^Apply$/i
        });

        if (await applyButton.count() > 0) {

            console.log("Apply button found");

            await applyButton.first().scrollIntoViewIfNeeded();

            await applyButton.first().click();

            await jobPage.waitForTimeout(3000);

            await completeEasyApply(jobPage);

            return await detectApplicationStatus(jobPage);
        }

// --------------------------------------------------
// Apply on company site
// --------------------------------------------------

        const companyApply = jobPage.getByRole("button", {
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

        try {

            if (!jobPage.isClosed()) {

                await jobPage.close();

            }

        } catch (e) {

            console.log(
                "Unable to close job tab:",
                e.message
            );

        }

    }
}



module.exports = applyNaukriJob;

