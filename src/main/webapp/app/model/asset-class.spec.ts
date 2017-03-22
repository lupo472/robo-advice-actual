import {AssetClass} from './asset-class';

describe('AssetClass', () => {
  it('should create an instance', () => {
    expect(new AssetClass(1,"custom")).toBeTruthy();
  });
  it('should accept values in the constructor', ()=>{
    let assetClass = new AssetClass(1,'custom');
    expect(assetClass.getId()).toEqual(1);
    expect(assetClass.getName()).toEqual('custom');
  });

  it('should assign the rigth colour', ()=>{
    let assetClass = new AssetClass(1,'custom');
    let colour = assetClass.assignColour();
    expect(colour).toEqual("#4dbd74");

  });
  it('should accept values in the set methods', ()=>{
    let assetClass = new AssetClass(1,'custom');
    assetClass.setId(1);
    assetClass.setName("custom");
    let id = assetClass.getId();
    let name= assetClass.getName();
    expect(id).toEqual(1);
    expect(name).toEqual('custom');

  });
});
