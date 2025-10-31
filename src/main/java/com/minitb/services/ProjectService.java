package com.minitb.services;

import com.minitb.entity.Project;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProjectService {

    public List<Project> listOfProjects() {
        return Project.listAll();
    }

    public Project findById(Long id) {
        return Project.findById(id);
    }

    @Transactional
    public Project create(Project p) {
        p.persist();
        return p;
    }

    @Transactional
    public Project update(Long id, Project updated) {
        Project entity = Project.findById(id);
        if (entity == null) {
            return null;
        }
        entity.projectName = updated.projectName;
        entity.client = updated.client;
        entity.description = updated.description;
        entity.startDate = updated.startDate;
        entity.endDate = updated.endDate;
        entity.state = updated.state;
        return entity;
    }

    @Transactional
    public boolean delete(Long id) {
        return Project.deleteById(id);
    }
}
