import { Injectable, Inject } from '@angular/core';

@Injectable()
export class MockAppService {
    constructor(){}

    getDefaultStrategySet() {
        let data = [{
                list: [
                    {
                        id:1,
                        name:"bonds",
                        percentage:95
                    },
                    {
                        id:4,
                        name:"commodities",
                        percentage:5
                    }
                ],
                name: "bonds",
                risk: 1

            },
            {
                list: [
                    {
                        id:1,
                        name:"bonds",
                        percentage:65
                    },
                    {
                        id:2,
                        name:"forex",
                        percentage:15
                    },
                    {
                        id:3,
                        name:"stocks",
                        percentage:10
                    },
                    {
                        id:4,
                        name:"commodities",
                        percentage:15
                    }
                ],
                name: "income",
                risk: 2

            }/*,
            {
                list: [],
                name: "balanced",
                risk: 1

            },
            {
                list: [],
                name: "bonds",
                risk: 1

            },*/
        ];
        return data;

    }

}
