/**
 * An action that can be performed on an entity.
 *
 * @property name the name of the action
 * @property class the class of the action (optional)
 * @property method the HTTP method of the action (optional)
 * @property href the URI of the action
 * @property title the title of the action (optional)
 * @property type the media type of the action (optional)
 * @property fields the fields of the action (optional)
 */
export interface Action {
    name: string;
    class?: string[]
    method?: string;
    href: string;
    title?: string;
    type?: string;
    fields?: Field[];
}

/**
 * An action that can be performed on an entity.
 *
 * @property name the name of the action
 * @property class the class of the action (optional)
 * @property method the HTTP method of the action (optional)
 * @property href the URI of the action
 * @property title the title of the action (optional)
 * @property type the media type of the action (optional)
 * @property fields the fields of the action (optional)
 */
export class Action {
    constructor(action: Action) {
        this.name = action.name;
        this.class = action.class;
        this.method = action.method;
        this.href = action.href;
        this.title = action.title;
        this.type = action.type;
        this.fields = action.fields;
    }
}

/**
 * A field that is part of an action.
 *
 * @property name the name of the field
 * @property class the class of the field (optional)
 * @property type the type of the field (optional)
 * @property value the value of the field (optional)
 * @property title the title of the field (optional)
 */
export interface Field {
    name: string;
    value?: string
    type?: string
}

/**
 * A field that is part of an action.
 *
 * @property name the name of the field
 * @property class the class of the field (optional)
 * @property type the type of the field (optional)
 * @property value the value of the field (optional)
 * @property title the title of the field (optional)
 */
export class Field {
    constructor(field: Field) {
        this.name = field.name;
        this.value = field.value;
        this.type = field.type;
    }
}
