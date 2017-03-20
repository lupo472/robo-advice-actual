import { AssetClassStrategy } from './asset-class-strategy';

export class AssetClassStrategies {
    private assetClassStrategies: AssetClassStrategy[] = [];

    constructor() { }

    setAssetClassStrategies(assetClassStrategies: AssetClassStrategy[]): void {
        this.assetClassStrategies = assetClassStrategies;
    }
    addAssetClassStrategy(assetClassStrategy: AssetClassStrategy): void {
        this.assetClassStrategies.push(assetClassStrategy);
    }
    getAssetClassStrategies(): AssetClassStrategy[] {
        return this.assetClassStrategies;
    }
    createAssetClassStrategies(data): void {
        data.forEach((item, i) => {
            let assetClassStrategy = new AssetClassStrategy(0, item.id, item.name);
            this.addAssetClassStrategy(assetClassStrategy);
        });
        console.log("assetClassStrategies",this.getAssetClassStrategies());
    }
}
