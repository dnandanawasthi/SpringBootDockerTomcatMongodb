package com.mvpjava.repository;

import com.mvpjava.model.Incident;
import com.mvpjava.model.SelectionCriteria;
import com.mvpjava.util.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IncidentRepository {
	//public static final String COLLECTION_NAME = "incident";
	public static final String COLLECTION_NAME = "logs";
	private static final Logger logger = LoggerFactory
			.getLogger(IncidentRepository.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	public void addIncident(Incident incident) {
		if (!mongoTemplate.collectionExists(Incident.class)) {
			mongoTemplate.createCollection(Incident.class);
		}
		mongoTemplate.insert(incident, COLLECTION_NAME);
	}

	public Incident getIncidentById(String id) {
		return mongoTemplate.findOne(
				Query.query(Criteria.where("id").is(id)), Incident.class,
				COLLECTION_NAME);
	}

	public List<Incident> getAllIncidents() {
		return mongoTemplate.findAll(Incident.class, COLLECTION_NAME);
	}

	public List<Incident> getIncidentsStandardSearch(
			@RequestParam("createdBy") String createdBy) {
		logger.info("getIncidentsStandardSearch: "+createdBy);
		List<Criteria> andCriteriaList = new ArrayList<Criteria>();
		boolean ok = false;

		Query query = new Query();

		if (createdBy != null && createdBy.length() > 0) {
			Criteria c1 = Criteria.where("createdBy").regex(createdBy, "i");
			andCriteriaList.add(c1);
			ok = true;
		}
		if (ok) {
			query.addCriteria(new Criteria().andOperator(andCriteriaList
					.toArray(new Criteria[andCriteriaList.size()])));

			return mongoTemplate.find(query, Incident.class,
					COLLECTION_NAME);
		} else {
			return null;
		}
	}

	public List<Incident> getIncidentsBySelectionCriteria(
			List<SelectionCriteria> criteriaList) {

		List<Criteria> andCriteriaList = new ArrayList<Criteria>();

		Query query = new Query();

		for (SelectionCriteria criteriaElem : criteriaList) {
			if (criteriaElem.getOperator().getId().equals("equalTo")) {
				Criteria c1 = Criteria.where(criteriaElem.getField().getId())
						.is(criteriaElem.getValue());
				andCriteriaList.add(c1);
			} else if (criteriaElem.getOperator().getId().equals("like")) {
				Criteria c1 = Criteria.where(criteriaElem.getField().getId())
						.regex(criteriaElem.getValue(), "i");
				andCriteriaList.add(c1);
			} else if (criteriaElem.getOperator().getId()
					.equals("notEqualTo")) {
				Criteria c1 = Criteria.where(criteriaElem.getField().getId())
						.ne(criteriaElem.getValue());
				andCriteriaList.add(c1);
			} else if (criteriaElem.getOperator().getId()
					.equals("greaterThan")) {
				Criteria c1 = Criteria.where(criteriaElem.getField().getId())
						.gt(DateUtility.getDate(criteriaElem.getValue()));
				andCriteriaList.add(c1);
			} else if (criteriaElem.getOperator().getId()
					.equals("lessThan")) {
				Criteria c1 = Criteria.where(criteriaElem.getField().getId())
						.lt(DateUtility.getDate(criteriaElem.getValue()));
				andCriteriaList.add(c1);
			}
			logger.info(criteriaElem.toString());
		}
		query.addCriteria(new Criteria().andOperator(andCriteriaList
				.toArray(new Criteria[andCriteriaList.size()])));

		return mongoTemplate.find(query, Incident.class, COLLECTION_NAME);
	}

	public Incident deleteIncident(String id) {
		Incident Incident = mongoTemplate.findOne(
				Query.query(Criteria.where("id").is(id)), Incident.class,
				COLLECTION_NAME);
		mongoTemplate.remove(Incident, COLLECTION_NAME);

		return Incident;
	}

	public Incident updateIncident(String id,
								   Incident Incident) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));

		Update update = new Update();
		update.set("id", Incident.getId());
		update.set("problemDescription", Incident.getProblemDescription());
		update.set("severity", Incident.getSeverity());
		update.set("status", Incident.getStatus());
		update.set("component", Incident.getComponent());
		update.set("createdBy", Incident.getCreateDate());
		update.set("assignedTo", Incident.getAssignedTo());
		update.set("createDate", Incident.getCreateDate());
		update.set("closeDate", Incident.getCloseDate());
		update.set("lastUpdatedDate", Incident.getLastUpdatedDate());
		update.set("solutionDescription", Incident.getSolutionDescription());
		update.set("daysOpen", Incident.getDaysOpen());

		mongoTemplate.updateFirst(query, update, Incident.class, COLLECTION_NAME);

		return Incident;
	}
}