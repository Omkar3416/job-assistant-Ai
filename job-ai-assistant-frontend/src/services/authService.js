import axiosClient from "../api/axiosClient";

export const logout = async () => {

    const refreshToken =
        localStorage.getItem("refreshToken");

    await axiosClient.post(
        "/auth/logout",
        {
            refreshToken
        }
    );

    localStorage.clear();
};