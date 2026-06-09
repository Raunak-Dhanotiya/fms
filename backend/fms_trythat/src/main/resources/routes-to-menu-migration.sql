-- ============================================================================
-- ROUTES SYNCHRONIZATION MIGRATION SCRIPT
-- ============================================================================
-- This script populates the fm_processes, fm_sub_processes, and screen tables
-- based on the Angular app-routing.module.ts routes
--
-- STRUCTURE:
-- Dashboard & Reports (Process)
--   └─ No Sub-Process
--       └─ Home, Dashboards, Space Dashboard, Booking Reports (Screens)
--
-- System Configuration (Process)
--   └─ No Sub-Process
--       └─ Roles, User Processes, App Parameters (Screens)
-- ============================================================================

-- TRUNCATE TABLES (OPTIONAL - UNCOMMENT IF YOU WANT TO RESET)
-- TRUNCATE TABLE user_screens;
-- TRUNCATE TABLE screen;
-- TRUNCATE TABLE fm_sub_processes;
-- TRUNCATE TABLE fm_processes;

-- ============================================================================
-- 1. INSERT PROCESSES (Main Menu Categories)
-- ============================================================================

INSERT INTO fm_processes (process_id, process_code, title, icon, display_order) VALUES
(1, 'DASHBOARD', 'Dashboard & Reports', 'dashboard', 1),
(2, 'SYSCFG', 'System Configuration', 'setting', 2),
(3, 'LOCFAC', 'Location & Facilities', 'home', 3),
(4, 'EMPMGT', 'Employee Management', 'team', 4),
(5, 'ASSTMGT', 'Asset Management', 'tool', 5),
(6, 'BOOKING', 'Booking & Reservations', 'calendar', 6),
(7, 'HELPDESK', 'Help Desk Management', 'customer-service', 7),
(8, 'WORKREQ', 'Work Requests', 'file-text', 8),
(9, 'HDRPTS', 'Help Desk Reports', 'bar-chart', 9),
(10, 'ROOMVIS', 'Room Visualization', 'layout', 10),
(11, 'SPACEMGT', 'Space Management & Allocation', 'appstore', 11),
(12, 'SPARPTS', 'Space Reports', 'file-excel', 12),
(13, 'PPM', 'PPM (Preventive Maintenance)', 'build', 13),
(14, 'PPMRPTS', 'PPM Reports', 'file-pdf', 14),
(15, 'FINCMGT', 'Financial Management', 'dollar', 15),
(16, 'INVMGT', 'Inventory Management', 'inbox', 16),
(17, 'ADMIN', 'Administration', 'appstore', 17);

-- ============================================================================
-- 2. INSERT SCREENS FOR DASHBOARD & REPORTS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(1, 'Home', 'welcome', 'WelcomeComponent', 1, 1),
(2, 'Dashboards', 'dashboards', 'DashboardComponent', 1, 2),
(3, 'Space Dashboard', 'space-dashboard', 'SpaceDashboardComponent', 1, 3),
(4, 'Booking Reports', 'booking-reports', 'BookingReportsComponent', 1, 4),
(5, 'Booking Reports By Month', 'booking-reports-by-month', 'BookingReportsByMonthComponent', 1, 5);

-- ============================================================================
-- 3. INSERT SCREENS FOR SYSTEM CONFIGURATION
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(6, 'Roles', 'roles', 'RoleComponent', 2, 1),
(7, 'User Processes', 'user-procs', 'UserProcsComponent', 2, 2),
(8, 'App Parameters', 'app-params', 'AppParamsComponent', 2, 3),
(9, 'Security Group', 'security-group', 'SecurityGroupComponent', 2, 4),
(10, 'User Security Group', 'user-security-group', 'UserSecurityGroupComponent', 2, 5),
(11, 'Room Configuration', 'rm-config', 'RmConfigComponent', 2, 6),
(12, 'Connector', 'connector', 'ConnectorComponent', 2, 7);

-- ============================================================================
-- 4. INSERT SCREENS FOR LOCATION & FACILITIES
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(13, 'Location', 'geo-loc', 'LocationComponent', 3, 1),
(14, 'Background Location', 'back-loc', 'BackgroundLocComponent', 3, 2),
(15, 'Rooms', 'room-category', 'RoomCategoryComponent', 3, 3),
(16, 'Room Teams', 'room-teams', 'RoomTeamsComponent', 3, 4),
(17, 'Space Team', 'space-team', 'SpaceTeamComponent', 3, 5),
(18, 'Visitors', 'visitors', 'VisitorsComponent', 3, 6);

