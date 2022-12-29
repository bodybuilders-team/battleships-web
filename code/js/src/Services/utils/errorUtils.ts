/**
 * Throws an error (used to convert a throw new Error into an expression...)
 * @param msg the error message
 */
export function throwError(msg: string): never {
    throw new Error(msg)
}
