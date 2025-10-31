package com.minitb.services;

import com.minitb.entity.Consultant;
import com.minitb.entity.Performance;
import com.minitb.utils.EntityUpdater;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PerformanceService {

    public List<Performance> listOfPerformances() {
        return Performance.listAll();
    }

    public Performance findPerformanceById(Long id) {
        return Performance.findById(id);
    }

    @Transactional
    public Performance createPerformance(Performance perf) {
        if (perf.consultant != null && perf.consultant.id != null) {
            /*Consultant c = Consultant.findById(perf.consultant.id);
            perf.consultant = c;*/
            perf.consultant = Consultant.findById(perf.consultant.id);
        }
        perf.persist();
        return perf;
    }

    @Transactional
    public Performance updatePerformance(Long id, Performance updated) {
        Performance entity = Performance.findById(id);
        if (entity == null) return null;
        entity.consultant = EntityUpdater.resolverConsultant(updated.consultant);
        entity.monthPerf = updated.monthPerf;
        entity.occupancyRate = updated.occupancyRate;
        entity.evaluationNote = updated.evaluationNote;
        entity.comments = updated.comments;
        return entity;
    }

    @Transactional
    public boolean deletePerformance(Long id) {
        return Performance.deleteById(id);
    }
}
