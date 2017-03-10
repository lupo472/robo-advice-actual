import { Component, OnInit } from '@angular/core';
import { Cookie } from "ng2-cookies";
import { Router } from '@angular/router';
import { User } from '../model/user';
import { AppService } from '../services/app.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './full-layout.component.html'
})
export class FullLayoutComponent implements OnInit {
  
  user:User;
  check:number;
  constructor(private UserService: UserService, private AppService:AppService, private router: Router) { }

  ngOnInit(): void {

/*
    if(Cookie.check('id')){
      this.UserService.setUser({user:{
        id: Cookie.get('id'), email: "a@a", password: "aaaaaa"}});
    }else{
      console.log("Not Logged");
      this.router.navigate(['pages/login']);
    }*/
    this.UserService.authenticate().subscribe(res => this.checkAuth(res));
    this.user = this.UserService.getUser();
    this.AppService.getCapitalPeriod(this.user.id, 0).subscribe(res => this.assignCapitalData(res));
  }

  checkAuth(res):void{
    console.log(res.response);
    if(res.response == 0){
      Cookie.delete("id");
      Cookie.delete("token");
      this.router.navigate(['pages/login']);
    }

  }

  public isLoaded: boolean = false;
  public disabled: boolean = false;
  public status: { isopen: boolean } = { isopen: false };
  
  public response = 'Data not yet available'

  public logout(): void {
    Cookie.deleteAll();
  }

  public toggleDropdown($event: MouseEvent): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.status.isopen = !this.status.isopen;
  }

  // social box charts

  public capitalData: Array<any>
  
  public capitalLabels: Array<any>
  
  assignCapitalData(res) {
    
    if(res.response == 1){
  
    var data = [];
    var date = [];

    res.data.forEach((item, i) => {
      data.push(item.amount);
      date.push(item.date);
    })

    this.capitalData = [{ data: data, label: this.user.email }];
    console.dir(this.capitalData);
    this.capitalLabels = date;
    console.dir(this.capitalLabels);
    
    this.isLoaded = true;
    }else{
      this.response = 'Come back tomorrow'
    }
  }

  public socialChartOptions: any = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      xAxes: [{
        display: false,
      }],
      yAxes: [{
        display: false,
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
  public socialChartColours: Array<any> = [
    {
      backgroundColor: 'rgba(148,159,177,0.2)',
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: '#20a8d8',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#20a8d8',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public socialChartLegend: boolean = false;
  public socialChartType: string = 'line';



}
