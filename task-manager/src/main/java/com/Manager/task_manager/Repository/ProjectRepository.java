package com.Manager.task_manager.Repository;

import com.Manager.task_manager.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByProjectNameAndTeamLead_Email(String projectName, String email);

}
