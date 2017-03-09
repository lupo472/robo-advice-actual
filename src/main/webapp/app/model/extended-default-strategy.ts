import { DefaultStrategy } from './default-strategy';

export class ExtendedDefaultStrategy  extends DefaultStrategy {
  private amount:number;

  constructor(name) {
    super(name);
    //this.color = color;
  }
  getList(){
    return super.getList();
  }
  setList(list){
    super.setList(list);
  }
}
