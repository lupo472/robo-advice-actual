import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';
import { DefaultStrategy } from './default-strategy';

export class Strategies {
  private strategies:Strategy[] = [];
  private currentStrategy: Strategy = new Strategy();

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
  setCurrentStrategy(strategy: Strategy) {
    this.currentStrategy = strategy;
    console.log("DEFAULTSTRATEGY",this.currentStrategy);
  }
  getCurrentStrategy() : Strategy {
    return this.currentStrategy;
  }
}
