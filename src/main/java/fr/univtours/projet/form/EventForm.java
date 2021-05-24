package fr.univtours.projet.form;

public class EventForm {

    private int id;
	private String name;
    private String place;
    private String type;
    private String date;
    private String time;
    private Double price;

    public EventForm(String name, String place, String type, String date, String time, Double price) {
    	this.name = name;
        this.place = place;
        this.type = type;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public EventForm() {
    }
    
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
    
}
