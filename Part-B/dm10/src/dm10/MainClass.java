package dm10;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in=new Scanner(System.in);
		System.out.println("Enter the Confusion Matrix:-\n");
		
		int truePos, falseNeg, falsePos, trueNeg;
		truePos=Integer.parseInt(in.next());
		falseNeg=Integer.parseInt(in.next());
		falsePos=Integer.parseInt(in.next());
		trueNeg=Integer.parseInt(in.next());
		
		System.out.println("\tPredicted Yes\tPredicted No");
		System.out.println("Actual Yes\t"+truePos+"\t"+falseNeg);
		System.out.println("Actual No\t"+falsePos+"\t"+trueNeg);
		

		double accuracy=(double)(truePos+trueNeg)/(truePos+falseNeg+falsePos+trueNeg);
		double recall,sensitivity;
		recall=sensitivity=(double)(truePos)/(truePos+falseNeg);
		double specificity=(double)(trueNeg)/(trueNeg+falsePos);
		double precision=(double)(truePos)/(truePos+falsePos);
		
		System.out.println("\nAccuracy = "+accuracy);
		System.out.println("Sensitivity = "+sensitivity);
		System.out.println("Specificity = "+specificity);
		System.out.println("Recall = "+recall);
		System.out.println("Precision = "+precision);
		

	}

}
