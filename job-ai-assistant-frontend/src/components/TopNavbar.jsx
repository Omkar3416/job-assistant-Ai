import "./TopNavbar.css";

import { useAuth }
    from "../context/AuthContext";

function TopNavbar() {

    const {
        user,
        logout
    } = useAuth();

    return (
        <header className="top-navbar">

            <h3>
                Welcome Back
            </h3>

            <div
                style={{
                    display: "flex",
                    alignItems: "center",
                    gap: "12px"
                }}
            >
                <div className="user-box">
                    {user?.email || "User"}
                </div>

                <button
                    onClick={logout}
                >
                    Logout
                </button>

            </div>

        </header>
    );
}

export default TopNavbar;