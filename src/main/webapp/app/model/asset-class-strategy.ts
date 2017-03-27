import {AssetClass} from './asset-class';
import {IAssetClassStrategy} from "./interfaces/iasset-class-strategy";

export class AssetClassStrategy extends AssetClass implements IAssetClassStrategy {
    percentage: number;

    constructor(percentage: number, id: number, name: string) {
        super(id, name);
        this.percentage = percentage;
    }

    setPercentage(percentage: number): void {
        this.percentage = percentage;
    }

    getPercentage(): number {
        return this.percentage;
    }
}
