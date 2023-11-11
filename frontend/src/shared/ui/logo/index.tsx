import React from "react";
import { resource } from "../resources";

import styles from "./style.module.css";

export const Logo: React.FC = () => {
    return (
        <div className={styles.logo}>
            <img
                alt="Логотип Совкомбанка"
                src={resource("svg/sovcombank-logo.svg")}
            />
        </div>
    );
};
