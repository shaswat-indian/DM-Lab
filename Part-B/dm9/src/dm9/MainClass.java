package dm9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
	
	Scanner in=new Scanner(System.in);
	
	int k,itr;
	String fileName;
	
	ArrayList<Double[]> data;
	HashMap<Integer, ArrayList<Double[]>> clusters;
	HashMap<Integer, Double[]> centroids;
	
	
	public MainClass() {
		System.out.print("Enter Number of Clusters: ");
		k=Integer.parseInt(in.nextLine());
		
		System.out.print("Enter Number of Iterations: ");
		itr=Integer.parseInt(in.nextLine());
		
		System.out.print("Enter Input File name: ");
		fileName=in.nextLine();
		
		data=new ArrayList<>();
		clusters=new HashMap<>();
		centroids=new HashMap<>();
		
		loadFile(fileName);

	}



	private void loadFile(String fileName) {
		// TODO Auto-generated method stub
		int dataSize;
		try {
			BufferedReader br =new BufferedReader(new FileReader(new File(fileName)));
			String ipLine;
			ipLine=br.readLine();
			while(ipLine!=null) {
				String rowData[]=ipLine.split(",");
				Double distData[]=new Double[rowData.length];
				for(int i=0;i<rowData.length;i++)
					distData[i]=Double.parseDouble(rowData[i]);
				data.add(distData);
				ipLine=br.readLine();
			}
			dataSize=data.size();
			if(dataSize<=k) {
				System.out.println("\nClusters are:-");
				for(int i=0;i<dataSize;i++) {
					System.out.println("\nCluster "+(i+1)+":-");
					String centroid=Arrays.toString(data.get(i));
					centroid=centroid.substring(1,centroid.length()-1);
					System.out.println(centroid);
				}
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found!");
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public double findDistance(Double[] d1, Double[] d2) {
		double distance=0.0;
		for(int i=0;i<d1.length;i++) {
			distance=distance+(Math.pow(d1[i]-d2[i], 2));
		}
		return Math.sqrt(distance);
	}
	
	public Double[] findCentroid(ArrayList<Double[]> cluster) {
		Double[] centroid=new Double[cluster.get(0).length];
		for(int i=0;i<centroid.length;i++) {
			centroid[i]=0.0;
		}
		
		for(Double[] record: cluster) {
			for(int i=0;i<record.length;i++) {
				centroid[i]+=record[i];
			}
		}
		
		for(int i=0;i<centroid.length;i++) {
			centroid[i]=centroid[i]/cluster.size();
		}
		return centroid;
	}
	
	public void computeAllCentroids(HashMap<Integer, ArrayList<Double[]>> clusters) {
		for(Map.Entry<Integer, ArrayList<Double[]>> me: clusters.entrySet()) {
			Double[] centroid=findCentroid(me.getValue());
			centroids.put(me.getKey(), centroid);
		}
	}
	
	public void initializeClusters() {
		for(int i=0;i<k;i++) {
			ArrayList<Double[]> record=new ArrayList<>();
			record.add(data.get(i));
			clusters.put(i, record);
			centroids.put(i, data.get(i));
		}
	}

	private void findClusters() {
		// TODO Auto-generated method stub
		for(int i=0;i<itr;i++) {
			
			HashMap<Integer, ArrayList<Double[]>> tempClusters=new HashMap<>();
			for(int j=0;j<k;j++) {
				ArrayList<Double[]> tempRecords=new ArrayList<>();
				tempClusters.put(j, tempRecords);
			}
			
			for(Double[] record: data) {
				
				double minDist=Double.MAX_VALUE;
				int minClusterNo=0;
				
				for(int j=0;j<k;j++) {
					Double[] centroid=centroids.get(j);
					double distance=findDistance(record, centroid);
					if(distance<minDist) {
						minDist=distance;
						minClusterNo=j;
					}
				}
				
				ArrayList<Double[]> tempRecords=tempClusters.get(minClusterNo);
				tempRecords.add(record);
				tempClusters.put(minClusterNo, tempRecords);
			}
			
			computeAllCentroids(tempClusters);
			clusters=tempClusters;
			
		}
		
	}
	
	public void displayClusters() {
		System.out.println("\nClusters:-");
		for(int i=0;i<k;i++) {
			System.out.println("Cluster "+(i+1)+":-\n");
			ArrayList<Double[]> records=clusters.get(i);
			for(Double[] record: records) {
				String recordData=Arrays.toString(record);
				recordData=recordData.substring(1,recordData.length()-1);
				System.out.println(recordData);
			}
			
			
		}
	}
	
	public void displayCentroids() {
		System.out.println("\nCentroids:-");
		for(int i=0;i<k;i++) {
			String centroid=Arrays.toString(centroids.get(i));
			centroid=centroid.substring(1,centroid.length()-1);
			System.out.println("Cluster "+(i+1)+": "+centroid);
		}
	}
	
	private void classifyNewData() {
		// TODO Auto-generated method stub
		System.out.print("Enter the comma-seperated values for the new record: ");
		String[] newData=in.nextLine().split(",");
		if(newData.length!=data.get(0).length) {
			System.out.println("Cannot Classify this records! Number of parameters is different!\n");
			return;
		}
		Double recordData[]=new Double[newData.length];
		for(int i=0;i<newData.length;i++)
			recordData[i]=Double.parseDouble(newData[i]);
		
		double minDist=Double.MAX_VALUE;
		int minClusterNo=0;
		
		for(int j=0;j<k;j++) {
			Double[] centroid=centroids.get(j);
			double distance=findDistance(recordData, centroid);
			if(distance<minDist) {
				minDist=distance;
				minClusterNo=j;
			}
		}
		
		System.out.println("Record belongs to Cluster "+(minClusterNo+1)+"\n");
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainClass mc=new MainClass();
		mc.initializeClusters();
		mc.findClusters();
		mc.displayClusters();
		mc.displayCentroids();
		mc.classifyNewData();
		

	}



	



	

}
