package fr.univtours.projet.data;

import fr.univtours.projet.dao.EventRepository;
import fr.univtours.projet.dao.TicketRepository;
import fr.univtours.projet.dao.UserRepository;
import fr.univtours.projet.entities.Event;
import fr.univtours.projet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class InitializationImpl implements InitializationInterface{

	@Autowired
	TicketRepository ticketDAO;
	@Autowired
	UserRepository userDAO;
	@Autowired
	EventRepository eventDAO;

	DataInitialization data = new DataInitialization();

	public void initUsers() {
		ArrayList<User> listUsers = data.usersToList();
		for (User u : listUsers) {
			userDAO.save(u);
		}
	}
	
	public void initEvents() {
		ArrayList<Event> listEvents = data.eventsToList();
		for (Event e : listEvents) {
			eventDAO.save(e);
		}
	}
}
