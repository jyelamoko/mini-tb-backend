package com.minitb.repository;

import com.minitb.entity.Mission;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MissionRepository implements PanacheRepository<Mission> {
}
