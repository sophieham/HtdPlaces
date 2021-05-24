package fr.univtours.projet.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue
    private int id;
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "eventID")
    private Event event;
    @ManyToOne (cascade=CascadeType.MERGE) 
    @JoinColumn(name = "userID")
    private User user;
    @Column
    private String name;
    @Column
    private String bookingDate;
    @Column
    private String billingDate;
    
    public Ticket() {
    }

    public Ticket(Event event, User user) {
        this.event = event;
        this.user = user;
        this.bookingDate = LocalDate.now().toString();  
        }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUtilisateur() {
        return user;
    }

    public void setUtilisateur(User user) {
        this.user = user;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate() {
		this.bookingDate = LocalDate.now().toString(); 
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate() {
		this.billingDate = LocalDate.now().toString(); 
	}
}
