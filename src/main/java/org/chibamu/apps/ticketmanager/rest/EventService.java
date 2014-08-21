package org.chibamu.apps.ticketmanager.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chibamu.apps.ticketmanager.model.Event;
//import org.jboss.arquillian.core.api.annotation.Inject;

@Path("/events")
@RequestScoped
public class EventService {
	
	@Inject
	private EntityManager em;
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getAllEvents(){
		final List<Event> results = em.createQuery("select e from Event order by e.name").getResultList();
		return results;
	}
}
