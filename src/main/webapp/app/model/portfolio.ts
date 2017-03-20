export class Portfolio {

    private data = {};

    constructor(portfolio: any) {

        let datasets = [];
        let labels = [];
        let value = [];
        let percentage = [];
        let name = [];

        let colors = ['#4dbd74', '#63c2de', '#f8cb00', '#f86c6b'];

        portfolio.forEach((item, index) => {

            let portfolioElem = item.list;
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
                    tendency = "up";
                } else if (value[j][index] < value[j][index - 1]) {
                    tendency = "down";
                } else {
                    tendency = "=";
                }

                datasets[j] = {
                    data: value[j],
                    label: name[j],
                    backgroundColor: this.convertHex(colors[j], 30),
                    borderColor: colors[j],
                    pointBackgroundColor: colors[j],
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(148,159,177,0.8)',
                    percentage: percentage[j],
                    value: value[j][index],
                    tendency: tendency
                };
            });

            labels.push(item.date);
        });

        for (let i = 0; i < value.length; i++) {
            if (value[i] != undefined) {
                for (let j = 0; j < value[i].length; j++) {
                    if (value[i][j] == undefined) {
                        value[i][j] = 0;
                    }
                }

                datasets[i].data = value[i];
            }
        }

        console.log("DATASETS: ", datasets);

        let datasplice = [];

        for (let iter = 0; iter < datasets.length; iter++) {
            if (datasets[iter] != undefined) {
                datasplice.push(datasets[iter]);
            }
        }

        this.data = {datasets: datasplice, labels: labels};
        console.log("DATA MAPPED: ", this.data);
    }

    getData() {
        return this.data;
    }

    //convert Hex to RGBA
    public convertHex(hex: string, opacity: number) {
        hex = hex.replace('#', '');
        let r = parseInt(hex.substring(0, 2), 16);
        let g = parseInt(hex.substring(2, 4), 16);
        let b = parseInt(hex.substring(4, 6), 16);

        let rgba = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
        return rgba;
    }
}
