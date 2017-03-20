import {AssetClass} from './asset-class';

describe('AssetClass', () => {
  it('should create an instance', () => {
    expect(new AssetClass()).toBeTruthy();
  });
  it('should accept values in the constructor', ()=>{
    let assetClass = new AssetClass(1,'custom');
    let id = assetClass.getId();
    let name= assetClass.getName();
    expect(id).toEqual(1);
    expect(name).toEqual('custom');
  });
  it('should accept values in the set methods', ()=>{
    let assetClass = new AssetClass();
    assetClass.setId(1);
    assetClass.setName("custom");
    let id = assetClass.getId();
    let name= assetClass.getName();
    expect(id).toEqual(1);
    expect(name).toEqual('custom');
  });
});
