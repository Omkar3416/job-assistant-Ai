import "./Sidebar.css";

function Sidebar() {
    return (
        <aside className="sidebar">

            <h2 className="logo">
                Job AI
            </h2>

            <nav>

                <a href="/dashboard">
                    Dashboard
                </a>

                <a href="/profile">
                    Profile
                </a>
                <a href="/job-preferences">
                    Job Preferences
                </a>
                <a href="/resume">
                    Resume
                </a>
                <a href="/jobs">
                    Jobs
                </a>

                <a href="#">
                    Applications
                </a>

                <a href="#">
                    Settings
                </a>

            </nav>

        </aside>
    );
}

export default Sidebar;