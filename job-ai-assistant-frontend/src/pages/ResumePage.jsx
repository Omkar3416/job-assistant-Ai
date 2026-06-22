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

            const formData =
                new FormData();

            formData.append(
                "file",
                file
            );

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

            alert(
                response.data.message
            );

            loadResume();

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

                <h1>
                    Resume Management
                </h1>

                {
                    resume && (
                        <div className="resume-card">

                            <h3>
                                Current Resume
                            </h3>

                            <p>
                                <strong>
                                    File Name:
                                </strong>{" "}
                                {resume.fileName}
                            </p>

                            <p>
                                <strong>
                                    File Type:
                                </strong>{" "}
                                {resume.fileType}
                            </p>

                            <p>
                                <strong>
                                    Uploaded:
                                </strong>{" "}
                                {resume.uploadedAt}
                            </p>

                        </div>
                    )
                }

                <div className="resume-upload-section">

                    <label>
                        Upload New Resume
                    </label>

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
                                ? "Uploading..."
                                : resume
                                    ? "Update Resume"
                                    : "Upload Resume"
                        }
                    </button>

                </div>

                {
                    resume?.extractedText && (
                        <div className="resume-preview">

                            <h3>
                                Extracted Resume Content
                            </h3>

                            <textarea
                                readOnly
                                value={
                                    resume.extractedText
                                }
                            />

                        </div>
                    )
                }

            </div>

        </DashboardLayout>
    );
}

export default ResumePage;