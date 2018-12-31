import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

import com.mysql.jdbc.Connection;


public class Sql {

	private static Connection connect; 
	
	//print the months that each apartment in the building paid for 
	public void showEntireApartmentsPayment()
	{
		try {
			PreparedStatement state=connect.prepareStatement("select houseid,month from lab3.house2 where month>0");
			
			System.out.println("Apartments payments:");
			ResultSet rs=state.executeQuery();
			while(rs.next())
			{
				 //get value of month column
				 int month=Integer.parseInt(rs.getString("month"));
				 //get value of houseid column
				 int id=rs.getInt("houseid");
				 System.out.println("Apartment: "+id+" paid for months:");
				 while(month>0)
				 {
					 System.out.print(Month.of(month)+" ");
					 month--;
				 }
				 System.out.println();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//print the monthly profit for entire building
	public void monthProfit()
	{
		try {
			PreparedStatement state=connect.prepareStatement("select MonthlyPayment,month from lab3.house2 where month>0");
			int arr[]=new int[13];//months array
			System.out.println("Monthly profit:");
			ResultSet rs=state.executeQuery();
			while(rs.next())
			{
				 //get value of month column
				 int month=Integer.parseInt(rs.getString("month"));
				 //get value of monthlyPayment column
				 int monthlyPayment=Integer.parseInt(rs.getString("MonthlyPayment"));
				 
				 for(int i=1;i<=month;i++)
				 {
					 //calculate each month payment for every tenant
					 arr[i]+=monthlyPayment;
				 }
				 
			}
			 //print total payment for each month
			 for(int i=1;i<arr.length;i++)
			 {
				 System.out.println(Month.of(i)+" total payments: "+arr[i]);
			 }
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//update the last month that the tenant paid for
	public void updateTenantPayment(int id, int payment)
	{
		try {
			PreparedStatement statement = connect.prepareStatement("select MonthlyPayment,month from lab3.house2 where houseid =?");
			PreparedStatement statement2 = connect.prepareStatement("UPDATE lab3.house2 set month =?  where houseid =?");
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			int currMonth=0,monthlyPay=0;
			while(rs.next())
			{
			 currMonth=rs.getInt("month");
			 monthlyPay=rs.getInt("MonthlyPayment");
			}
			
			//calculate month from the tenant payment and the monthly payment of his apartment
			while(currMonth<12 && payment>monthlyPay)			
			{
				payment-=monthlyPay;
				currMonth++;
			}
			
			statement2.setString(1,Integer.toString(currMonth));
			statement2.setInt(2,id);
			statement2.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	//print the months that apartment id paid for
	public void tenantPaidMonth(int id)
	{
		try {
			PreparedStatement statement = connect.prepareStatement("select month,firstName,lastName from lab3.house2 where houseid = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
					
			if(rs.next())
			{
				//get name of the apartment owner
				String first=rs.getString("firstName");
				String last=rs.getString("lastName");
				
				System.out.println(first+" "+last+" from partment "+id+" paid for months:");
				
				int month=Integer.parseInt(rs.getString("month"));
				while(month>0)
				{
					System.out.print(Month.of(month)+" ");
					month--;
				}
			}
			System.out.println();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	//if user is tenant, print the months that he paid for
	public void tenantPaidMonthByUsername(String userName)
	{
		try {
			PreparedStatement statement = connect.prepareStatement("select month,firstName from lab3.house2 where UserName = ?");
			statement.setString(1, userName);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				int month=Integer.parseInt(rs.getString("month"));
				String first=rs.getString("firstName");
				System.out.println("Hello, "+first+" the months you have paid for are:");
				while(month>0)
				{
					System.out.print(Month.of(month)+" ");
					month--;
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkIfTenantInDB(String user)
	{

		try {
			PreparedStatement statement = connect.prepareStatement("select * from lab3.users where userName = ?");
			statement.setString(1, user);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return false;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return true;
	}
	
	public boolean checkIfApartmentExists(int id)
	{

		try {
			PreparedStatement statement = connect.prepareStatement("select * from lab3.house2 where houseid = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}

	//check if userName and password in database
	public static String select_query2(String username,String password)
	{

		try {
			PreparedStatement statement = connect.prepareStatement("select * from lab3.users where UserName = ? AND Password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next())
			{
				return "ok";
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "not ok";
	}
	
	//insert new tenant to 2 databases
	public void insertTenant(String houseId, String userName,String MonthlyPayment,String password,String firstName,String lastName ){
	
		String sqlInsert = "insert into lab3.house2 VALUES (?,?,?,?,?,?)";
		String sqlInsertUser="insert into lab3.users VALUES (?,?)";
		try {
				PreparedStatement pst = connect.prepareStatement(sqlInsert);
				PreparedStatement statement=connect.prepareStatement(sqlInsertUser);
			
				pst.setString(1, houseId);
				pst.setString(2, "0");
				pst.setString(3, userName);
				pst.setString(4,MonthlyPayment);
				pst.setString(5,firstName);
				pst.setString(6,lastName);
				statement.setString(1, userName);
				statement.setString(2,password);
				
				pst.execute();
				statement.execute();
					
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//update the username's password
	public static String select_query3(String username,String password)
	{
		
		try {
			PreparedStatement statement = connect.prepareStatement("UPDATE lab3.users set Password =?  where UserName =?");
			statement.setString(2, username);
			statement.setString(1, password);
			
			int count=statement.executeUpdate();
			
			if(count>0)
			{
				return "ok";
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "not ok";
	}
	
	public static void connection()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ConectingToSQL ()
	{
		
		connection();
		String host = "jdbc:mysql://localhost:3306/lab3";
		String username = "root";
		String password = "1234";
		
		
		try {
			 connect = (Connection) DriverManager.getConnection(host, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
