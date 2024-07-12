export const apiBaseUrl = import.meta.env.VITE_API_BASE_URL
export const localTokenName = import.meta.env.VITE_LOCAL_TOKEN_NAME
export const headerTokenName = import.meta.env.VITE_HEADER_TOKEN_NAME
export const loginUrl = import.meta.env.VITE_LOGIN_URL === 'false'?false:import.meta.env.VITE_LOGIN_URL