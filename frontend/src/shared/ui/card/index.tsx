import React from "react";

import { UiElementProps } from "../props";

import styles from "./style.module.css";

interface CardProps extends React.PropsWithChildren<UiElementProps> {
    loading?: boolean;
}

export const Card: React.FC<CardProps> = ({
    children,
    className: classes,
    loading,
}) => {
    const loadingClass = loading ? styles.loading : "";
    const className = `${styles.card} ${loadingClass} ${classes}`.replace(
        /\s+/g,
        " "
    );
    return <div className={className}>{children}</div>;
};
