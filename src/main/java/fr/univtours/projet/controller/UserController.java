package fr.univtours.projet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.univtours.projet.dao.TicketRepository;
import fr.univtours.projet.dao.UserRepository;
import fr.univtours.projet.entities.Ticket;
import fr.univtours.projet.entities.User;
import fr.univtours.projet.form.LoginForm;
import fr.univtours.projet.form.UserForm;

@Controller
public class UserController {
	@Autowired
    TicketRepository ticketDAO;

	@Autowired
    UserRepository userDAO;
	
	/*
	 * Affiche la page d'inscription.
	 */
	@RequestMapping(value = {"/inscription"}, method = RequestMethod.GET)
    public String getSignUpPage() {
        return "inscription";
    }
	
	/*
	 * Inscrit un utilisateur dans la base de données.
	 */
	 @RequestMapping(value = {"/inscription"}, method = RequestMethod.POST)
	    public String signUp(@ModelAttribute(name = "UserForm") UserForm userForm, Model model, HttpServletRequest request) {
	        List<User> users = userDAO.findAll();
	        String login = userForm.getLogin();
	        
	        int sameLogin = 0;

	        for (User u : users) {
	            if(u.getEmail().equals(login)) {
	            	sameLogin++;
	            }
	        }
	        if(sameLogin == 0) {
	        	User u = new User(userForm.getName(), login, userForm.getPassword(), 0);
	        	userDAO.save(u);
	        	return "redirect:/connexion";
	        }
	        
	        // TODO: ajouter un msg d'erreur dans la page d'inscription
	        model.addAttribute("invalidMail",true);
	        return "inscription";

	    }
	
	/*
	 * Affiche la page de connexion.
	 */
	@RequestMapping(value = {"/connexion"}, method = RequestMethod.GET)
    public String getLoginPage(Model model) {
		return "connexion";
    }

	/*
	 * Connecte l'utilisateur à l'application
	 */
    @RequestMapping(value = "/connexion", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "loginForm") LoginForm loginForm, Model model, HttpServletRequest request) {

        List<User> UtilisateurList = userDAO.findAll();

        String typedLogin = loginForm.getLogin();
        String typedPassword = loginForm.getPassword();

        for (User u : UtilisateurList) {
            if(u.getEmail().equals(typedLogin) && u.getPassword().equals(User.hash(typedPassword))) {
                request.getSession().setAttribute("userAttr", u);
                return "redirect:/index";
            }
        }
        model.addAttribute("invalidCredentials",true);
        return "connexion";
    }

    /*
     * Affiche la page de paramètres du compte
     */
    @RequestMapping(value = "/moncompte", method = RequestMethod.GET)
    public String getAccountPage(Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("userAttr");
    	model.addAttribute("user", user);
    	return "mon-compte";
    }
    
    /*
     * Modifie les informations du compte en base de données.
     */
    @RequestMapping(value ="/moncompte", method = RequestMethod.POST)
    public String changeAccountInfo(Model model, HttpServletRequest request, UserForm form) {
    	User u = (User) request.getSession().getAttribute("userAttr");
    	u.setEmail(form.getLogin());
    	u.setPassword(User.hash(form.getPassword()));
    	u.setName(form.getName());
    	userDAO.save(u);
    	return "redirect:/index";
    }
    

    /*
     * Affiche les évenements reservés par l'utilisateur.
     */
    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String getBookingsPage(Model model, HttpServletRequest request) {
    	
    	User user = (User) request.getSession().getAttribute("userAttr");
        List<Ticket> tickets  = ticketDAO.findByUser(user);
        ArrayList<Ticket> bookings = new ArrayList<Ticket>();
        for (Ticket ticket : tickets) {
        	if(ticket.getBillingDate() == null)
        		bookings.add(ticket);
		}
        model.addAttribute("bookings", bookings);

        return "mes-reservations";
    }
    
    /*
     * Affiche les tickets payés par l'utilisateur.
     */
    @RequestMapping(value = "/billets", method = RequestMethod.GET)
    public String getTicketsPage(Model model, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute("userAttr");
        List<Ticket> tickets  = ticketDAO.findByUser(user);
        ArrayList<Ticket> purchasedTickets = new ArrayList<Ticket>();
        for (Ticket ticket : tickets) {
        	if(ticket.getBillingDate() != null)
        		purchasedTickets.add(ticket);
		}
        model.addAttribute("tickets", purchasedTickets);

        return "mes-billets";
    }

    /*
     * Deconnexion de l'utilisateur.
     */
	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String deconnexion(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/index";
    }

}
