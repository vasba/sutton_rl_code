package com.ml.rl.efb;

public class TenArmedSoftmax extends TenArmedTestbed {

	public int softMaxSelection(double temperature) {
		double totalSum = 0;
		double[] partialSums = new double[n];
		
		for (int a=0;a<n;a++) {
			double value = q[a];
			totalSum += Math.exp(value/temperature);
			partialSums[a] = totalSum;
		}
		
		double probability = random.nextDouble()*totalSum;
		for (int a=0;a<n;a++) {
			if (partialSums[a] > probability)
				return a;
		}
		
		return 0;
	}
	
	@Override
	public int policyAction(double policy_value) {
		return softMaxSelection(policy_value);
	}
}
