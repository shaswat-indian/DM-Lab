package dm4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class MainClass {

	Scanner in=new Scanner(System.in);
	ArrayList<String[]> data;
	double classEntropy, classGini;

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
	
	private void findClassEntropyGini() {
		
		
		int total=0;
		int classIndex=data.get(0).length-1;
		HashMap<String, Integer> hm=new HashMap<String, Integer>();
		for(String[] rowData:data) {
			hm.put(rowData[classIndex], hm.getOrDefault(rowData[classIndex], 0)+1);
			total++;
		}
		
		double logVal=0;
		double squareSum=0;
		for(Map.Entry<String, Integer> me: hm.entrySet()) {
			double tempVal=me.getValue();
			tempVal=tempVal/total;
			squareSum=squareSum+(Math.pow(tempVal, 2));
			logVal=logVal+(tempVal)*(log2(tempVal));
		}
		classEntropy=-1*logVal;
		classGini=1-squareSum;
		
		System.out.println("Class Entropy="+classEntropy);
		System.out.println("Class Gini="+classGini);
		
	}
	
	public void bestEntropySplit() {
		
		System.out.println("\nEntropy Gain:-\n-----------------");
		double maxDiff=Double.MIN_VALUE;
		int bestIndex=0;
		for(int i=0;i<data.get(0).length-1;i++) {
			System.out.println("\nAttribute "+(i+1));
			double thisEntropy=findAttributeEntropy(i);
			double diff=this.classEntropy-thisEntropy;
			System.out.println("Change in Entropy="+diff);
			if(diff>maxDiff) {
				maxDiff=diff;
				bestIndex=i;
			}
				
		}
		
		System.out.println("Best Atrribute for Multiway Split by Entropy gain is Attribute "+(bestIndex+1));
		
	}
	
	
	private double findAttributeEntropy(int index) {
		
		HashMap<String, HashMap<String, Integer>> hm= new HashMap<String, HashMap<String, Integer>>();
		int classIndex=data.get(0).length-1;
		HashMap<String, Integer> total=new HashMap<String, Integer>();
		
		for(String[] rowData: data) {
			
			total.put(rowData[index], total.getOrDefault(rowData[index], 0)+1);
			
			if(hm.containsKey(rowData[index])) {
				HashMap<String, Integer> tempMap=hm.get(rowData[index]);
				tempMap.put(rowData[classIndex], tempMap.getOrDefault(rowData[classIndex], 0)+1);
				hm.put(rowData[index], tempMap);
			}
			else {
				HashMap<String, Integer> tempMap=new HashMap<String,Integer>();
				tempMap.put(rowData[classIndex], 1);
				hm.put(rowData[index], tempMap);
			}
		}
		
		double entropy=0;
		int totalPN=data.size();
		for(Map.Entry<String,HashMap<String, Integer>> tempMap: hm.entrySet()) {
			double p,n;
			
			//for 'input.csv'
			p=tempMap.getValue().getOrDefault("Yes",0);	//p-class
			n=tempMap.getValue().getOrDefault("No", 0);		//n-class
			
			//for 'input2.csv'
			/*
			p=tempMap.getValue().getOrDefault("C0",0);	//p-class
			n=tempMap.getValue().getOrDefault("C1", 0);		//n-class
			*/
			
			entropy=entropy+(((double)total.get(tempMap.getKey())/totalPN)*findEntropy(p, n));
			
			
			
		}
		
		System.out.println(hm);
		System.out.println("Entropy = "+entropy);
		
		//System.out.println(total);
		
		return entropy;
		
	}
	
	private double findEntropy(double p, double n) {
		if(p==0||n==0)
			return 0.0;
		if(p==n)
			return 1.0;
		double total=p+n;
		double entropy=(p/total)*log2(p/total)+(n/total)*log2(n/total);
		return -1*entropy;
	}
	
	private double log2(double val) {
		return Math.log(val)/Math.log(2);
	}
	
	public void bestGiniSplit() {
		
		System.out.println("\nGini Index:-\n-----------------");
		double maxDiff=Double.MIN_VALUE;
		int bestIndex=0;
		for(int i=0;i<data.get(0).length-1;i++) {
			System.out.println("\nAttribute "+(i+1));
			double thisGini=findAttributeGini(i);
			double diff=this.classGini-thisGini;
			System.out.println("Change in Gini Index="+diff);
			if(diff>maxDiff) {
				maxDiff=diff;
				bestIndex=i;
			}
				
		}
		
		System.out.println("Best Atrribute for Multiway Split by Gini Index is Attribute "+(bestIndex+1));
		
	}
	
	
	
	private double findAttributeGini(int index) {
		
		HashMap<String, HashMap<String, Integer>> hm= new HashMap<String, HashMap<String, Integer>>();
		int classIndex=data.get(0).length-1;
		HashMap<String, Integer> total=new HashMap<String, Integer>();
		
		for(String[] rowData: data) {
			
			total.put(rowData[index], total.getOrDefault(rowData[index], 0)+1);
			
			if(hm.containsKey(rowData[index])) {
				HashMap<String, Integer> tempMap=hm.get(rowData[index]);
				tempMap.put(rowData[classIndex], tempMap.getOrDefault(rowData[classIndex], 0)+1);
				hm.put(rowData[index], tempMap);
			}
			else {
				HashMap<String, Integer> tempMap=new HashMap<String,Integer>();
				tempMap.put(rowData[classIndex], 1);
				hm.put(rowData[index], tempMap);
			}
		}
		
		double gini=0;
		int totalPN=data.size();
		for(Map.Entry<String,HashMap<String, Integer>> tempMap: hm.entrySet()) {
			double p,n;
			//for 'input.csv'
			p=tempMap.getValue().getOrDefault("Yes",0);	//p-class
			n=tempMap.getValue().getOrDefault("No", 0);		//n-class
			
			//for 'input2.csv'
			/*
			p=tempMap.getValue().getOrDefault("C0",0);	//p-class
			n=tempMap.getValue().getOrDefault("C1", 0);		//n-class
			*/
			
			gini=gini+(((double)total.get(tempMap.getKey())/totalPN)*findGini(p, n));
			
			
			
		}
		
		System.out.println(hm);
		System.out.println("Gini Index = "+gini);
		
		//System.out.println(total);
		
		return gini;
		
	}
	
	private double findGini(double p, double n) {
		double total=p+n;
		double gini=Math.pow((p/total),2)+Math.pow((n/total),2);
		return 1-gini;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		//mc.showData();
		mc.findClassEntropyGini();
		mc.bestEntropySplit();
		mc.bestGiniSplit();

		
	}

}
