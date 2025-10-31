package com.minitb.services;

import com.minitb.entity.Consultant;
import com.minitb.entity.Mission;
import com.minitb.entity.Project;
import com.minitb.utils.EntityUpdater;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MissionService {
    public List<Mission> listOfMissions() {
        return Mission.listAll();
    }

    public Mission findMissionById(Long id) {
        return Mission.findById(id);
    }

    @Transactional
    public Mission createMission(Mission mission) {
        // minimal checks: ensure consultant and projet exist (if provided with id)
        if (mission.consultant != null && mission.consultant.id != null) {
            mission.consultant = Consultant.findById(mission.consultant.id);
        }
        if (mission.project != null && mission.project.id != null) {
            Project p = Project.findById(mission.project.id);
            mission.project = p;
        }
        mission.persist();
        return mission;
    }

    @Transactional
    public Mission updateMission(Long id, Mission updated) {
        Mission entity = Mission.findById(id);
        if (entity == null) return null;
        entity.consultant = EntityUpdater.resolverConsultant(updated.consultant);
        entity.project = EntityUpdater.resolveProject(updated.project);
        entity.role = updated.role;
        entity.startDateMission = updated.startDateMission;
        entity.endDateMission = updated.endDateMission;
        return entity;
    }

    @Transactional
    public boolean deleteMission(Long id) {
        return Mission.deleteById(id);
    }

}
