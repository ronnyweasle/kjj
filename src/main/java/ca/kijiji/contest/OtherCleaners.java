package ca.kijiji.contest;

import java.util.LinkedList;

public class OtherCleaners {
	public static String cleanStreetFast1 (String input){
		if (!input.contains(" ")) return input;
				
		
		String[] words = input.split(" ");
		
		int wordCount = words.length;
		if (wordCount == 1)
		{
			return input;
		}
		int endTrim = 0; int startTrim = 0;
		
		if (words[wordCount-1].length() == 1) // end in dir
			endTrim = 2;
		else if (words[wordCount-1].length()<6) // end in suffix
			endTrim = 1;
		
		if (ParkingTicketsStats.isInteger(words[0]))
		{
			startTrim = 1;
		}
		String result = "";
		for (int i=startTrim;i<wordCount-endTrim;i++)
		{
			result+=words[i]+" ";
		}
		if (result.length()>0){
			result = result.substring(0, result.length()-1);
			//System.out.println(result);
		}
		return result;
	}
	public static String cleanStreetSlow (String input)
	{
		
		//String original = new String(input);
		boolean done = false;
		for(String roadName : new LinkedList<String>() {{
		    add("ST");
		    add("AVE");
		    add("RD");
		    add("AV");
		    add("DR");
		    add("BLVD");
		    add("CRES");
		    add("CR");
		    add("CRT");
		    add("BL");
		    add("CIR");
		    add("QUAY");
		    add("SQ");
		    add("ROAD");
		    add("TER");
		    add("GDNS");
		    add("TRL");
		    add("LANE");
		    add("WAY");
		}})
		{
			if (input.length()<roadName.length())
				continue;
			
			for(String dir : new LinkedList<String>() {{
			    add("N");
			    add("E");
			    add("S");
			    add("W");
			    add("WEST");
			    add("EAST");
			    add("NORTH");
			    add("SOUTH");
			}}){
				if (input.contains(" "+roadName + " " +dir))
				{
					input = input.substring(0, input.indexOf(" "+roadName+" "+dir));
					done = true;
					break;
				}
			}
			
			if (done) 
				break;
			int lenDiff =input.length()-roadName.length();
			if (input.substring(lenDiff).equals(roadName))
			{
				input = input.substring(0, lenDiff);
				break;
			}
			
			
			
		}
		input = input.trim();
		if (input.length()>0){
			
			if (!input.contains(" ")){
				
				return input;
			}
			else{
				
				String stNumberMinus1 =input.substring(0,input.indexOf(" ")-1);
				if (!ParkingTicketsStats.isInteger(stNumberMinus1) && stNumberMinus1.length()>0){
					
					return input;
				}
				
				return input.substring(input.indexOf(" ")+1);
			}
		}
		
		
		
		return input;
	}
	

}
