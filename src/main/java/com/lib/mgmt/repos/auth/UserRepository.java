package com.lib.mgmt.repos.auth;

import com.lib.mgmt.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);

}
