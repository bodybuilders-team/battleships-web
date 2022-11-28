/**
 * Returns a promise of an array of two elements where the first element
 * is the error and the second element is the result.
 *
 * @param promise the promise to "convert"
 * @returns the resulting promise of an array
 */
export default function to(promise: Promise<any>): Promise<any[] | [any, any]> {
    return promise
        .then(data => [null, data])
        .catch(err => [err, null]);
}
