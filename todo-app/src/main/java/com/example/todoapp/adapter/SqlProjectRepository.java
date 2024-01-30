package com.example.todoapp.adapter;

import com.example.todoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SqlProjectRepository extends JpaRepository<Project, Integer>{

    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();
}
