package com.Manager.task_manager.Repository;

import com.Manager.task_manager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
