import DashboardLayout from "../layouts/DashboardLayout";

import "./DashboardPage.css";

function DashboardPage() {

    return (
        <DashboardLayout>

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