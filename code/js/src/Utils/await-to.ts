/**
 * Returns a promise of an array of two elements where the first element
 * is the error and the second element is the result.
 *
 * @param promise the promise to "convert"
 * @returns the resulting promise of an array
 */
export default function to<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
    return promise
        .then<[null, T]>(data => [null, data])
        .catch<[E, null]>(err => [err, null])
}
