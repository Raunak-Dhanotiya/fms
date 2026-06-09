import { Component, Input, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { UtilConstant } from 'src/common/UtilConstant';
import { PartsUsageanalysisService } from '../../parts-usage-analysis/services/parts-usage-analysis.service';
import { MessageService } from 'primeng/api';
import { AddWorkRequestService } from '../../work-request/service/add-work-request.services';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-budget-analysis',
  templateUrl: './budget-analysis.component.html',
  styleUrls: ['./budget-analysis.component.scss'],
  providers: [MessageService]
})
export class BudgetAnalysisComponent implements OnInit {
  filterPanel!: UntypedFormGroup;
  budgetalysisData: any[] = [];
  budgetalysisDataForAll: any[] = [];
  requestId: any[] = [];
  allRequests: any[] = [];
  actualCostSum: number = 0;
  sumOfDiffernces: number = 0;
  estimateCostSum: number = 0;
  loading: boolean = false;
  rowCount: number = UtilConstant.ROW_COUNT;

  datesReadOnly : boolean = false;
  showRequestsTypeList:any[] = [{label:"Preventive Maintenance",value:"ppm"},
  {label:"Facilities Helpdesk",value:"facilities"},
  {label:"All",value:"all"}]
  @Input() isReadOnly: boolean = false;
  @Input() hideFilterPanel: boolean = false;
  @Input()showType:string = "facilities";
  constructor(
    private formBuilder: UntypedFormBuilder,
    private datePipe: DatePipe,
    private router: Router,
    private wrServ: AddWorkRequestService,
    private partsUsageanalysisService: PartsUsageanalysisService
  ) {
    this.filterPanel = this.formBuilder.group({
      wrId: [null],
      dateRequestedFrom: [new Date(new Date().getFullYear(), new Date().getMonth(), 1)],
      dateRequestedTo: [new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0)],
      showType : [this.showType]
    });
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.filterPanel.patchValue({
        showType: this.showType
      });
      const filterData = this.getFilterData();
      this.getRequestBugdet(filterData);
    });
    this.loadRequests();
  }
  onSearch() {
  const filterData = this.getFilterData();
    this.getRequestBugdet(filterData);
  }

  getRequestBugdet(filterData: any) {
    this.loading = true
    this.partsUsageanalysisService.getRequestByBugdet(filterData).subscribe((res: any) => {
      const records = this.asArray(res);
      this.loading = false;
      this.budgetalysisData = [];
      this.budgetalysisDataForAll = [];
      this.estimateCostSum = 0;
      this.actualCostSum = 0;
      this.sumOfDiffernces = 0;
      if (this.hideFilterPanel) {
        if (records.length > 0) {
          this.createDataToDisplay(records[0]);
        }
      } else {
        records.forEach((data: any) => {
          this.createDataForMultipleRequests(data);
        });
      }
   }, _error => {
      this.loading = false;
      this.budgetalysisData = [];
      this.budgetalysisDataForAll = [];
      this.estimateCostSum = 0;
      this.actualCostSum = 0;
      this.sumOfDiffernces = 0;
   });
  }

  loadRequests() {
    this.wrServ.getAllWr().subscribe((res: any) => {
      this.allRequests = this.asArray(res);
      this.allRequests.unshift(new Object({ wrId: "Make a selection" }));
    }, _error => {
      this.allRequests = [{ wrId: "Make a selection" }];
    });
  }

  getFilterData() {
    var dateRequestedFrom = this.filterPanel.controls.dateRequestedFrom.value;
    var dateRequestedTo = this.filterPanel.controls.dateRequestedTo.value;
    var wrId = this.filterPanel.controls.wrId.value;
    var filterData = {

      "dateRequestedFrom": this.datePipe.transform(dateRequestedFrom, "yyyy-MM-dd"),
      "dateRequestedTo": this.datePipe.transform(dateRequestedTo, "yyyy-MM-dd"),
      "wrId": wrId,
      "showRequestType": this.filterPanel.controls.showType.value
     };
    return filterData;
  }

  createDataToDisplay(data: any) {
    const dataArray: any[] = [];
    for (const key in data) {
      if (key !== "RequestId") {
        const items = this.asArray(data[key]);
        items.forEach((item: any) => {
          const obj = {
            label: key,
            EstimateCost: this.toNumber(item?.EstimateCost),
            ActualCost: this.toNumber(item?.ActualCost)
          };
          dataArray.push(obj);
        });
      }
    }

    this.estimateCostSum = 0;
    this.actualCostSum = 0;
    this.sumOfDiffernces = 0;

    dataArray.forEach(record => {
      this.estimateCostSum += this.toNumber(record.EstimateCost);
      this.actualCostSum += this.toNumber(record.ActualCost);
      this.sumOfDiffernces += (this.toNumber(record.EstimateCost) - this.toNumber(record.ActualCost));
    });

    this.budgetalysisData = dataArray;
  }

  createDataForMultipleRequests(data: any) {
    const requestId = data.RequestId;
    let sumOfEstimated = 0;
    let sumOfActual = 0;

    for (const part of this.asArray(data.Part)) {
      sumOfEstimated += this.toNumber(part?.EstimateCost);
      sumOfActual += this.toNumber(part?.ActualCost);
    }

    for (const technician of this.asArray(data.Technician)) {
      sumOfEstimated += this.toNumber(technician?.EstimateCost);
      sumOfActual += this.toNumber(technician?.ActualCost);
    }

    for (const tool of this.asArray(data.Tools)) {
      sumOfEstimated += this.toNumber(tool?.EstimateCost);
      sumOfActual += this.toNumber(tool?.ActualCost);
    }

    for (const cost of this.asArray(data['Other Cost'])) {
      sumOfEstimated += this.toNumber(cost?.EstimateCost);
      sumOfActual += this.toNumber(cost?.ActualCost);
    }

    for (const cost of this.asArray(data.Trade)) {
      sumOfEstimated += this.toNumber(cost?.EstimateCost);
      sumOfActual += this.toNumber(cost?.ActualCost);
    }

    const sumOfDifference = sumOfEstimated - sumOfActual;

    const requestObject = {
      requestId: requestId,
      sumOfEstimated: sumOfEstimated,
      sumOfActual: sumOfActual,
      sumOfDifference: sumOfDifference
    };
    this.budgetalysisDataForAll.push(requestObject);
  }

  onClickView(data: any) {
    const wrId = data.requestId;
    const url = this.router.serializeUrl(
      this.router.createUrlTree(["/work-request-details"], { queryParams: { requestId: wrId, index: 0, action: "details", viewDetails: true, isNavigationFromReport: true } })
    );
    window.open(url, '_blank');
  }

  getColorCode(data: any) {
    var currDate = new Date();

    if (data.sumOfDifference < 0) {
      return '#FF9999'
    }
    else {
      return '#CCFFFF'
    }
  }

  onSelectRequst(data: any){
    if(data.wrId !== 'Make a selection'){
      this.filterPanel.patchValue({
        wrId : data.wrId,
        dateRequestedFrom : null,
        dateRequestedTo : null
      })
      this.datesReadOnly = true;
    }else{
      this.filterPanel.patchValue({
        wrId : null
      })
      this.datesReadOnly = false;
    }
  }

  onClickClear(){
    this.filterPanel.patchValue({
      wrId: null,
      dateRequestedFrom : null,
      dateRequestedTo : null ,
      showType: this.showType
    });
    this.datesReadOnly = false;
  }

  private asArray(value: any): any[] {
    if (Array.isArray(value)) {
      return value;
    }
    if (value && Array.isArray(value.content)) {
      return value.content;
    }
    if (value && Array.isArray(value.data)) {
      return value.data;
    }
    return [];
  }

  private toNumber(value: any): number {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : 0;
  }
}
