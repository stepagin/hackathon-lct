import React from "react";

import { createBrowserRouter } from "react-router-dom";
import { SuspensePage } from "./lazy";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <SuspensePage name="main" />,
    },
    {
        path: "/login",
        element: <SuspensePage name="login" />,
    },
]);
