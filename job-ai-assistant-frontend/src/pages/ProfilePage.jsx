import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import {
    createProfile,
    getProfile
} from "../api/profileApi";
import "./ProfilePage.css";


function ProfilePage() {

    const [formData, setFormData] = useState({
        fullName: "",
        phone: "",
        currentRole: "",
        preferredRoles: "",
        currentCity: "",
        preferredCities: "",
        remoteAllowed: false,
        hybridAllowed: false,
        officeAllowed: false
    });

    const [loading, setLoading] =
        useState(true);

    const [saving, setSaving] =
        useState(false);

    useEffect(() => {

        loadProfile();

    }, []);

    const loadProfile = async () => {

        try {

            const profile =
                await getProfile();

            setFormData({

                fullName:
                    profile.fullName || "",

                phone:
                    profile.phone || "",

                currentRole:
                    profile.currentRole || "",

                preferredRoles:
                    profile.preferredRoles || "",

                currentCity:
                    profile.currentCity || "",

                preferredCities:
                    profile.preferredCities || "",

                remoteAllowed:
                    profile.remoteAllowed || false,

                hybridAllowed:
                    profile.hybridAllowed || false,

                officeAllowed:
                    profile.officeAllowed || false
            });

        } catch (error) {

            console.log(
                "No profile found yet"
            );

        } finally {

            setLoading(false);
        }
    };

    const handleChange = (e) => {

        const { name, value, type, checked } =
            e.target;

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
                await createProfile(formData);

            alert(response.message);

        } catch (error) {

            console.error(error);

            alert("Failed to save profile");

        } finally {

            setSaving(false);
        }
    };

    if (loading) {

        return (
            <DashboardLayout>
                <h2>Loading profile...</h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>

            <div className="profile-container">

                <h1>User Profile</h1>

                <form
                    className="profile-form"
                    onSubmit={handleSubmit}
                >

                    <label>
                        Full Name
                    </label>

                    <input
                        type="text"
                        name="fullName"
                        value={formData.fullName}
                        placeholder="Omkar Patil"
                        onChange={handleChange}
                    />

                    <label>
                        Phone Number
                    </label>

                    <input
                        type="text"
                        name="phone"
                        value={formData.phone}
                        placeholder="9876543210"
                        onChange={handleChange}
                    />

                    <label>
                        Current Role
                    </label>

                    <input
                        type="text"
                        name="currentRole"
                        value={formData.currentRole}
                        placeholder="Software Engineer"
                        onChange={handleChange}
                    />

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
                        Current City
                    </label>

                    <input
                        type="text"
                        name="currentCity"
                        value={formData.currentCity}
                        placeholder="Pune"
                        onChange={handleChange}
                    />

                    <label>
                        Preferred Cities
                    </label>

                    <input
                        type="text"
                        name="preferredCities"
                        value={formData.preferredCities}
                        placeholder="Pune, Mumbai, Bangalore"
                        onChange={handleChange}
                    />

                    <div className="work-mode-section">

                        <h3>
                            Work Mode Preferences
                        </h3>

                        <label className="checkbox-label">
                            <input
                                type="checkbox"
                                name="remoteAllowed"
                                checked={formData.remoteAllowed}
                                onChange={handleChange}
                            />
                            Remote Jobs
                        </label>

                        <label className="checkbox-label">
                            <input
                                type="checkbox"
                                name="hybridAllowed"
                                checked={formData.hybridAllowed}
                                onChange={handleChange}
                            />
                            Hybrid Jobs
                        </label>

                        <label className="checkbox-label">
                            <input
                                type="checkbox"
                                name="officeAllowed"
                                checked={formData.officeAllowed}
                                onChange={handleChange}
                            />
                            Office Jobs
                        </label>

                    </div>

                    <button
                        type="submit"
                        disabled={saving}
                    >
                        {
                            saving
                                ? "Saving..."
                                : "Save Profile"
                        }
                    </button>

                </form>

            </div>

        </DashboardLayout>
    );
}

export default ProfilePage;