package fr.univtours.projet.form;

public class UserForm {
	 	private String login;
	    private String password;
	    private String name;

	    public UserForm() {
	    	super();
	    }

	    public String getLogin() {
	        return login;
	    }

	    public void setLogin(String login) {
	        this.login = login;
	    }

	    public String getPassword() {
	    	return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}            
}
