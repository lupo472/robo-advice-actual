import { DefaultStrategy } from './default-strategy';

export class ExtendedDefaultStrategy  extends DefaultStrategy {
  private amount:number;

  constructor(name) {
    super(name);
  }
  getName() {
    return super.getName();
  }
  setName(name){
    super.setName(name);
  }
  getList(){
    return super.getList();
  }
  setList(list){
    super.setList(list);
  }
}
