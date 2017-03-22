export class FinancialDataElement {
    private date:string;
    private value:number;

    constructor(date:string,value:number) {
        this.date = date;
        this.value = value;
    }

    setDate(date:string) :void {
        this.date = date;
    }
    getDate() : string {
        return this.date;
    }
    setValue(value:number) : void {
        this.value = value;
    }
    getVlaue() : number {
        return this.value;
    }
}
