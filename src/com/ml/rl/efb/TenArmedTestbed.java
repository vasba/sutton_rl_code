package com.ml.rl.efb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TenArmedTestbed {
	int n=10;
	int tasksCount=2000;
	double[][] qStar=new double[n][tasksCount];
	double[] q=new double[n];
	double[] nA=new double[n];
	Random random = new Random();
	
	public TenArmedTestbed() {
		super();
		for (int task=0;task<tasksCount;task++) {
			for (int i=0;i<n;i++){
				qStar[i][task] = random.nextGaussian();
			}
		}
	}
	
	public void init() {
		for (int i=0;i<n;i++){
			q[i]=0;
			nA[i]=0;
		}
	}

	public void runs(int steps, double epsilon) {
		double[] averageReward = new double[steps];
		double[] probabilityAStar = new double[steps];
				
		for (int task=0; task<tasksCount; task++) {
			int aStar = 0;
			for (int a = 0; a<n; a++) {
				if (qStar[a][task] > qStar[aStar][task])
					aStar = a;
			}
			init();
			for (int time_step = 0; time_step<steps; time_step++) {
				int a = policyAction(epsilon);
				double r = reward(a, task);
				learn(a, r);
				averageReward[time_step] = averageReward[time_step] + r;
				if (a == aStar) {
					probabilityAStar[time_step] = probabilityAStar[time_step]+1;
				}
			}	
		}
		
		for (int time_step = 0; time_step<steps; time_step++) {
			averageReward[time_step] = averageReward[time_step]/tasksCount;
			probabilityAStar[time_step] = probabilityAStar[time_step]/tasksCount;
		}
		int i = 0;
	}
	
	public void learn(int a, double reward) {
		nA[a]++;
		double increment = (reward-q[a])/(nA[a]);
		q[a] = q[a] + increment;		
	}
	
	public double reward(int a, int task) {
		return qStar[a][task] + random.nextGaussian();
	}
	
	public int policyAction(double policy_value) {
		return epsilonGreedy(policy_value);
	}
	
	/** Policy used to select action
	 * @param epsilon
	 * @return
	 */
	public int epsilonGreedy(double epsilon) {
		return argMaxRandomTiebreak(q, epsilon);
	}
	
	public int greedy() {
		return argMaxRandomTiebreak(q, 0);
	}
	
	/**
	 * Returns index to first instance of the largest value in the array
	 * @param array
	 * @return
	 */
	public int argMaxRandomTiebreak(double[] array, double epsilon) {
		double nextDouble = random.nextDouble();
		if (epsilon == 0 || nextDouble >= epsilon) {
			//ArrayList<Integer> bestArgs = new ArrayList<>();
			double largest = array[0];
			int index = 0;
			for (int i = 1; i < array.length; i++) {
				if ( array[i] > largest ) {
					largest = array[i];
					index = i;
				}
			}
			return index;
		} else {
			return random.nextInt(array.length);
		}
	}
	
	public double maxQStar(int numberOfTasks) {
		double maxQstarSum = 0;
		for (int task=0;task<numberOfTasks;task++) {
			ArrayList<Double> qStarTasks = new ArrayList<>(); 
			for(int i = 0; i<n ;i++) {
				qStarTasks.add(qStar[i][task]);
			}
			maxQstarSum += Collections.max(qStarTasks);
		}
		return maxQstarSum/numberOfTasks;
	}
	
}