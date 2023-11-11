import React, { useEffect, useState } from "react";

import { Page } from "shared/ui";

const LoadingPage: React.FC = () => {
    const [show, setShow] = useState(false);

    useEffect(() => {
        setTimeout(() => {
            setShow(true);
        }, 300);
    }, []);

    return show ? <Page>Загрузка...</Page> : null;
};

export default LoadingPage;
