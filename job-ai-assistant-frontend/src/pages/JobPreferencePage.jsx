import { useEffect, useState } from "react";

import DashboardLayout
    from "../layouts/DashboardLayout";

import {
    saveJobPreference,
    getJobPreference
} from "../api/jobPreferenceApi";

import "./JobPreferencePage.css";

function JobPreferencePage() {

    const [saving, setSaving] =
        useState(false);

    const [loading, setLoading] =
        useState(true);

    const [formData, setFormData] =
        useState({
            preferredRoles: "",
            preferredLocations: "",
            remoteAllowed: false,
            hybridAllowed: false,
            onsiteAllowed: false,
            minimumMatchScore: 70,
            maxApplicationsPerCompany: 3
        });

    useEffect(() => {

        loadPreference();

    }, []);

    const loadPreference = async () => {

        try {

            const data =
                await getJobPreference();

            setFormData({
                preferredRoles:
                    data.preferredRoles || "",

                preferredLocations:
                    data.preferredLocations || "",

                remoteAllowed:
                    data.remoteAllowed || false,

                hybridAllowed:
                    data.hybridAllowed || false,

                onsiteAllowed:
                    data.onsiteAllowed || false,

                minimumMatchScore:
                    data.minimumMatchScore || 70,

                maxApplicationsPerCompany:
                    data.maxApplicationsPerCompany || 3
            });

        } catch (error) {

            console.log(
                "No preferences found yet"
            );

        } finally {

            setLoading(false);
        }
    };

    const handleChange = (e) => {

        const {
            name,
            value,
            checked,
            type
        } = e.target;

        setFormData({
            ...formData,
            [name]:
                type === "checkbox"
                    ? checked
                    : value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setSaving(true);

            const response =
                await saveJobPreference(
                    formData
                );

            alert(
                response.message
            );

        } catch (error) {

            console.error(error);

            alert(
                "Failed to save preferences"
            );

        } finally {

            setSaving(false);
        }
    };

    if (loading) {

        return (
            <DashboardLayout>
                <h2>
                    Loading preferences...
                </h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>

            <div className="job-pref-container">

                <h1>
                    Job Preferences
                </h1>

                <form
                    className="job-pref-form"
                    onSubmit={handleSubmit}
                >

                    <label>
                        Preferred Roles
                    </label>

                    <input
                        type="text"
                        name="preferredRoles"
                        value={formData.preferredRoles}
                        placeholder="Java Developer, Backend Developer"
                        onChange={handleChange}
                    />

                    <label>
                        Preferred Locations
                    </label>

                    <input
                        type="text"
                        name="preferredLocations"
                        value={formData.preferredLocations}
                        placeholder="Pune, Mumbai, Bangalore"
                        onChange={handleChange}
                    />

                    <label>
                        Minimum Match Score (%)
                    </label>

                    <input
                        type="number"
                        name="minimumMatchScore"
                        min="1"
                        max="100"
                        value={formData.minimumMatchScore}
                        placeholder="75"
                        onChange={handleChange}
                    />

                    <label>
                        Maximum Applications Per Company
                    </label>

                    <input
                        type="number"
                        name="maxApplicationsPerCompany"
                        min="1"
                        max="10"
                        value={formData.maxApplicationsPerCompany}
                        placeholder="3"
                        onChange={handleChange}
                    />

                    <div className="work-mode-section">

                        <h3>
                            Work Mode Preferences
                        </h3>

                        <label>
                            <input
                                type="checkbox"
                                name="remoteAllowed"
                                checked={formData.remoteAllowed}
                                onChange={handleChange}
                            />
                            Remote Jobs
                        </label>

                        <label>
                            <input
                                type="checkbox"
                                name="hybridAllowed"
                                checked={formData.hybridAllowed}
                                onChange={handleChange}
                            />
                            Hybrid Jobs
                        </label>

                        <label>
                            <input
                                type="checkbox"
                                name="onsiteAllowed"
                                checked={formData.onsiteAllowed}
                                onChange={handleChange}
                            />
                            Onsite Jobs
                        </label>

                    </div>

                    <button
                        type="submit"
                        disabled={saving}
                    >
                        {
                            saving
                                ? "Saving..."
                                : "Save Preferences"
                        }
                    </button>

                </form>

            </div>

        </DashboardLayout>
    );
}

export default JobPreferencePage;