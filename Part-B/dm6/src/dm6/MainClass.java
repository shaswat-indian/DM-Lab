package dm6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
	
	Scanner in=new Scanner(System.in);

	HashMap<ArrayList<Double>, String> data;
	int k;
	
	ArrayList<ArrayList<Double>> neighbours;
	
	
	public MainClass() {
		
		System.out.print("Enter value of K: ");
		k=Integer.parseInt(in.nextLine());
		System.out.print("Enter Input File Name: ");
		String fileName=in.nextLine();
		data=new HashMap<>();
		neighbours=new ArrayList<>();
	
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
				ArrayList<Double> temp=new ArrayList<>();
				for(int i=0;i<rowData.length-1;i++)
					temp.add(Double.parseDouble(rowData[i]));
				
				data.put(temp, rowData[rowData.length-1]);
				ipLine=br.readLine();
				
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
	
	public void findNeighbours(Double ip[]) {
		
		ArrayList<Double> ipValues=new ArrayList<>(Arrays.asList(ip));
		for(Map.Entry<ArrayList<Double>,String> me: data.entrySet()) {
			ArrayList<Double> values=me.getKey();
			neighbours.add(values);
			
		}
		
		Collections.sort(neighbours, new Comparator<ArrayList<Double>>() {

			@Override
			public int compare(ArrayList<Double> o1, ArrayList<Double> o2) {
				// TODO Auto-generated method stub
				double d1=findDistance(ipValues, o1);
				double d2=findDistance(ipValues, o2);
				if(d1>d2)
					return 1;
				else if(d1<d2)
					return -1;
				else
					return 0;
			}
		});
		
		
		
		System.out.println(k+" Nearest Neighbours and their Class:-");
		for(int i=0;i<k;i++) {
			System.out.println(neighbours.get(i)+" : "+data.get(neighbours.get(i)));
		}
		
		
	}
	
	
	public double findDistance(ArrayList<Double> ipValues, ArrayList<Double> values) {
		// TODO Auto-generated method stub
		double dist=0;
		for(int i=0;i<ipValues.size();i++) {
			dist=dist+(Math.pow(ipValues.get(i)-values.get(i), 2));
		}
		return Math.sqrt(dist);
	}

	public void predictClass() {
		// TODO Auto-generated method stub
		HashMap<String, Integer> countMap=new HashMap<>();
		for(int i=0;i<k;i++) {
			String key=data.get(neighbours.get(i));
			countMap.put(key, countMap.getOrDefault(key, 0)+1);
		}
		
		int maxCount=Integer.MIN_VALUE;
		String predClass="";
		for(Map.Entry<String, Integer> me:countMap.entrySet()) {
			if(me.getValue()>maxCount) {
				maxCount=me.getValue();
				predClass=me.getKey();
			}
		}
		
		System.out.println("\nPredicted Class is "+predClass);
		
		
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		
		Double[] new_row = {6.0,3.5,4.1};
		mc.findNeighbours(new_row);
		mc.predictClass();
		

	}

	
}
