package org.jugistanbul.hibernatesearch.resources;

import io.quarkus.runtime.StartupEvent;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jugistanbul.hibernatesearch.model.Event;
import org.jugistanbul.hibernatesearch.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@Path("/")
public class SearchResource
{

    private final Logger logger = LoggerFactory.getLogger(SearchResource.class);

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onStartup(@Observes StartupEvent event){
        SearchSession searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer();
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            logger.error("An InterruptedException was thrown", e);
        }
    }

    @GET
    @Path("/event/{name}")
    @Produces(APPLICATION_JSON)
    public List<Event> searchEventsByName(@PathParam String name){
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Event> result = searchSession.search(Event.class)
                .where( f -> f.simpleQueryString()
                        .field("name")
                        .matching(name + "*"))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/host/name/{name}")
    @Produces(APPLICATION_JSON)
    public List<Host> searchHostsByName(@PathParam String name){
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("firstname", "lastname")
                        .matching(name))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/host/title/{title}")
    @Produces(APPLICATION_JSON)
    public List<Host> searchHostsByTitle(@PathParam String title){
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("title")
                        .matching(title))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/events")
    @Produces(APPLICATION_JSON)
    public List<Event> allEvents(){
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Event> result = searchSession.search(Event.class)
                .where( f -> f.matchAll())
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/hosts")
    @Produces(APPLICATION_JSON)
    public List<Host> allHosts(){
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.matchAll())
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @Transactional
    @POST
    @Path("/event/add")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Event addEvent(Event event){
        entityManager.persist(event);
        return event;
    }

    @Transactional
    @POST
    @Path("/event/update")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Event updateEvent(Event event){
        entityManager.merge(event);
        return event;
    }

    @Transactional
    @POST
    @Path("/host/add")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Host addHost(Host host){
        entityManager.persist(host);
        return host;
    }

    @Transactional
    @POST
    @Path("/host/update")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Host updateHost(Host host){
        entityManager.merge(host);
        return host;
    }

    @Transactional
    @DELETE
    @Path("/event/delete/{id}")
    @Produces(APPLICATION_JSON)
    public String deleteEventById(@PathParam int id){
        Event event = entityManager.find(Event.class, id);
        entityManager.remove(event);
        return String.join(" : ", "Removed", event.toString());
    }
}
