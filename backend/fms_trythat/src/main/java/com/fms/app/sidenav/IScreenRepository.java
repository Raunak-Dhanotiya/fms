package com.fms.app.sidenav;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScreenRepository extends JpaRepository<Screen, Integer> {


}
