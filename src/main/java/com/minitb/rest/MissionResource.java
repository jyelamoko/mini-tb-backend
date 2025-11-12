package com.minitb.rest;

import com.minitb.entity.Mission;
import com.minitb.services.MissionService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/missions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MissionResource {

    @Inject
    MissionService missionService;

    @GET
    public List<Mission> listAll() {
        return missionService.listOfMissions();
    }

    @GET
    @Path("/{id}")
    public Mission getMissionById(@PathParam("id") Long id) {
        Mission m = missionService.findMissionById(id);
        if (m == null) throw new NotFoundException();
        return m;
    }

    @POST
    @Transactional
    public Mission createMission(Mission m) {
        return missionService.createMission(m);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Mission updateMission(@PathParam("id") Long id, Mission m) {
        Mission updated = missionService.updateMission(id, m);
        if (updated == null) throw new NotFoundException();
        return updated;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean removed = missionService.deleteMission(id);
        if (!removed) throw new NotFoundException();
    }
}
