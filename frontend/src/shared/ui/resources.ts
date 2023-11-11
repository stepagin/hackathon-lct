export const pathJoin = (...parts: string[]) =>
    parts.join("/").replace(/\/+/g, "/");

export const resource = (path: string): string =>
    pathJoin(process.env.PUBLIC_URL!, path);
