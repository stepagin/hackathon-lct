import React, { ChangeEvent, FormEvent, useState } from "react";

import { Button, Card, Logo } from "shared/ui";

import styles from "./style.module.css";
import { InputGroup } from "shared/ui";
import { useEvent, useStore } from "effector-react";
import { loginModel } from "features/login";
import { Link } from "react-router-dom";

export const LoginWidget: React.FC = () => {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");

    const someEmpty = login.trim() === "" || password.trim() === "";

    const pending = useStore(loginModel.loginFx.pending);
    const error = useStore(loginModel.$error);
    const loginEvent = useEvent(loginModel.loginFx);
    const resetError = useEvent(loginModel.resetError);

    const handleLoginChange = (e: ChangeEvent<HTMLInputElement>) => {
        setLogin(e.target.value);
        resetError();
    };

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
        resetError();
    };

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();
        loginEvent({ login, password });
    }

    return (
        <Card loading={pending} className={styles.widget}>
            <form onSubmit={handleSubmit}>
                <Logo />

                <h1 className={styles.title}>Войти в систему</h1>

                <InputGroup
                    id="login"
                    type="text"
                    placeholder="Введите имя пользователя"
                    error={error}
                    value={login}
                    onChange={handleLoginChange}
                >
                    Логин
                </InputGroup>
                <InputGroup
                    id="password"
                    type="password"
                    placeholder="Введите пароль"
                    error={error}
                    value={password}
                    onChange={handlePasswordChange}
                >
                    Пароль
                </InputGroup>

                {error && <span className={styles.error}>Неправильный логин или пароль</span>}
                <Link to="/">
                    <Button type="submit" disabled={someEmpty || error}>Войти в систему</Button>
                </Link>
            </form>
        </Card>
    );
};
