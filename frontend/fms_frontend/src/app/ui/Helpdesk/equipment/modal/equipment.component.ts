import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { EnumService } from 'src/app/services/enum.service';
import { UtilConstant } from 'src/common/UtilConstant';
import { EquipmentDialogueProvider } from '../providers/equipment.provider';
import { EquipmentService } from '../services/equipment.services';
import { PaginationObj } from 'src/app/model/pagination-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.scss'],
  providers: [MessageService]
})
export class EquipmentComponent implements OnInit {
  eqData: any[] = [];
  loading: boolean = false;
  rowCount: number = UtilConstant.ROW_COUNT;
  enumList: any[] = []; //Enums
  enumClonedList: any[] = [];
  enumStatus: any[] = [];
  enumStatusData: any[] = [];
  totalElements:number = 0;
  paginationObj:PaginationObj = {
    pageNo:0,
    pageSize:this.rowCount,
    sortBy:["eqId"],
    sortOrder:"ASC"
  }
  filterCriteria:any = {};
  isFiltered:boolean = false;
  filterCriteriaList :any[]=[];
  constructor(
    private eqProvider: EquipmentDialogueProvider,
    private eqService: EquipmentService,
    private messageService: MessageService,
    private enumsrv: EnumService,
    private confirmationService: ConfirmationService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.loadEnums();
    this.loadRecords();
  }
  loadEnums() {
    this.enumList = [];
    this.enumsrv.getEnums().subscribe(
      (res: any[]) => {
        this.enumList = res;
        this.enumClonedList = this.enumList.map(x => Object.assign({}, x));
        // this.enumEq = this.enumClonedList.filter(t => t.tableName.toLocaleUpperCase() === 'eq'.toLocaleUpperCase());
        this.enumStatus = this.enumClonedList.filter(t => this.matchesEnumField(t, 'eq', 'status'));
        this.enumStatusData = this.enumStatus.filter(t => t && t.enumValue != null);
       // this.enumStatusData.unshift(new Enums(0, "", "", 'Make a selection'));
      },
      _error => {
      });
  }

  loadRecords() {
    this.loading = true;
    let data={paginationDTO:this.paginationObj,filterCriteria:this.filterCriteriaList};
    this.eqService.getAllEquipmentsPaginated(data).subscribe((res: any) => {
      if (res) {
        this.isFiltered= false;
        this.eqData = res.content ? res.content : [];
        this.totalElements = res.totalElements ? res.totalElements : 0;
      }
      else {
        this.eqData = [];
      }
      this.loading = false;
    },
      _error => {
        this.loading = false;
      }
    );
  }

  private matchesEnumField(item: any, tableName: string, fieldName: string): boolean {
    return item?.tableName?.toLocaleUpperCase() === tableName.toLocaleUpperCase()
      && item?.fieldName?.toLocaleUpperCase() === fieldName.toLocaleUpperCase();
  }

  // onAdd() {
  //   const dialogConfig = new MatDialogConfig();
  //   dialogConfig.disableClose = true;
  //   dialogConfig.autoFocus = false;
  //   dialogConfig.width = '900px';
  //   dialogConfig.data = {
  //     isEdit: false,
  //     eqId: null,
  //     newRecord: true
  //   };
  //   this.eqProvider.openDialog(dialogConfig);
  //   this.eqProvider.onDialogueClosed.subscribe((result: any) => {
  //       this.loadRecords();
  //   });
  // }
  // onEdit(id: any) {
  //   const dialogConfig = new MatDialogConfig();
  //   dialogConfig.disableClose = true;
  //   dialogConfig.autoFocus = true;
  //   dialogConfig.width = '900px';
  //   dialogConfig.data = {
  //     eqId: id,
  //     isEdit: true,
  //     newRecord: false
  //   };
  //   this.eqProvider.openDialog(dialogConfig);
  //   this.eqProvider.onDialogueClosed.subscribe((result: any) => {
  //     this.messageService.clear();
  //     if(result) {
  //       this.messageService.add({ key: 'eqSave', severity: 'success', summary: 'Record saved successfully', detail: 'Record saved successfully' });
  //     this.loadRecords();
  //     }
  //   });
  // }

  onEdit(eqId: any) {
    this.router.navigate(['/asset-details'], {
      queryParams: {
        eqId: eqId,
      },skipLocationChange: true
    })
  }

  onAdd() {
    this.router.navigate(['/asset-details'], {
      queryParams: {
        eqId: null,
      },skipLocationChange: true
    })
  }

  onDelete(eq: any) {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to delete '+eq.eqCode+'?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.deleteEquipment(eq.eqId);
      },
      key: "mygrid"
    });
  }

  deleteEquipment(id: any) {
    this.eqService.deleteById(id).subscribe((res: any) => {
      this.messageService.clear();
      if (res != null && res.code == 200) {
        this.messageService.add({ key: 'eqSave', severity: 'success', summary: 'Record deleted successfully', detail: 'Record deleted successfully' });
        this.loadRecords();
      } else {
        this.messageService.add({ key: 'eqSave', severity: 'error', summary: 'error', detail: res?.text });
      }
    },
      _error => {

      }
    );

  }

  onPageChange(event:any){
    const pageNo = event.first ? event.first / event.rows : 0;
    const pageSize = event.rows;
    this.paginationObj.pageNo = pageNo;
    this.paginationObj.pageSize = pageSize;
    this.loadRecords();
  }

  onInnerFilter(event: any) {
    if(this.isFiltered){
      this.filterCriteria = {};
      Object.keys(event.filters).forEach((field) => {
        const filterValue = event.filters[field][0].value;
        if (filterValue !== undefined ) {
          let filterCriteria: any;
          if(field=="eqStd"){
            filterCriteria = { fieldName: "eqStd.eqStd", value: filterValue, matchMode: event.filters[field][0].matchMode };
          }else if(field=="blName"){
            filterCriteria = { fieldName: "bl.blName", value: filterValue, matchMode: event.filters[field][0].matchMode };
          }else if(field=="flName"){
            filterCriteria = { fieldName: "fl.flName", value: filterValue, matchMode: event.filters[field][0].matchMode };
          }else if(field=="rmName"){
            filterCriteria = { fieldName: "rm.rmName", value: filterValue, matchMode: event.filters[field][0].matchMode };
          }else{
            filterCriteria = { fieldName: field, value: filterValue, matchMode: event.filters[field][0].matchMode };
          }
          this.updateFilterCriteriaList(filterCriteria);
        }
      });
      this.paginationObj.pageNo = 0;
      this.loadRecords();
    }
    this.isFiltered = true;
  }

  updateFilterCriteriaList(filterCriteria:any){
    let index = this.filterCriteriaList.findIndex(item => item.fieldName === filterCriteria['fieldName']);
    if(filterCriteria['value']==null){
      if(index !==-1){
        this.filterCriteriaList.splice(index, 1);
      }
    }else {
      if (index !== -1) {
        this.filterCriteriaList[index] = filterCriteria;
      } else {
        this.filterCriteriaList.push(filterCriteria);
      }
    }
  }

   getEnumValue(enumKey:any) {
     const enumItem = this.enumStatus.find(t => t.enumKey == enumKey);
     return enumItem?.enumValue ?? '';
   }

}
