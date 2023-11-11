import React from "react";

import { useLoginRedirect } from "features/login";
import HomePage from "pages/manager/home";
import { MOCK_USER } from "shared/api";

const MainPage: React.FC = () => {
    // const user = useLoginRedirect();
    const user = MOCK_USER;
    
    if (user === null) {
        return null;
    }

    return user.role === "MANAGER" ? (
        <HomePage />
    ) : null;
};

export default MainPage;
