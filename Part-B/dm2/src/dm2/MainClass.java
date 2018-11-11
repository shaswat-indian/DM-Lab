package dm2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
	
	Scanner in=new Scanner(System.in);
	ArrayList<String[]> data;
	
	public MainClass() {
		// TODO Auto-generated constructor stub
		
		System.out.print("Enter Input File Name: ");
		String fileName=in.nextLine();
		//System.out.println(fileName);
		data=new ArrayList<String[]>();
	
		loadFile(fileName);
	}

	private void loadFile(String fileName) {
		// TODO Auto-generated method stub
		try {
			String ipLine;
			BufferedReader br=new BufferedReader(new FileReader(new File(fileName)));
			ipLine=br.readLine();
			while(ipLine!=null){
				
				String[] rowData=ipLine.split(",");
				ipLine=br.readLine();
				data.add(rowData);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("No such file found!");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void showData() {
		for(String rowData[]:data)
			System.out.println(Arrays.toString(rowData));
		
	}
	
	public void missingString(int col, String defValue) {
		HashMap<String, Integer> hm=new HashMap<String,Integer>();
		for(String[] row: data) {
			if(!row[col].equalsIgnoreCase(defValue)) {
				hm.put(row[col], hm.getOrDefault(row[col],0)+1);
			}
		}
		
		String maxValName = null;
		int maxVal=0;
		for(Map.Entry<String, Integer> me:hm.entrySet()) {
			if(me.getValue()>maxVal)
				maxValName=me.getKey();
		}
		
		for(String[] row: data) {
			if(row[col].equalsIgnoreCase(defValue)) {
				row[col]=maxValName;
			}
		}
		
		
	}
	
	public void missingInteger(int col, String defValue) {
		float avg=0;
		int count=0;
		for(String[] row: data) {
			if(!row[col].equalsIgnoreCase(defValue)) {
				avg+=Float.parseFloat(row[col]);
			}
			count++;
		}
		avg=avg/count;
		for(String[] row: data) {
			if(row[col].equalsIgnoreCase(defValue)) {
				row[col]=Integer.toString((int)avg);
			}
		}
		
	}
	
	public void missingDecimal(int col, String defValue) {
		float avg=0;
		int count=0;
		for(String[] row: data) {
			if(!row[col].equalsIgnoreCase(defValue)) {
				avg+=Float.parseFloat(row[col]);
			}
			count++;
		}
		avg=avg/count;
		for(String[] row: data) {
			if(row[col].equalsIgnoreCase(defValue)) {
				row[col]=Float.toString(avg);
			}
		}
	
	}
	
	public void generateFile() {
		System.out.print("\nEnter Output File Name: ");
		String fileName=in.nextLine();
		try {
			FileWriter fw=new FileWriter(new File(fileName));
			for(String[] rowData:data) {
				String newData=Arrays.toString(rowData);
				newData=newData.substring(1,newData.length()-1);
				//System.out.println(newData);
				fw.write(newData);
				fw.write('\n');
			}
			
			fw.close();
			System.out.println("\nFile generated successfully!");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		mc.missingString(5, "NA");
		mc.missingInteger(1, "NA");
		mc.generateFile();
		
	}

}
