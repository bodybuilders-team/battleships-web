export class Problem extends Error {
    type: string
    title: string
    status: number
    detail?: string
    instance?: string
    properties?: {
        [key: string]: string
    }

    constructor(param: any) {
        super(param);
        this.type = param.type;
        this.title = param.title;
        this.status = param.status;
        this.detail = param.detail;
        this.instance = param.instance;
        this.properties = param.properties;
        this.message = this.title;
        this.name = this.type;
    }
}
