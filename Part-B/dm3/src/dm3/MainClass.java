package dm3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MainClass {

	Scanner in=new Scanner(System.in);
	
	String items[];
	String freqItemSet[];
	float support,confidence;
	
	ArrayList<String> rules;
	ArrayList<String> strongRules;
	
	public MainClass() {
		// TODO Auto-generated constructor stub
		
		rules=new ArrayList<String>();
		strongRules=new ArrayList<String>();

		freqItemSet=new String[5];
		
		System.out.println("Enter the 4 Items:-");
		items=new String[4];
		for(int i=0;i<4;i++)
			items[i]=in.nextLine();	
		
		System.out.print("Enter the Min. Support Value: ");
		support=Float.parseFloat(in.nextLine());
		

		System.out.print("Enter the Min. Confidence Value: ");
		confidence=Float.parseFloat(in.nextLine());
		
		

	}
	
	public void showFreqItemSets() {
		System.out.println("\nAll Generated Frequent Itemsets:-\n--------------------------------");
		for(int i=1;i<freqItemSet.length;i++)
			System.out.println(freqItemSet[i]);
		
	}

	public void showRules() {
		System.out.println("\nAll Generated Rules:-\n-------------------");
		for(String rule: rules) {
			System.out.println(rule);
		} 
		
	}

	
	public void generateFreqItemSet() {
		
		for(int i=1;i<=4;i++) {
			
			if(i==1) {
				freqItemSet[i]=String.join(",",items);
			}
			else if(i==4) {
				freqItemSet[i]=String.join("&", items);
			}
			else if(i==2){
				ArrayList<String> temp=new ArrayList<String>();
				for(int j=0;j<items.length-1;j++) {
					for(int k=j+1;k<items.length;k++) {
						temp.add(String.join("&", items[j],items[k]));
					}
				}
				freqItemSet[i]=String.join(",", temp);
			}
			else if(i==3) {
				ArrayList<String> temp=new ArrayList<String>();
				
				//k-1*k-1
				String freq2groups[]=freqItemSet[2].split(",");
				int freq2len=freq2groups.length;
				for(int j=0;j<freq2len;j++) {
					
					String freq2items1[]=freq2groups[j].split("&");
					
					for(int k=j+1;k<freq2len;k++) {
						
						String freq2items2[]=freq2groups[k].split("&");
						
						if(freq2items1[0].equals(freq2items2[0])) {
							temp.add(String.join("&", freq2items1[0],freq2items1[1],freq2items2[1]));
						}
						
					}
				}
				
				freqItemSet[i]=String.join(",",temp);
				
			}
		}
		
	}
	
	
	public void generateRules() {
		
		for(int i=2;i<freqItemSet.length;i++) {
			
			if(i==2) {
				String freqItems[]=freqItemSet[i].split(",");
				for(String item:freqItems) {
					String splitItems[]=item.split("&");
					String newRule=splitItems[0]+" -> "+splitItems[1];
					rules.add(newRule);
					newRule=splitItems[1]+" -> "+splitItems[0];
					rules.add(newRule);
					
				}
			}
			
			
			else if(i==3) {
				
				String freqItems[]=freqItemSet[i].split(",");
				for(String item:freqItems) {
					String splitItems[]=item.split("&");
					
					String newRule;
					for(int j=0;j<3;j++) {
						ArrayList<String> temp=new ArrayList<String>(Arrays.asList(splitItems));
						String predec=temp.remove(j);
						String succ=String.join("&", temp);
						newRule=predec+" -> "+succ;
						rules.add(newRule);
						newRule=succ+" -> "+predec;
						rules.add(newRule);
					}
					
					
					
				}
				
			}
			
			else if(i==4) {
				
				String freqItems[]=freqItemSet[i].split(",");
				for(String item:freqItems) {
					String splitItems[]=item.split("&");
					
					String newRule;
					for(int j=0;j<3;j++) {
						ArrayList<String> temp=new ArrayList<String>(Arrays.asList(splitItems));
						String predec=temp.remove(j);
						String succ=String.join("&", temp);
						newRule=predec+" -> "+succ;
						rules.add(newRule);
						newRule=succ+" -> "+predec;
						rules.add(newRule);
					}
					
					for(int j=0;j<3;j++) {
						for(int k=j+1;k<4;k++) {
							
							ArrayList<String> temp=new ArrayList<String>(Arrays.asList(splitItems));
							String predec1=temp.get(j);
							String predec2=temp.get(k);
							temp.remove(predec1);
							temp.remove(predec2);
							String predec=String.join("&", predec1,predec2);
							String succ=String.join("&", temp);
							newRule=predec+" -> "+succ;
							if(!rules.contains(newRule))
								rules.add(newRule);
							newRule=succ+" -> "+predec;
							if(!rules.contains(newRule))
								rules.add(newRule);
							
						}
					}
					
					
					
				}
				
			}
			
			
		}
		
		
	}
	
	
	public void generateStrongRules() {
		
		System.out.println("\nAll Generated Rules with Support & Confidence:-\n--------------------------------");
		for(String rule: rules) {
			double thisSup=Math.random();
			double thisConf=Math.random();
			if(thisSup>=support && thisConf>=confidence)
				strongRules.add(rule);
		
			System.out.printf("%s\tSupport=%.2f\tConfidence=%.2f\n",rule,thisSup,thisConf);
		}
		
		System.out.println("\nStrong Rules:-\n------------");
		for(String rule:strongRules)
			System.out.println(rule);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainClass mc=new MainClass();
		mc.generateFreqItemSet();
		mc.showFreqItemSets();
		mc.generateRules();
		//mc.showRules();
		mc.generateStrongRules();
		

	}

}
