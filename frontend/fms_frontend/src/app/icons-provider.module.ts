import { NgModule } from '@angular/core';
import { NZ_ICONS, NzIconModule } from 'ng-zorro-antd/icon';

import {
  MenuFoldOutline,
  MenuUnfoldOutline,
  FormOutline,
  DashboardOutline,
  AppstoreOutline,
  SettingOutline,
  HomeOutline,
  TeamOutline,
  ToolOutline,
  CalendarOutline,
  CustomerServiceOutline,
  FileTextOutline,
  BarChartOutline,
  LayoutOutline,
  FileExcelOutline,
  BuildOutline,
  FilePdfOutline,
  DollarOutline,
  InboxOutline,
  RightCircleOutline,
  CloseOutline,
  UserOutline,
  SafetyCertificateOutline
} from '@ant-design/icons-angular/icons';

const icons = [
  MenuFoldOutline,
  MenuUnfoldOutline,
  DashboardOutline,
  FormOutline,
  AppstoreOutline,
  SettingOutline,
  HomeOutline,
  TeamOutline,
  ToolOutline,
  CalendarOutline,
  CustomerServiceOutline,
  FileTextOutline,
  BarChartOutline,
  LayoutOutline,
  FileExcelOutline,
  BuildOutline,
  FilePdfOutline,
  DollarOutline,
  InboxOutline,
  RightCircleOutline,
  CloseOutline,
  UserOutline,
  SafetyCertificateOutline
];

@NgModule({
  imports: [NzIconModule],
  exports: [NzIconModule],
  providers: [
    { provide: NZ_ICONS, useValue: icons }
  ]
})
export class IconsProviderModule {
}
