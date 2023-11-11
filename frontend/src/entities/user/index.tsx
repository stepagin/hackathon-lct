import React from "react";
import { UserEntity } from "shared/api";
import { Avatar, Icon } from "shared/ui";

import styles from "./style.module.css";

export interface UserProps {
    user: UserEntity;
}

export const UserCard: React.FC<UserProps> = ({ user }) => {
    const fullName = `${user.firstName} ${user.lastName}`;

    return (
        <div className={styles.card}>
            <Avatar user={user} />
            <span>{fullName}</span>
            <Icon size="1rem" type="select" />
        </div>
    );
};
