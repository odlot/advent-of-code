package A01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Integer> leftList = new ArrayList<Integer>();
        List<Integer> rightList = new ArrayList<Integer>();
        fillLists(leftList, rightList);
        part1(leftList, rightList);
        part2(leftList, rightList);
    }

    static void fillLists(List<Integer> leftList, List<Integer> rightList) {
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader("/home/lutz/GitHub/advent-of-code/2024/A01/input.txt");
            bufferedReader = new BufferedReader(fileReader);
    
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] strings = line.split("\\s+");
                assert strings.length == 2;
                leftList.add(Integer.parseInt(strings[0]));
                rightList.add(Integer.parseInt(strings[1]));
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void part1(List<Integer> leftList, List<Integer> rightList) {
        Collections.sort(leftList);
        Collections.sort(rightList);
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); ++i) {
            int distance = Math.abs(leftList.get(i) - rightList.get(i));
            totalDistance += distance;
        }
        System.out.printf("The total distance is: %d\n", totalDistance);
    }

    static void part2(List<Integer> leftList, List<Integer> rightList) {
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int i = 0; i < rightList.size(); ++i) {
            int number = rightList.get(i);
            count.put(number, count.getOrDefault(number, 0) + 1);
        }
        int totalSimilarityScore = 0;
        for (int i = 0; i < leftList.size(); ++i) {
            int number = leftList.get(i);
            if (count.containsKey(number)) {
                totalSimilarityScore += (number * count.get(number));
            }
        }
        System.out.printf("The total similarity score is: %d\n", totalSimilarityScore);
    }
}