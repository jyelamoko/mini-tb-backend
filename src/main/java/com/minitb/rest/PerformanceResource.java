package com.minitb.rest;

import com.minitb.entity.Performance;
import com.minitb.services.PerformanceService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/performance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerformanceResource {

    @Inject
    PerformanceService performanceService;

    @GET
    public List<Performance> listAll() {
        return performanceService.listOfPerformances();
    }

    @GET
    @Path("/{id}")
    public Performance getPerformanceById(@PathParam("id") Long id) {
        Performance p = performanceService.findPerformanceById(id);
        if (p == null) throw new NotFoundException();
        return p;
    }

    @POST
    @Transactional
    public Performance createPerformance(Performance p) {
        return performanceService.createPerformance(p);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Performance updatePerformance(@PathParam("id") Long id, Performance p) {
        Performance updated = performanceService.updatePerformance(id, p);
        if (updated == null) throw new NotFoundException();
        return updated;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean removed = performanceService.deletePerformance(id);
        if (!removed) throw new NotFoundException();
    }
}
