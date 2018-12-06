// Ryan Cave

package charm;

import java.util.ArrayList;
import java.util.List;

public class charmItem {
	
	List<String> names = new ArrayList<String>();
	List<Integer> owners = new ArrayList<Integer>();
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + names + "";
	}

	public List<String> getnames(){
		return names;
	}
	
	public List<Integer> getOwners(){
		return owners;		
	}
	
	public void setnames(List<String> x){
		names = x;
	}
	
	public void setOwners(List<Integer> x){
		owners = x;
	}
	
	public void addnames(List<String> x){
		for (String s : x){
			if (!names.contains(s)){
				names.add(s);
			}
		}
	}
	
	public void addOwners(List<Integer> x){
		owners.addAll(x);
	}
	
	public void addOwner(Integer x){
		owners.add(x);
	}
	
	public void addTransaction(String x){
		names.add(x);
	}
	
	public charmItem combine(charmItem x){
		charmItem combo = new charmItem();
		
		for (int y : owners){
			for (int z : x.owners){
				if (y == z){
					combo.addOwner(y);
				}
			}
			
		}
		
		combo.addnames(names);
		combo.addnames(x.names);
		
		return combo;
	}
	
	public boolean subsumes(charmItem x){
		
		if (owners.size() < x.owners.size()){
			return false;
		}
		
		for (Integer y : x.owners){
			if (!owners.contains(y)){
				return false;
			}
		}
		
		
		
		return true;
	}
	
	public boolean equals(charmItem x){
		
		if (names.equals(x.names)){
			return false;
		}
		
		if (!owners.equals(x.owners)){
			return false;
		}
		
		return true;
	}
	
	public Double supportSize(){
		return (double) owners.size();
	}
	
	public Integer size(){
		return names.size();
	}
	
}
