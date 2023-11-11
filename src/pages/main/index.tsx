import React from "react";

import { useLoginRedirect } from "features/login";
import HomePage from "pages/manager/home";

const MainPage: React.FC = () => {
    const user = useLoginRedirect();
    
    if (user === null) {
        return null;
    }

    return user.role === "MANAGER" ? (
        <HomePage />
    ) : null;
};

export default MainPage;
