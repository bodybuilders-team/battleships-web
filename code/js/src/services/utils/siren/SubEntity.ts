import {Link} from "./Link";
import {Action} from "./Action";

export interface EmbeddedLink {
    class?: string[]
    rel: string[]
    href: string
    type?: string
    title?: string
}

export class EmbeddedLink implements EmbeddedLink {
    constructor(link: EmbeddedLink) {
        this.class = link.class;
        this.rel = link.rel;
        this.href = link.href;
        this.type = link.type;
        this.title = link.title;
    }
}

export interface EmbeddedSubEntity<T> {
    class?: string[]
    rel: string[]
    properties?: T
    entities?: EmbeddedSubEntity<unknown>[] | EmbeddedLink[]
    actions?: Action[]
    links?: Link[]
}

export class EmbeddedSubEntity<T> implements EmbeddedSubEntity<T> {

    constructor(entity: EmbeddedSubEntity<T>) {
        this.class = entity.class;
        this.rel = entity.rel;
        this.properties = entity.properties;
        this.entities = entity.entities;
        this.links = entity.links;
    }
}