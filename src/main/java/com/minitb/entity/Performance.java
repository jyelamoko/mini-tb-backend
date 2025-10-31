package com.minitb.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PERFORMANCES",
uniqueConstraints = @UniqueConstraint(columnNames = {"consultant_id", "month_perf"}))
@NoArgsConstructor
@AllArgsConstructor
public class Performance extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "consultant_id", nullable = false)
    public Consultant consultant;

    @Column(name = "month_perf", length = 7, nullable = false)
    public String monthPerf; // format "AAAA-MM"

    @Column(name = "occupancy_rate")
    public Double occupancyRate;

    @Column(name = "evaluation_note")
    public Double evaluationNote;

    @Column(name = "comments")
    public String comments;
}
