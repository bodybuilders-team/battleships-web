export interface Field {
    name: string;
    value?: string
    type?: string
}

export class Field {
    constructor(field: Field) {
        this.name = field.name;
        this.value = field.value;
        this.type = field.type;
    }
}
