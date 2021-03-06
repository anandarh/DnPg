package com.anandarherdianto.dinaspangan.utilitas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Ananda R. Herdianto on 12/04/2017.
 * Contain K Nearest Neighbor
 * Original java code by Dr Noureddin Sadawi
 */

public class MyAlgorithm {

    private String resultLevel;

    public MyAlgorithm() {
    }

    public String getResultLevel() {
        return resultLevel;
    }

    private static int[][] dataset = {

            // Level 2
            {103, 121, 24},
            {111, 166, 47},
            {101, 124, 21},
            {120, 159, 84},
            {139, 161, 96},

            //Level 3
            {76, 93, 28},
            {93, 135, 50},
            {70, 93, 25},
            {75, 121, 29},
            {83, 144, 30},

            //Level 4
            {54, 68, 30},
            {67, 108, 36},
            {74, 120, 28},
            {60, 116, 32},
            {58, 107, 34},

            //Level 5
            {32, 42, 23},
            {44, 85, 43},
            {55, 89, 38},
            {57, 96, 31},
            {54, 96, 28},
    };

    public void proses(int[] rgbFromImage){

        int k = 5;// # of neighbours
        //list to save level data
        List<CodeLevel> levelList = new ArrayList<>();
        //list to save distance result
        List<Result> resultList = new ArrayList<>();
        // add dataset data to levelList
        levelList.add(new CodeLevel(dataset[0],"Level 2"));
        levelList.add(new CodeLevel(dataset[1],"Level 2"));
        levelList.add(new CodeLevel(dataset[2],"Level 2"));
        levelList.add(new CodeLevel(dataset[3],"Level 2"));
        levelList.add(new CodeLevel(dataset[4],"Level 2"));

        levelList.add(new CodeLevel(dataset[5],"Level 3"));
        levelList.add(new CodeLevel(dataset[6],"Level 3"));
        levelList.add(new CodeLevel(dataset[7],"Level 3"));
        levelList.add(new CodeLevel(dataset[8],"Level 3"));
        levelList.add(new CodeLevel(dataset[9],"Level 3"));

        levelList.add(new CodeLevel(dataset[10],"Level 4"));
        levelList.add(new CodeLevel(dataset[11],"Level 4"));
        levelList.add(new CodeLevel(dataset[12],"Level 4"));
        levelList.add(new CodeLevel(dataset[13],"Level 4"));
        levelList.add(new CodeLevel(dataset[14],"Level 4"));

        levelList.add(new CodeLevel(dataset[15],"Level 5"));
        levelList.add(new CodeLevel(dataset[16],"Level 5"));
        levelList.add(new CodeLevel(dataset[17],"Level 5"));
        levelList.add(new CodeLevel(dataset[18],"Level 5"));
        levelList.add(new CodeLevel(dataset[19],"Level 5"));


        //find disnaces
        for(CodeLevel clevel : levelList){
            double dist = 0.0;
            for(int j = 0; j < clevel.levelAttributes.length; j++){
                dist += Math.pow(clevel.levelAttributes[j] - rgbFromImage[j], 2) ;
            }
            double distance = Math.sqrt( dist );
            resultList.add(new Result(distance,clevel.levelName));
            //System.out.println(distance);
        }


        Collections.sort(resultList, new DistanceComparator());
        String[] ss = new String[k];
        for(int x = 0; x < k; x++){
            //System.out.println(resultList.get(x).levelName+ " .... " + resultList.get(x).distance);
            //get classes of k nearest dataset (level names) from the list into an array
            ss[x] = resultList.get(x).levelName;
        }
        resultLevel = findMajorityClass(ss);
        //System.out.println("Class of new instance is: "+majClass);

    }


    //simple class to model dataset (features + class)
    private static class CodeLevel {
        int[] levelAttributes;
        String levelName;
        CodeLevel(int[] levelAttributes, String levelName){
            this.levelName = levelName;
            this.levelAttributes = levelAttributes;
        }
    }


    //simple class to model results (distance + class)
    static class Result {
        double distance;
        String levelName;
        Result(double distance, String levelName){
            this.levelName = levelName;
            this.distance = distance;
        }
    }


    //simple comparator class used to compare results via distances
    private static class DistanceComparator implements Comparator<Result> {
        @Override
        public int compare(Result a, Result b) {
            return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
        }
    }



    private static String findMajorityClass(String[] array)
    {
        //add the String array to a HashSet to get unique String values
        Set<String> h = new HashSet<>(Arrays.asList(array));
        //convert the HashSet back to array
        String[] uniqueValues = h.toArray(new String[0]);
        //counts for unique strings
        int[] counts = new int[uniqueValues.length];
        // loop thru unique strings and count how many times they appear in origianl array
        for (int i = 0; i < uniqueValues.length; i++) {
            for (String anArray : array) {
                if (anArray.equals(uniqueValues[i])) {
                    counts[i]++;
                }
            }
        }


        /*
        for (int i = 0; i < uniqueValues.length; i++)
            System.out.println(uniqueValues[i]);
        for (int i = 0; i < counts.length; i++)
            System.out.println(counts[i]);
        */

        int max = counts[0];
        for (int counter = 1; counter < counts.length; counter++) {
            if (counts[counter] > max) {
                max = counts[counter];
            }
        }
        //System.out.println("max # of occurences: "+max);

        //how many times max appears
        //we know that max will appear at least once in counts
        //so the value of freq will be 1 at minimum after this loop
        int freq = 0;
        for (int count : counts) {
            if (count == max) {
                freq++;
            }
        }

        //index of most freq value if we have only one mode
        int index = -1;
        if(freq==1){
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    index = counter;
                    break;
                }
            }
            //System.out.println("one majority class, index is: "+index);
            return uniqueValues[index];
        } else{//we have multiple modes
            int[] ix = new int[freq];//array of indices of modes
            //System.out.println("multiple majority classes: "+freq+" classes");
            int ixi = 0;
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    ix[ixi] = counter;//save index of each max count value
                    ixi++; // increase index of ix array
                }
            }

           // for (int counter = 0; counter < ix.length; counter++)
             //   System.out.println("class index: "+ix[counter]);

            //now choose one at random
            Random generator = new Random();
            //get random number 0 <= rIndex < size of ix
            int rIndex = generator.nextInt(ix.length);
            System.out.println("random index: "+rIndex);
            int nIndex = ix[rIndex];
            //return unique value at that index
            return uniqueValues[nIndex];
        }

    }


}
