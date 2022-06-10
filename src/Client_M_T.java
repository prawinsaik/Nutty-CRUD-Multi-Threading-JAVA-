import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client_M_T {
	
	// ATTRIBUTES
	private Socket socket = null;
	private static PrintStream output = null;
	
    public String CRUDType;
    public String Result;
    
	public static String CustomerID;
	public static String CustomerName;
	public static String CustomerEmail;
	public static int CreditPeriod;
	public static String Status;
	public static Double PaymentBalance;
	
	public String filepath = "customer.csv";
	File customer = new File(filepath);
	
	// CONSTRUCTOR
	public Client_M_T(Socket socket) {
        try {
            this.socket = socket;
//            System.out.println("NETWORK CONNECTED...!");
            this.output = new PrintStream(this.socket.getOutputStream());
            this.CustomerID = "";
            this.CustomerName = "";
            this.CustomerEmail = "";
            this.CreditPeriod = -1;
            this.Status = "ACTIVE";
            this.PaymentBalance = -1.0d;  
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, output);
        }
    }

	// CLOSE ALL
	public void closeEverything(Socket socket, PrintStream output) {
        try {
            if (output != null) {
                output.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// FILE EXISTANCE FOR CREATE
	public boolean createFileExistance() {
		boolean FileExists = false;
		try {
			if(customer.createNewFile()) {		
				System.out.println("File Created...");
				FileExists = false;
			}
			else {
				System.out.println("File Already Exists");
				FileExists = true;
			}
		}
		catch(IOException e) {
//			System.out.println("IOException: "+ e);
			e.printStackTrace();			
		}		
		return FileExists;
	}
	
	// FILE EXISTANCE FOR UPDATE
	public boolean updateFileExistance() {
		boolean FileExists = false;
		try {
			if(customer.exists()) {		
				System.out.println("File Created...");
				FileExists = false;
			}
			else {
				System.out.println("File Already Exists");
				FileExists = true;
			}
		}
		catch(Exception e) {
//			System.out.println("IOException: "+ e);
			e.printStackTrace();			
		}
		return FileExists;
	}
	
	
	// CREATE
	public String Create() {
//		System.out.println("CREATE");
		
		CRUDType = "CREATE";
		
		Scanner ss = new Scanner(System.in);		
		boolean FileExists = createFileExistance();
									
		System.out.println("Enter the Customer Details: ");
		System.out.println("Customer ID: ");
		CustomerID = ss.nextLine();
		System.out.println("Customer Name: ");
		CustomerName = ss.nextLine();
		System.out.println("Customer Email: ");
		CustomerEmail = ss.nextLine();
		
		String Data = CustomerID + "," + CustomerName + "," + CustomerEmail + "," + CreditPeriod + "," + Status + "," + PaymentBalance;
		Result = CRUDType + "," + Data;
		
		return Result;
//		System.out.println("CREATED CUSTOMER DETAILS...!");
	}
	
	
	// READ
	public String Read() {
//		System.out.println("READ");
		
		CRUDType = "READ";
		
		Scanner sr = new Scanner(System.in);
		
		System.out.print("Enter the Customer ID to read: ");
		String ID = sr.nextLine();
		
		Result = CRUDType + "," + ID;
		
		return Result;
		
//		System.out.println("READING CUSTOMER DETAILS...!");
	}
	

	// UPDATE
	public String Update() {
//		System.out.println("UPDATE");
		CRUDType = "UPDATE";
		Scanner su = new Scanner(System.in);
		
		System.out.println("Enter the Customer ID to update: ");
		String UpdateID = su.nextLine();
		
		boolean UpdateFileExists = updateFileExistance();
								
		if(UpdateFileExists) {
			System.out.println("Enter the Customer Details: ");
			
//			System.out.println("Customer ID: ");
			CustomerID = UpdateID;
			
			System.out.println("Customer Name: ");
			CustomerName = su.nextLine();
			
			System.out.println("Customer Email: ");
			CustomerEmail = su.nextLine();

			PaymentBalance = 0.0d;
			
			String UpdateData = CustomerID + "," + CustomerName + "," + CustomerEmail + "," + CreditPeriod + "," + Status + "," + PaymentBalance;
			Result = CRUDType + "," + UpdateData;
			// Make a way for update ID...................
			return Result;
		}
		else {
//			System.out.println("Closing...!");
			System.exit(0);
		}
		return Result;		
//		System.out.println("UPDATED CUSTOMER DETAILS...!");
	}
	
	
	// DELETE
	public String Delete() {
		
		CRUDType = "DELETE";
		
		// System.out.println("DELETE");
		Scanner sd = new Scanner(System.in);					
		System.out.println("Enter the Customer ID to delete: ");
		String DeleteID = sd.nextLine();
		
		Result = CRUDType + "," + DeleteID;
		
		return Result;
//		System.out.println("DELETED CUSTOMER DETAILS...!");
	}
	
	
	// MAIN METHOD
	public static void main(String[] args) throws IOException{
		
		String serverIP = "127.0.0.1";
		int serverPort = 49000;
		
		String result = "";
		Socket socket = new Socket(serverIP, serverPort);
		Client_M_T client = new Client_M_T(socket);
		
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		
		try 
		{		
			Scanner s = new Scanner(System.in);
			int Choice;
					
			while(true) {			
				System.out.println("1. Create,");
				System.out.println("2. Read,");		
				System.out.println("3. Update,");			
				System.out.println("4. Delete,");		
				System.out.println("0. Exit.");
				System.out.print("Enter your choice: ");
				Choice = s.nextInt();
				if(Choice >= 0 || Choice <= 4) {
					break;
				}
				else {
					System.out.println("Invalid Choice...!");
					continue;
				}
			}
			
			switch(Choice) {
			
				// CREATE
				case 1:
					result = client.Create();
					break;
				
				// READ
				case 2:
					result = client.Read();
//					if(ReadData == null) {
//						System.out.print("ID doesn't Exist...!");
//					}
//					else {
//						System.out.println("Customer Data: " + ReadData);						
//					}
					break;
				
				// UPDATE
				case 3:
					result = client.Update();
					break;
			
				// DELETE
				case 4:
					result = client.Delete();
//					if(ReadData == null) {
//						System.out.print("ID doesn't Exist...!");
//					}
//					else {
//						System.out.println("Customer Data: " + ReadData);						
//					}
					break;
			
				case 0:
					System.exit(0);
					System.out.println("EXITING...!");
					break;
			}		
						
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
		
		output.println(result);
		socket.close();
	}
}
