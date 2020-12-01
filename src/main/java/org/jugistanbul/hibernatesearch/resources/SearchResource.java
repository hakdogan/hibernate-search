package org.jugistanbul.hibernatesearch.resources;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.jugistanbul.hibernatesearch.model.Event;
import org.jugistanbul.hibernatesearch.model.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@RestController
public class SearchResource
{
    private SearchSession searchSession;
    private EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(SearchResource.class);

    @Autowired
    public SearchResource(SearchSession searchSession, EntityManager entityManager) {
        this.searchSession = searchSession;
        this.entityManager = entityManager;
    }

    @GetMapping(path = "/search/event/{name}", produces = "application/json")
    public List<Event> searchEventsByName(@PathVariable("name") String name){
        SearchResult<Event> result = searchSession.search(Event.class)
                .where( f -> f.simpleQueryString()
                        .field("name")
                        .matching(name + "*"))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GetMapping(path = "/search/host/name/{name}", produces = "application/json")
    public List<Host> searchHostsByName(@PathVariable("name") String name){
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("firstname", "lastname")
                        .matching(name))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GetMapping(path = "/search/host/title/{title}", produces = "application/json")
    public List<Host> searchHostsByTitle(@PathVariable("title") String title){
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.simpleQueryString()
                        .fields("title")
                        .matching(title + "*"))
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GetMapping(path = "/search/events", produces = "application/json")
    public List<Event> allEvents(){
        SearchResult<Event> result = searchSession.search(Event.class)
                .where( f -> f.matchAll())
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @GetMapping(path = "/search/hosts", produces = "application/json")
    public List<Host> allHosts(){
        SearchResult<Host> result = searchSession.search(Host.class)
                .where( f -> f.matchAll())
                .fetch(20);

        logger.info("Hit count is {}", result.total().hitCount());
        return result.hits();
    }

    @Transactional
    @PostMapping(path = "/event/add", consumes = "application/json", produces = "application/json")
    public Event addEvent(@RequestBody Event event){
        entityManager.persist(event);
        return event;
    }

    @Transactional
    @DeleteMapping(path = "/event/delete/{id}", produces = "text/plain")
    public String deleteEventById(@PathVariable("id") int id){
        Event event = entityManager.find(Event.class, id);
        entityManager.remove(event);
        return String.join(" : ", "Removed", event.toString());
    }
}
