import axiosClient from "./axiosClient";

export const saveJobPreference = async (data) => {

    const response =
        await axiosClient.post(
            "/job-preferences",
            data
        );

    return response.data;
};

export const getJobPreference = async () => {

    const response =
        await axiosClient.get(
            "/job-preferences"
        );

    return response.data;
};