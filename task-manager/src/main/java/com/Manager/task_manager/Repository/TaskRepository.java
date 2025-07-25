package com.Manager.task_manager.Repository;

import com.Manager.task_manager.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByTitleAndTeamLeadIdAndUserId(String title, Long teamLeadId, Long userId);
}
