/**
 * Gets the position of an element relative to the document instead of the viewport.
 *
 * @param element the element to get the position of
 * @returns {Position} the position of the element
 */
export function getPosition(element: HTMLElement) {
    const clientRect = element.getBoundingClientRect()

    return {
        left: clientRect.left + document.documentElement.scrollLeft,
        top: clientRect.top + document.documentElement.scrollTop
    }
}
