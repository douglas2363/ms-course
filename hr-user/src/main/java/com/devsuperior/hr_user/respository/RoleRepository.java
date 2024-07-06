package com.devsuperior.hr_user.respository;

import com.devsuperior.hr_user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
