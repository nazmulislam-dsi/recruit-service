package org.ni.recruitservice.repository;

import org.ni.recruitservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nazmul on 10/6/2018.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
