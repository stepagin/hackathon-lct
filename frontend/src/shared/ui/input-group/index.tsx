import React from "react";

import styles from "./style.module.css";

type InputGroupProps = React.PropsWithChildren<
    RequireFields<React.HTMLProps<HTMLInputElement>, "id">
> & { error?: boolean };

export const InputGroup: React.FC<InputGroupProps> = ({
    id,
    children,
    error,
    ...props
}) => {
    const errorClass = error ? " " + styles.error : "";
    
    return (
        <div className={styles.group}>
            <label className={errorClass} htmlFor={id}>
                {children ?? ""}
            </label>
            <input className={errorClass} id={id} {...props} />
        </div>
    );
};
