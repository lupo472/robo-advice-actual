export class Portfolio {

    private dataset = [];
    private date = [];

    constructor(portfolio: any) {

        let dataset = [];
        let date = [];
        let value = [];
        let percentage = [];
        let name = [];

        portfolio.forEach((item, index) => {

            let portfolioElem = item.list;
            console.log("PORTFOLIOELEM: ", portfolioElem);
            let tendency;

            portfolioElem.forEach(element => {

                let j = element.id - 1;

                if (value[j] == undefined) {
                    value[j] = [];
                }

                value[j][index] = element.value;
                percentage[j] = element.percentage;
                name[j] = element.name;
                if (value[j][index] > value[j][index - 1]) {
                    tendency = "positive";
                } else if (value[j][index] < value[j][index - 1]) {
                    tendency = "negative";
                } else {
                    tendency = "equal";
                }

                dataset[j] = {
                    data: value[j],
                    label: name[j],
                    percentage: percentage[j],
                    value: value[j][index],
                    tendency: tendency
                };
            });

            date.push(item.date);
        });

        for (let iter = 0; iter < dataset.length - 1; iter++) {
            console.log("Object: ", iter, dataset[iter]);
            if (dataset[iter] == undefined) {
                console.log("splice");
                dataset.splice(iter, 1);
                iter = 0;
            }
        }

        this.dataset = dataset;
        this.date = date;
    }

    getDataset() {
        return this.dataset;
    }

    getDate() {
        return this.date;
    }

}
