import React from "react";

import styles from "./style.module.css";

import { UiElementProps } from "../props";

interface PageProps extends React.PropsWithChildren<UiElementProps> {
    header?: React.ReactNode;
}

export const Page: React.FC<PageProps> = ({
    header,
    children,
    className: classes,
}) => {
    let className = styles.page;

    if (classes !== undefined) {
        className += ` ${classes}`;
    }

    return (
        <main className={className}>
            <header>{header}</header>
            <article className={styles.content}>{children}</article>
        </main>
    );
};
