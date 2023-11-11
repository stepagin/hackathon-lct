import React from "react";
import { resource } from "../resources";

interface IconProps {
    type: string;
    size?: string;
}

export const Icon: React.FC<IconProps> = ({ type, size }) => {
    const style = size
        ? {
              height: size,
              width: size,
          }
        : {};

    return (
        <div style={style}>
            <img alt="" src={resource(`svg/icons/${type}.svg`)} />
        </div>
    );
};
