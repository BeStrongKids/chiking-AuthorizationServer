package com.bestrongkids.authorizationserver.repositories;

import com.bestrongkids.authorizationserver.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
