

import java.io.*; 
import java.net.*;
import java.util.Scanner;



class clientObject { 

    @SuppressWarnings("deprecation")
	public static void main(String argv[]) throws Exception 
    { 
    	Integer j=0;
    	Scanner input =  new Scanner (System.in);
    	String in ="";
        Object fromServer = null;
        
        BufferedReader r=new BufferedReader(new FileReader("res/id.txt"));
        j=Integer.parseInt(r.readLine());
        r.close();
        Socket clientSocket = new Socket("localhost", 10000); 
        
        try {
        ObjectOutputStream outToServer = 
          new ObjectOutputStream(clientSocket.getOutputStream()); 

        DataInputStream  inFromServer = 
          new DataInputStream (clientSocket.getInputStream());
        
        
        		      
        System.out.println("Hello please choose one of the options:\n"
        		+ "1: Tenant\n"
        		+ "2: House manager");
        String user=input.nextLine();
        System.out.println("Please choose one of the options: \n"
        		+ "1: Login\n"
        		+ "2: Change password");
        String login=input.nextLine();
       
        Person p = null;
        switch(user)
        {
        	case "1":
        	{
        		p=new Tenant("", "", "", "", "","","");
        		break;
        	}
        	case "2":
        	{
        		p=new houseCommittee("", "", "", "", "","");
        		break;
        	}
        }
        
        p.setChoice(login);
        
        String username = "",password="";
        System.out.println("Please enter your username");
        username=input.nextLine();
        switch(login)
        {
        	case "1":
        	{
        		System.out.println("Please enter your password");
        		break;
        	}
        	case "2":
        	{
        		System.out.println("Please enter your new password");
        		break;
        	}       		     		
        }
        password=input.nextLine();
		p.setUserName(username);
		p.setPassword(password);
        outToServer.writeObject(p);
        fromServer = inFromServer.readLine();
      
        if(fromServer.equals("ok"))
        {
        	switch(user)
        	{
        		case "1":
        		{
        			//outToServer.writeObject(p);
        			break;
        		}
        		case "2":
        			while(!in.equals("6"))
        			{
		        		{
		        			System.out.println("Please choose one of the options:\n"
		        					+ "1: Display tenant's payment details\n"
		        					+ "2: Total tenants payments\n"
		        					+ "3: Update tenant's payments\n"
		        					+ "4: Total monthly payments\n"
		        					+ "5: Add a tenant\n"
		        					+ "6: Exit");
		        			
		        			in=input.nextLine();
        					Person newTenant=null;

		        			switch(in)
		        			{
		        				case "1":
		        				{
		        					System.out.println("Please enter apartment's id");
		        					String id=input.nextLine();
		        					newTenant=new Tenant(id,"","","","",in,"");
		        					outToServer.writeObject(newTenant);
		        					break;
		        				}
		        				case "2":
		        				{
		        					newTenant=new Tenant("","","","","",in,"");
		        					outToServer.writeObject(newTenant);
		        					break;
		        				}
		        				case "3":
		        				{
		        					
		        					System.out.println("Please enter apartment's id and payment");
		        					String[] update=input.nextLine().split(" ");
		        					newTenant=new Tenant(update[0],"","","","","3",update[1]);
		        					outToServer.writeObject(newTenant);
		        					break;
		        				}
		        				case "4":
		        				{
		        					newTenant=new Tenant("","","","","",in,"");
		        					outToServer.writeObject(newTenant);
		        					break;   					
		        				}
		        				case "5":
		        				{
		        					p.setChoice(in);
		        					System.out.println("Please enter tenant's full name");
		        					String[] fullName=input.nextLine().split(" ");
		        					System.out.println("Please enter username");
		        					String userName=input.nextLine();
		        					System.out.println("Please enter password");
		        					String pass=input.nextLine();
		        					System.out.println("Please enter monthly payment");
		        					String monthPayment=input.next();
		        					newTenant=new Tenant((j++).toString(),fullName[0],fullName[1],pass,userName,in,monthPayment);
		        					outToServer.writeObject(newTenant);
		        					break;                                                                                      
		        				}
		        				case "6":
		        				{
		        					newTenant=new Tenant("", "", "", "", "","6","");
		        					outToServer.writeObject(newTenant);
		        					break;
		        				}
		        			}
		        		}
        			}
        	}
        	
        }
        else
        {
        	System.out.println("wrong login");
        }
        
                   
    }catch(IOException e)
        {
        	e.printStackTrace();
        }
        finally {
            Writer wr=new FileWriter("res/id.txt");
            wr.write(j.toString());
            wr.close();
        	clientSocket.close(); 
        	input.close();
        }
    }
    
} 

