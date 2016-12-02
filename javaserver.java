import java.net.*; 
import java.io.*;

public class javaserver extends Thread
{
	protected Socket clientSocket;
	Turtle Bob = create_bob();

 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null;

    try { 
         serverSocket = new ServerSocket(23657); 
         System.out.println ("Connection Socket Created");
         try { 
              while (true)
                 {
                  System.out.println ("Waiting for Connection");
                  new javaserver (serverSocket.accept()); 
                 }
             } 
         catch (IOException e) 
             { 
              System.err.println("Accept failed."); 
              System.exit(1); 
             } 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10008."); 
         System.exit(1); 
        } 
    finally
        {
         try {
              serverSocket.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10008."); 
              System.exit(1); 
             } 
        }
   }
	private javaserver (Socket clientSoc)
	{
		clientSocket = clientSoc;
		System.out.println ("Socket accepted");
		start();	
	}

	public void run()
	{
		String inputLine;
		char direction = 'e';
		double size = 10;
		Turtle bob;
		
		System.out.println ("New Communication Thread Started");

		try 
		{ 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
			BufferedReader in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
			System.out.println("Getting Client input");
			bob = create_bob();

			while ((inputLine = in.readLine()) != null) 
			{ 
				System.out.println ("Server: " + inputLine); 

				out.println(inputLine);
			  
				if((inputLine.equals("North")) || (inputLine.equals("South")) || (inputLine.equals("East")) || (inputLine.equals("West")))
				{
					direction = check_direction(inputLine, size, direction, bob);
				}
				else if(inputLine.equals("Up"))
				{
					bob.up();
				}
				else if(inputLine.equals("Down"))
				{
					bob.down();
				}
				else if(inputLine.equals("Big"))
				{
					size += 1;
				}
				else if(inputLine.equals("Small"))
				{
					size -= 1;
				}
			} 
			out.close(); 
			in.close(); 
			clientSocket.close(); 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Problem with Communication Server");
			System.exit(1); 
		} 
	}
	
	public synchronized Turtle create_bob(){
		Turtle bob = new Turtle();
		return bob;
	}
		
	public char check_direction(String inputLine, double size, char direction, Turtle bob)
	{
		if(inputLine.equals("North"))
		{
			if(direction == 'e')
			{
				bob.left(90);
			}
			else if(direction == 's')
			{
				bob.left(180);
			}
			else if(direction == 'w')
			{
				bob.right(90);
			}
				
			bob.forward(size);
			direction = 'n';
		}
		else if(inputLine.equals("South"))
		{
			if(direction == 'n')
			{
				bob.left(180);
			}
			else if(direction == 'e')
			{
				bob.right(90);
			}
			else if(direction == 'w')
			{
				bob.left(90);
			}
			
			bob.forward(size);
			direction = 's';
		}
		else if(inputLine.equals("East"))
		{
			if(direction == 'n')
			{
				bob.right(90);
			}
			else if(direction == 's')
			{
				bob.left(90);
			}
			else if(direction == 'w')
			{
				bob.right(180);
			}
				
			bob.forward(size);
			direction = 'e';
		}
		else if(inputLine.equals("West"))
		{
			if(direction == 'n')
			{
				bob.left(90);
			}
			else if(direction == 's')
			{
				bob.right(90);
			}
			else if(direction == 'e')
			{
				bob.left(180);
			}
			
			bob.forward(size);
			direction = 'w';
		}
		return direction;
	}
}
