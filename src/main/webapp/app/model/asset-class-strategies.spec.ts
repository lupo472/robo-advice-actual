import {AssetClassStrategies} from './asset-class-strategies';
import {AssetClassStrategy} from './asset-class-strategy';


describe('AssetClassStrategies', () => {
    it('should create an instance', () => {
        expect(new AssetClassStrategies()).toBeTruthy();
    });
    it('should add an assetClassStrategy to assetClassStrategies', () => {
        let assetClassStrategy = new AssetClassStrategy(0, 1, 'custom');
        let assetClassStrategies = new AssetClassStrategies();
        assetClassStrategies.addAssetClassStrategy(assetClassStrategy);
        expect(assetClassStrategies.getAssetClassStrategies()).toContain(assetClassStrategy);
    });
    it('should create the array of assetClassStrategy ', () => {
        let data = [{
                id:1,
                name:"bounds"
            },
            {
                id:2,
                name:"forex"
            }
        ];

        let assetClassStrategies = new AssetClassStrategies();
        assetClassStrategies.createAssetClassStrategies(data);
        expect(assetClassStrategies.getAssetClassStrategies().length).toBeGreaterThan(0);
    });
});
