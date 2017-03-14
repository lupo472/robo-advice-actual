import {AssetClass} from './asset-class';

export class AssetClassStrategy extends AssetClass {
    private percentage: number;

    constructor(percentage, id, name) {
        super(id, name);
        this.percentage = percentage;
    }

    setPercentage(percentage): void {
        this.percentage = percentage;
    }

    getPercentage(): number {
        return this.percentage;
    }
}
