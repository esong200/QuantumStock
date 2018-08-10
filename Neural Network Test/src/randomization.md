int rand = (int) ((int) dataTaylored.size()*Math.random());
				System.out.println(rand);
				for(int i=0; i<data.get(0).length; i++) {
					inputs[i]=(dataTaylored.get(rand))[i];
				}
				for(int i=0; i<ans.get(0).length; i++) {
					desiredOutcome[i] = ans.get(rand)[i];
				}
