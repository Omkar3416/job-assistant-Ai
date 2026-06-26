const express = require("express");
const cors = require("cors");

const loginToNaukri =
    require("./services/naukriLogin");

const searchNaukriJobs =
    require("./services/searchNaukriJobs");

const applyNaukriJob =
    require("./services/applyNaukriJob");

const app = express();

app.use(cors());
app.use(express.json());

app.post("/naukri/login", async (req, res) => {
    try {

        const result =
            await loginToNaukri(
                req.body.email,
                req.body.password
            );

        res.json(result);

    } catch (error) {

        res.status(500).json({
            success: false,
            message: error.message
        });
    }
});

app.post("/naukri/search-jobs", async (req, res) => {
    try {

        const jobs =
            await searchNaukriJobs(
                req.body.email,
                req.body.password,
                req.body.keyword
            );

        res.json(jobs);

    } catch (error) {

        res.status(500).json({
            success: false,
            message: error.message
        });
    }
});

app.post("/naukri/apply-job", async (req, res) => {


    console.log("========== APPLY JOB ==========");
    console.log(req.body);

    try {

        const result =
            await applyNaukriJob(
                req.body.email,
                req.body.password,
                req.body.jobUrl
            );

        res.json(result);

    } catch (error) {

        res.status(500).json({
            applied: false,
            quotaReached: false,
            externalApply: false,
            error: error.message
        });
    }
});

app.listen(3001, () => {
    console.log("Playwright running on 3001");
});
