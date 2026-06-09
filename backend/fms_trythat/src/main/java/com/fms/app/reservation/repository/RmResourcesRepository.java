package com.fms.app.reservation.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.fms.app.reservation.models.RmResources;



public interface RmResourcesRepository extends CrudRepository<RmResources, Integer> {

	List<RmResources> getByBlIdAndFlIdAndRmId(int blId, int flId, int rmId);

}
