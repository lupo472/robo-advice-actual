import { Component, OnInit } from '@angular/core';
import {Cookie} from "ng2-cookies";

import { User } from '../model/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './full-layout.component.html'
})
export class FullLayoutComponent implements OnInit {
  
  user:User;

  constructor(private UserService:UserService) { }
  
  ngOnInit(): void { 
    
    if(Cookie.check('email')){
        this.UserService.setUser({email: Cookie.get('email'), 
                      password: Cookie.get('password'), 
                      id: Cookie.get('id')});
      }
    
    this.user = this.UserService.getUser();
  }

  public disabled:boolean = false;
  public status:{isopen:boolean} = {isopen: false};

  public toggled(open:boolean):void {
    console.log('Dropdown is now: ', open);
  }

  public logout():void{
    Cookie.deleteAll();
  }

  public toggleDropdown($event:MouseEvent):void {
    $event.preventDefault();
    $event.stopPropagation();
    this.status.isopen = !this.status.isopen;
  }
  
  // social box charts

  public socialChartData1:Array<any> = [
    {
      data: [65, 59, 84, 84, 51, 55, 40],
      label: 'Facebook'
    }
  ];
  
  public socialChartLabels:Array<any> = ['January','February','March','April','May','June','July'];
  public socialChartOptions:any = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      xAxes: [{
        display:false,
      }],
      yAxes: [{
        display:false,
      }]
    },
    elements: {
      line: {
        borderWidth: 2
      },
      point: {
        radius: 0,
        hitRadius: 10,
        hoverRadius: 4,
        hoverBorderWidth: 3,
      }
    },
    legend: {
      display: false
    }
  };
  public socialChartColours:Array<any> = [
    {
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointHoverBackgroundColor: '#fff'
    }
  ];
  public socialChartLegend:boolean = false;
  public socialChartType:string = 'line';
  

  
}
