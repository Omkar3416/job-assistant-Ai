import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";

import axiosClient from "../api/axiosClient";

const AuthContext = createContext();

export function AuthProvider({ children }) {

    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);

    useEffect(() => {

        loadCurrentUser();

    }, []);

    async function loadCurrentUser() {

        const token =
            localStorage.getItem("accessToken");

        if (!token) {
            setLoading(false);
            return;
        }

        try {

            const response =
                await axiosClient.get("/auth/me");

            setUser(response.data);

        } catch (error) {

            console.error(
                "Failed to load current user",
                error
            );

            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");

            setUser(null);
        }

        setLoading(false);
    }

    async function logout() {

        try {

            const accessToken =
                localStorage.getItem("accessToken");

            const refreshToken =
                localStorage.getItem("refreshToken");

            if (accessToken && refreshToken) {

                await axiosClient.post(
                    "/auth/logout",
                    {
                        refreshToken
                    },
                    {
                        headers: {
                            Authorization:
                                `Bearer ${accessToken}`
                        }
                    }
                );
            }

        } catch (error) {

            console.error(
                "Logout API failed",
                error
            );

        } finally {

            localStorage.removeItem(
                "accessToken"
            );

            localStorage.removeItem(
                "refreshToken"
            );

            setUser(null);

            window.location.href = "/";
        }
    }

    return (
        <AuthContext.Provider
            value={{
                user,
                loading,
                setUser,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}