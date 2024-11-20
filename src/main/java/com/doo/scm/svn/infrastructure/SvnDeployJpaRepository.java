package com.doo.scm.svn.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SvnDeployJpaRepository extends JpaRepository<SvnDeployEntity, Long> {
    
}
