package org.jugistanbul.hibernatesearch.resources;

import io.quarkus.runtime.StartupEvent;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jugistanbul.hibernatesearch.model.Event;
import org.jugistanbul.hibernatesearch.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@Path("/")
public class SearchResource
{
    private final int NOT_FOUND = 404;
    private final Logger logger = LoggerFactory.getLogger(SearchResource.class);

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onStartup(@Observes StartupEvent event){
        MassIndexer indexer = Search.session(entityManager).massIndexer();
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            logger.error("An InterruptedException was thrown", e);
            Thread.currentThread().interrupt();
        }
    }

    @GET
    @Path("/search/event/{name}")
    @Produces(APPLICATION_JSON)
    public List<Event> searchEventsByName(@PathParam String name){
        SearchResult<Event> result = Search.session(entityManager)
                .search(Event.class)
                .where( f -> f.simpleQueryString()
                        .field("name")
                        .matching(name))
                .fetch(20);

        throwExceptionIfNotFound(result.total().hitCount(), "event");
        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/host/name/{name}")
    @Produces(APPLICATION_JSON)
    public List<Host> searchHostsByName(@PathParam String name){
        SearchResult<Host> result = Search.session(entityManager)
                .search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("firstname", "lastname")
                        .matching(name))
                .fetch(20);

        throwExceptionIfNotFound(result.total().hitCount(), "host");
        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/host/title/{title}")
    @Produces(APPLICATION_JSON)
    public List<Host> searchHostsByTitle(@PathParam String title){
        SearchResult<Host> result = Search.session(entityManager)
                .search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("title")
                        .matching(title))
                .fetch(20);

        throwExceptionIfNotFound(result.total().hitCount(), "title");
        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/events")
    @Produces(APPLICATION_JSON)
    public List<Event> allEvents(){
        SearchResult<Event> result = Search.session(entityManager)
                .search(Event.class)
                .where( f -> f.matchAll())
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GET
    @Path("/search/hosts")
    @Produces(APPLICATION_JSON)
    public List<Host> allHosts(){
        SearchResult<Host> result = Search.session(entityManager)
                .search(Host.class)
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

    void throwExceptionIfNotFound(long count, String type){
        if(count == 0){
            throw new WebApplicationException(String.format("No %s found", type), NOT_FOUND);
        }
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception e) {

            int code = 500;
            if(e instanceof WebApplicationException){
                code = ((WebApplicationException) e).getResponse().getStatus();
            }

            JsonObjectBuilder entityBuilder = Json
                    .createObjectBuilder()
                    .add("exceptionType", e.getClass().getName())
                    .add("code", code);

            if(null != e.getMessage()){
                entityBuilder.add("error", e.getMessage());
            }

            return Response.status(code).entity(entityBuilder.build()).build();
        }
    }
}
