package org.jugistanbul.hibernatesearch.resources;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.jugistanbul.hibernatesearch.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private final Logger logger = LoggerFactory.getLogger(SearchResource.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @GetMapping(path = "/search/events", produces = "application/json")
    public List<Event> allEvents(){
        SearchSession searchSession = Search.session( entityManager );

        SearchResult<Event> result = searchSession.search(Event.class)
                .where( f -> f.matchAll())
                .fetch( 20 );

        return result.hits();
    }
}
