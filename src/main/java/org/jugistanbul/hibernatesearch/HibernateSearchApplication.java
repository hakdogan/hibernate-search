package org.jugistanbul.hibernatesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

@SpringBootApplication
@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFERRED)
public class HibernateSearchApplication
{
	public static void main(String[] args) {
		SpringApplication.run(HibernateSearchApplication.class, args);
	}
}
