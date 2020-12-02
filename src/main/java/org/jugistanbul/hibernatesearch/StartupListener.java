package org.jugistanbul.hibernatesearch;

import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@Component
@Order(0)
class StartupListener implements ApplicationListener<ApplicationReadyEvent>
{
    private SearchSession searchSession;
    private final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    public StartupListener(SearchSession searchSession) {
        this.searchSession = searchSession;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        MassIndexer indexer = searchSession.massIndexer();
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            logger.error("An InterruptedException was thrown", e);
            Thread.currentThread().interrupt();
        }
    }

}