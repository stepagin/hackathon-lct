import React from "react";

import styles from "./style.module.css";

import { UiElementProps } from "../props";

export const Page: React.FC<React.PropsWithChildren<UiElementProps>> = ({
    children,
    className: classes,
}) => {
    let className = styles.page;

    if (classes !== undefined) {
        className += ` ${classes}`;
    }

    return (
        <div className={className}>
            <article className={styles.content}>{children}</article>
        </div>
    );
};
