import {AssetClass} from './asset-class';

export class AssetClassStrategy extends AssetClass {
    private percentage: number;

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
