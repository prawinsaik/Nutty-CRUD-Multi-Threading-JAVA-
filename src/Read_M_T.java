import java.io.*;
import java.util.*;

public class Read_M_T implements Runnable{

	// ATTRIBUTES
	public static String filepath = "customer.csv";
	File customer = new File(filepath);
	
	private volatile String Data ="";
	public String CustomerID;
	
	// CONSTRUCTOR
	public Read_M_T(String customerID){
		this.CustomerID = customerID;
	}
	
	// GETTER 
	public String getData() {
		return Data;
	}
	
//	@Override
	public void run() {
		try {
			Scanner sr = new Scanner(System.in);
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			
			String line = "";
			while((line = br.readLine()) != null ) {
				if(line.split(",")[0].equals(CustomerID)) {
					Data = line;
				}
			}
			System.out.println(Data);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
