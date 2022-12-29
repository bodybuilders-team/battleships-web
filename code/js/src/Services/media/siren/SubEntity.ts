import {Link} from "./Link"
import {Action} from "./Action"
import {throwError} from "../../utils/errorUtils"

/**
 * A sub-entity is an entity that is part of another entity.
 * It is represented as a link with an embedded representation.
 */
export type SubEntity<T> = EmbeddedSubEntity<T> | EmbeddedLink

/**
 * A sub-entity that is an embedded link.
 *
 * @property class the class of the entity (optional)
 * @property rel the relation of the link
 * @property href the URI of the link
 * @property type the media type of the link (optional)
 * @property title the title of the link (optional)
 */
export interface IEmbeddedLink {
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
export class EmbeddedLink implements IEmbeddedLink {
    class?: string[]
    rel: string[]
    href: string
    type?: string
    title?: string

    constructor(link: IEmbeddedLink) {
        this.class = link.class
        this.rel = link.rel
        this.href = link.href
        this.type = link.type
        this.title = link.title
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
export interface IEmbeddedSubEntity<T> {
    class?: string[]
    rel: string[]
    properties?: T
    entities?: SubEntity<any>[]
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
export class EmbeddedSubEntity<T> implements IEmbeddedSubEntity<T> {
    class?: string[]
    rel: string[]
    properties?: T
    entities?: SubEntity<any>[]
    actions?: Action[]
    links?: Link[]

    constructor(entity: IEmbeddedSubEntity<T>) {
        this.class = entity.class
        this.rel = entity.rel
        this.properties = entity.properties
        this.entities = entity.entities
        this.links = entity.links
        this.actions = entity.actions
    }

    /**
     * Gets the link with the given [rels] from [links].
     *
     * @param rels the relations of the link
     * @return the link with the given [rels]
     */
    getLink(...rels: string[]): string {
        return this.links
            ?.find(link => rels.every((rel) => link.rel.includes(rel)))
            ?.href ?? throwError("Link not found")
    }

    /**
     * Gets the action with the given [name] from [actions].
     *
     * @param name the name of the action
     * @return the action with the given [name]
     */
    getAction(name: string): string {
        return this.actions
            ?.find(action => action.name === name)
            ?.href ?? throwError("Action not found")
    }

    /**
     * Gets the links from the actions in a map where the key is the action name and the value is the action link.
     *
     * @return the links from the actions in a map
     */
    getActionLinks(): Map<string, string> {
        const map = new Map<string, string>()

        this.actions?.forEach(action => {
            map.set(action.name, action.href)
        })

        return map
    }

    /**
     * Gets the embedded sub entities with the given [rels] from [entities],
     * appropriately casting to EmbeddedSubEntity of [T].
     *
     * @return the embedded sub entities with the given [rels]
     */
    getEmbeddedSubEntities<T>(): EmbeddedSubEntity<T>[] {
        return this.entities
            ?.filter(entity => !Object.keys(entity).includes("href"))
            .map(entity => new EmbeddedSubEntity<T>(entity as IEmbeddedSubEntity<T>)) ?? []
    }

    /**
     * Gets the embedded links with the given [rels] from [entities],
     * appropriately casting to EmbeddedLink.
     *
     * @param rels the relations of the embedded links
     *
     * @return the embedded links with the given [rels]
     */
    getEmbeddedLinks(...rels: string[]): EmbeddedLink[] {
        const embeddedLinks = this.entities
            ?.filter(entity => Object.keys(entity).includes("href"))
            .map(entity => new EmbeddedLink(entity as IEmbeddedLink)) ?? []

        return embeddedLinks
            .filter(link => rels.every((rel) => link.rel.includes(rel)))
    }
}
