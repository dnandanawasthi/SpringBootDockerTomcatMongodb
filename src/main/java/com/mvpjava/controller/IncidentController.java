package com.mvpjava.controller;

import com.mvpjava.LogRecord;
import com.mvpjava.Main;
import com.mvpjava.MongoService;
import com.mvpjava.model.Incident;
import com.mvpjava.model.Incidents;
import com.mvpjava.model.SelectionCriteria;
import com.mvpjava.model.StatusMessage;
import com.mvpjava.service.IncidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/portal")
@Api(value="Incident Service")
public class IncidentController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Main.class);
    public static final String APPLICATION_JSON = "application/json; charset=UTF-8";
    public static final String APPLICATION_XML = "application/xml; charset=UTF-8";
    public static final String APPLICATION_HTML = "text/html";


    @Autowired
    MongoService mongoLoggerSerive;
    @Autowired
    private IncidentService incidentService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String home() {
        mongoLoggerSerive.logToMongo(new LogRecord("INFO", "New Home page hit"));
        return "Spring Boot war deployment in Tomcat Docker Container successfull <P>" +
                mongoLoggerSerive.getMongoDbInfo();
    }

    @RequestMapping(value="/hits", method = RequestMethod.GET)
    public String getHomePageHits() {
        long homePageHitCount = mongoLoggerSerive.getHomePageHitCount();
        return "The Home page has been hit " +  homePageHitCount + " times";
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = APPLICATION_HTML)
    public @ResponseBody
    String status() {
        logger.info("Inside status() method...");
        return "application OK...";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public @ResponseBody String getErrorMessage() {
        return "error";
    }

    @ApiOperation(value="Find Incidents",notes="Find all the Incidents",response=Main.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Incident successfully"),
            @ApiResponse(code = 401, message = "You are Not authorized to find Incident"),
            @ApiResponse(code = 403, message = "Find Incident is forbidden"),
            @ApiResponse(code = 404, message = "Resource Not found")
    })
    @RequestMapping(value = "/v1/incidents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllIncidentsJson() {
        logger.info("Inside getAllIncidents() method...");

        List<Incident> allIncidents = incidentService.getAllIncidents();

        return new ResponseEntity<>(allIncidents, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/incidents", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getAllIncidentsXml() {
        logger.info("Inside getAllIncidents() method...");

        Incidents allIncidents = new Incidents(incidentService.getAllIncidents());

        return new ResponseEntity<>(allIncidents, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/incidents/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id", required = false) String id) {

        if (id == null || id.isEmpty()) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("'id' is a required field for this request");

            if (logger.isInfoEnabled()) {
                logger.info("'id' is a required field for this request");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }

        Incident incident = incidentService.getIncidentById(id);

        if (incident == null) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.NOT_FOUND.value());
            statusMessage.setMessage("'id' is a required field for this request");

            logger.info("Inside getIncidentById, ID: " + id + ", NOT FOUND!");

            return new ResponseEntity<>(statusMessage, HttpStatus.NOT_FOUND);
        }

        logger.info("Inside getIncidentById, returned: " + incident.toString());

        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/search/std", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> standardSearchJson(@RequestParam(value = "createdBy", required = false) String createdBy) {

        logger.info("Inside standardSearchJson() method...");
        logger.info("createdBy....: " + createdBy);

        if (createdBy == null) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("createdBy may not be empty.");

            logger.error("createdBy may not be empty.  Search aborted!!!");
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        } else {
            List<Incident> filteredAssociates = incidentService.getIncidentStandardSearch(createdBy);

            return new ResponseEntity<>(filteredAssociates, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/v1/search/std", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> standardSearchXML(@RequestParam(value = "createdBy", required = false) String createdBy) {

        logger.info("Inside standardSearchXML() method...");
        logger.info("createdBy....: " + createdBy);

        if (createdBy == null) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("Both createdBy may not be empty.");

            logger.error("Both firstName and lastName may not be empty.  Search aborted!!!");
            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        } else {
            Incidents filteredAssociates = new Incidents(incidentService.getIncidentStandardSearch(createdBy));

            return new ResponseEntity<>(filteredAssociates, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/v1/search/adv", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> advancedSearchJson(@RequestBody List<SelectionCriteria> criteriaList) {
        logger.info("Inside advancedSearchJson() method...");

  /*
   * for (SelectionCriteria criteria: criteriaList) {
   * logger.info(criteria.toString()); }
   */

        List<Incident> filteredAssociates = incidentService.getIncidentBySelectionCriteria(criteriaList);

        return new ResponseEntity<>(filteredAssociates, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/search/adv", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> advancedSearchXml(@RequestBody List<SelectionCriteria> criteriaList) {
        logger.info("Inside advancedSearchXml() method...");

	/*
	* for (SelectionCriteria criteria: criteriaList) {
	* logger.info(criteria.toString()); }
	*/

        Incidents filteredAssociates = new Incidents(incidentService.getIncidentBySelectionCriteria(criteriaList));

        return new ResponseEntity<>(filteredAssociates, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/incident/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIncidentById(@PathVariable(value = "id", required = false) String id) {

        if (id == null || id.isEmpty()) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("'id' is a required field for this request");

            if (logger.isInfoEnabled()) {
                logger.info("'id' is a required field for this request");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }

        Incident incident = incidentService.deleteIncident(id);

        if (incident == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Inside deleteIncidentById, ID: " + id + ", NOT FOUND!");
            }

            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.NOT_FOUND.value());
            statusMessage.setMessage("Unable to delete incident ID: " + id);

            if (logger.isInfoEnabled()) {
                logger.info("Inside getIncidentById, ID: " + id + ", NOT FOUND!");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.NOT_FOUND);
        }

        if (logger.isInfoEnabled()) {
            logger.info("Inside deleteIncidentById, deleted: "+ incident.toString());
        }

        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/incidents/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateIncidentById(
            @PathVariable(value = "id", required = false) String id,
            @RequestBody Incident incident) {

        if (id == null || id.isEmpty()) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("'id' is a required field for this request");

            if (logger.isInfoEnabled()) {
                logger.info("'id' is a required field for this request");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }

        Incident myIncident = incidentService.updateIncident(id, incident);

        if (myIncident == null) {
            logger.info("Unable to update incident.  ID: " + id + ", NOT FOUND!");

            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.NOT_FOUND.value());
            statusMessage.setMessage("Unable to delete incident ID: " + id);

            return new ResponseEntity<>(statusMessage, HttpStatus.NOT_FOUND);
        }

        if (logger.isInfoEnabled()) {
            logger.info("Inside updateIncidentById, updated: "+ myIncident.toString());
        }
        return new ResponseEntity<>(myIncident, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/incidents", method = RequestMethod.POST)
    public ResponseEntity<?> addIncident(@RequestBody Incident incident) {

        logger.info("Inside addIncident, model attribute: "+ incident.toString());

        if (incident.getId() == null || incident.getId().isEmpty()) {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.BAD_REQUEST.value());
            statusMessage.setMessage("'id' is a required field for this request");

            if (logger.isInfoEnabled()) {
                logger.info("'id' is a required field for this request");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.BAD_REQUEST);
        }

        Incident myIncident = incidentService.getIncidentById(incident.getId());
        if (myIncident != null) {
            if (myIncident.getId() != null && myIncident.getId().equalsIgnoreCase(incident.getId())) {
                StatusMessage statusMessage = new StatusMessage();
                statusMessage.setStatus(HttpStatus.CONFLICT.value());
                statusMessage.setMessage("ID already exists in the system.");

                logger.info("'id' is a required field for this request");

                return new ResponseEntity<>(statusMessage, HttpStatus.CONFLICT);
            }
        }

        if (incident.getId() != null && incident.getId().length() > 0) {
            logger.info("Inside addIncident, adding: " + incident.toString());
            incidentService.addIncident(incident);
        } else {
            StatusMessage statusMessage = new StatusMessage();
            statusMessage.setStatus(HttpStatus.NOT_MODIFIED.value());
            statusMessage.setMessage("Failed to add incident");

            if (logger.isInfoEnabled()) {
                logger.info("Failed to insert...");
            }

            return new ResponseEntity<>(statusMessage, HttpStatus.NOT_MODIFIED);
        }

        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

}
