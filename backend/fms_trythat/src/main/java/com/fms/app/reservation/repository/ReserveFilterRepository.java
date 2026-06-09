package com.fms.app.reservation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fms.app.reservation.models.ReserveFilter;

@Repository
public interface ReserveFilterRepository extends JpaRepository<ReserveFilter, Integer> {
	
	public List<ReserveFilter> getReserveFilterByUserId(int userId);
	
//	@Query("SELECT rf FROM reserve_filter rf where user_id=?1")
//	public List<ReserveFilter> findByUserId(int userId);
}
