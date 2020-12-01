package org.jugistanbul.hibernatesearch.config;

import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 30.11.2020
 **/
public class EnglishAnalysisConfigurer implements ElasticsearchAnalysisConfigurer
{
    @Override
    public void configure(ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer( "english" ).custom()
                .tokenizer( "standard" )
                .tokenFilters( "lowercase", "snowball_english", "asciifolding" );

        context.tokenFilter( "snowball_english" )
                .type( "snowball" )
                .param( "language", "English" );

        context.analyzer( "name" ).custom()
                .tokenizer( "standard" )
                .tokenFilters( "lowercase", "asciifolding" );
    }
}
