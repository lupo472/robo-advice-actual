import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {IDefaultStrategy} from "../model/interfaces/idefault-strategy";

export const strategiesMock : Observable<IDefaultStrategy[]> = Observable.of([
        {
            list:[{
                percentage:100,
                id: 1,
                name: 'Bonds'
            }],
            name: 'Bonds',
            risk:1
        },
        {
            list:[{
                    percentage:65,
                    id: 1,
                    name: 'Bonds'
                },
                {
                    percentage:15,
                    id: 1,
                    name: 'Forex'
                },
                {
                    percentage:10,
                    id: 1,
                    name: 'Stocks'
                },
                {
                    percentage:10,
                    id: 1,
                    name: 'Commodities'
                }
            ],
            name: 'Income',
            risk:2
        },
        {
            list:[{
                percentage:30,
                id: 1,
                name: 'Bonds'
            },
                {
                    percentage:20,
                    id: 1,
                    name: 'Forex'
                },
                {
                    percentage:30,
                    id: 1,
                    name: 'Stocks'
                },
                {
                    percentage:20,
                    id: 1,
                    name: 'Commodities'
                }
            ],
            name: 'Balanced',
            risk:3
        },
        {
            list:[{
                percentage:20,
                id: 1,
                name: 'Bonds'
            },
                {
                    percentage:10,
                    id: 1,
                    name: 'Forex'
                },
                {
                    percentage:60,
                    id: 1,
                    name: 'Stocks'
                },
                {
                    percentage:10,
                    id: 1,
                    name: 'Commodities'
                }
            ],
            name: 'Growth',
            risk:4
        },
        {
            list:[{
                percentage:100,
                id: 1,
                name: 'Stocks'
            }],
            name: 'Stocks',
            risk:5
        }
]);
