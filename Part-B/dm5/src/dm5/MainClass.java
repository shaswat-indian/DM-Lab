package dm5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {

	Scanner in=new Scanner(System.in);
	ArrayList<String[]> data;
	
	
	HashMap<String, Double> prior;
	HashMap<String, Double> means;
	HashMap<String, Double> vars;
	
	HashMap<String, HashMap<String, Double>> attr2prob;
	

	public MainClass() {
		// TODO Auto-generated constructor stub
		
		System.out.print("Enter Input File Name: ");
		String fileName=in.nextLine();
		data=new ArrayList<String[]>();
		
		
		prior=new HashMap<>();
		means=new HashMap<>();
		vars=new HashMap<>();
		attr2prob=new HashMap<>();
	
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
	
	
	void findPrior() {
		HashMap<String, Integer> classCount=new HashMap<>();
		int classIndex=data.get(0).length-1;
		
		for(String rowData[]: data) {
			String className=rowData[classIndex];
			classCount.put(className, classCount.getOrDefault(className, 0)+1);
		}
		
		for(String rowData[]: data) {
			String className=rowData[classIndex];
			prior.put(className, (double)classCount.get(className)/data.size());
		}
		
		
		//System.out.println(prior);
		System.out.println("Prior Probability:-\n------------");
		for(Map.Entry<String, Double> me: prior.entrySet()) {
			System.out.println("Class "+me.getKey()+": "+me.getValue());
		}
		
		
	}
	
	public void numericAttribute(int index) {
		// TODO Auto-generated method stub
		int classIndex=data.get(0).length-1;
		
		
		HashMap<String, Double> temp=new HashMap<>();
		HashMap<String, ArrayList<Double>> temp1=new HashMap<>();
		for(String rowData[]: data) {
			String className=rowData[classIndex];
			temp.put(className, temp.getOrDefault(className, 0.0)+Double.parseDouble(rowData[index]));
			if(temp1.containsKey(className)) {
				ArrayList<Double> tempArr=temp1.get(className);
				tempArr.add(Double.parseDouble(rowData[index]));
				temp1.put(className, tempArr);
			}
			else {
				ArrayList<Double> tempArr=new ArrayList<>();
				tempArr.add(Double.parseDouble(rowData[index]));
				temp1.put(className, tempArr);
			}
		}
		
		for(Map.Entry<String, Double> me: temp.entrySet()) {
			means.put(me.getKey(), me.getValue()/temp1.get(me.getKey()).size());
		}
		
		for(Map.Entry<String, ArrayList<Double>> me: temp1.entrySet()) {
			double var=0.0;
			double mean=means.get(me.getKey());
			for(Double d: me.getValue()) {
				var=var+Math.pow(d-mean, 2);
			}
			vars.put(me.getKey(), var/(me.getValue().size()*(me.getValue().size()-1)));
		}
		
		
		System.out.println("\nAttribute "+(index+1)+":-\n--------------");
		for(Map.Entry<String, Double> me: means.entrySet()) {
			System.out.println("\nClass "+me.getKey()+":-");
			System.out.println("Mean="+me.getValue());
			System.out.println("Variance="+vars.get(me.getKey()));
		}
		
	}
	
	public void catAttribute(int index) {
		// TODO Auto-generated method stub
		int classIndex=data.get(0).length-1;
		HashMap<String, HashMap<String, Integer>> count = new HashMap<>();
		
		
		for(String rowData[]: data) {
			String className=rowData[classIndex];
			String attrName=rowData[index];
			if(count.containsKey(className)) {
				HashMap<String, Integer> temp=count.get(className);
				temp.put(attrName,temp.getOrDefault(attrName,0)+1);
				count.put(className, temp);
			}
			else {
				HashMap<String, Integer> temp=new HashMap<>();
				temp.put(attrName, 1);
				count.put(className, temp);
			}
		}
		
		for(Map.Entry<String, HashMap<String, Integer>> me: count.entrySet()) {
			double classSize=0;
			for(Map.Entry<String, Integer> me2: me.getValue().entrySet()) {
				classSize+=me2.getValue();
			}
			String className=me.getKey();
			for(Map.Entry<String, Integer> me2: me.getValue().entrySet()) {
				double prob=(double)me2.getValue()/classSize;
				String attrName=me2.getKey();
				if(attr2prob.containsKey(className)) {
					HashMap<String, Double> temp=attr2prob.get(className);
					temp.put(attrName, prob);
					attr2prob.put(className, temp);
				}
				else {
					HashMap<String, Double> temp=new HashMap<>();
					temp.put(attrName, prob);
					attr2prob.put(className, temp);
				}
				
			}
		}
		
		
		
		
		System.out.println("\nAttribute "+(index+1)+":-\n--------------");
		for(Map.Entry<String, HashMap<String, Double>> me: attr2prob.entrySet()) {
			System.out.println("\nClass "+me.getKey()+":-");
			
			for(Map.Entry<String, Double> me2: me.getValue().entrySet()) {
				System.out.println(me2.getKey()+"="+me2.getValue());
			}

			
		}
		
		
	}
	
	public void classifyNewRecord() {
		Scanner in=new Scanner(System.in);
		System.out.println("\nEnter Details of New Record:-");
		System.out.print("Value of attribute 1: ");
		double attr1=Double.parseDouble(in.nextLine());
		System.out.print("Value of attribute 2: ");
		String attr2=in.nextLine();
		
		String maxClass=null;
		double maxprob=Double.MIN_VALUE;
		
		HashMap<String, Double> newProb=new HashMap<>();
		for(Map.Entry<String, Double> me: means.entrySet()) {
			double mean=me.getValue();
			double var=vars.get(me.getKey());
			double prob=(Math.exp(-1*(Math.pow((attr1-mean),2)/(2*var))))/(Math.sqrt(2*Math.PI*var));
			prob=prob*attr2prob.get(me.getKey()).getOrDefault(attr2, 0.0)*prior.get(me.getKey());
			newProb.put(me.getKey(), prob);
			System.out.println("Probability of Class "+me.getKey()+"="+prob);
			if(prob>maxprob) {
				maxprob=prob;
				maxClass=me.getKey();
			}
		}
		
		System.out.println("New Record belongs to Class "+maxClass);
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		mc.findPrior();
		mc.numericAttribute(0);
		mc.catAttribute(1);
		mc.classifyNewRecord();
		

	}

	

}
