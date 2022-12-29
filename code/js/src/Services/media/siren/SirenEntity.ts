import {Link} from "./Link"
import {Action} from "./Action"
import {EmbeddedLink, EmbeddedSubEntity, IEmbeddedLink, IEmbeddedSubEntity, SubEntity} from "./SubEntity"
import {throwError} from "../../utils/errorUtils"

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
export interface ISirenEntity<T> {
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
export class SirenEntity<T> implements ISirenEntity<T> {
    class?: string[]
    properties?: T
    entities?: SubEntity<unknown>[]
    actions?: Action[]
    links?: Link[]
    title?: string

    constructor(entity: ISirenEntity<T>) {
        this.class = entity.class
        this.properties = entity.properties
        this.entities = entity.entities
        this.actions = entity.actions
        this.links = entity.links
        this.title = entity.title
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
            .map(entity => new EmbeddedSubEntity(entity as IEmbeddedSubEntity<T>)) ?? []
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

export const sirenMediaType = "application/vnd.siren+json"
