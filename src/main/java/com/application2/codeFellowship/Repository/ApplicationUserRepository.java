package com.application2.codeFellowship.Repository;

import com.application2.codeFellowship.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Integer> {
ApplicationUser findApplicationUserByUsername(String username);
}
