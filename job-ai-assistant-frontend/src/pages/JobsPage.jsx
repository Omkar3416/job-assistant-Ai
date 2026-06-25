import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import axiosClient from "../api/axiosClient";
import { getMatchScore } from "../api/jobMatchApi";
import "./JobsPage.css";

function JobsPage() {

    const [jobs, setJobs] = useState([]);

    const [loading, setLoading] = useState(true);

    const [matchResult, setMatchResult] =
        useState(null);

    const [selectedJobId, setSelectedJobId] =
        useState(null);

    const [loadingJobId, setLoadingJobId] =
        useState(null);

    useEffect(() => {

        loadJobs();

    }, []);

    const loadJobs = async () => {

        try {

            const response =
                await axiosClient.get(
                    "/jobs/recommendations"
                );

            setJobs(response.data);

        } catch (error) {

            console.error(error);

        } finally {

            setLoading(false);
        }
    };

    const checkMatchScore = async (
        jobId
    ) => {

        try {

            setLoadingJobId(jobId);

            const result =
                await getMatchScore(
                    jobId
                );

            setSelectedJobId(jobId);

            setMatchResult(result);

        } catch (error) {

            console.error(error);

            alert(
                "Failed to calculate score"
            );

        } finally {

            setLoadingJobId(null);
        }
    };

    if (loading) {

        return (
            <DashboardLayout>
                <h2>Loading Jobs...</h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>


            <h1>Recommended Jobs</h1>

            {
                jobs.length === 0 && (
                    <p>
                        No recommended jobs found.
                    </p>
                )
            }

            {
                jobs.map(job => (
                    //
                    // <div
                    //     key={job.id}
                    //     style={{
                    //         border: "1px solid #ddd",
                    //         padding: "15px",
                    //         marginBottom: "15px",
                    //         borderRadius: "10px"
                    //     }}
                    // >
                    <div
                        key={job.id}
                        className="job-card"
                    >

                        <div className="job-header">

                            <h3 className="job-title">
                            {job.title}

                            {
                                selectedJobId === job.id &&
                                matchResult &&
                                (
                                    <span
                                        style={{
                                            marginLeft: "10px",
                                            color:
                                                matchResult.score >= 80
                                                    ? "#00c853"
                                                    : matchResult.score >= 60
                                                        ? "#ffb300"
                                                        : "#ff5252"
                                        }}
                                    >
                ({matchResult.score}% Match)
            </span>
                                )
                            }

                        </h3>
                        </div>
                        <div className="job-info">

                            <p>
                                <strong>Company:</strong>
                                {" "}
                                {job.companyName}
                            </p>

                            <p>
                                <strong>Location:</strong>
                                {" "}
                                {job.location}
                            </p>

                        </div>

                        <button
                            className="match-btn"
                            onClick={() =>
                                checkMatchScore(
                                    job.id
                                )
                            }
                            disabled={
                                loadingJobId === job.id
                            }
                        >
                            {
                                loadingJobId === job.id
                                    ? "Calculating..."
                                    : "AI Match Score"
                            }
                        </button>
                        {

                            loadingJobId === job.id && (

                                <div className="analysis-card">
                                    🤖 AI is analyzing your resume against
                                    this job description.

                                    <br />

                                    Please wait...

                                </div>

                            )
                        }


                        {
                            selectedJobId === job.id &&
                            matchResult && (

                                <div
                                    style={{
                                        marginTop: "15px",
                                        padding: "15px",
                                        background: "#111",
                                        borderRadius: "8px",
                                        lineHeight: "1.8"
                                    }}
                                >
                                    <div
                                        style={{
                                            color: "#00c853",
                                            fontWeight: "bold",
                                            fontSize: "20px",
                                            marginBottom: "10px"
                                        }}
                                    >
                                        <div
                                            className="analysis-header"
                                            style={{
                                                color:
                                                    matchResult.score >= 80
                                                        ? "#22c55e"
                                                        : matchResult.score >= 60
                                                            ? "#f59e0b"
                                                            : "#ef4444"
                                            }}
                                        >
                                            Match Score: {matchResult.score}%
                                        </div>
                                    </div>

                                    <div className="analysis-content">
                                        {matchResult.analysis}
                                    </div>
                                </div>

                            )
                        }

                    </div>

                ))
            }



        </DashboardLayout>
    );
}

export default JobsPage;