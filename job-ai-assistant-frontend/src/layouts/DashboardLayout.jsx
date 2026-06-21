import Sidebar from "../components/Sidebar";
import TopNavbar from "../components/TopNavbar";

import "./DashboardLayout.css";

function DashboardLayout({ children }) {
    return (
        <div className="dashboard-layout">

            <Sidebar />

            <div className="dashboard-main">

                <TopNavbar />

                <div className="dashboard-content">
                    {children}
                </div>

            </div>

        </div>
    );
}

export default DashboardLayout;