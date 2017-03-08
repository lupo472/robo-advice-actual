export class AssetClass {
  
  id:number;
  name:string;
  
  constructor(id,name) {
    this.id = id;
    this.name = name;
  }

  setId(id){
    this.id = id;
  }

  setName(name) {
    this.name = name;
  }

  getId() {
    return this.id;
  }

  getName() {
    return this.name;
  }
}
