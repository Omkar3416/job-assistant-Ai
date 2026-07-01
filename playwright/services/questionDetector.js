async function questionDetector(page) {

    const messages =
        await page.locator(
            ".chatbot_MessageContainer"
        ).allInnerTexts().catch(() => []);

    if (messages.length === 0) {

        return null;

    }

    return messages[
    messages.length - 1
        ].trim();

}

module.exports =
    questionDetector;