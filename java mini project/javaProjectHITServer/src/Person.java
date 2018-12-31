import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String userName;
	private String choice;

	
	public Person(String id,String firstName,String lastName,String password,String userName,String choice)
	{
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.password=password;
		this.userName=userName;
		this.choice=choice;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
}
