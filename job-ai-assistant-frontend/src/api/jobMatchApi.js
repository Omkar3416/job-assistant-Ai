import axiosClient from "./axiosClient";

export const getMatchScore = async (
    jobId
) => {

    const response =
        await axiosClient.get(
            `/job-match/${jobId}`
        );

    return response.data;
};