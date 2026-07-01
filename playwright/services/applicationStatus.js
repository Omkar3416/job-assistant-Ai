async function detectApplicationStatus(page) {

    const pageText =
        (
            await page.locator("body").innerText()
        ).toLowerCase();

    await page.screenshot({
        path: `application-status-${Date.now()}.png`,
        fullPage: true
    });

    console.log("========== PAGE TEXT ==========");
    console.log(pageText.substring(0, 3000));
    console.log("================================");

    // ----------------------------------
    // Already applied
    // ----------------------------------

    if (
        pageText.includes("already applied")
    ) {

        return {
            applied: true,
            alreadyApplied: true,
            quotaReached: false,
            externalApply: false,
            resumeRequired: false,
            multiStep: false,
            success: true,
            error: null
        };

    }

    // ----------------------------------
    // Daily limit
    // ----------------------------------

    if (
        pageText.includes("daily apply limit") ||
        pageText.includes("application limit")
    ) {

        return {
            applied: false,
            alreadyApplied: false,
            quotaReached: true,
            externalApply: false,
            resumeRequired: false,
            multiStep: false,
            success: false,
            error: "Daily quota reached"
        };

    }

    // ----------------------------------
    // Resume upload
    // ----------------------------------

    const resumeInput =
        page.locator(
            'input[type="file"]'
        );

    if (
        await resumeInput.count() > 0
    ) {

        return {
            applied: false,
            alreadyApplied: false,
            quotaReached: false,
            externalApply: false,
            resumeRequired: true,
            multiStep: false,
            success: false,
            error: "Resume upload required"
        };

    }

    // ----------------------------------
    // Multi-step form
    // ----------------------------------

    const nextButton =
        page.getByRole("button", {
            name: /^next$/i
        });

    if (
        await nextButton.count() > 0
    ) {

        return {
            applied: false,
            alreadyApplied: false,
            quotaReached: false,
            externalApply: false,
            resumeRequired: false,
            multiStep: true,
            success: false,
            error: "Multi-step application"
        };

    }

    // ----------------------------------
    // Success
    // ----------------------------------

    if (
        pageText.includes("application submitted") ||
        pageText.includes("successfully applied") ||
        pageText.includes("you have successfully applied")
    ) {

        return {
            applied: true,
            alreadyApplied: false,
            quotaReached: false,
            externalApply: false,
            resumeRequired: false,
            multiStep: false,
            success: true,
            error: null
        };

    }

    // ----------------------------------

    return {

        applied: false,

        alreadyApplied: false,

        quotaReached: false,

        externalApply: false,

        resumeRequired: false,

        multiStep: false,

        success: false,

        error: "Unknown application state"

    };

}

module.exports = {

    detectApplicationStatus

};