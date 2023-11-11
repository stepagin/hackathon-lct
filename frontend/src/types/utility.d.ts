type RequireFields<T, K extends keyof T> = T & Pick<T, K>;

type Optional<T> = T | null;
