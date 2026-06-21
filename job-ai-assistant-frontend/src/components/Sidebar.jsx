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