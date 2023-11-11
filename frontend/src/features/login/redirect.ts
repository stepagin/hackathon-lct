import { useStore } from "effector-react";
import { useLocation, useNavigate } from "react-router-dom";
import { loginModel } from "./model";
import { UserEntity } from "shared/api";
import { useEffect } from "react";

export const useLoginRedirect = (): Optional<UserEntity> => {
    const user = useStore(loginModel.$user);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        if (user === null) {
            navigate(`/login?next=${location.pathname}`);
        }
    });

    return user;
}
