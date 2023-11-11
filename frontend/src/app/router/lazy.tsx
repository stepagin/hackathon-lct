import React from "react";

import LoadingPage from "pages/loading";

const lazyPage = (name: string): React.FC =>
    React.lazy(() => import(`pages/${name}`));

interface SuspensePageProps {
    name: string;
}

export const SuspensePage: React.FC<SuspensePageProps> = ({ name }) => {
    const Page = lazyPage(name);

    return (
        <React.Suspense fallback={<LoadingPage />}>
            <Page />
        </React.Suspense>
    );
};
