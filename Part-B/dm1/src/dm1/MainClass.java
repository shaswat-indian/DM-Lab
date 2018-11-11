package dm1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	
	public void aggregation() {
		System.out.println("\n\nAggregation:-\n---------------------\n");
		System.out.print("Enter Column No. to be grouped by: ");
		int groupCol=Integer.parseInt((in.nextLine()))-1;
		System.out.print("Enter Column No. of Value to be Aggregated: ");
		int valCol=Integer.parseInt((in.nextLine()))-1;
		HashMap<String,Float> maxMap=new HashMap<String,Float>();
		HashMap<String,Float> minMap=new HashMap<String,Float>();
		
		for(String[] rowData:data) {
			
			float newValue=Float.parseFloat(rowData[valCol]);
			
			if(maxMap.containsKey(rowData[groupCol])) {
				float maxValue=maxMap.get(rowData[groupCol]);
				if(newValue>maxValue) 
					maxMap.put(rowData[groupCol], newValue);
			}
			else
				maxMap.put(rowData[groupCol], Float.parseFloat(rowData[valCol]));
			
			
			if(minMap.containsKey(rowData[groupCol])) {
				float minValue=minMap.get(rowData[groupCol]);
				if(newValue<minValue)
					minMap.put(rowData[groupCol], newValue);
			}
			else
				minMap.put(rowData[groupCol], Float.parseFloat(rowData[valCol]));
		}
		
		
		for(Map.Entry<String, Float> me: maxMap.entrySet()) {
			Float maxValue=me.getValue();
			Float minValue=minMap.get(me.getKey());
			System.out.println("\n"+me.getKey()+": Min="+minValue+" Max="+maxValue);
		}
		
		
	}

	
	public void discretization() {
		System.out.println("\n\nDiscretization:-\n---------------------\n");
		System.out.print("Enter Column No. of Value to be considered for Discretization: ");
		int valCol=Integer.parseInt((in.nextLine()))-1;
		
		int newColNo=data.get(0).length;
		
		HashMap<String[],String> classes=new HashMap<String[],String>();
		
		for(String[] rowData: data) {
			float value=Float.parseFloat(rowData[valCol]);
			String classValue;
			if(value < 200)
               classValue = "A";
            else if(value < 400)
            	classValue = "B";
            else if(value < 600)
            	classValue = "C";
            else if(value < 800)
            	classValue = "D";
            else if(value < 1000)
            	classValue = "E";
            else
            	classValue = "F";
			
			
			classes.put(rowData, classValue);
			
		}
		
		System.out.print("Enter Output File Name: ");
		String fileName=in.nextLine();
		
		try {
			FileWriter fw=new FileWriter(new File(fileName));
			
			for(String[] rowData: data) {
				String newData=Arrays.toString(rowData);
				newData=newData.substring(1,newData.length()-1);
				fw.write(newData+","+classes.get(rowData)+"\n");
			}
			
			fw.close();
			System.out.println("Discretized file generated Successfully!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	public void stratifiedSampling() {
		System.out.println("\n\nStratified Sampling:-\n---------------------\n");
		
		int totalClassSize=data.size();
		System.out.print("\nEnter the Sample Size: ");
		int sampleSize=Integer.parseInt(in.nextLine());
		if(sampleSize>totalClassSize) {
			System.out.println("Sample size cannot exceed Class size! Exiting..");
			System.exit(0);
		}
		
		System.out.print("Enter Column No. to be grouped by: ");
		int groupCol=Integer.parseInt((in.nextLine()))-1;
		
		HashMap<String, ArrayList<String[]>> stratas=new HashMap<String, ArrayList<String[]>>();
		
		for(String[] rowData: data) {
			String colName=rowData[groupCol];
			if(stratas.get(colName)==null)
			{
				ArrayList<String[]> values=new ArrayList<String[]>();
				values.add(rowData);
				stratas.put(colName, values);
			}
			else
			{
				ArrayList<String[]> values=stratas.get(colName);
				values.add(rowData);
				stratas.put(colName, values);
			}
		}
		
		for(Map.Entry<String, ArrayList<String[]>> e: stratas.entrySet()) {
		
			String groupName=e.getKey();
			int thisSize=e.getValue().size();
			int thisSampleSize=(int) Math.rint(((double)(sampleSize)/totalClassSize)*thisSize);
			System.out.println("\n"+groupName+":-");
			System.out.println("Class Size="+thisSize+"/"+totalClassSize);
			System.out.println("Sample Size="+thisSampleSize);
			
			ArrayList<String[]> samples= e.getValue();
			Collections.shuffle(samples);
			for(int i=0;i<thisSampleSize;i++)
				System.out.println(Arrays.toString(samples.get(i)));
			
			
		}
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		mc.aggregation();
		mc.discretization();
		mc.stratifiedSampling();
	}

}