-- ============================================================================
-- 5. INSERT SCREENS FOR EMPLOYEE MANAGEMENT
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(19, 'Define Employee', 'define-employee', 'DefineEmployeeComponent', 4, 1),
(20, 'Employee Standard', 'emstd', 'EmStdComponent', 4, 2),
(21, 'Employee Teams', 'emp-teams', 'EmpTeamsComponent', 4, 3),
(22, 'Division Department', 'division-department', 'DivisionDepartmentComponent', 4, 4),
(23, 'Room Category Type', 'rmcat-rmtype', 'RmCatRmTypeComponent', 4, 5),
(24, 'Locate Employee', 'locate-employee', 'LocateEmployeeComponent', 4, 6);

-- ============================================================================
-- 6. INSERT SCREENS FOR ASSET MANAGEMENT
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(25, 'Equipment', 'asset', 'EquipmentComponent', 5, 1),
(26, 'Asset Standard', 'aststd', 'AssetStandardComponent', 5, 2),
(27, 'Asset Classification', 'asset-classification', 'AssetClassificationComponent', 5, 3),
(28, 'Asset Details', 'asset-details', 'AssetDetailsComponent', 5, 4),
(29, 'Resources', 'resources', 'ResourcesComponent', 5, 5),
(30, 'RM Resources', 'rm-resources', 'RmResourcesComponent', 5, 6),
(31, 'Locate Asset', 'locate-asset', 'LocateAssetComponent', 5, 7);

-- ============================================================================
-- 7. INSERT SCREENS FOR BOOKING & RESERVATIONS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(32, 'Booking', 'booking', 'BookingComponent', 6, 1),
(33, 'Create Booking', 'mark-reserv', 'MarkReservComponent', 6, 2),
(34, 'Mark Hotel', 'mark-hotel', 'MarkHotelComponent', 6, 3),
(35, 'Edit Booking', 'edit-booking', 'EditBookingComponent', 6, 4),
(36, 'Approve Booking', 'approve-booking', 'ApproveBookingComponent', 6, 5),
(37, 'Check Booking', 'check-booking', 'CheckBookingComponent', 6, 6),
(38, 'Arrangement', 'arrangement', 'ArrangementComponent', 6, 7);

-- ============================================================================
-- 8. INSERT SCREENS FOR HELPDESK MANAGEMENT
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(39, 'Tool Type', 'tool-type', 'ToolTypeComponent', 7, 1),
(40, 'Tools', 'tools', 'ToolsComponent', 7, 2),
(41, 'Team', 'team', 'TeamComponent', 7, 3),
(42, 'Work Teams', 'work-teams', 'WorkTeamsComponent', 7, 4),
(43, 'Problem Type', 'problem-type', 'ProblemTypeComponent', 7, 5),
(44, 'Problem Description', 'problem-description', 'ProblemDescriptionComponent', 7, 6),
(45, 'Trades', 'trades', 'TradesComponent', 7, 7),
(46, 'Technician', 'technician', 'TechnicianComponent', 7, 8),
(47, 'Cost Type', 'cost-type', 'CostTypeComponent', 7, 9),
(48, 'Parts', 'parts', 'PartsComponent', 7, 10),
(49, 'Define SLA', 'define-sla', 'DefineSlaComponent', 7, 11),
(50, 'Add/Edit SLA', 'add-edit-sla', 'AddEditSlaComponent', 7, 12);

-- ============================================================================
-- 9. INSERT SCREENS FOR WORK REQUESTS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(51, 'Add Work Request', 'add-work-request', 'AddWorkRequestComponent', 8, 1),
(52, 'View/Edit Work Request', 'view-edit-work-request', 'ViewEditWorkRequestComponent', 8, 2),
(53, 'Work Request Details', 'work-request-details', 'WorkRequestDetailsComponent', 8, 3);

-- ============================================================================
-- 10. INSERT SCREENS FOR HELP DESK REPORTS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(54, 'Escalated Summary Reports', 'escalated-summary-reports', 'EscalatedSummaryReportsComponent', 9, 1),
(55, 'Request Summary Reports', 'request-summary-reports', 'RequestSummaryReportsComponent', 9, 2),
(56, 'Request By Asset Reports', 'request-by-asset-reports', 'RequestByAssetReportsComponent', 9, 3),
(57, 'Parts Usage Analysis', 'parts-usage-analysis', 'PartsUsageAnalysisComponent', 9, 4),
(58, 'Technician Time Usage Analysis', 'technician-time-usage-analysis', 'TechnicianTimeUsageAnalysisComponent', 9, 5),
(59, 'SLA Escalation Analysis', 'sla-escaltion-analysis', 'SlaEscalationAnalysisComponent', 9, 6),
(60, 'Budget Analysis', 'budget-analysis', 'BudgetAnalysisComponent', 9, 7),
(61, 'Technician Dashboard', 'technician-dashboard', 'TechnicianDashboardComponent', 9, 8),
(62, 'Supervisor Dashboard', 'supervisor-dashboard', 'SupervisorDashboardComponent', 9, 9);

