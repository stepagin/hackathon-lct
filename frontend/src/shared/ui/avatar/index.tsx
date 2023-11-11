import React from "react";
import { UserEntity } from "shared/api/models";

import styles from "./style.module.css";

interface AvatarProps {
    user: UserEntity;
}

export const Avatar: React.FC<AvatarProps> = ({
    user: { firstName, lastName },
}) => {
    const initials = firstName.charAt(0) + lastName.charAt(0);

    return <div className={styles.avatar}>{initials}</div>;
};
