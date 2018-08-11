ArrayList<Integer> correct = new ArrayList<Integer>();
double largest = 0;
			int index = 0;
			int index2 = 0;
			double second = 0;
			for(int i = 0; i < finalAnsArr.length; i++) {
				if(finalAnsArr[i]>largest) {
					largest = finalAnsArr[i];
					index = i;
				}
			}
			for(int i = 0; i<finalAnsArr.length; i++) {
				if(finalAnsArr[i]>second && finalAnsArr[i]<largest) {
					second = finalAnsArr[i];
					index2 = i;
				}
			}
			if(desiredOutcome[index]==1 || desiredOutcome[index2]==1) {
				if(correct.size() == 200) {
					correct.remove(0);
				}
				correct.add(1);
			}
			else {
				if(correct.size() == 200) {
					correct.remove(0);
				}
				correct.add(0);
			}
			if(m%200 == 0) {
				int right = 0;
				for(double i:correct) {
					if(i == 1) {
						right++;
					}
				}
				System.out.println("Last 200 correct:" + right);
			}
			if(m%1000 == 0) {
				double  avg = 0;
				for(double i: error2) {
					avg+=i;
				}
				avg= avg/error2.length;
				System.out.println("error " + m + " :" + avg);

			}
