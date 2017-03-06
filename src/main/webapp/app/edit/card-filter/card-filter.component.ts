import { Component, OnInit, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-card-filter',
  templateUrl: './card-filter.component.html',
  styleUrls: ['./card-filter.component.scss']
})
export class CardFilterComponent implements OnInit {
@Input() name;

val:number;
  constructor() {
    this.val=0;
  }

  ngOnInit() {
    console.log(this.name);

  }


}
