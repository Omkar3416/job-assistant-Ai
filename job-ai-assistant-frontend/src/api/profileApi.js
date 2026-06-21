import axiosClient from "./axiosClient";

export const createProfile = async (profileData) => {

    const response =
        await axiosClient.post(
            "/profile",
            profileData
        );

    return response.data;
};

export const getProfile = async () => {

    const response =
        await axiosClient.get(
            "/profile"
        );

    return response.data;
};