import { useNavigate } from "react-router-dom";
import { logout } from "../services/authService";

function LogoutButton() {

    const navigate = useNavigate();

    const handleLogout = async () => {

        try {

            await logout();

        } catch (error) {

            console.error(error);

        } finally {

            localStorage.clear();

            navigate("/");
        }
    };

    return (
        <button onClick={handleLogout}>
            Logout
        </button>
    );
}

export default LogoutButton;