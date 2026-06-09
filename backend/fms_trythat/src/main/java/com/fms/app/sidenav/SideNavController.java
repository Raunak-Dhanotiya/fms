package com.fms.app.sidenav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fms.app.response.input.ScreenAssignInput;
import com.fms.app.response.output.MenuItems;
import com.fms.app.response.output.SideNavItems;
import com.fms.app.response.output.SubMenuItems;
import com.fms.app.response.output.SubProcessItem;
import com.fms.app.utils.CommonUtil;
import com.fms.app.utils.ResponseUtil;

@Controller
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RequestMapping(value = "/api/v1")
public class SideNavController {

	@Autowired
	UserScreensService userScreenSrv;
	@Autowired
	ScreenServiceImpl screenService;
	@Autowired
	FMSubProcessesService subProcessServ;
	@Autowired
	ModelMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(SideNavController.class);

	private String processId;

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@RequestMapping(value = "/side-nav/get", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getScreens(@RequestBody ScreenAssignInput input) {

		try {
			if (input != null) {
				List<Screen> screenRecords = this.screenService.getScreen();
				List<UserScreens> records = this.userScreenSrv.getUserScreensByUserRole(
						input.getUserRoleId());
				SideNavItems sideNav = new SideNavItems();
				List<MenuItems> sideNavList = new ArrayList<MenuItems>();
				
				MenuItems menuItems = new MenuItems();
				
				List<UserScreens> disProcees = records.stream().filter(distinctByKeys(UserScreens::getProcessId,UserScreens::getSubProcessId))
						.collect(Collectors.toList());
				for (UserScreens process : disProcees) {
					List<SubMenuItems> subMenuRecords = new ArrayList<SubMenuItems>();
					List<SubProcessItem> subProcessItem = new ArrayList<SubProcessItem>();
					
					
					menuItems = new MenuItems();
					
					if(process.getSubProcessId() != null) {
						FMSubProcess subProcess = this.subProcessServ.getFMSubProcessById(process.getSubProcessId());
						SubProcessItem subProc = new SubProcessItem();
						List<SubMenuItems> subProcMenuRecords = new ArrayList<SubMenuItems>();
						List<UserScreens> subProcessScreens = records.stream()
								.filter(x -> x.getProcessId() == process.getProcessId() && x.getSubProcessId() > 0 
										&& x.getSubProcessId() == subProcess.getSubProcessId())
								.collect(Collectors.toList());
						if (subProcessScreens != null) {
							subProcessScreens.forEach(rec -> {
//							SubMenuItems subProcMenu = screenRecords.stream()
//									.filter(subProcItem -> subProcItem.getScreenNum() == rec.getScreenNum())
//									.map(this::convertScreenTOSubMenuItems).findFirst().get();
								if(rec.getScreen()!=null) {
									SubMenuItems subProcMenu = convertScreenTOSubMenuItems(rec.getScreen());
									subProcMenuRecords.add(subProcMenu);
								}	
						});
						}
						subProc.setTitle(subProcess.getTitle());
						subProc.setIcon("");
						subProc.setSubMenuItems(subProcMenuRecords);
						subProcessItem.add(subProc);

					}else {
						records.stream().filter(
								x -> x.getProcessId() == process.getProcessId()
										&& (x.getSubProcessId() == null || x.getSubProcessId() <= 0 ))
								.forEach(rec -> {
//							SubMenuItems subMenu = screenRecords.stream()
//									.filter(subItem -> subItem.getScreenNum() == rec.getScreenNum())
//									.map(this::convertScreenTOSubMenuItems).findFirst().get();
									if(rec.getScreen()!=null) {
										SubMenuItems subMenu = convertScreenTOSubMenuItems(rec.getScreen());
										subMenuRecords.add(subMenu);
									}
								});
					}
					MenuItems data = findPreviousProcessStage(sideNavList, process.getFmProcess().getTitle());
					if (data != null) {
						menuItems = data;
						if (!subMenuRecords.isEmpty())
							menuItems.setSubMenuItems(subMenuRecords);
						if (!subProcessItem.isEmpty()) {
							List<SubProcessItem> allSubProcess = new ArrayList<SubProcessItem>();
							List<SubProcessItem> prevItem = menuItems.getSubProcessItem();
							prevItem.forEach(rec -> {
								allSubProcess.add(rec);
							});
							allSubProcess.add(subProcessItem.get(0));
							menuItems.setSubProcessItem(allSubProcess);	
						}
						sideNavList.remove(data);
						sideNavList.add(menuItems);
					} else {
						menuItems.setTitle(process.getFmProcess().getTitle());
						menuItems.setIcon(process.getFmProcess().getIcon());
						menuItems.setSubMenuItems(subMenuRecords);
						menuItems.setSubProcessItem(subProcessItem);
						sideNavList.add(menuItems);
					}
				}
				// Sort by display_order for consistent ordering
				sideNavList.sort((a, b) -> {
					Integer aOrder = screenRecords.stream()
							.filter(s -> s.getScreenName() != null && s.getScreenName().contains(a.getTitle()))
							.map(Screen::getDisplayOrder)
							.findFirst().orElse(Integer.MAX_VALUE);
					Integer bOrder = screenRecords.stream()
							.filter(s -> s.getScreenName() != null && s.getScreenName().contains(b.getTitle()))
							.map(Screen::getDisplayOrder)
							.findFirst().orElse(Integer.MAX_VALUE);
					return aOrder.compareTo(bOrder);
				});
				sideNav.setMenuItems(sideNavList);
				return new ResponseEntity<>(sideNav, HttpStatus.OK);
			}
			return new ResponseEntity<>(new ResponseUtil("Bad Request", HttpStatus.INTERNAL_SERVER_ERROR.value()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SideNavController.getScreens: "+stacktrace,e);
			return new ResponseEntity<>(new ResponseUtil<>(exceptionCause, HttpStatus.CONFLICT.value()), HttpStatus.OK);
		}
	}

	private MenuItems findPreviousProcessStage(List<MenuItems> sideNavList, String proceesId) {
		try {

			if (sideNavList != null) {
				return sideNavList.stream().filter(x -> x.getTitle() != null && x.getTitle().equals(proceesId))
						.findAny().orElse(null);

			} else {
				return null;
			}
		} catch (Exception e) {
			String exceptionCause = CommonUtil.getExceptionCause(e);
			String stacktrace = CommonUtil.getStakeTrace(e);
			logger.error("Exception in SideNavController.findPreviousProcessStage: "+stacktrace,e);
			return null;
		}
	}

	private SubMenuItems convertScreenTOSubMenuItems(Screen screenMap) {
		SubMenuItems subMenuItems = new SubMenuItems();
		subMenuItems.setTitle(screenMap.getScreenName());
		subMenuItems.setLink(normalizeScreenNavUrl(screenMap.getScreenNavUrl()));

		return subMenuItems;

	}

	private String normalizeScreenNavUrl(String link) {
		if (link == null) {
			return "/welcome";
		}
		String rawLink = link.trim();
		if (rawLink.isEmpty()) {
			return "/welcome";
		}
		String[] parts = rawLink.split("\\?", 2);
		String normalizedPath = parts[0];
		try {
			normalizedPath = java.net.URLDecoder.decode(normalizedPath, java.nio.charset.StandardCharsets.UTF_8.name());
		} catch (Exception ignored) {
			normalizedPath = parts[0];
		}
		normalizedPath = normalizedPath.replace('\\', '/').replaceAll("/+$", "").replaceAll("^/+", "").replaceAll("/+", "/");
		normalizedPath = normalizedPath.replaceAll("\\s+", "-").toLowerCase();
		java.util.Map<String, String> aliasMap = new java.util.HashMap<String, String>();
		aliasMap.put("dashboard", "space-dashboard");
		aliasMap.put("dashboards", "welcome");
		aliasMap.put("index", "welcome");
		aliasMap.put("home", "welcome");
		aliasMap.put("background-loc", "back-loc");
		aliasMap.put("romms", "room-category");
		aliasMap.put("room-cat", "room-category");
		if (aliasMap.containsKey(normalizedPath)) {
			normalizedPath = aliasMap.get(normalizedPath);
		}
		if (normalizedPath.isEmpty()) {
			return "/welcome";
		}
		return "/" + normalizedPath + (parts.length > 1 && !parts[1].isEmpty() ? "?" + parts[1] : "");
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<Object, Boolean>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {

	    final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

	    return t -> {

	        final List<?> keys = Arrays.stream(keyExtractors)
	            .map(ke -> ke.apply(t))
	            .collect(Collectors.toList());

	        return seen.putIfAbsent(keys, Boolean.TRUE) == null;

	    };

	}
}
