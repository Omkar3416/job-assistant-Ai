async function questionAnswerProvider(
    page,
    question
) {

    const q =
        question.toLowerCase();

    // --------------------------------

    if (
        q.includes("web api")
    ) {

        await page
            .locator("textarea,input")
            .last()
            .fill("3");

        return;

    }

    // --------------------------------

    if (
        q.includes("last working day")
    ) {

        await page
            .locator("textarea,input")
            .last()
            .fill("15th May");

        return;

    }

    // --------------------------------

    if (
        q.includes("hyderabad")
    ) {

        const yes =
            page.getByRole(
                "radio",
                {
                    name:/yes/i
                }
            );

        if (
            await yes.count() > 0
        ) {

            await yes.check();

        }

        return;

    }

}

module.exports =
    questionAnswerProvider;