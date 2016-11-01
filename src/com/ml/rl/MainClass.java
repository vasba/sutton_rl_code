package com.ml.rl;

import com.ml.rl.efb.TenArmedSoftmax;
import com.ml.rl.efb.TenArmedTestbed;

public class MainClass {
	 public static void main(String[] args) {
		 TenArmedTestbed bandits = new TenArmedTestbed();
		 bandits.init();
		 bandits.runs(1000, 0.1);
		 
		 TenArmedSoftmax bandits2 = new TenArmedSoftmax();
		 bandits2.init();
		 bandits2.runs(1000, 1);
	 }
}
