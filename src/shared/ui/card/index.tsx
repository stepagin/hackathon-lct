import React from "react";

import { UiElementProps } from "../props";

import styles from "./style.module.css";

interface CardProps extends React.PropsWithChildren<UiElementProps> {

}

export const Card: React.FC<CardProps> = ({ children, className: classes }) => {
    classes = classes === undefined ? "" : ` ${classes}`;
    const className = `${styles.card}${classes}`;
    return <div className={className}>
        {children}
    </div>
}