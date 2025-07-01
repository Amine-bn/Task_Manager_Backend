package com.Manager.task_manager.Repository;

import com.Manager.task_manager.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
