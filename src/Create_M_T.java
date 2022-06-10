import java.io.*;

public class Create_M_T implements Runnable{

	// ATTRIBUTES
	public static String filepath = "customer.csv";
	File customer = new File(filepath);
	
	public String CustomerData = null;
	
	// CONSTRUCTOR
	public Create_M_T(String customerData) {
		this.CustomerData = customerData;
	}
	
	// RUN METHOD
//	@Override
	public void run() {
		try {			
			//Instantiating the CSVWriter class
		    FileWriter writer = new FileWriter(filepath, true);
		    
		    //Writing data to file
//		    System.out.println(> CustomerData);
		    writer.append(CustomerData + "\n");
		    
		    //Flushing data from writer to file
		    writer.flush();
		    writer.close();
		    System.out.println("> DATA ENTERED!");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
