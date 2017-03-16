import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';
import { DefaultStrategy } from './default-strategy';

export class Strategies {
  private strategies:Strategy[] = [];
  private currentStrategy: Strategy = new Strategy();
  public arrayPercentages: number[] = [];
  public arrayColor: string[] = [];
  public arrayColors: any[] = [];
  public arrayLabels: string[] = [];

  constructor() {
  }

  setStrategies(strategies: Strategy[]): void {
      this.strategies = strategies;
  }

  addStrategy(strategy: Strategy): void {
      this.strategies.push(strategy);
  }

  getStrategies(): Strategy[] {
      return this.strategies;
  }
  createStrategies(data): void {
      data.forEach((item, i) => {
          let defaultStrategy = new DefaultStrategy();
          defaultStrategy.setName(item.name);
          item.list.forEach((element, i) => {
              defaultStrategy.addAssetClassStrategy(new AssetClassStrategy(element.percentage,
                  element.id, element.name));
          });
          this.addStrategy(defaultStrategy);
      });
  }
  createStrategyForChart(strategy): void {
      for (let assetClassStrategy of strategy.list) {
          this.arrayPercentages.push(assetClassStrategy.percentage);
          this.arrayLabels.push(assetClassStrategy.name);
          this.arrayColor.push(assetClassStrategy.assignColour());
          this.arrayColors = [{ backgroundColor: this.arrayColor, borderWidth: 3 }];
      }

  }
  setCurrentStrategy(strategy: Strategy) {
    this.currentStrategy = strategy;
    console.log("DEFAULTSTRATEGY",this.currentStrategy);
  }
  getCurrentStrategy() : Strategy {
    return this.currentStrategy;
  }
}
