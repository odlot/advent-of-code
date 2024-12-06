package A02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // list of reports, containing a list of levels
        ArrayList<ArrayList<Integer>> reports = new ArrayList<ArrayList<Integer>>();
        readInput(reports);
        /*
        reports.stream().forEach((report) -> {
            report.stream().forEach(System.out::println);
            System.out.println("---");
        });
        */
        analyzeReports(reports);
        analyzeReportsPart2(reports);
    }

    static void readInput(ArrayList<ArrayList<Integer>> reports) {
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader("/home/lutz/GitHub/advent-of-code/2024/A02/input.txt");
            bufferedReader = new BufferedReader(fileReader);
    
            String line = bufferedReader.readLine();
            while (line != null) {
                /* 
                String[] strings = line.split("\\s+");
                ArrayList<Integer> levels = new ArrayList<Integer>();
                for (int i = 0; i < strings.length; ++i) {
                    levels.add(Integer.parseInt(strings[i]));
                } 
                */
                // Integer::parseInt is a method reference, it is equivalent to
                // (String s) -> Integer.parseInt(s)
                ArrayList<Integer> levels = Arrays.stream(line.split("\\s+")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                reports.add(levels);
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

    static void analyzeReports(ArrayList<ArrayList<Integer>> reports) {
        int safeReports = 0;
        for (ArrayList<Integer> report : reports) {
            if (isReportOk(report)) {
                safeReports++;
            }
        }
        System.out.printf("The number of safe reports is: %d\n", safeReports);
    }

    static boolean isReportOk(ArrayList<Integer> report) {
        ArrayList<Integer> sortedLevels = new ArrayList<>(report);
        Collections.sort(sortedLevels);
        boolean allIncreasingOrDecreasing = (report.equals(sortedLevels));
        Collections.sort(sortedLevels, Collections.reverseOrder());
        allIncreasingOrDecreasing = allIncreasingOrDecreasing || (report.equals(sortedLevels));
        boolean differenceOk = true;
        for (int i = 0; i < report.size() - 1; ++i) {
            int difference = Math.abs(report.get(i) - report.get(i + 1));
            if (!(difference >= 1 && difference <= 3)) {
                differenceOk = false;
                break;
            }
        }
        return allIncreasingOrDecreasing && differenceOk;
    }

    static void analyzeReportsPart2(ArrayList<ArrayList<Integer>> reports) {
        int safeReports = 0;
        for (ArrayList<Integer> report : reports) {
            if (isReportOk(report)) {
                safeReports++;
            } else {
                for (int i = 0; i < report.size(); ++i) {
                    ArrayList<Integer> alternativeReport = new ArrayList<Integer>(report);
                    alternativeReport.remove(i);
                    if (isReportOk(alternativeReport)) {
                        safeReports++;
                        break;
                    }
                }
            }
        }
        System.out.printf("The number of safe reports is: %d\n", safeReports);
    }
}
