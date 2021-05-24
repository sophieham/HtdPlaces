package fr.univtours.projet.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column 
    private String name;
    @Column
    private String place;
    @Column
    private String type;
    @Column
    private String date;
    @Column
    private String time;
    @Column
    private double price;

    @OneToMany(mappedBy = "event",fetch=FetchType.LAZY)
    private Set<Ticket> tickets = new HashSet<>();

    public Event() {
    }

    public Event(String name, String place, String type, String date, String time, double price) {
    	this.name = name;
        this.place = place;
        this.type = type;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", place=" + place + ", type=" + type + ", date=" + date
				+ ", time=" + time + ", price=" + price + ", tickets=" + tickets + "]";
	}
}
