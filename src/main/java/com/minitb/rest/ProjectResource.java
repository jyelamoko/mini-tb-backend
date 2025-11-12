package com.minitb.rest;

import com.minitb.entity.Project;
import com.minitb.services.ProjectService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @GET
    public List<Project> listAll() {
        return projectService.listOfProjects();
    }

    @GET
    @Path("/{id}")
    public Project get(@PathParam("id") Long id) {
        return Project.findById(id);
    }

    @POST
    @Transactional
    public Project create(Project p) {
        p.persist();
        return p;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Project update(@PathParam("id") Long id, Project p) {
        Project updated = projectService.update(id, p);
        if (updated == null) throw new NotFoundException();
        return updated;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean removed = projectService.delete(id);
        if (!removed) throw new NotFoundException();
    }
}
