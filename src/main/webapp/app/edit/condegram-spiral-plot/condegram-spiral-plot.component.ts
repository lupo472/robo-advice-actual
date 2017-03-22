import { Component, OnInit } from '@angular/core';
import * as d3 from 'd3';

@Component({
  selector: 'app-condegram-spiral-plot',
  templateUrl: './condegram-spiral-plot.component.html',
  styleUrls: ['./condegram-spiral-plot.component.scss']
})
export class CondegramSpiralPlotComponent implements OnInit {

  constructor() { }
  width = 500;
  height = 500;
  start = 0;
  end = 2.25;
  numSpirals = 3;
  margin = {top:50,bottom:50,left:50,right:50};

  ngOnInit() {}

}
