package fr.univtours.projet.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue
    private int id;
    private String name;
    private String email;
    private String password;
    private int rank;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    private Set<Ticket> tickets = new HashSet<>();

    public User() {

    }

    public User(String name, String email, String password, int rank) {
        this.name = name;
        this.email = email;
        this.password = hash(password);
        this.rank = rank;
    }
    
    public static String hash(String password) {
        MessageDigest msg;
		try {
			msg = MessageDigest.getInstance("SHA-256");
	        byte[] hash = msg.digest(password.getBytes(StandardCharsets.UTF_8));
	        StringBuilder hashedPassword = new StringBuilder();
	        for (byte b : hash) {
	            hashedPassword.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	        }
	        return hashedPassword.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
       return "";
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        if(this.rank==0) {
        	return false;
        }
        else return true;
    }
    
    public int getRank() {
    	return this.rank;
    }

    public void setRank(int rank) {
    	if(rank<=1) // ranks définis sur 0 et 1 uniquement
    		this.rank = rank;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rank='" + rank + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
