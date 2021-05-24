package fr.univtours.projet.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.univtours.projet.dao.EventRepository;
import fr.univtours.projet.entities.Event;
import fr.univtours.projet.entities.User;
import fr.univtours.projet.form.EventForm;

@Controller
public class EventController {
	  @Autowired
	    EventRepository eventDAO;
	  
	  /*
	   * Affiche la page d'accueil
	   */
	  @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	    public String getHomePage(Model model, HttpServletRequest request) {

	        List<Event> events = eventDAO.findAll();
	        model.addAttribute("events", events);
	        Object session = request.getSession().getAttribute("userAttr");
	        if(session == null) {
	        	User vide = new User("", "", "", 0);
	        	model.addAttribute("user", vide);
	        	model.addAttribute("connecte", null);
	        }
	        else {
			User user = (User) request.getSession().getAttribute("userAttr");
			model.addAttribute("user", user);
			model.addAttribute("connecte", "oui");
			model.addAttribute("admin", user.getRank());
	        }
	        return "index";
	    }
	  
	  /*
	   * Affiche les détails (lieu, date, heure, prix) d'un évenement
	   */
	  @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
	    public String detailsEvent(@PathVariable("id") int id, Model model, HttpServletRequest request) {
			 Object session = request.getSession().getAttribute("userAttr");
		        if(session == null) {
		        	User vide = new User("", "", "", 0);
		        	model.addAttribute("user", vide);
		        	model.addAttribute("connecte", null);
					model.addAttribute("error_connect", true);
		        }
		        else {
				User user = (User) request.getSession().getAttribute("userAttr");
				model.addAttribute("user", user);
				model.addAttribute("connecte", "oui");
				model.addAttribute("admin", user.getRank());
		        }
		  	Optional<Event> event = eventDAO.findById(id);
		  	if(event.isPresent()) {
		  		 model.addAttribute("event", event.get());
		  		 
		  		return "details";
		  	}
		  return "redirect:/index";
	        
	    }

	  /*
	   * Affiche la page d'ajout d'evenement.
	   */
	  @RequestMapping(value = "/events/ajout", method = RequestMethod.GET)
	    public String getAddPage(Model model) {
	        return "ajout-event";
	    }

	  /*
	   * Ajoute un nouvel evenement à la base de données. 
	   */
	  @RequestMapping(value = "/events/ajout", method = RequestMethod.POST)
	    public String newEvent(@ModelAttribute(name = "evenementForm") EventForm eventForm, Model model) {
	        Event event = new Event(eventForm.getName(), eventForm.getPlace(), eventForm.getType(), eventForm.getDate(), eventForm.getTime(), eventForm.getPrice());
	        eventDAO.save(event);
	        model.addAttribute("success_add", true);
	        return "redirect:/index";
	    }

	  /*
	   * Affichage la page de modification d'un évenement.
	   */
	  @RequestMapping(value = "/events/modifier/{id}", method = RequestMethod.GET)
	    public String getPageModify(@PathVariable("id") int id, Model model) {
		  Optional<Event> event = eventDAO.findById(id);
		  	if(event.isPresent()) {
		  		model.addAttribute("event", event.get());
		  		return "modifier-event";
		  	}
		  	return "redirect:/index";
	    }

	  /*
	   * Modifie un évenement dans la base de données.
	   */
	    @RequestMapping("/events/modifier/{id}")
	    public String changeEvent(@PathVariable("id") int id, Model model, EventForm form) {
	    	Optional<Event> event = eventDAO.findById(id);
		  	if(event.isPresent()) {
		  		Event e = event.get();
		  		e.setName(form.getName());
		  		e.setType(form.getType());
		  		e.setDate(form.getDate());
		  		e.setTime(form.getTime());
		  		e.setPlace(form.getPlace());
		  		e.setPrice(form.getPrice());
		  		eventDAO.save(e);
		  		model.addAttribute("success_change", true);
		  		return "redirect:/events/"+id;
		  	}
	        return "redirect:/index";
	    }
	    
	    /*
	     * Supprime un évenement de la base de données.
	     */
	    @RequestMapping("/events/supprimer/{id}")
	    public String deleteEvent(@PathVariable("id") int id, Model model) {
	    	Optional<Event> event = eventDAO.findById(id);
		  	if(event.isPresent()) {
	        eventDAO.delete(event.get());
	        model.addAttribute("success_delete", true);
		  	}
		  	return "redirect:/index";
	    }
	    
	    /*
	     * Affiche les évenements correspondant à la recherche.
	     */
	    @RequestMapping(value = "/recherche", method = RequestMethod.POST)
	    public String search(@RequestParam String search, Model model, HttpServletRequest request) {
	    	 List<Event> events;
	    	 events = eventDAO.findByName(search);
	    	 
	    	 Object session = request.getSession().getAttribute("userAttr");
	    	 if(session == null) {
		        	User vide = new User("", "", "", 0);
		        	model.addAttribute("user", vide);
		        	model.addAttribute("connecte", null);
		        }
		        else {
				User user = (User) request.getSession().getAttribute("userAttr");
				model.addAttribute("user", user);
				model.addAttribute("connecte", "oui");
		        }
	    	 
	    	 model.addAttribute("events", events);
	    	 return "index";
	    }
	    
	    /*
	     * Tri les évenements en fonction du lieu, de la date et du type d'évenement.
	     */
	    @RequestMapping(value = "/rechercheTri", method = RequestMethod.POST)
	    public String sort(@RequestParam String id, Model model, HttpServletRequest request) {
	    	 List<Event> events;
	    	 if (id.equals("Localisation")) {
	    		 events = eventDAO.findAll(Sort.by(Sort.Direction.ASC, "place"));
	    		 model.addAttribute("events", events);
	    	 } else if (id.equals("Date")) {
	    		 events = eventDAO.findAll(Sort.by(Sort.Direction.ASC,"date"));
	    		 model.addAttribute("events", events);
	    	 } else if (id.equals("Type d'évènement")) {
	    		 events = eventDAO.findAll(Sort.by(Sort.Direction.ASC,"type"));
	    		 model.addAttribute("events", events);
	    	 }
	    	 
	    	 Object session = request.getSession().getAttribute("userAttr");
	    	 if(session == null) {
		        	User vide = new User("", "", "", 0);
		        	model.addAttribute("user", vide);
		        	model.addAttribute("connecte", null);
		        }
		        else {
				User user = (User) request.getSession().getAttribute("userAttr");
				model.addAttribute("user", user);
				model.addAttribute("connecte", "oui");
		        }
	    	 return "index";
	    }
	 
}
