import {Link} from "./Link";
import {Action} from "./Action";
import {SubEntity} from "./SubEntity";

/**
 * Siren is a specification for representing hypermedia entities in JSON.
 *
 * @see <a href="https://github.com/kevinswiber/siren">Siren Specification</a>
 *
 * @property class the class of the entity (optional)
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 * @property title the title of the entity (optional)
 */
export interface SirenEntity<T> {
    class?: string[]
    properties?: T
    entities?: SubEntity<unknown>[]
    actions?: Action[]
    links?: Link[]
    title?: string
}

/**
 * Siren is a specification for representing hypermedia entities in JSON.
 *
 * @see <a href="https://github.com/kevinswiber/siren">Siren Specification</a>
 *
 * @property class the class of the entity (optional)
 * @property properties the properties of the entity (optional)
 * @property entities the sub-entities of the entity (optional)
 * @property actions the actions that can be performed on the entity (optional)
 * @property links the links to other entities (optional)
 * @property title the title of the entity (optional)
 */
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

export const sirenMediaType = "application/vnd.siren+json";
