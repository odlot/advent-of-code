package A04;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Idea: represent input as 2d array, each line is a row
        // Do 4 passes: horizontal, vertical, diagonal, diagonal reverse
        ArrayList<String> puzzle = readInput("/home/lutz/GitHub/advent-of-code/2024/A04/input.txt");
        System.out.println("Part 1:");
        int horizontalCount = searchHorizontal(puzzle);
        int verticalCount = searchVertical(puzzle);
        int diagonalCount = searchDiagonal(puzzle);
        System.out.printf("The number of horizontal XMAS occurrences is: %d\n", horizontalCount);
        System.out.printf("The number of vertical XMAS occurrences is: %d\n", verticalCount);
        System.out.printf("The number of diagonal XMAS occurrences is: %d\n", diagonalCount);
        System.out.printf("The total number of XMAS occurrences is: %d\n", horizontalCount + verticalCount + diagonalCount);
        System.out.println("Part 2:");
        int XMASCount = searchXMAS(puzzle);
        System.out.printf("The number of diagonal XMAS occurrences is: %d\n", XMASCount);
        return;
    }

    public static int searchXMAS(ArrayList<String> puzzle) {
        int XMASCount = 0;
        String find = "MAS";
        String findReverse = "SAM";
        for (int row = 0; row < puzzle.size() - 2; ++row) {
            String row1 = puzzle.get(row);
            String row2 = puzzle.get(row + 1);
            String row3 = puzzle.get(row + 2);
            for (int column = 0; column < row1.length() - 2; ++column) {
                String forwardDiagonal = String.valueOf(row1.charAt(column)) + String.valueOf(row2.charAt(column + 1)) + String.valueOf(row3.charAt(column + 2));
                String backwardDiagonal = String.valueOf(row1.charAt(column + 2)) + String.valueOf(row2.charAt(column + 1)) + String.valueOf(row3.charAt(column));
                if ((forwardDiagonal.equals(find) || forwardDiagonal.equals(findReverse)) && (backwardDiagonal.equals(find) || backwardDiagonal.equals(findReverse))) {
                    XMASCount++;
                }
            }
        }
        return XMASCount;
    }

    public static int searchHorizontal(ArrayList<String> puzzle) {
        int horizontalCount = 0;
        String find = "XMAS";
        String findReverse = "SAMX";
        for (String row : puzzle) {
            for (int i = 0; i < row.length() - 3; ++i) {
                String currentWindow = row.substring(i, i + 4);
                if (currentWindow.equals(find) || currentWindow.equals(findReverse)) {
                    horizontalCount++;
                }
            }
        }
        return horizontalCount;
    }

    public static int searchVertical(ArrayList<String> puzzle) {
        int verticalCount = 0;
        String find = "XMAS";
        String findReverse = "SAMX";
        for (int row = 0; row < puzzle.size() - 3; ++row) {
            String row1 = puzzle.get(row);
            String row2 = puzzle.get(row + 1);
            String row3 = puzzle.get(row + 2);
            String row4 = puzzle.get(row + 3);
            for (int column = 0; column < row1.length(); ++column) {
                String currentWindow = String.valueOf(row1.charAt(column)) + String.valueOf(row2.charAt(column)) + String.valueOf(row3.charAt(column)) + String.valueOf(row4.charAt(column));
                if (currentWindow.equals(find) || currentWindow.equals(findReverse)) {
                    verticalCount++;
                }
            }
        }
        return verticalCount;
    }

    public static int searchDiagonal(ArrayList<String> puzzle) {
        int diagonalCount = 0;
        String find = "XMAS";
        String findReverse = "SAMX";
        // forward diagonal
        int forwardDiagonal = 0;
        for (int row = 0; row < puzzle.size() - 3; ++row) {
            String row1 = puzzle.get(row);
            String row2 = puzzle.get(row + 1);
            String row3 = puzzle.get(row + 2);
            String row4 = puzzle.get(row + 3);
            for (int column = 0; column < row1.length() - 3; ++column) {
                String currentWindow = String.valueOf(row1.charAt(column)) + String.valueOf(row2.charAt(column + 1)) + String.valueOf(row3.charAt(column + 2)) + String.valueOf(row4.charAt(column + 3));
                if (currentWindow.equals(find) || currentWindow.equals(findReverse)) {
                    forwardDiagonal++;
                }
            }
        }
        // backward diagonal
        int backwardDiagonal = 0;
        for (int row = 0; row < puzzle.size() - 3; ++row) {
            String row1 = puzzle.get(row);
            String row2 = puzzle.get(row + 1);
            String row3 = puzzle.get(row + 2);
            String row4 = puzzle.get(row + 3);
            for (int column = row1.length() - 1; column > 2; --column) {
                String currentWindow = String.valueOf(row1.charAt(column)) + String.valueOf(row2.charAt(column - 1)) + String.valueOf(row3.charAt(column - 2)) + String.valueOf(row4.charAt(column - 3));
                if (currentWindow.equals(find) || currentWindow.equals(findReverse)) {
                    backwardDiagonal++;
                }
            }
        }
        return forwardDiagonal + backwardDiagonal;
    }

    public static ArrayList<String> readInput(String path) {
        ArrayList<String> result = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                result.add(line);
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
        return result;
    }
}
