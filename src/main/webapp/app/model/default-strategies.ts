import { AssetClassStrategy } from './asset-class-strategy';
import { DefaultStrategy } from './default-strategy';

export class DefaultStrategies {

    private defaultStrategies: DefaultStrategy[] = [];
    private currentDefaultStrategy: DefaultStrategy = new DefaultStrategy();
    public arrayPercentages: number[] = [];
    public arrayColor: string[] = [];
    public arrayColors: any[] = [];
    public arrayLabels: string[] = [];

    constructor() {
    }

    setDefaultStrategies(defaultStrategies: DefaultStrategy[]): void {
        this.defaultStrategies = defaultStrategies;
    }

    addDefaultStrategy(defaultStrategy: DefaultStrategy): void {
        this.defaultStrategies.push(defaultStrategy);
    }

    getDefaultStrategies(): DefaultStrategy[] {
        return this.defaultStrategies;
    }
    createDefaultStrategies(data): void {
        data.forEach((item, i) => {
            let defaultStrategy = new DefaultStrategy();
            defaultStrategy.setName(item.name);
            item.list.forEach((element, i) => {
                defaultStrategy.addAssetClassStrategy(new AssetClassStrategy(element.percentage,
                    element.id, element.name));
            });
            this.addDefaultStrategy(defaultStrategy);
        });
    }
    createDefaultStrategyForChart(strategy): void {
        for (let assetClassStrategy of strategy.list) {
            this.arrayPercentages.push(assetClassStrategy.percentage);
            this.arrayLabels.push(assetClassStrategy.name);
            this.arrayColor.push(assetClassStrategy.assignColour());
            this.arrayColors = [{ backgroundColor: this.arrayColor, borderWidth: 3 }];
        }

    }
    setCurrentDefaultStrategy(defaultStrategy: DefaultStrategy) {
      this.currentDefaultStrategy = defaultStrategy;
      console.log("DEFAULTSTRATEGY",this.currentDefaultStrategy);
    }
    getCurrentDefaultStrategy() : DefaultStrategy {
      return this.currentDefaultStrategy;
    }

}
