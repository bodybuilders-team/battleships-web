export interface Link {
    rel: string[],
    class?: string[],
    href: string,
    title?: string,
    type?: string
}

export class Link {
    constructor(link: Link) {
        this.rel = link.rel;
        this.class = link.class;
        this.href = link.href;
        this.title = link.title;
        this.type = link.type;
    }
}