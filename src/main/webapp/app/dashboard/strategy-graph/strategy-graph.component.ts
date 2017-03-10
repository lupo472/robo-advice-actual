import { AppService } from '../../services/app.service';
import { UserService } from '../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-strategy-graph',
  templateUrl: './strategy-graph.component.html',
  styleUrls: ['./strategy-graph.component.scss']
})
export class StrategyGraphComponent implements OnInit {

  constructor(private AppService: AppService, private UserService: UserService) { }

  public labels: Array<string> = [];
  public data: Array<number> = [];
  public date: string;

  public render: boolean = false;

  ngOnInit() {

    var user = this.UserService.getUser();
    this.AppService.getActiveStrategy(user).subscribe(res => this.getStrategy(res.data));
  }

  getStrategy(data) {
    console.log("strategy: ", data);

    this.date = data.date;

    data.list.forEach((item, index) => {
      this.labels[index] = item.assetClass.name;
      this.data[index] = item.percentage;
    })

    this.render = true;
  }

  public brandPrimary: string = '#20a8d8';
  public brandSuccess: string = '#4dbd74';
  public brandInfo: string = '#63c2de';
  public brandWarning: string = '#f8cb00';
  public brandDanger: string = '#f86c6b';

  //convert Hex to RGBA
  public convertHex(hex: string, opacity: number) {
    hex = hex.replace('#', '');
    let r = parseInt(hex.substring(0, 2), 16);
    let g = parseInt(hex.substring(2, 4), 16);
    let b = parseInt(hex.substring(4, 6), 16);

    let rgba = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
    return rgba;
  }

  // events
  public chartClicked(e: any): void {
    console.log(e);
  }

  public chartHovered(e: any): void {
    console.log(e);
  }


  // Pie
  public colors = [
    {
      backgroundColor: [this.brandSuccess,
      this.brandInfo,
      this.brandWarning,
      this.brandDanger], borderWidth: 3
    }];
  public strategyOptions: any = {
    maintainAspectRatio: false,
    cutoutPercentage: 20,
    legend: {
      display: false
    }
  };
  public pieChartType: string = 'doughnut';

}
