import {Link} from "./Link";
import {Action} from "./Action";
import {EmbeddedLink, EmbeddedSubEntity} from "./SubEntity";

export interface SirenEntity<T> {
    class?: string[];
    properties?: T;
    entities?: EmbeddedSubEntity<unknown>[] | EmbeddedLink[]
    actions?: Action[]
    links?: Link[]
    title?: string
}

export class SirenEntity<T> {
    constructor(entity: SirenEntity<T>) {
        this.class = entity.class;
        this.properties = entity.properties;
        this.entities = entity.entities;
        this.actions = entity.actions;
        this.links = entity.links;
        this.title = entity.title
    }
}