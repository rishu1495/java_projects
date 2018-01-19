package com.checker.anagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AnagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnagramApplication.class, args);


        long initTime = System.currentTimeMillis();
        HashMap<String, ArrayList<String>> anagramFinalMap = anagramList(returnMapFromFile("textfiles//dictionary"));
        printList(anagramFinalMap);
        long finalTime = System.currentTimeMillis() - initTime;
        System.out.println("");
        System.out.println("Total Time to execute " + finalTime + " ms");
    } // end of main method

    /*
     * returnMapFromFile function reads dictionary using buffered reader and
     * returns Hashmap with sorted words as key and list of words as values.
     */
    public static HashMap<String, ArrayList<String>> returnMapFromFile(
            String fileName) {
        BufferedReader br = null;
        HashMap<String, ArrayList<String>> anagramMap = new HashMap<String, ArrayList<String>>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            char[] wordToChar = null;
            String dictionaryWord = "";
            ArrayList<String> newList = null;
            while ((dictionaryWord = br.readLine()) != null) {
                // convert string to character array
                wordToChar = dictionaryWord.toLowerCase().toCharArray();
                Arrays.sort(wordToChar);
                newList = anagramMap.get(String.valueOf(wordToChar));
                if (newList == null)
                    newList = new ArrayList<String>();

                newList.add(dictionaryWord);
                // add sorted character array and list to dictionary HashMap
                anagramMap.put(String.valueOf(wordToChar), newList);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return anagramMap;

    }

    /*
     * anagramList method return only Anagram List from HashMap
     */
    public static HashMap<String, ArrayList<String>> anagramList(
            HashMap<String, ArrayList<String>> anagramMap) {
        HashMap<String, ArrayList<String>> finalList = new HashMap<String, ArrayList<String>>();
        try {
            for (Map.Entry<String, ArrayList<String>> entry : anagramMap.entrySet()) {
                // get all values where list has more than 1 word
                if (entry.getValue().size() > 1) {
                    finalList.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return finalList;
    }

	/*
	 * printList method is printing list of Anagrams
	 */

    public static void printList(HashMap<String, ArrayList<String>> anagramMap) {
        ArrayList<String> anagramList = new ArrayList<String>();
        int count = 0;
        for (Map.Entry<String, ArrayList<String>> entry : anagramMap.entrySet()) {
            count++;
            anagramList = entry.getValue();
            for (String anagramWord : anagramList) {
                System.out.print(anagramWord + " ");

            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Total count is " + count);

    }
}


