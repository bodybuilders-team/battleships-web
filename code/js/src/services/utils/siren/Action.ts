import {Field} from "./Field";

export interface Action {
    name: string;
    class?: string[]
    method?: string;
    href: string;
    title?: string;
    type?: string;
    fields?: Field[];
}

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
