package com.minitb.rest;

import com.minitb.entity.Consultant;
import com.minitb.services.ConsultantService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/consultants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultantResource {

    @Inject
    ConsultantService consultantService;

    @GET
    public List<Consultant> listOfConsultants() {
        return consultantService.retrieveAll();
    }

    @GET
    @Path("/{id}")
    public Consultant getConsultant(@PathParam("id") Long id) {
        Consultant consultant = consultantService.retrieveById(id);
        if (consultant == null) throw new NotFoundException();
        return consultant;
    }

    @POST
    @Transactional
    public Response create(Consultant c) {
        Consultant created = consultantService.addConsultant(c);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Consultant c) {
        Consultant updated = consultantService.updateConsultant(id, c);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean removed = consultantService.removeConsultant(id);
        if (!removed) throw new NotFoundException();
    }
}
