import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-card-asset',
  templateUrl: './card-asset.component.html',
  styleUrls: ['./card-asset.component.scss']
})
export class CardAssetComponent implements OnInit {
  
   @Input() value;
    
   @Input() lineChartData;
   @Input() lineChartLabels;
   @Input() lineChartOptions;
   @Input() lineChartColours;
   @Input() lineChartLegend;
   @Input() lineChartType;
    
   @Input() barChartData;
   @Input() barChartLabels;
   @Input() barChartOptions;
   @Input() barChartColours;
   @Input() barChartLegend;
   @Input() barChartType;

  constructor() { }

  ngOnInit() {
  }

}
