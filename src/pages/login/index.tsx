import React, { useEffect } from "react";

import { Page } from "shared/ui";
import { LoginWidget } from "widgets";

import styles from "./style.module.css";
import { useNavigate, useSearchParams } from "react-router-dom";
import { loginModel } from "features/login";
import { useStore } from "effector-react";

const LoginPage: React.FC = () => {
    const [params] = useSearchParams();
    const navigate = useNavigate();

    const user = useStore(loginModel.$user);
    
    useEffect(() => {
        if (user !== null) {
            navigate(params.get("next") ?? "/");
        }
    });

    return (
        <Page className={styles.login}>
            <LoginWidget />
        </Page>
    );
};

export default LoginPage;