-- ============================================================================
-- 11. INSERT SCREENS FOR ROOM VISUALIZATION
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(63, 'Link Room SVG', 'link-room-svg', 'LinkRoomSvgComponent', 10, 1),
(64, 'Room Employee SVG', 'room-employee-svg', 'RoomEmployeeSvgComponent', 10, 2),
(65, 'RMCAT RMTYPE SVG', 'rmcat-rmtype-svg', 'RmcatRmtypeSvgComponent', 10, 3),
(66, 'Division Department SVG', 'div-dept-svg', 'DivDeptSvgComponent', 10, 4),
(67, 'Floorplan Arrangement SVG', 'floorplan-arrangement-svg', 'FloorplanArrangementSvgComponent', 10, 5),
(68, 'Highlight Rooms', 'highlight-rooms', 'HighlightRoomsComponent', 10, 6),
(69, 'Highlight By Department', 'highlight-by-department', 'HighlightByDepartmentComponent', 10, 7),
(70, 'Highlight By Division', 'highlight-by-division', 'HighlightByDivisionComponent', 10, 8),
(71, 'Highlight By Room Category', 'highlight-by-rmcat', 'HighlightByRmCatComponent', 10, 9),
(72, 'Highlight By Room Type', 'highlight-by-rmtype', 'HighlightByRmTypeComponent', 10, 10),
(73, 'Highlight By Sub Department', 'highlight-by-sub-department', 'HighlightBySubDepartmentComponent', 10, 11);

-- ============================================================================
-- 12. INSERT SCREENS FOR SPACE MANAGEMENT & ALLOCATION
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(74, 'Locate Room', 'locate-room', 'LocateRoomComponent', 11, 1),
(75, 'Assign Employee Room', 'assign-employee-room', 'AssignEmployeeRoomComponent', 11, 2),
(76, 'Allocate Division Department Room', 'allocate-div-dep-room', 'AllocateDivDepRoomComponent', 11, 3),
(77, 'Allocate Employee Room', 'allocate-emp-room', 'AllocateEmpRoomComponent', 11, 4),
(78, 'View Employee Report', 'view-employee-report', 'ViewEmployeeReportComponent', 11, 5),
(79, 'View Room Report', 'view-room-report', 'ViewRoomReportComponent', 11, 6),
(80, 'View Common Area', 'view-common-area', 'ViewCommonAreaComponent', 11, 7),
(81, 'View Occupancy Statistics', 'view-occupancy-statistics', 'ViewOccupancyStatisticsComponent', 11, 8);

-- ============================================================================
-- 13. INSERT SCREENS FOR SPACE REPORTS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(82, 'Allocation Report By BL/FL', 'allocation-report-by-bl-fl', 'AllocationReportByBlFlComponent', 12, 1),
(83, 'Allocation Report By Div/Dep', 'allocation-report-by-div-dep', 'AllocationReportByDivDepComponent', 12, 2),
(84, 'Allocation Report By Sub Dep', 'allocation-report-by-sub-dep', 'AllocationReportBySubDepComponent', 12, 3),
(85, 'Space Allocation Statistics', 'space-allocation-statistics', 'SpaceAllocationStatisticsComponent', 12, 4),
(86, 'Space Alloc Stats By BL/FL', 'space-allocation-statistics-by-bl-fl', 'SpaceAllocationStatisticsByBlFlComponent', 12, 5),
(87, 'Space Alloc Stats By Div/Dep', 'space-allocation-statistics-by-div-dep', 'SpaceAllocationStatisticsByDivDepComponent', 12, 6);

