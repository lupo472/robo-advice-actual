import { AssetClassStrategy } from './asset-class-strategy';

export class PortfolioElem extends AssetClassStrategy {
    private value: number;

    constructor(portfolioelem: any) {
        this.value = portfolioelem.value;
    }

    getValue(): number {
        return this.value;
    }

    setValue(value: number): void {
        this.value = value;
    }
}
