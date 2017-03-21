import { Component, OnInit, Renderer } from '@angular/core';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {


  constructor() { }

  ngOnInit() {
    console.log("dashboard");
  }

}
