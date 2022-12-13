/**
 * A link A navigational transition.
 *
 * @property rel the relation of the link
 * @property class the class of the link (optional)
 * @property href the URI of the link
 * @property title the title of the link (optional)
 * @property type the media type of the link (optional)
 */
export interface ILink {
    rel: string[];
    class?: string[];
    href: string;
    title?: string;
    type?: string;
}

/**
 * A link A navigational transition.
 *
 * @property rel the relation of the link
 * @property class the class of the link (optional)
 * @property href the URI of the link
 * @property title the title of the link (optional)
 * @property type the media type of the link (optional)
 */
export class Link implements ILink {
    rel: string[];
    class?: string[];
    href: string;
    title?: string;
    type?: string;

    constructor(link: ILink) {
        this.rel = link.rel;
        this.class = link.class;
        this.href = link.href;
        this.title = link.title;
        this.type = link.type;
    }
}
