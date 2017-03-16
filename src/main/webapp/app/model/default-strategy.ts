import { Strategy } from './strategy';

export class DefaultStrategy extends Strategy {
    private name: string;

    constructor() {
        super();
        //this.name = name;
        this.list = [];
    }
    setName(name: string): void {
        this.name = name;
    }
    getName(): string {
        return this.name;
    }

}
