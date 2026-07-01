const questionDetector =
    require("./questionDetector");

const questionAnswerProvider =
    require("./questionAnswerProvider");

async function completeEasyApply(page) {

    console.log("================================");
    console.log("STARTING EASY APPLY");
    console.log("================================");

    const MAX_STEPS = 20;

    for (let step = 1; step <= MAX_STEPS; step++) {

        console.log(`Step ${step}`);

        await page.waitForTimeout(1500);

        // -----------------------------
        // Detect current question
        // -----------------------------

        const question =
            await questionDetector(page);

        console.log("Question:", question);

        if (!question) {

            console.log("No question detected.");
        }

        // -----------------------------
        // Fill answer
        // -----------------------------

        if (question) {

            await questionAnswerProvider(
                page,
                question
            );

        }

        // -----------------------------
        // Save button
        // -----------------------------

        const saveButton =
            page.getByRole("button", {
                name: /^save$/i
            });

        if (await saveButton.count() > 0) {

            console.log("Click Save");

            await saveButton.first().click();

            await page.waitForLoadState("networkidle");

            await page.waitForTimeout(1500);

            continue;

        }

        // -----------------------------
        // Submit
        // -----------------------------

        const submitButton =
            page.getByRole("button", {
                name:
                    /apply|submit|finish/i
            });

        if (await submitButton.count() > 0) {

            console.log("Submitting");

            await submitButton.first().click();

            await page.waitForLoadState("networkidle");

            break;

        }

        console.log("No Save / Submit button found.");

        break;

    }

}

module.exports =
    completeEasyApply;