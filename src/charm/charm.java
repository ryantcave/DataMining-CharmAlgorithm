// Ryan Cave

package charm;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class charm {
	

	public static void main (String args[]) {	
		
		List<String> transactions = new ArrayList<String>();
		List<String> frequentItems = new ArrayList<String>();
		List<charmItem> charmList = new ArrayList<charmItem>();
		Set<String> items = new HashSet<String>();
		int numTransactions = 0;
		double minSupport = 3.5;
		
		
		// reading all transactions from file and storing them in list
		try {
			File file = new File("assignment1_input.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				line = line.substring(line.indexOf("\t")).trim();
				transactions.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		numTransactions = transactions.size();
	
		// getting all unique items from transactions
		
		for (int i = 0; i < transactions.size(); i++){
			String temp = transactions.get(i);
		
			String parts[] = temp.split("\\t");
			
			for (int j = 0; j < parts.length; j++){
				items.add(parts[j]);
			}
		
		}
		
		// find size 1 frequent items
				
		for (String s : items){
			double counter = 0;			

			charmItem temp = new charmItem();
			temp.addTransaction(s);
			
			for (int i = 0; i < transactions.size(); i++){
				if (transactions.get(i).contains(s)){
					counter++;
					temp.addOwner(i);
				}
			}
			
			double supportFactor = counter / numTransactions * 100;
			
			if (supportFactor > minSupport){
				frequentItems.add(s);
				charmList.add(temp);
				
			}
		}
		
		// Utilize pruning strategy over list of frequent item sets		
		
		
		for (int p1 = 0; p1 < charmList.size(); p1++){
			for (int p2 = 0; p2 < charmList.size(); p2++){
				
				charmItem x1 = charmList.get(p1);
				charmItem x2 = charmList.get(p2);
				
				// Pruning Case 1
				
				if (x1.equals(x2)){
					charmItem combo = x1.combine(x2);
					charmList.set(p1, combo);
					charmList.remove(p2);
					
					double comboCounter = combo.getOwners().size();
					double supportFactor = comboCounter / numTransactions * 100;
					
					
					if (supportFactor < minSupport){
						charmList.remove(combo);
					}
				}
				
				// Pruning Case 2	
				
				else if (x2.subsumes(x1)){
					charmItem combo = x1.combine(x2);
					charmList.set(p1, combo);
					
					double comboCounter = combo.getOwners().size();
					double supportFactor = comboCounter / numTransactions * 100;
					
					if (supportFactor < minSupport){
						charmList.remove(combo);
					}
				}
				
				// Pruning Case 3
				
				else if (x1.subsumes(x2)){
					charmItem combo = x1.combine(x2);
					charmList.set(p2, combo);
					
					double comboCounter = combo.getOwners().size();
					double supportFactor = comboCounter / numTransactions * 100;
					
					if (supportFactor < minSupport){
						charmList.remove(combo);
					}
				}
				
				// Pruning Case 4
				
				else{
					// In the last case we keep both itemsets, so nothing is done here.
				}
				
				
			}
		}
		
		// charmList should contain all of our valid frequent item sets that are not subsumed by others
		// printing all item sets with at least size 2
		
		for (charmItem x : charmList){
			
			double counter = x.supportSize();
			double supportFactor = counter / numTransactions * 100;
			
			if (supportFactor >= minSupport && x.size() >= 2){					
				System.out.print(x.toString() + " ");
				System.out.printf("%.2f%%%n", supportFactor);
			}
			
		}
		
			
	}

}