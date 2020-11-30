package org.jugistanbul.hibernatesearch.config;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 30.11.2020
 **/
@Configuration
public class BeanConfig
{
    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public SearchSession getSearchSession(){
        return Search.session(entityManager);
    }
}
