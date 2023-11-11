import React, { ReactNode } from "react";

import styles from "./style.module.css";

interface TableProps<T extends Record<string, ReactNode>, K extends keyof T> {
    data: T[];
    displayColumns: Record<K, string>;
}

export const ModelTable = <T extends {}, K extends keyof T>({
    data,
    displayColumns,
}: TableProps<T, K>) => {
    return (
        <table className={styles.table}>
            <thead>
                <tr>
                    {Object.keys(displayColumns).map((key, index) => (
                        <th key={index}>{displayColumns[key as K]}</th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {data.map((row, index) => (
                    <tr key={index}>
                        {Object.keys(displayColumns).map((key, index) => (
                            <td key={index}>{row[key as K] as ReactNode}</td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
};
