import { AssetClassStrategy } from './asset-class-strategy';
import { Strategy } from './strategy';
import { DefaultStrategy } from './default-strategy';

export class Strategies {
  private strategies:Strategy[] = [];
  private currentStrategy: Strategy = new Strategy();


    public period = 30;
    public startdate: Date;
    public chartData=[];
    public dataclass=["bonds","forex","stocks","commodities"]; // da sostituire con il model
    public colorclass=["#4dbd74","#63c2de","#f8cb00","#f86c6b"]; // da sostituire con il model
    public labels=[];
    public dati=[];
    public render: boolean = false;

  constructor() {
      this.startdate=new Date();
  }
  setStrategies(strategies: Strategy[]): void {
      this.strategies = strategies;
  }
  addStrategy(strategy: Strategy): void {
      this.strategies.push(strategy);
  }
  getStrategies(): Strategy[] {
      return this.strategies;
  }
  createStrategies(data): void {
      data.forEach((item, i) => {
          let defaultStrategy = new DefaultStrategy();
          defaultStrategy.setName(item.name);
          item.list.forEach((element, i) => {
              defaultStrategy.addAssetClassStrategy(new AssetClassStrategy(element.percentage,
                  element.id, element.name));
          });
          this.addStrategy(defaultStrategy);
      });
  }
  setCurrentStrategy(strategy: Strategy) {
    this.currentStrategy = strategy;
    console.log("DEFAULTSTRATEGY",this.currentStrategy);
  }
  getCurrentStrategy() : Strategy {
    return this.currentStrategy;
  }

    createChartDataHistory(data: any) {
        console.log("data nel model: ",data)
        data.forEach((strategy,i)=>{
            var beginning = new Date(strategy.date);
            console.log("this.startdate :"+this.startdate);
            if(beginning>=this.startdate){
                console.log("strategia compresa nell'intervallo, date:"+beginning);
                this.labels.push(strategy.date);
                strategy.list.forEach((classe,j)=>{
                    var idClasse=classe.id;
                    if(this.dati[idClasse-1]==undefined){
                        this.dati[idClasse-1]=new Array(data.length);
                    }
                    this.dati[idClasse-1][i]=classe.percentage;
                })
            }
        })
        for(var k=0;k<this.dataclass.length;k++){
            if(this.dati[k]==undefined){
                this.dati[k]=new Array(this.labels.length);
            }
        }
        console.log("PRIMA DEGLI ZERI ",this.dati);
        //INSERIMENTO ZERI
        for(var i=0;i<this.dati.length;i++){
            for(var j=0;j<this.dati[i].length;j++){
                if(this.dati[i][j]==undefined){
                    this.dati[i][j]=0;
                }
            }
        }console.log("DOPO GLI ZERI ",this.dati);

        for(var i=0;i<this.dati.length;i++){
            this.chartData.push({data: this.dati[i], label:this.dataclass[i], backgroundColor: this.colorclass[i]});
        }
        console.log("chartData: ",this.chartData);
        return {data:this.chartData,labels:this.labels};
    }
}
