package com.minitb.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PROJECTS")
@NoArgsConstructor
@AllArgsConstructor
public class Project extends PanacheEntity {

    @Column(name = "project_name", length = 150, nullable = false)
    public String projectName; // projectName - nomProjet

    @Column(name = "client", length = 150, nullable = false)
    public String client;

    @Column(name = "description")
    public String description;

    @Column(name = "start_date", nullable = false)
    public LocalDate startDate; // startDate - dateDebut

    @Column(name = "end_date")
    public LocalDate endDate; // endDate - dateFin

    @Column(name = "state")
    public String state; // state - etat
}
