package com.minitb.repository;

import com.minitb.entity.Performance;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PerformanceRepository implements PanacheRepository<Performance> { }
