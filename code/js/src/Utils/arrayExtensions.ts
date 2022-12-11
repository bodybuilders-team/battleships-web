export {}

declare global {
    interface ReadonlyArray<T> {

        /**
         * Replaces the elements in the array that match the predicate with the result of the newFunc.
         *
         * @param predicate the predicate to match elements
         * @param newFunc the function to generate the new value
         */
        replaceIf(predicate: (value: T) => boolean, newFunc: (value: T) => T): ReadonlyArray<T>;
    }

    interface Array<T> {

        /**
         * Replaces the elements in the array that match the predicate with the result of the newFunc.
         *
         * @param predicate the predicate to match elements
         * @param newFunc the function to generate the new value
         */
        replaceIf(predicate: (value: T) => boolean, newFunc: (value: T) => T): Array<T>;
    }
}

Array.prototype.replaceIf = function (predicate, newFunc) {
    return this.map((value) => {
        return predicate(value) ? newFunc(value) : value;
    });
}
