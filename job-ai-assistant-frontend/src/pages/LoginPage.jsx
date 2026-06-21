import "./LoginPage.css";
import GoogleLoginButton from "../components/GoogleLoginButton";

function LoginPage() {
    return (
        <div className="login-container">
            <div className="blur blur-purple"></div>
            <div className="blur blur-orange"></div>
            <div className="blur blur-red"></div>

            <div className="login-card">
                <h1>Job AI Assistant</h1>

                <p>
                    Automate job search, resume management,
                    and application tracking with AI.
                </p>

                <GoogleLoginButton />
            </div>
        </div>
    );
}

export default LoginPage;