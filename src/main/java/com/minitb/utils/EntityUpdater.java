package com.minitb.utils;

import com.minitb.entity.Consultant;
import com.minitb.entity.Project;

public class EntityUpdater {

    public static Consultant resolverConsultant(Consultant updatedConsultant) {
        if (updatedConsultant != null && updatedConsultant.id !=null) {
            return Consultant.findById(updatedConsultant.id);
        }
        return updatedConsultant;
    }

    public static Project resolveProject(Project upatedProject) {
        if (upatedProject != null && upatedProject.id != null) {
            return Project.findById(upatedProject.id);
        }
        return upatedProject;
    }
}
