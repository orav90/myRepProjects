
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;


public class socketHandler extends Thread {
	Socket incoming;
	Sql sql;
	Object o;

	
	socketHandler(Socket _in,Sql _sql)
	{
		incoming=_in;
		sql=_sql;
	}
	
	public void run()
	{
	    
		try
		{
	    
		ObjectInputStream inFromClient = new ObjectInputStream(incoming.getInputStream()); 
		          
		               
		DataOutputStream  outToClient = new DataOutputStream (incoming.getOutputStream() );
          
		
		while(true) {
			
			String res = "";
			o=inFromClient.readObject();
			
			if(o instanceof Tenant)//if user is tenant
			{
				//check if useName and password are correct
				res=checkUserValidity(((Tenant) o).getChoice());
				outToClient.writeBytes(res+ '\n');//write to client ok if correct
			
				//get the months that the user paid for them
				sql.tenantPaidMonthByUsername(((Tenant) o).getUserName());
				
			}
			if(o instanceof houseCommittee)//if user is house manager
			{
				//check if username and password are correct
				res=checkUserValidity(((houseCommittee) o).getChoice());
				outToClient.writeBytes(res+ '\n');//write to client ok if correct
				
				//while not done
				while(!((Person) o).getChoice().equals("6"))
				{
					o=inFromClient.readObject();
					switch(((Person)o).getChoice())
					{
					
						case "1":
						{
							//if id exists in database
							if(sql.checkIfApartmentExists(Integer.parseInt(((Tenant) o).getId())))
							{
								//get the monthly payments of this apartment id
								sql.tenantPaidMonth(Integer.parseInt(((Tenant) o).getId()));
							}
							break;
						}
						case "2":
						{
							//print the months that each apartment in the building paid for
							sql.showEntireApartmentsPayment();
							break;
						}
						case "3":
						{
							//if id exists in database
							if(sql.checkIfApartmentExists(Integer.parseInt(((Tenant) o).getId())))
							{
								//update its payment by calculating the monthly payment for the apartment and how much he paid
								sql.updateTenantPayment(Integer.parseInt(((Tenant) o).getId()),Integer.parseInt(((Tenant) o).getMonthlyPayment()));
							}
							else
							{
								System.out.println("id does not exist in database");
							}
							break;
						}
						case "4":
						{
							//get the profit for each month from the whole building
							sql.monthProfit();
							break;
						}
						case "5":
						{
							//check if that tenant username already exists in database
							if(sql.checkIfTenantInDB(((Tenant) o).getUserName()))
							{
								//insert new tenant to database of users and database of apartments
								sql.insertTenant(((Tenant)o).getId(), ((Tenant)o).getUserName(),((Tenant)o).getMonthlyPayment(),((Tenant)o).getPassword(),((Tenant) o).getFirstName(),((Tenant) o).getLastName());
							}
							else
							{
								System.out.println("Tenant already exists in database");
							}
							break;
						}
						case "6":
						{
							break;
						}
					}
				}
			}
		}
	            
       
	           
		}          
	        
		
		catch(IOException | ClassNotFoundException e)
		{
			
		}
		
	}

	public String checkUserValidity(String choice)
	{
		String res = "";
		switch(choice)
		{
			case "1":
			{
				//check if user name and password in data base
				res=Sql.select_query2(((Person) o).getUserName(),((Person) o).getPassword());
			}
			case "2":
			{
				//update password of this username
				res=Sql.select_query3(((Person) o).getUserName(),((Person) o).getPassword());
			}
		}
		return res;
		
	}
	
}
