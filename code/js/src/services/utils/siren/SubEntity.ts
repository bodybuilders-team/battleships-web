import {Link} from "./Link";
import {Action} from "./Action";

/**
 * A sub-entity is an entity that is part of another entity.
 * It is represented as a link with an embedded representation.
 */
export type SubEntity<T> = EmbeddedSubEntity<T> | EmbeddedLink;

/**
 * A sub-entity that is an embedded link.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property href the URI of the link
 * @property type the media type of the link (optional)
 * @property title the title of the link (optional)
 */
export interface EmbeddedLink {
    class?: string[]
    rel: string[]
    href: string
    type?: string
    title?: string
}

/**
 * A sub-entity that is an embedded link.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property href the URI of the link
 * @property type the media type of the link (optional)
 * @property title the title of the link (optional)
 */
export class EmbeddedLink implements EmbeddedLink {
    constructor(link: EmbeddedLink) {
        this.class = link.class;
        this.rel = link.rel;
        this.href = link.href;
        this.type = link.type;
        this.title = link.title;
    }
}

/**
 * A sub-entity that is an embedded representation.
 * Embedded sub-entity representations retain all the characteristics of a standard entity.,
 * including a [rel] property.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 */
export interface EmbeddedSubEntity<T> {
    class?: string[]
    rel: string[]
    properties?: T
    entities?: EmbeddedSubEntity<unknown>[] | EmbeddedLink[]
    actions?: Action[]
    links?: Link[]
}

/**
 * A sub-entity that is an embedded representation.
 * Embedded sub-entity representations retain all the characteristics of a standard entity.,
 * including a [rel] property.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 */
export class EmbeddedSubEntity<T> implements EmbeddedSubEntity<T> {
    constructor(entity: EmbeddedSubEntity<T>) {
        this.class = entity.class;
        this.rel = entity.rel;
        this.properties = entity.properties;
        this.entities = entity.entities;
        this.links = entity.links;
    }
}
