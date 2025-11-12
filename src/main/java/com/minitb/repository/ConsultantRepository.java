package com.minitb.repository;

import com.minitb.entity.Consultant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsultantRepository implements PanacheRepository<Consultant> {
    // méthodes utilitaires si besoin
}
