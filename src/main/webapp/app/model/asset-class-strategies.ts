import { AssetClassStrategy } from './asset-class-strategy';

export class AssetClassStrategies {
    private assetClassStrategies: AssetClassStrategy[] = [];

    constructor() {}

    private setAssetClassStrategies(assetClassStrategies: AssetClassStrategy[]): void {
        this.assetClassStrategies = assetClassStrategies;
    }
    private addAssetClassStrategies(assetClassStrategy: AssetClassStrategy): void {
        this.assetClassStrategies.push(assetClassStrategy);
    }
    getAssetClassStrategies(): AssetClassStrategy[] {
        console.log("hghghgghghgh",this.assetClassStrategies);
        return this.assetClassStrategies;
    }
    createAssetClassStrategies(data): void {
        data.forEach((item, i) => {
            let assetClassStrategy = new AssetClassStrategy(0, item.id, item.name);
            this.addAssetClassStrategies(assetClassStrategy);
        });
    }
    private resetAssetClassStrategies(){
        this.assetClassStrategies = [];
    }
}
