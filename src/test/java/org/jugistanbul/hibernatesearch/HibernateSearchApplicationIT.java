package org.jugistanbul.hibernatesearch;

import org.jugistanbul.hibernatesearch.resources.SearchResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HibernateSearchApplicationIT {

	@Autowired
	private SearchResource resource;

	@Test
	void allHostsTest() {
		assertEquals(false, resource.allHosts().isEmpty());
	}

	@Test
	void allEventsTest() {
		assertEquals(false, resource.allEvents().isEmpty());
	}

	@Test
	void searchEventsByNameTest(){
		assertEquals(false, resource.searchEventsByName("quarkus").isEmpty());
	}

	@Test
	void searchHostsByNameTest() {
		assertEquals(true, resource.searchHostsByName("huseyin").isEmpty());
		assertEquals(false, resource.searchHostsByName("Huseyin").isEmpty());
	}

	@Test
	void searchHostsByTitleTest() {
		assertEquals(false, resource.searchHostsByTitle("consultation").isEmpty());
	}
}