-- ============================================================================
-- 14. INSERT SCREENS FOR PPM (PREVENTIVE MAINTENANCE)
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(88, 'Define Plan', 'define-plan', 'DefinePlanComponent', 13, 1),
(89, 'Plan Details', 'plan-details', 'PlanDetailsComponent', 13, 2),
(90, 'Link Plan To Location', 'link-plan-to-location', 'LinkPlanToLocationComponent', 13, 3),
(91, 'Link Plan To Asset', 'link-plan-to-asset', 'LinkPlanToAssetComponent', 13, 4),
(92, 'Link Assets To Plan', 'link-assets-to-plan', 'LinkAssetsToPlanComponent', 13, 5),
(93, 'PPM Schedule', 'ppm-schedule', 'PpmScheduleComponent', 13, 6),
(94, 'Define Schedule', 'define-schedule', 'DefineScheduleComponent', 13, 7),
(95, 'Schedule Details', 'schedule-details', 'ScheduleDetailsComponent', 13, 8),
(96, 'Generate Requests', 'generate-requests', 'GenerateRequestsComponent', 13, 9),
(97, 'Show Requests', 'show-requests', 'ShowRequestsComponent', 13, 10),
(98, 'PM Planner', 'pm-planner', 'PmPlannerComponent', 13, 11),
(99, 'Forecast', 'forecast', 'ForecastComponent', 13, 12),
(100, 'Forecast Details', 'forecast-details', 'ForecastDetailsComponent', 13, 13),
(101, 'PPM Request Console', 'ppm-request-console', 'PpmRequestConsoleComponent', 13, 14);

-- ============================================================================
-- 15. INSERT SCREENS FOR PPM REPORTS
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(102, 'PPM Request Summary Reports', 'ppm-request-summary-reports', 'PpmRequestSummaryReportsComponent', 14, 1),
(103, 'PPM Escalated Summary Reports', 'ppm-escalated-summary-reports', 'PpmEscalatedSummaryReportsComponent', 14, 2),
(104, 'PPM Parts Usage Analysis', 'ppm-parts-usage-analysis', 'PpmPartsUsageAnalysisComponent', 14, 3),
(105, 'PPM SLA Escalation Analysis', 'ppm-sla-escaltion-analysis', 'PpmSlaEscalationAnalysisComponent', 14, 4),
(106, 'PPM Technician Time Analysis', 'ppm-technician-time-usage-analysis', 'PpmTechnicianTimeUsageAnalysisComponent', 14, 5),
(107, 'PPM Budget Analysis', 'ppm-budget-analysis', 'PpmBudgetAnalysisComponent', 14, 6);

-- ============================================================================
-- 16. INSERT SCREENS FOR FINANCIAL MANAGEMENT
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(108, 'Financial Year', 'financial-year', 'FinancialYearComponent', 15, 1),
(109, 'Terms', 'terms', 'TermsComponent', 15, 2);

-- ============================================================================
-- 17. INSERT SCREENS FOR INVENTORY MANAGEMENT
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(110, 'Item List', 'item-list', 'ItemListComponent', 16, 1),
(111, 'Budget Term', 'budget-term', 'BudgetTermComponent', 16, 2),
(112, 'Center Budget', 'center-budget', 'CenterBudgetComponent', 16, 3),
(113, 'Center Usage', 'center-usage', 'CenterUsageComponent', 16, 4),
(114, 'Inventory Report', 'inventory-report', 'InventoryReportComponent', 16, 5);

-- ============================================================================
-- 18. INSERT SCREENS FOR ADMINISTRATION
-- ============================================================================

INSERT INTO screen (screen_num, screen_name, screen_nav_url, screen_component, process_id, display_order) VALUES
(115, 'Messages', 'messages', 'MessagesComponent', 17, 1),
(116, 'Holidays', 'holidays', 'HolidaysComponent', 17, 2),
(117, 'Password Reset', 'password-reset', 'PasswordResetComponent', 17, 3);

-- ============================================================================
-- VERIFY INSERTS
-- ============================================================================

-- Count total processes
SELECT COUNT(*) AS total_processes FROM fm_processes;

-- Count total screens
SELECT COUNT(*) AS total_screens FROM screen;

-- Display all processes with screen counts
SELECT p.process_id, p.title, p.icon, COUNT(s.screen_num) AS screen_count
FROM fm_processes p
LEFT JOIN screen s ON p.process_id = s.process_id
GROUP BY p.process_id, p.title, p.icon
ORDER BY p.display_order;

-- ============================================================================
-- NOTES:
-- ============================================================================
-- 1. Before running this script, ensure the database tables exist:
--    - fm_processes
--    - fm_sub_processes (optional, for future use)
--    - screen
--    - user_screens
--
-- 2. Ensure foreign key constraints are properly defined
--
-- 3. After running this script, assign screens to user roles via user_screens table:
--    INSERT INTO user_screens (user_screens_num, process_id, sub_process_id, screen_num, user_role_id)
--    SELECT
--        ROW_NUMBER() OVER (ORDER BY s.screen_num),
--        s.process_id,
--        ISNULL(s.sub_process_id, 0),
--        s.screen_num,
--        1 -- Admin role ID
--    FROM screen s;
--
-- 4. To reset and re-run this script, uncomment the TRUNCATE statements at the top
-- ============================================================================

