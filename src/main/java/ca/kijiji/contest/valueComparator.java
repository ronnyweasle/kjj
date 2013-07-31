package ca.kijiji.contest;
import java.util.Comparator;
import java.util.SortedMap;
import java.lang.*;

public class valueComparator<String> implements Comparator<String> {
	
	private SortedMap<String, Integer> myMap;
	
	public valueComparator(SortedMap<String, Integer> map)
	{
		myMap = map;
	}
	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		return myMap.get(o2).compareTo(myMap.get(o1));
	}

}
