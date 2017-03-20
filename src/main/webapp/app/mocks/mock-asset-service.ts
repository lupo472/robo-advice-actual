import { Injectable, Inject } from '@angular/core';

@Injectable()
export class MockAssetService {
    getAssetClassStrategies() {
        let data = [
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
        ];
        return data;
    }
}
