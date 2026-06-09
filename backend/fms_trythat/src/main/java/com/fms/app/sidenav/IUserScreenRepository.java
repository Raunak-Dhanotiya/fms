package com.fms.app.sidenav;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserScreenRepository extends JpaRepository<UserScreens, Integer> {

	@Query("SELECT us FROM UserScreens us left join FMProcesses fp on fp.processId =us.processId left join FMSubProcess fsp "
			+ " on (fsp.subProcessId =us.subProcessId AND fsp.processId =us.processId)"
			+ " left join Screen s on s.screenNum = us.screenNum WHERE us.userRoleId= ?1"
			+ " ORDER BY fp.displayOrder,fsp.displayOrder,s.displayOrder")
	public List<UserScreens> findByUserRole(Integer userRoleId);

	@Query("SELECT count(*) FROM UserScreens e WHERE userRoleId= ?1 and screenNum=?2")
	long countByUserRoleAndScreenNum(int userRoleId, int screenNum);

//	@Query(value = "Select count(screen) as count  from ( "
//			+ " Select distinct user_screens_num as screen, process_id from user_screens where process_id in (select process_id from fm_processes where process_code in ('Helpdesk','Order Management','Account Management','Facilities Desk','Space Management')) and user_role_id = ?1 "
//			+ " group by user_screens_num,process_id " + ") as t group by t.process_id" + 
//					" "
	@Query(value ="SELECT COALESCE(COUNT(t.screen), 0) AS count "
			+ "FROM (SELECT 'Facilities Desk' AS process_code, 1 AS order_column  UNION SELECT 'Space Management', 2 UNION SELECT 'Preventive Maintenance', 3  ) "
			+ "AS process_order JOIN fm_processes p ON p.process_code = process_order.process_code LEFT JOIN ( SELECT DISTINCT user_screens_num AS screen,  "
			+ "process_id  FROM user_screens  WHERE process_id IN ( SELECT process_id FROM fm_processes WHERE process_code IN ('Facilities Desk', "
			+ "'Space Management', 'Preventive Maintenance') ) AND user_role_id = ?1  GROUP BY user_screens_num, process_id) AS t ON p.process_id = t.process_id  "
			+ "GROUP BY p.process_id, process_order.order_column ORDER BY process_order.order_column"		
	, nativeQuery = true)
	List<List<Object>> getUserDashboard(int user_role_id);

	public List<UserScreens> findByUserRoleId(Integer userRole);
}
