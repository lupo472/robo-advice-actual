import {FinancialData} from "./financial-data";
import {FinancialDataElement} from "./financial-data-element";
export class FinancialDataSet {
    private financialDataSet : FinancialData[];

    constructor() {
        this.financialDataSet = [];
    }

    setFinancialDataSet(){

    }
    getFinancialDataSet(){
        return this.financialDataSet;
    }
    createFinancialDataSet(data){
        data.forEach((item)=>{
            let financialData = new FinancialData(item.assetClass.id,item.assetClass.name);
            item.list.forEach((element)=>{
                let financialDataElement = new FinancialDataElement(element.date,element.value);
                financialData.addFinancialDataElement(financialDataElement);
            });
            financialData.createLineChart();
            this.financialDataSet.push(financialData);
        });

        console.log("financialDataSet",this.financialDataSet);
    }


}
