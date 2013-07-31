package ca.kijiji.contest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public class ParkingTicketsStats {
    
    public static SortedMap<String, Integer> sortStreetsByProfitability(InputStream parkingTicketsStream) 
    {
        SortedMap<String, Integer> mapByKey = new TreeMap<String, Integer>();
        
        String line;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(parkingTicketsStream));
        try 
        {
            bufferReader.readLine(); //skip first line headings
            while ((line = bufferReader.readLine()) != null ) {
                int i = 0; int commas = 0; 
                int fineStartIndex = 0; int fineEndIndex = 0; 
                int streetStartIndex = 0; int streetEndIndex = 0;
                while (commas<8)
                {
                    if (line.charAt(i++) == ',')
                    {
                        commas++;
                        switch (commas)
                        {
                            case(4):
                                fineStartIndex = i + 1;
                                break;  //fine between 4th and 5th comma
                            case(5):
                                fineEndIndex = i - 1;
                                break;
                            case(7):
                                streetStartIndex = i + 1;
                                break;  //street name between 7th and 8th comma
                            case(8):
                                streetEndIndex = i - 1;
                                break;
                        }   
                    }
                }
                processAndEnterTree(streetStartIndex, streetEndIndex, fineStartIndex, fineEndIndex, line, mapByKey);        
            }
        } catch (IOException e){}
        
        SortedMap<String, Integer> mapByValue = new TreeMap<String, Integer>(new valueComparator<String>(mapByKey)); 
        mapByValue.putAll(mapByKey);
        try 
        {
            bufferReader.close(); 
        } catch (IOException e){}
        
        return mapByValue;
    }
    
    public static void processAndEnterTree(int streetStartIndex, int streetEndIndex, int fineStartIndex, int fineEndIndex, String line, SortedMap<String, Integer> map)
    {
        String street = line.substring(streetStartIndex - 1, streetEndIndex);
        String fine = line.substring(fineStartIndex - 1, fineEndIndex);
        
        int money = Integer.parseInt(fine);
        
        street = cleanStreet(street);
        
        if (street.length()==0) return;
        
        if (!map.containsKey(street))
            map.put(street, money);
      
        else
            map.put(street, map.get(street) + money);
        
    }
    
    public static String cleanStreet (String input)
    {
        //keep track of space indices and use substrings sparingly
        Vector<Integer> spaceIndices = new Vector<Integer>();
        
        for (int i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) == ' ')
                spaceIndices.add(i);
            
        }
        
        if (spaceIndices.size() == 0) return input;

        int wordCount = spaceIndices.size() + 1;
        
        int frontTrimCount = 0;
        int endTrimCount = 0; 
        
        String firstWord = input.substring(0, spaceIndices.firstElement());
        String lastWord = input.substring(spaceIndices.lastElement() + 1);
        
        int lastWordLen = lastWord.length();
        
        // string ends in direction
        if (lastWordLen == 1 || lastWord == "EAST" || lastWord == "WEST")
            endTrimCount = 2;
        // string end in road suffix
        else if (lastWordLen < 7) 
            endTrimCount = 1;
        
        //starts with street number
        if (isInteger(firstWord)) 
            frontTrimCount = 1;
        
        String cleanedStreet = "";
        
        for (int i = frontTrimCount; i < wordCount - endTrimCount; i++)
        {
            if (i == 0)
                cleanedStreet += firstWord + " ";
            else if (i == wordCount-1)
                cleanedStreet += lastWord + " ";
            else 
                cleanedStreet += input.substring(spaceIndices.get(i - 1) + 1, spaceIndices.get(i)) + " ";
        }
               
        if (cleanedStreet.length() > 0)
            cleanedStreet = cleanedStreet.substring(0, cleanedStreet.length() - 1);
        
        return cleanedStreet;
    }
    public static boolean isInteger(String input)  
    {  
       try  
       {  
          Integer.parseInt(input);  
          return true;  
       }  
       catch(Exception e)  
       {  
          return false;  
       }  
    }    
}