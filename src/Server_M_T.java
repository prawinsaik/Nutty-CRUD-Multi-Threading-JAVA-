import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server_M_T extends FilePath{

	
	// CONSTRUCTOR
	private Socket 			socket = null;
	private ServerSocket 	server = null;
	private Scanner in = null;
	public static String line = "";

	// CONSTRUCTOR WITH PORT
	public Server_M_T(int port){
		// SERVER STARTS AND WAITS FOR A CONNECTION
		try{
			server = new ServerSocket(port);
			System.out.println("\t\t******** SERVER STARTED ********");

			System.out.println("\n> WAITING FOR A CLIENT....!");

			socket = server.accept();
			System.out.println("> CLIENT ACCEPTED!");

			// TAKES INPUT FROM CLIENT SOCKET
			in = new Scanner(socket.getInputStream());

			this.line = in.nextLine();
//			System.out.println("RECEIVED DATA:" + line);
//			String SentenceCompletion = "Done";
			
			System.out.println("\n> CLOSING CONNECTION");
			// CLOSING CONNECTION
			socket.close();
			in.close();
			
		}
		catch(IOException IOE){
			System.out.println("IO Exception: " + IOE);
		}
	}	

	// MAIN METHOD
	public static void main(String args[]) throws IOException {
		Server_M_T server = new Server_M_T(49000);
//		System.out.println(line);
		String[] ResultDataArr = line.split(",");
//		System.out.println(line.split(",")[0]);
		
		switch(ResultDataArr[0]) {
			
			case "CREATE":
//				System.out.println("In switch create");
				String temp = null;
				for(int i = 1; i < ResultDataArr.length; i++)
				{
					if(i == 1) {
						temp = ResultDataArr[i];
					}
					else
					{
						temp =  temp + "," + ResultDataArr[i];							
					}
				}
				
				// CREATING A THREAD
				Runnable rCreate = new Create_M_T(temp);
				new Thread(rCreate).start();
				
//				server.CreateCSV(temp);

				System.out.println("> CREATED THE CUSTOMER DATA!");
				break;
				
			case "READ":
				
				Read_M_T rRead = new Read_M_T(ResultDataArr[1]);
				Thread thread = new Thread(rRead);
				thread.start();
				
				String data = rRead.getData();				

				System.out.print("\n> CUSTOMER DATA: " + data);
				break;
				
			case "UPDATE":
				String UpdateTemp = null;
				for(int i = 1; i < ResultDataArr.length - 2; i++)
				{
					if(i == 1) {
						UpdateTemp = ResultDataArr[i];
					}
					else {
						UpdateTemp = UpdateTemp + "," + ResultDataArr[i];							
					}
				}
				
				String UpdateID = ResultDataArr[ResultDataArr.length -1];
				Runnable rUpdate = new Update_M_T(UpdateTemp, UpdateID);
				new Thread(rUpdate).start();
				
//				server.UpdateCSV(UpdateTemp, UpdateID);
				System.out.println("> UPDATED THE CUSTOMER DETAILS!");
				break;
				
			case "DELETE":
				
				Runnable rDelete = new Delete_M_T(ResultDataArr[1]);
				new Thread(rDelete).start();
				System.out.println("> CUSTOMER DATA DELETED SUCCESSFULLY!");
				break;
		}			
	}
}