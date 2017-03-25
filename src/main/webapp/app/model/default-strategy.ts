import { Strategy } from './strategy';

export class DefaultStrategy extends Strategy {
    private name: string;
    private risk: number;

    constructor() {
        super();
        this.list = [];
    }
    setName(name: string): void {
        this.name = name;
    }
    getName(): string {
        return this.name;
    }

}
