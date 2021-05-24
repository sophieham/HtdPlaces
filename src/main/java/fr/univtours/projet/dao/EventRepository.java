package fr.univtours.projet.dao;

import fr.univtours.projet.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Integer> {
	@Query("select e from Event e where e.name like %?1%")
	List<Event> findByName(String name);
}
