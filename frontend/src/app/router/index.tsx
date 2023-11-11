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
    {
        path: "/employees",
        element: <SuspensePage name="manager/home" />
    },
    {
        path: "/tasks",
        element: <SuspensePage name="manager/home" />
    },
    {
        path: "/points",
        element: <SuspensePage name="manager/home" />
    },
    {
        path: "/history",
        element: <SuspensePage name="manager/home" />
    }
]);

export { SuspensePage };
