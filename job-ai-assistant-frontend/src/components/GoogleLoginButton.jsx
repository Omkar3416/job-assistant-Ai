import axios from "axios";
import { GoogleLogin } from "@react-oauth/google";

function GoogleLoginButton() {
    const handleSuccess = async (credentialResponse) => {

        // console.log(
        //     "GOOGLE TOKEN:",
        //     credentialResponse.credential
        // );

        try {
            const response = await axios.post(
                `${import.meta.env.VITE_API_BASE_URL}/auth/google`,
                {
                    idToken: credentialResponse.credential,
                }
            );

            console.log("LOGIN SUCCESS");
            console.log(response.data);

            localStorage.setItem(
                "accessToken",
                response.data.token
            );

            localStorage.setItem(
                "refreshToken",
                response.data.refreshToken
            );

            window.location.href = "/dashboard";

        } catch (error) {
            console.error(error);
            alert("Login Failed");
        }

    };

    return (
        <GoogleLogin
            useOneTap
            onSuccess={handleSuccess}
            onError={() => {
                alert("Google Login Failed");
            }}
        />
    );
}

export default GoogleLoginButton;