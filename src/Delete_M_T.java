import java.io.*;
import java.util.*;

public class Delete_M_T implements Runnable{
	
	// ATTRIBUTES
	public static String filepath = "customer.csv";
	File customer = new File(filepath);
	
	private String tempFilePath = "temp.csv";
	File temp = new File(tempFilePath);
	
	private volatile String CustomerID;
	
	// CONSTRUCTOR
	public Delete_M_T(String customerID) {
		this.CustomerID = customerID;
	}
	
//	@Override
	public void run() {
		try 
		{
			String line = "";
			String tempLine = "";

			Scanner sr = new Scanner(System.in);
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			FileWriter writer = new FileWriter(tempFilePath, true);
					
			while((line = br.readLine()) != null ) {
				if(line.split(",")[0].equals(CustomerID)) {
					continue;
				}
					
				//Writing data to file
				writer.append(line + "\n");
				writer.flush();
				writer.close();
				
				FileReader fin = new FileReader(tempFilePath);  
				BufferedReader br1 = new BufferedReader(fin);
				FileWriter fout = new FileWriter(filepath, false);  
				
				String Line1;
				
				while ((Line1 = br1.readLine()) != null) {  
					fout.append(Line1 + "\n"); 
					fout.flush();
				}  

				fin.close();  
				fout.close();  
				
				// DELETING TEMP FILE
				temp.delete();
//					System.out.println("At the file end");
			}
		  	
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		catch(IOException e) {
			e.printStackTrace();
		}		
	}
}
