import { Component, OnInit, ViewChild } from '@angular/core';
import { RmcatComponent } from '../../rmcat/modal/rmcat/rmcat.component';
import { RmcatFilterInput } from '../../rmcat/modal/rmcatFilterInput.model';
import { RmTypeListComponent } from '../widgets/rm-type-list/rm-type-list.component';
import { RmTypeFilterInputDTO } from '../model/DTO/rmTypeFilterInput.model';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-romms',
  templateUrl: './romms.component.html',
  styleUrls: ['./romms.component.scss']
})
export class RommsComponent implements OnInit {

  subscriptions: Subscription[] = [];
  loading: boolean = false;
  tab_name_clicked: string = "Cat";

  @ViewChild(RmcatComponent, { static: false }) rmCatPanel!: RmcatComponent;
  @ViewChild(RmTypeListComponent, { static: false }) rmTypePanel!: RmTypeListComponent;
  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.rmCatPanel.loadRecords(new RmcatFilterInput("", ""));
    this.rmTypePanel.loadRecords(new RmTypeFilterInputDTO("", "",""));
    $("#rmtabContent,#Cat").show();
  }

  openClickTab(event: any, name: any) {
    this.loadTabData(name);
    event.preventDefault();
  }
  onClear(data: any){
    if(data == true){
      this.rmCatPanel.loadRecords(new RmcatFilterInput("", ""));
      this.rmTypePanel.loadRecords(new RmTypeFilterInputDTO("", "",""));
    }else if(data != true && data != ''){
      this.rmTypePanel.loadRecords(new RmTypeFilterInputDTO("", "", data));
      this.rmTypePanel.isHide = false;
    }
    
  }

  loadTabData(name: any) {
    this.hidePrevTab(this.tab_name_clicked);
    this.tab_name_clicked = name;
    $("#rmtabContent").hide();

    switch (this.tab_name_clicked) {
      case "Cat": {
        $("#rmtabContent,#Cat").show();
        break;
      }
      case "Type": {
        $("#rmtabContent,#Type").show();
        break;
      }
      default: {
        break;
      }
    }

  }

  hidePrevTab(name: any) {
    switch (name) {
      case "Cat": {
        $("#Cat").hide();
        break;
      }
      case "Type": {
        $("#Type").hide();
        break;
      }
      default: {
        break;
      }
    }
  }

  loadRmType(data: any) {
    
    if(data.rmCat != ''){
      $("#Cat").hide();
      this.rmTypePanel.loadRecords(new RmTypeFilterInputDTO("", "", data.rmCat));
      this.rmTypePanel.isHide = false;
      $("#rmtabContent,#Type").show();
      this.tab_name_clicked = "Type";
    }else{
      
      this.rmTypePanel.loadRecords(new RmTypeFilterInputDTO("", "", ""));
      this.rmTypePanel.isHide = true;
      
      
    }
    
    
    
  }

}
