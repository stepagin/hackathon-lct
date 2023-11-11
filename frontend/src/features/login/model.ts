import { attach, createEvent, createStore } from "effector";
import { api } from "shared/api";
import { UserEntity } from "shared/api/models";
import { closure } from "shared/closure";

export const loginModel = closure(() => {
    const loginFx = attach({ effect: api.login });

    const initialUser = JSON.parse(localStorage.getItem("user") || "null");

    const $user = createStore<Optional<UserEntity>>(initialUser);
    const $error = createStore<boolean>(false);

    const resetError = createEvent();

    $user.on(loginFx.doneData, (_, { user }) => {
        if (user !== null) {
            localStorage.setItem("user", JSON.stringify(user));
        }

        return user;
    });

    $error.on(resetError, () => false);
    $error.on(loginFx.doneData, (_, { success }) => !success);
    $error.on(loginFx.failData, () => true);

    return {
        loginFx,
        $error,
        $user,
        resetError
    }
});
