import { attach } from "effector";
import { api } from "shared/api";
import { closure } from "shared/closure";

export const employeesModel = closure(() => {
    const getByIdFx = attach({ effect: api.getEmployeeById });

    return {
        getByIdFx,
    };
});
