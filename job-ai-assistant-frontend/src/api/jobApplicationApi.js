import axiosClient from "./axiosClient";

export const queueApplication = async (
    jobId
) => {

    const response =
        await axiosClient.post(
            `/job-applications/queue/${jobId}`
        );

    return response.data;
};
export const getMyApplications = async () => {

    const response =
        await axiosClient.get(
            "/job-applications"
        );

    return response.data;
};