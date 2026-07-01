const {
    getPage
} = require("./browserManager");

const MAX_PAGES = 1;

async function searchNaukriJobs(

    email,
    password,
    keywords
) {

    console.log("INSIDE searchNaukriJobs()");

    const page =
        await getPage(
            email,
            password
        );

    try {

        const allJobs = [];

        const visitedUrls = new Set();

        for (const keyword of keywords) {

         try{
             const formattedKeyword =
                 keyword
                     .trim()
                     .toLowerCase()
                     .replace(/\s+/g, "-");



             console.log(
                 "SEARCH KEYWORD:",
                 formattedKeyword
             );

             for (
                 let pageNumber = 1;
                 pageNumber <= MAX_PAGES;
                 pageNumber++
             ) {

                 const url =
                     pageNumber === 1
                         ? `https://www.naukri.com/${formattedKeyword}-jobs`
                         : `https://www.naukri.com/${formattedKeyword}-jobs-${pageNumber}`;

                 console.log(
                     "Opening:",
                     url
                 );

                 await page.goto(
                     url,
                     {
                         waitUntil: "domcontentloaded",
                         timeout: 60000
                     }
                 );

                 await page.waitForTimeout(2500);

                 const bodyText = await page.locator("body").innerText();

                 if (
                     bodyText.includes("We'll be back soon") ||
                     bodyText.includes("upgrading our systems")
                 ) {
                     throw new Error(
                         "Naukri is currently under maintenance. Try again later."
                     );
                 }
                 try {

                     await page.waitForSelector(
                         ".srp-jobtuple-wrapper",
                         {
                             timeout: 15000
                         }
                     );
                     const html = await page.$eval(
                         ".srp-jobtuple-wrapper",
                         e => e.outerHTML
                     );

                     console.log(html);

                 } catch (e) {

                     console.log("No job cards found.");

                     break;

                 }
                 const jobs =
                     await page.$$eval(
                         ".srp-jobtuple-wrapper",
                         cards =>
                             cards.map(card => ({

                                 title:
                                     card.querySelector(".title")
                                         ?.innerText
                                         ?.trim() || "",

                                 company:
                                     card.querySelector(".comp-name")
                                         ?.innerText
                                         ?.trim() || "",

                                 location:
                                     card.querySelector(".locWdth")
                                         ?.innerText
                                         ?.trim() || "",

                                 experience:
                                     card.querySelector(".expwdth")
                                         ?.innerText
                                         ?.trim() || "",

                                 salary:
                                     card.querySelector(".sal-wrap")
                                         ?.innerText
                                         ?.trim() || "",

                                 description:
                                     card.querySelector(".job-desc")
                                         ?.innerText
                                         ?.trim() || "",

                                 url:
                                     card.querySelector(".title")
                                         ?.href || "",

                                 easyApply:
                                     card.innerText
                                         .toLowerCase()
                                         .includes("easy apply"),

                                 externalApply:
                                     card.innerText
                                         .toLowerCase()
                                         .includes("apply on company site")

                             }))
                     );

                 if (jobs.length === 0) {

                     console.log(
                         `No jobs found on page ${pageNumber}`
                     );

                     break;
                 }

                 console.log(
                     `Found ${jobs.length} jobs on page ${pageNumber}`
                 );

                 for (const job of jobs) {

                     if (
                         !job.url ||
                         visitedUrls.has(job.url)
                     ) {

                         continue;

                     }

                     visitedUrls.add(
                         job.url
                     );

                     allJobs.push(
                         job
                     );

                 }
             }
         }catch (e){

             console.log(
                 `Skipping keyword ${keyword}`
             );

             console.log(
                 `Navigation failed for keyword: ${keyword}`
             );

             console.log(e.message);

             continue;

         }
        }

        console.log(
            `Total unique jobs collected: ${allJobs.length}`
        );

        return allJobs;

    } catch (error) {


        throw error;
    }
}

module.exports =
    searchNaukriJobs;