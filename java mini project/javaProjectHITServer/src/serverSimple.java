
import java.io.*; 
import java.net.*; 

class serverSimple { 
    
  @SuppressWarnings("resource")
public static void main(String argv[]) throws Exception 
    { 
	  Sql sql=new Sql(); //create class that consists queries and all the stuff from sql lesson
	  ServerSocket s = null;
	 
		try {
		    s = new ServerSocket(10000);
		
		} catch(IOException e) {
		    System.out.println(e);
		    System.exit(1);
		}

		while (true) {
		    Socket incoming = null;
		    
		    try {
			incoming = s.accept();
		    } catch(IOException e) {
			System.out.println(e);
			continue;
		    }
		    Sql.ConectingToSQL();
		    new socketHandler(incoming,sql).start();

		}
    } 
} 
