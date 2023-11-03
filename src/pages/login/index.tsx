import React from "react";

import { Page } from "shared/ui";
import { LoginWidget } from "widgets";

import styles from "./style.module.css";

const LoginPage: React.FC = () => {
    return (
        <Page className={styles.login}>
            <LoginWidget />
        </Page>
    );
};

export default LoginPage;
