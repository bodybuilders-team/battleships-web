export {}

declare global {
    interface ReadonlyArray<T> {
        replaceIf(predicate: (value: T) => boolean, newFunc: (value: T) => T): ReadonlyArray<T>;
    }

    interface Array<T> {
        replaceIf(predicate: (value: T) => boolean, newFunc: (value: T) => T): Array<T>;
    }
}

Array.prototype.replaceIf = function(predicate, newFunc) {
    return this.map((value) => {
        return predicate(value) ? newFunc(value) : value;
    })
}