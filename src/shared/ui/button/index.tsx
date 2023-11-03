import React from "react";
import { UiElementProps } from "../props";

import styles from "./style.module.css";

interface ButtonProps extends React.PropsWithChildren<UiElementProps> {
    type?: "primary" | "secondary";
}

export const Button: React.FC<ButtonProps> = ({
    children,
    className: classes,
    type,
}) => {
    type ??= "primary";
    classes = classes === undefined ? "" : ` ${classes}`;

    const className = `${styles.button} ${styles[type]}${classes}`;

    return <button className={className}>{children}</button>;
};
