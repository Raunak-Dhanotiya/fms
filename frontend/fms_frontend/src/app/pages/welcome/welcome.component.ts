import { Component, OnInit, ViewChild } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/services/auth.service';
import { SharedService } from 'src/app/services/shared.service';
import { Home } from './model/Home.model';
import { WelcomeService } from './services/welcome.service';
import { SupervisorDashboardComponent } from 'src/app/ui/facilities-helpdesk-dashboards/supervisor-dashboard/modal/supervisor-dashboard.component';
import { AppParamsService } from 'src/app/ui/app-params/services/app-params.service';
import { UtilConstant } from 'src/common/UtilConstant';
import { SideNavService } from 'src/app/services/side-nav.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  title: string = "Welcome";
  intervalId: any;
  autoRefreshTime: any = 60;
  homeDashboard: Home = new Home();
  showEmptyPage: boolean = true;
  isFacilitiesDeskTabActive = true;
  isSpaceTabActive = false;
  isHelpdeskTabActive = false;
  isOrderTabActive = false;
  isAccountTabActive = false;
  @ViewChild(SupervisorDashboardComponent, { static: false }) supervisorDashboardComponent!: SupervisorDashboardComponent;
  constructor(private ss: SharedService,
    private authService: AuthService,
    private welcomeSrv: WelcomeService,
    private spinner: NgxSpinnerService,
    private appParamsServ: AppParamsService,
    private sideNavSrv: SideNavService,) { }

   ngOnInit() {
     setTimeout(() => {
       this.ss.change(this.authService.isLoggedIn());
     });
     this.spinner.show();
     const filter = {
       "userName": this.authService.getLoggedInUserId(),
       "userRole": this.authService.getLoggedInUserRole()
     }
     this.welcomeSrv.getUserDashboard(filter).subscribe((res: any) => {
       try {
         // backend may return the dashboard payload directly or wrapped (e.g. { data: {...} } or { response: {...} })
         let payload: any = res;
         if (res && res.data) payload = res.data;
         if (res && res.response) payload = res.response;

         // Initialize with default Home object
         let homeObj: any = new Home();

         if (payload && typeof payload === 'object') {
           // map known keys into Home model if present
           ['helpdesk', 'order', 'account', 'facilitiesDesk', 'space', 'preventiveMaintenance'].forEach(k => {
             if (payload[k] !== undefined && payload[k] !== null) {
               homeObj[k] = payload[k];
             }
           });
         }

         this.homeDashboard = homeObj;
         const isAnyKeyGreaterThanZero = Object.values(this.homeDashboard).some((value: any) => Number(value) > 0);
         this.showEmptyPage = !isAnyKeyGreaterThanZero;
         if (this.showEmptyPage) {
           this.applyDashboardFallbackFromMenu();
         }
       } catch (error) {
         console.error('Error processing dashboard:', error);
         this.homeDashboard = new Home();
         this.showEmptyPage = true;
         this.applyDashboardFallbackFromMenu();
       } finally {
         this.spinner.hide();
       }
     }, _error => {
       console.error('Error fetching dashboard:', _error);
       this.homeDashboard = new Home();
       this.showEmptyPage = true;
       this.applyDashboardFallbackFromMenu();
       this.spinner.hide();
     })
     this.getDashBoardAutoRefreshTimeInterval();
   }

  getDashBoardAutoRefreshTimeInterval() {
    const paramId = UtilConstant.DASHBOARD_AUTO_REFRESH_TIME_INTERVAL;
    this.appParamsServ.getAppParamByCode(paramId).subscribe((res: any) => {
      this.autoRefreshTime = res.paramValue;
      // clearInterval(this.intervalId);
      this.intervalId = setInterval(() => {
        // Call a function to perform the rendering logic here
        this.supervisorDashboardComponent?.loadWrRecords();
      }, this.autoRefreshTime * 60 * 1000);
    })
  }

  onTabChange(event: any): void {
    
    // Activate the selected tab
    switch (event.index) {
      case 0:
        this.isFacilitiesDeskTabActive = true;
        break;
      case 1:
        this.isSpaceTabActive = true;
        break;
      case 2:
        this.isHelpdeskTabActive = true;
        break;
      case 3:
        this.isOrderTabActive = true;
        break;
      case 4:
        this.isAccountTabActive = true;
        break;
      // Add cases for other tabs

      default:
        break;
    }
  }

  ngOnDestroy() {
    // Clear the interval when the component is destroyed
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  private applyDashboardFallbackFromMenu(): void {
    const roleId = this.authService.getLoggedInUserRole();
    if (!roleId) {
      return;
    }
    const payload = { userRoleId: roleId };
    this.sideNavSrv.getSideMenu(payload).subscribe((res: any) => {
      const links = new Set<string>();
      this.collectLinks(res?.menuItems, links);

      if (links.has('space-dashboard')) {
        this.homeDashboard.space = 1;
      }
      if (links.has('supervisor-dashboard') || links.has('technician-dashboard') || links.has('welcome') || links.has('dashboards')) {
        this.homeDashboard.facilitiesDesk = 1;
      }

      const isAnyKeyGreaterThanZero = Object.values(this.homeDashboard).some((value: any) => Number(value) > 0);
      this.showEmptyPage = !isAnyKeyGreaterThanZero;
    }, _error => {
      // Keep default empty state when menu is unavailable.
    });
  }

  private collectLinks(items: any[], links: Set<string>): void {
    (items || []).forEach((item: any) => {
      if (!item) {
        return;
      }
      if (item.link) {
        links.add(String(item.link).toLowerCase());
      }
      if (Array.isArray(item.subMenuItems)) {
        this.collectLinks(item.subMenuItems, links);
      }
      if (Array.isArray(item.subProcessItem)) {
        item.subProcessItem.forEach((subProcess: any) => {
          if (Array.isArray(subProcess?.subMenuItems)) {
            this.collectLinks(subProcess.subMenuItems, links);
          }
        });
      }
    });
  }
}
