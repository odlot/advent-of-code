package A03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Pattern p = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)");
    public static void main(String[] args) {
        String input = readInput("/home/lutz/GitHub/advent-of-code/2024/A03/input.txt");
        System.out.println(input);
        Matcher m = p.matcher(input);
        int total = 0;
        // At the beginning of the program, `mul` instructions are enabled.
        boolean performMultiplication = true;
        while (m.find()) {
            String group = m.group();
            System.out.println(group);
            if (group.contains("do()")) {
                performMultiplication = true;
            } else if (group.contains("don't()")) {
                performMultiplication = false;
            } else {
                if (performMultiplication) {
                    group = group.replace("mul(","");
                    group = group.replace(")", "");
                    System.out.println(group);
                    String[] operands = group.split(",");
                    int result = Integer.parseInt(operands[0]) * Integer.parseInt(operands[1]);
                    total += result;
                }
            }
        }
        System.out.printf("The result of adding up all multiplications is: %d\n", total);
    }

    static String readInput(String path) {
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
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
        return builder.toString();
    }
}
