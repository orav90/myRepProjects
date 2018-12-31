
public class Tenant extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String monthlyPayment; 
	
	
	public Tenant(String id,String firstName,String lastName,String password, String userName,String choice,String monthlyPayment)
	{
		super(id,firstName,lastName,password,userName,choice);
		this.monthlyPayment=monthlyPayment;
	}

	

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
}
