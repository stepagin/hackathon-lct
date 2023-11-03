import React from "react";

import { Button, Card, resource } from "shared/ui";

import styles from "./style.module.css";

export const LoginWidget = () => {
    return (
        <Card className={styles.widget}>
            <form>
                <img
                    alt="Логотип Совкомбанка"
                    src={resource("svg/sovcombank-logo.svg")}
                />
                <label htmlFor="#username">Имя</label>
                <input
                    id="username"
                    type="text"
                    placeholder="Введите имя пользователя"
                />
                <label htmlFor="#password">Пароль</label>
                <input
                    id="password"
                    type="password"
                    placeholder="Введите пароль"
                />
                <Button>Войти в систему</Button>
            </form>
        </Card>
    );
};
