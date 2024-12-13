package A05;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, ArrayList<Integer>> pageOrderingRules = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> updates = new ArrayList<>();
        readInput("/home/lutz/GitHub/advent-of-code/2024/A05/input.txt", pageOrderingRules, updates);
        for (Integer key : pageOrderingRules.keySet()) {
            System.out.printf("%d: ", key);
            pageOrderingRules.get(key).stream().forEach(value -> {
                System.out.printf("%d, ", value);
            });
            System.out.println("");
        }
        whichUpdatesAreInTheRightOrder(pageOrderingRules, updates);
        return;
    }

    public static void whichUpdatesAreInTheRightOrder(HashMap<Integer, ArrayList<Integer>> pageOrderingRules, ArrayList<ArrayList<Integer>> updates) {
        ArrayList<Integer> validUpdateIndexes = new ArrayList<Integer>();
        ArrayList<Integer> invalidUpdateIndexes = new ArrayList<Integer>();
        for (int i = 0; i < updates.size(); ++i) {
            ArrayList<Integer> update = updates.get(i);
            Set<Integer> previousPages = new HashSet<Integer>();
            boolean ruleViolation = false;
            for (int page : update) {
                ArrayList<Integer> pageOrderingRule = pageOrderingRules.get(page);
                if (pageOrderingRule != null && !pageOrderingRule.isEmpty()) {
                    for (int pageBefore : pageOrderingRule) {
                        if (previousPages.contains(pageBefore)) {
                            ruleViolation = true;
                            break;
                        }
                    }
                    if (ruleViolation) {
                        System.out.printf("Update %d violates the ordering rules.\n", i);
                        break;
                    }
                }
                previousPages.add(page);
            }
            if (ruleViolation) {
                invalidUpdateIndexes.add(i);
            } else {
                validUpdateIndexes.add(i);
            }
        }
        int middlePageNumberSum = 0;
        for (int i = 0; i < validUpdateIndexes.size(); ++i) {
            System.out.printf("Processing update %d...\n", i);
            ArrayList<Integer> validUpdate = updates.get(validUpdateIndexes.get(i));
            int n = validUpdate.size();
            int middle = (n % 2 == 0) ? (n / 2) : (n  - 1) / 2;
            middlePageNumberSum += validUpdate.get(middle);
        }
        // Part 2
        int middlePageNumberSumOfIncorectlyOrderedUpdates = 0;
        for (int i = 0; i < invalidUpdateIndexes.size(); ++i) {
            System.out.printf("Processing invalid update %d...\n", i);
            ArrayList<Integer> invalidUpdate = updates.get(invalidUpdateIndexes.get(i));
            ArrayList<Integer> validUpdate = makeValid(pageOrderingRules, invalidUpdate);
            int n = validUpdate.size();
            int middle = (n % 2 == 0) ? (n / 2) : (n  - 1) / 2;
            middlePageNumberSumOfIncorectlyOrderedUpdates += validUpdate.get(middle);
        }
        System.out.printf("The sum of the middle page numbers of valid updates is: %d\n", middlePageNumberSum);
        System.out.printf("The sum of the middle page numbers of invalid updates is: %d\n", middlePageNumberSumOfIncorectlyOrderedUpdates);
    }

    public static ArrayList<Integer> makeValid(HashMap<Integer, ArrayList<Integer>> pageOrderingRules, ArrayList<Integer> invalidUpdate) {
        ArrayList<Integer> validUpdate = new ArrayList<Integer>();
        for (int i = 0; i < invalidUpdate.size(); ++i) {
            int page = invalidUpdate.get(i);
            int index = 0;
            for (int j = 0; j < validUpdate.size(); ++j) {
                int currentPage = validUpdate.get(j);
                ArrayList<Integer> currentPageOrderingRule = pageOrderingRules.get(currentPage);
                if (currentPageOrderingRule != null && currentPageOrderingRule.contains(page)) {
                    index = j + 1;
                } else {
                    ArrayList<Integer> pageOrderingRule = pageOrderingRules.get(page);
                    if (pageOrderingRule != null && pageOrderingRule.contains(currentPage)) {
                        index = j;
                        break;
                    }
                }
            }
            validUpdate.add(index, page);
        }
        return validUpdate;
    }

    public static void readInput(String path, HashMap<Integer, ArrayList<Integer>> pageOrderingRules, ArrayList<ArrayList<Integer>> updates) {
        BufferedReader bufferedReader = null;
        boolean firstSection = true;
        try {
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (line.isEmpty()) {
                    firstSection = false;
                    continue;
                }
                if (firstSection) {
                    String[] parts = line.split("\\|");
                    Integer key = Integer.parseInt(parts[0]);
                    Integer value = Integer.parseInt(parts[1]);
                    if (pageOrderingRules.containsKey(key)) {
                        pageOrderingRules.get(key).add(value);
                    } else {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(value);
                        pageOrderingRules.put(key, list);
                    }
                } else {
                    ArrayList<Integer> update = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                    updates.add(update);
                }
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
}
