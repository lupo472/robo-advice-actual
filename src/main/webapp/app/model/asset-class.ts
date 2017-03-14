export class AssetClass {
  private id: number;
  private name: string;

  constructor(id, name) {
      this.id = id;
      this.name = name;
  }

  setId(id) : void {
      this.id = id;
  }

  setName(name) : void {
      this.name = name;
  }

  getId() : number {
      return this.id;
  }

  getName() : string {
      return this.name;
  }
}
