import {IAssetClass} from "../model/interfaces/iasset-class";
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

export const mockAssetClass : Observable<IAssetClass[]> = Observable.of([
        {id: 1, name: 'Bonds'},
        {id: 2, name: 'Forex'},
        {id: 3, name: 'Stocks'},
        {id: 4, name: 'Commodities'}
    ]);
