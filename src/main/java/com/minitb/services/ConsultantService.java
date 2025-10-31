package com.minitb.services;

import com.minitb.entity.Consultant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ConsultantService {

    public List<Consultant> retrieveAll() {
        return Consultant.listAll();
    }

    public Consultant retrieveById(Long id) {
        return Consultant.findById(id);
    }

    @Transactional
    public Consultant addConsultant(Consultant consultant) {
        boolean exists = Consultant.find("email = ?1 OR phoneNumber = ?2", consultant.email, consultant.phoneNumber)
                .firstResult() != null;
        if (exists) {
            throw new WebApplicationException("Consultant déjà existant en BDD", Response.Status.CONFLICT);
        }
        consultant.id = null;
        consultant.persist();
        return consultant;
    }

    @Transactional
    public Consultant updateConsultant(Long id, Consultant updated) {
        Consultant consultantEntity = Consultant.findById(id);
        if (consultantEntity == null) {
            return null;
        }
        consultantEntity.firstName = updated.firstName;
        consultantEntity.lastName = updated.lastName;
        consultantEntity.jobTitle = updated.jobTitle;
        consultantEntity.status = updated.status;
        consultantEntity.hireDate = updated.hireDate;
        consultantEntity.email = updated.email;
        consultantEntity.phoneNumber = updated.phoneNumber;
        return consultantEntity;
    }

    @Transactional
    public boolean removeConsultant(Long id) {
        return Consultant.deleteById(id);
    }
}
