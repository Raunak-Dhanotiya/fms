import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { DataService } from 'src/app/services/data.service';


@Injectable({
  providedIn: 'root'
})
export class FinancialYearService {

  constructor(
    private service: DataService<any>,
    
  ) { }

  public getFinancialYearData(paramCode: string){
    return this.service.getSingleById('ap', 'getByCode', paramCode);
  }
  
  
}
