package dm8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainClass {

	double m,c;
	
	public double mean(ArrayList<Double> list) {
		double avg=0;
		for(Double item: list) {
			avg+=item;
		}
		avg/=list.size();
		return avg;
		
	}
	
	public double covariance(ArrayList<Double> listx,ArrayList<Double> listy) {
		double meanx=mean(listx);
		double meany=mean(listy);
		double cov=0;
		for(int i=0;i<listx.size();i++) {
			cov=cov+((listx.get(i)-meanx)*(listy.get(i)-meany));
		}
		cov=cov/(listx.size()-1);
		return cov;
	}
	
	public double variance(ArrayList<Double> list) {
		double mean=mean(list);
		double var=0;
		for(int i=0;i<list.size();i++) {
			var=var+(Math.pow((list.get(i)-mean), 2));
		}
		var=var/(list.size()-1);
		return var;
	}
	
	public void findCoefficients(ArrayList<Double> listx,ArrayList<Double> listy) {
		m=covariance(listx, listy)/variance(listx);
		c=mean(listy)-(m*mean(listx));
		
		System.out.println("M="+m+"\nC="+c);
	}
	
	public double predict(double x) {
		return (m*x)+c;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainClass mc=new MainClass();

        ArrayList<Double> listx=new ArrayList<Double>(Arrays.asList(2.0, 3.0, 4.0, 5.0, 6.0, 8.0, 10.0, 11.0));
        ArrayList<Double> listy=new ArrayList<Double>(Arrays.asList(5.0, 7.0, 9.0, 11.0, 13.0, 17.0, 21.0, 23.0));

        mc.findCoefficients(listx, listy);
        System.out.print("Enter value of X: ");
        Scanner in=new Scanner(System.in);
        Double ip=Double.parseDouble(in.nextLine());
        System.out.println("Predicted Value of Y= "+mc.predict(ip));
        
	}

}
