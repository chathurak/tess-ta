package com.languagematters.tessta.jpa.repository;

import com.languagematters.tessta.jpa.entity.Role;
import com.languagematters.tessta.jpa.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}