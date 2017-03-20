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
  it('should set the name to the assetClass', ()=>{
    let assetClass = new AssetClass(1,'custom');
    assetClass.setName("newCustom");
    expect(assetClass.getName()).toEqual("newCustom");
  });
  it('should assign the rigth colour', ()=>{
    let assetClass = new AssetClass(1,'custom');
    let colour = assetClass.assignColour();
    expect(colour).toEqual("#4dbd74");
  });
});
