import axios from "axios";
import { createHttp } from "effector-http-api/dist";
import { sleep } from "shared/sleep";

const instance = axios.create({
    baseURL: process.env.REACT_APP_API_URL,
    withCredentials: true,
});

instance.interceptors.request.use(async (config) => {
    await sleep(1000);

    return config;
});

instance.interceptors.response.use((response) => {
    return response;
});

export const http = createHttp(instance);
