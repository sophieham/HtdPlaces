package fr.univtours.projet.data;

import fr.univtours.projet.entities.Event;
import fr.univtours.projet.entities.User;

import java.util.ArrayList;

public class DataInitialization {
	ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<User> users = new ArrayList<User>();

    protected ArrayList<User> usersToList(){
        users.add(new User("Admin admin", "admin@admin.fr", "admin", 1));
        users.add(new User("Client client", "client@client.fr", "client", 0));
        users.add(new User("Julie Zatto", "julie.z@gmail.com", "123456", 0));
        return users;
    }
    
    protected ArrayList<Event> eventsToList(){
        events.add(new Event("La vida loca", "Paris", "Spectacle", "20/05/2021", "14:30", 30));
        events.add(new Event("Islands", "Lyon", "Concert", "31/07/2021", "21:30", 125));
        events.add(new Event("Nuit de folies", "Paris", "Festival", "28/05/2021", "23:30", 430));
        events.add(new Event("Belle", "Tours", "Théâtre", "20/05/2021", "16:30", 25));
        events.add(new Event("L'autre", "Marseille", "Comédie", "29/06/2021", "20:45", 125));
        events.add(new Event("Tomorrow", "Montpellier", "Concert", "24/05/2021", "22:30", 350));
        return events;
    }
}
