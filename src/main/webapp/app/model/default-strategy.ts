import { Strategy } from './strategy';

export class DefaultStrategy extends Strategy {
    private name: string;

    constructor(name: string) {
        super();
        this.name = name;
        this.list = [];
    }
    setName(name): void {
        this.name = name;
    }
    getName(): string {
        return name;
    }

}
