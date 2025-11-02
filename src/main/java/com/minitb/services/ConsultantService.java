package com.minitb.services;

import com.minitb.entity.Consultant;
import com.minitb.repository.ConsultantRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class ConsultantService {

    @Inject
    ConsultantRepository cRepository;

    public List<Consultant> retrieveAll() {
        return cRepository.listAll();
    }

    public Consultant retrieveById(Long id) {
        return cRepository.findById(id);
    }

    @Transactional
    public Consultant addConsultant(Consultant consultant) {
        boolean exists = cRepository.find("email = ?1 OR phoneNumber = ?2", consultant.email, consultant.phoneNumber)
                .firstResult() != null;
        if (exists) {
            throw new WebApplicationException("Consultant déjà existant en BDD", Response.Status.CONFLICT);
        }
        consultant.id = null;
        cRepository.persist(consultant);
        return consultant;
    }

    @Transactional
    public Consultant updateConsultant(Long id, Consultant updated) {
        Consultant consultantEntity = cRepository.findById(id);
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
        return cRepository.deleteById(id);
    }
}
