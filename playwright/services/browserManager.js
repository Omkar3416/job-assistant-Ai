const { chromium } = require("playwright");


let context = null;

let page = null;

let loggedInUser = null;

async function getPage(
    email,
    password
) {

    /*
     * Reuse existing session
     */
    if (
        context &&
        !context.isClosed?.() &&
        page &&
        !page.isClosed() &&
        loggedInUser === email
    ) {

        try {

            await page.title();

            console.log(
                "Reusing existing browser session..."
            );

            return page;

        } catch (e) {

            console.log("Browser session invalid.");

            try {

                await context.close();

            } catch (ex) {

            }

            context = null;
            page = null;
            loggedInUser = null;

            return getPage(
                email,
                password
            );
        }
    }

    /*
  * Close previous browser context
  */
    if (context) {

        try {

            await context.close();

        } catch (e) {

        }

    }

    console.log(
        "Launching new browser..."
    );


    const path = require("path");

    context = await chromium.launchPersistentContext(
        path.join(__dirname, "../profile"),
        {
            headless: false,

            permissions: [],

            geolocation: {
                latitude: 0,
                longitude: 0
            },
            args:[
                "--disable-geolocation",
                "--disable-notifications"
            ],

            locale: "en-IN"
        }
    );

    await context.clearPermissions();

    page = context.pages()[0] || await context.newPage();


    await login(
        page,
        email,
        password
    );

    loggedInUser =
        email;

    return page;
}

async function login(
    page,
    email,
    password
) {

    await page.goto("https://www.naukri.com/mnjuser/profile");

    if (!page.url().includes("/nlogin/login")) {
        console.log("Already logged in");
        return;
    }

    console.log("Opening login page...");

    try {

        await page.goto(
            "https://www.naukri.com/nlogin/login",
            {
                waitUntil: "domcontentloaded"
            }
        );

    } catch (e) {

        console.log("Unable to open login page");
        console.log(e);

        throw e;
    }

    console.log("Login page opened");

    console.log("Waiting for login form...");

    await page.waitForSelector(
        'input[placeholder="Enter Email ID /Username"], input[placeholder="Enter Email ID / Username"]',
        {
            timeout: 30000
        }
    );

    console.log("Login form found");

    console.log("Entering email...");



    try {

        await page
            .locator(
                'input[placeholder="Enter Email ID /Username"], input[placeholder="Enter Email ID / Username"]'
            )
            .fill(email);

    } catch (e) {

        console.log("Unable to fill email");

        throw e;

    }

    console.log("Entering password...");
    await page
        .locator(
            'input[type="password"]'
        )
        .fill(password);

    console.log("Clicking Login...");

    await page.waitForTimeout(5000);

    console.log(await page.locator("body").innerText());

    await page.screenshot({
        path: "after-login-click.png",
        fullPage: true
    });

    await page.click(
        'button[type="submit"]'
    );

    console.log("Waiting after login...");

    console.log("Waiting network idle...");

    try {

        await Promise.race([

            page.waitForURL(
                url => !url.includes("/nlogin/login"),
                { timeout: 30000 }
            ),

            page.waitForSelector(
                ".nI-gNb-header",
                { timeout: 30000 }
            ),

            page.waitForSelector(
                ".view-profile-wrapper",
                { timeout: 30000 }
            )

        ]);

    } catch (e) {

        console.log("========== LOGIN FAILED ==========");
        console.log("Current URL:", page.url());

        console.log(
            await page.locator("body").innerText()
        );

        await page.screenshot({
            path: "login-failed.png",
            fullPage: true
        });

        throw e;
    }

    console.log("Login successful.");

    await page.waitForTimeout(3000);

    if (
        page.url().includes(
            "/nlogin/login"
        )
    ) {

        throw new Error(
            "Naukri login failed."
        );

    }

    console.log(
        "Logged into Naukri successfully."
    );
}

async function closeBrowser() {

    if (context) {

        try {

            await context.close();

        } catch (e) {

        }

    }


    context = null;

    page = null;

    loggedInUser = null;

}

module.exports = {

    getPage,

    closeBrowser

};