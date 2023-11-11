import React from "react";
import { UiElementProps } from "../props";

import styles from "./style.module.css";

interface ButtonProps extends React.PropsWithChildren<UiElementProps> {
    color?: "primary" | "secondary";
    type?: "button" | "submit";
    disabled?: boolean;
}

export const Button: React.FC<ButtonProps> = ({
    children,
    className: classes,
    color,
    type,
    disabled,
}) => {
    color ??= "primary";
    classes = classes === undefined ? "" : ` ${classes}`;

    const className = `${styles.button} ${styles[color]}${classes}`;

    return (
        <button className={className} type={type} disabled={disabled}>
            {children}
        </button>
    );
};
