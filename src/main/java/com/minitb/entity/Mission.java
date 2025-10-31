package com.minitb.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "MISSIONS")
@NoArgsConstructor
@AllArgsConstructor
public class Mission extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "id_consultant", nullable = false)
    public Consultant consultant;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    public Project project;

    @Column(name = "role", length = 100)
    public String role;

    @Column(name = "start_date_mission", nullable = false)
    public LocalDate startDateMission;

    @Column(name = "end_date_mission")
    public LocalDate endDateMission;
}
