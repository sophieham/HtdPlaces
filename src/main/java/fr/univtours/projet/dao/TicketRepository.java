package fr.univtours.projet.dao;

import fr.univtours.projet.entities.Ticket;
import fr.univtours.projet.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	public List<Ticket> findByUser(User u);
}
