import { AssetClassStrategy } from './asset-class-strategy';

export class Strategy {
    protected list: AssetClassStrategy[];

    constructor() {
    }

    setStrategyArray(strategyArray: AssetClassStrategy[]): void {
        this.list = strategyArray;
    }

    addAssetClassStrategy(assetClassStrategy: AssetClassStrategy): void {
        this.list.push(assetClassStrategy);
    }

    getStrategyArray(): AssetClassStrategy[] {
        return this.list;
    }
    // setCustomStrategyToSend(customStrategy:CustomStrategy){
    //
    // }
    // setDefaultStrategyToSend(defaultStrategies:DefaultStrategies){
    //   let strategies = defaultStrategies.getDefaultStrategies();
    // }
}
