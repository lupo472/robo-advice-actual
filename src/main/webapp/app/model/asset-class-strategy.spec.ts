import {AssetClassStrategy} from './asset-class-strategy';

describe('AssetClassStrategy', () => {
    it('should create an instance', () => {
        expect(new AssetClassStrategy(0, 1, "custom")).toBeTruthy();
    });
    it('should accept values in the constructor', () => {
        let assetClassStrategy = new AssetClassStrategy(0, 1, 'custom');
        expect(assetClassStrategy.getPercentage()).toEqual(0);
        expect(assetClassStrategy.getId()).toEqual(1);
        expect(assetClassStrategy.getName()).toEqual('custom');
    });
    it('should change the percentage to 50', () => {
        let assetClassStrategy = new AssetClassStrategy(0, 1, 'custom');
        assetClassStrategy.setPercentage(50);
        expect(assetClassStrategy.getPercentage()).toEqual(50);
    });
});
