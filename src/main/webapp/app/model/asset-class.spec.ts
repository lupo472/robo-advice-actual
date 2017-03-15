import {AssetClass} from './asset-class';

describe('AssetClass', () => {
  it('should create an instance', () => {
    expect(new AssetClass()).toBeTruthy();
  });
  it('should accept values in the constructor', ()=>{
    let assetClass = new AssetClass(1,'custom');
    expect(assetClass.id).toEqual(1);
    expect(assetClass.name).toEqual('custom');
  });
});
