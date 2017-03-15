import { DefaultStrategy } from './default-strategy';

export class DefaultStrategies {

    private defaultStrategies: DefaultStrategy[];

    constructor() { }

    setDefaultStrategies(defaultStrategies: DefaultStrategy[]): void {
        this.defalutStrategies = defaultStrategies;
    }

    addDefaultStrategy(defaultStrategy: DefaultStrategy): void {
        this.defalutStrategies.push(defaultStrategy);
    }

    getDefaultStrategies(): DefaultStrategy[] {
        return this.defalutStrategies;
    }
}
