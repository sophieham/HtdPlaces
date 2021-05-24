package fr.univtours.projet.controller;

import fr.univtours.projet.dao.EventRepository;
import fr.univtours.projet.dao.TicketRepository;
import fr.univtours.projet.dao.UserRepository;
import fr.univtours.projet.entities.Event;
import fr.univtours.projet.entities.Ticket;
import fr.univtours.projet.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Controller
public class TicketController {
    @Autowired
    EventRepository eventDAO;
    
    @Autowired
    TicketRepository ticketDAO;
    
    @Autowired
    UserRepository userDAO;

    /*
     * Affiche la page de réservation
     */
    @RequestMapping(value = "/reserver/{id}", method = RequestMethod.GET)
    public String getBookingPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {
	  	Optional<Event> event = eventDAO.findById(id);
	  	if(event.isPresent()) {
	  		 model.addAttribute("event", event.get());
	  		Object session = request.getSession().getAttribute("userAttr");
	        if(session == null) {
	        	User blank = new User("", "", "", 0);
	        	model.addAttribute("user", blank);
	        	model.addAttribute("connecte", null);
				model.addAttribute("error_connect", true);
	        	return "redirect:/index";
	        }
	        else {
				User user = (User) request.getSession().getAttribute("userAttr");
				model.addAttribute("user", user);
				model.addAttribute("connecte", "oui");
	        }
	  		return "reserver";
	  	}
	  return "redirect:/index";
    }
    
    /*
     * Réservation d'un évenement. On récupère le nom de la personne qui utilisera le billet.
     */
    @Transactional
    @RequestMapping(value = "/reserver/{id}", method = RequestMethod.POST)
    public String bookTicket(@PathVariable("id") int id,  @RequestParam String name, Model model, HttpServletRequest request) {
    	Optional<Event> queryEvent = eventDAO.findById(id);
	  	if(queryEvent.isPresent()) {
	  		Event event = queryEvent.get();
	  		model.addAttribute("event", event);
	  		Object session = request.getSession().getAttribute("userAttr");
		        if(session == null) {
		        	User blank = new User("", "", "", 0);
		        	model.addAttribute("user", blank);
		        	model.addAttribute("connecte", null);
		        	return "redirect:/index";
		        }
		        else {
				User user = (User) request.getSession().getAttribute("userAttr");
				model.addAttribute("user", user);
				model.addAttribute("connecte", "oui");
				Set<Ticket> tickets = user.getTickets();
		            Ticket ticket = new Ticket(event, user);
		            ticket.setName(name);
		            tickets.add(ticket);
		            user.setTickets(tickets);
		            ticketDAO.save(ticket);
		            model.addAttribute("success_booking", "true");
		            return "redirect:/reservations";
		        }            
	  	}
        return "redirect:/index";
    }
    
    /*
     * Affichage la page de paiement
     */
    @RequestMapping(value = "/reservations/payer/{id}", method = RequestMethod.GET)
    public String getPaymentPage(@PathVariable("id") int id, Model model) {
	  	Optional<Ticket> bookings = ticketDAO.findById(id);
	  	if(bookings.isPresent()) {
	  		 model.addAttribute("booking", bookings.get());
	  		return "paiement";
	  	}
	  return "redirect:/index";
    }
    
    /*
     * Pour acheter un billet, il faut d'abord le reserver. 
     * On récupère donc la réservation et on lui rajoute la date du jour
     * au moment du paiement, pour noter que le billet à été payé.
     */
    @RequestMapping(value = "/reservations/payer/{id}", method = RequestMethod.POST)
    public String buyTicket(@PathVariable("id") int id, Model model, HttpServletRequest request) {
    	Optional<Ticket> queryReservation = ticketDAO.findById(id);
	  	if(queryReservation.isPresent()) {
	  		Ticket ticket = queryReservation.get();
	  		ticket.setBillingDate();
	  		ticketDAO.save(ticket);
	  		
            model.addAttribute("success_purchase", true);
	  	}
        return "redirect:/index";
    }
}


