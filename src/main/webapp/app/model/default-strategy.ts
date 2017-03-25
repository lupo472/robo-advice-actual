import { Strategy } from './strategy';
import {IDefaultStrategy} from "./interfaces/idefault-strategy";

export class DefaultStrategy extends Strategy implements IDefaultStrategy {
    name: string;
    risk: number;

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
