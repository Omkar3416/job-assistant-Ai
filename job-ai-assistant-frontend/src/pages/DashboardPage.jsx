import { useEffect, useState } from "react";

import DashboardLayout from "../layouts/DashboardLayout";

import {
    getJobPreference,
    saveJobPreference
} from "../api/jobPreferenceApi";

import "./DashboardPage.css";

function DashboardPage() {

    const [autoApplyEnabled, setAutoApplyEnabled] =
        useState(false);

    const [preference, setPreference] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    useEffect(() => {

        loadPreference();

    }, []);

    const loadPreference = async () => {

        try {

            const preference =
                await getJobPreference();

            setAutoApplyEnabled(
                preference.autoApplyEnabled === true
            );

        } catch (error) {

            console.log(error);

        } finally {

            setLoading(false);
        }
    };

    const handleToggle = async () => {

        const newValue = !autoApplyEnabled;

        setAutoApplyEnabled(newValue);

        try {

            const preference =
                await getJobPreference();

            setPreference(preference);

            setAutoApplyEnabled(
                preference.autoApplyEnabled === true
            );

            await saveJobPreference({

                ...preference,

                autoApplyEnabled: newValue

            });

        } catch (error) {

            setAutoApplyEnabled(!newValue);

            console.error(error);

            alert(
                "Unable to update Auto Apply"
            );
        }
    };

    return (
        <DashboardLayout>

            <div className="auto-apply-card">

                <div>

                    <h2>
                        🤖 Auto Job Apply
                    </h2>

                    <p>

                        {
                            loading
                                ? "Loading..."
                                : autoApplyEnabled
                                    ? "Enabled"
                                    : "Disabled"
                        }

                    </p>

                </div>

                <label className="switch">

                    <input
                        type="checkbox"
                        checked={autoApplyEnabled}
                        onChange={handleToggle}
                        disabled={loading}
                    />

                    <span className="slider"></span>

                </label>

            </div>

            <div className="stats-grid">

                <div className="stat-card">
                    <h2>0</h2>
                    <p>Applications Sent</p>
                </div>

                <div className="stat-card">
                    <h2>0</h2>
                    <p>Pending</p>
                </div>

                <div className="stat-card">
                    <h2>0</h2>
                    <p>Interviews</p>
                </div>

                <div className="stat-card">
                    <h2>0</h2>
                    <p>Rejected</p>
                </div>

            </div>

        </DashboardLayout>
    );
}

export default DashboardPage;