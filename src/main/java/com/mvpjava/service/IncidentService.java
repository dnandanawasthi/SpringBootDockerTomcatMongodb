package com.mvpjava.service;

import com.mvpjava.model.Incident;
import com.mvpjava.model.SelectionCriteria;
import com.mvpjava.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    public List<Incident> getAllIncidents() {
        return incidentRepository.getAllIncidents();
    }

    public Incident getIncidentById(final String id) {
        return incidentRepository.getIncidentById(id);
    }

    public List<Incident> getIncidentStandardSearch(final String createdBy) {
        return incidentRepository.getIncidentsStandardSearch(createdBy);
    }

    public List<Incident> getIncidentBySelectionCriteria(final List<SelectionCriteria> criteriaList) {
         return incidentRepository.getIncidentsBySelectionCriteria(criteriaList);
    }

    public void addIncident(final Incident incident) {
        incidentRepository.addIncident(incident);
    }
    public Incident deleteIncident(final String id) {
        return incidentRepository.deleteIncident(id);
    }

    public Incident updateIncident(final String id, final Incident incident) {
        return incidentRepository.updateIncident(id, incident);
    }
}
