import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import axiosClient from "../api/axiosClient";
import "./ResumePage.css";

function ResumePage() {

    const [file, setFile] = useState(null);

    const [resume, setResume] = useState(null);

    const [loading, setLoading] = useState(true);

    const [uploading, setUploading] =
        useState(false);

    const [uploadStatus, setUploadStatus] =
        useState("");

    useEffect(() => {

        loadResume();

    }, []);

    const loadResume = async () => {

        try {

            const response =
                await axiosClient.get(
                    "/resume"
                );

            setResume(response.data);

        } catch (error) {

            console.log(
                "No resume uploaded yet"
            );

        } finally {

            setLoading(false);
        }
    };

    const uploadResume = async () => {

        if (!file) {

            alert(
                "Please select resume"
            );

            return;
        }

        try {

            setUploading(true);

            setUploadStatus(
                "Uploading resume and generating AI analysis. This may take 10-30 seconds..."
            );

            const formData =
                new FormData();

            formData.append(
                "file",
                file
            );

            // setUploadStatus(
            //     "Extracting Resume..."
            // );

            const response =
                await axiosClient.post(
                    "/resume/upload",
                    formData,
                    {
                        headers: {
                            "Content-Type":
                                "multipart/form-data"
                        }
                    }
                );

            setUploadStatus(
                "Generating AI Summary..."
            );

            loadResume();

            setUploadStatus(
                "Completed"
            );

            setFile(null);

        } catch (error) {

            console.error(error);

            console.log(
                "RESPONSE = ",
                error.response
            );

            alert(
                error?.response?.data?.message
                || error.message
                || "Upload failed"
            );
        } finally {

            setUploading(false);
            setTimeout(() => {

                setUploadStatus("");

            }, 2000);
        }
    };
    const deleteResume = async () => {

        const confirmed =
            window.confirm(
                "Delete current resume?"
            );

        if (!confirmed) {
            return;
        }

        try {

            await axiosClient.delete(
                "/resume"
            );

            alert(
                "Resume deleted successfully"
            );

            setResume(null);

            setFile(null);

        } catch (error) {

            console.error(error);

            alert(
                "Delete failed"
            );
        }
    };

    if (loading) {

        return (
            <DashboardLayout>
                <h2>
                    Loading Resume...
                </h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>

            <div className="resume-container">

                <div className="resume-header">

                    <h1>Resume Management</h1>

                    <p>
                        Upload your resume and get
                        AI powered analysis.
                    </p>

                </div>

                {
                    uploading && (
                        <div className="upload-status-card">

                            <h3>
                                AI Processing Resume
                            </h3>

                            <p>
                                {uploadStatus}
                            </p>

                        </div>
                    )
                }

                <div className="resume-grid">

                    <div className="left-panel">

                        {
                            resume && (
                                <div className="resume-card">

                                    <h3>
                                        Resume Overview
                                    </h3>

                                    <p>
                                        <strong>
                                            File:
                                        </strong>{" "}
                                        {resume.fileName}
                                    </p>

                                    <p>
                                        <strong>
                                            Type:
                                        </strong>{" "}
                                        {resume.fileType}
                                    </p>

                                    <p>
                                        <strong>
                                            Uploaded:
                                        </strong>{" "}
                                        {resume.uploadedAt}
                                    </p>

                                    <button
                                        className="delete-btn"
                                        onClick={deleteResume}
                                    >
                                        Delete Resume
                                    </button>

                                </div>
                            )
                        }

                        <div className="resume-upload-section">

                            <h3>
                                Upload Resume
                            </h3>

                            <input
                                type="file"
                                accept=".pdf"
                                onChange={(e) =>
                                    setFile(
                                        e.target.files[0]
                                    )
                                }
                            />

                            <button
                                onClick={uploadResume}
                                disabled={uploading}
                            >
                                {
                                    uploading
                                        ? "Processing..."
                                        : resume
                                            ? "Update Resume"
                                            : "Upload Resume"
                                }
                            </button>

                        </div>

                    </div>

                    <div className="right-panel">

                        {
                            resume?.aiSummary && (
                                <div className="resume-summary">

                                    <h3>
                                        AI Resume Analysis
                                    </h3>

                                    <div className="ai-summary-content">

                                        <div className="summary-text">
                                            {resume.aiSummary}
                                        </div>

                                    </div>

                                </div>
                            )
                        }

                        {
                            resume?.extractedText && (
                                <div className="resume-preview">

                                    <details>

                                        <summary>
                                            View Full Resume
                                        </summary>

                                        <textarea
                                            readOnly
                                            value={
                                                resume.extractedText
                                            }
                                        />

                                    </details>

                                </div>
                            )
                        }

                    </div>

                </div>

            </div>

        </DashboardLayout>
    );
}

export default ResumePage;