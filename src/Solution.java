import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;


public class Solution {
    int[][] octopuses;

    public Solution(String filename) { this.octopuses = parse(filename); }

    private int[][] parse(String filename) {
        int[][] octopuses = new int[10][10];

        try {
            File f            = new File(filename);
            FileReader fr     = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                int j = 0;

                for (char c : line.toCharArray()) {
                    octopuses[i][j] = c - '0';
                    j++;
                }

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return octopuses;
    }

    private boolean inBounds(int i, int j) {
        return ((i < 10) && (i >= 0) && (j < 10) && (j >= 0));
    }

    private void energizeNeighbors(int i, int j, int[][] octopuses, int[][] visited) {
        if (inBounds(i, j)) {
            octopuses[i][j]++;

            if ((octopuses[i][j] > 9) && (visited[i][j] != 1)) {
                visited[i][j] = 1;

                energizeNeighbors(i+1, j-1, octopuses, visited);
                energizeNeighbors(i+1, j, octopuses, visited);
                energizeNeighbors(i+1, j+1, octopuses, visited);
                energizeNeighbors(i-1, j-1, octopuses, visited);
                energizeNeighbors(i-1, j, octopuses, visited);
                energizeNeighbors(i-1, j+1, octopuses, visited);
                energizeNeighbors(i, j-1, octopuses, visited);
                energizeNeighbors(i, j+1, octopuses, visited);
            }
        }
    }

    private int solveStep(int[][] octo) {
        int flashes = 0;

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                octo[i][j]++;
            }
        }

        int[][] visited = new int[10][10];
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (octo[i][j] > 9) {
                    energizeNeighbors(i, j, octo, visited);
                }
            }
        }

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (octo[i][j] > 9) {
                    octo[i][j] = 0;
                    flashes++;
                }
            }
        }

        return flashes;
    }

    private int[][] cloneOctopuses() {
        int [][] octopuses = new int[10][10];

        for (int i=0; i < 10; ++i) {
            System.arraycopy(this.octopuses[i], 0, octopuses[i], 0, 10);
        }

        return octopuses;
    }

    public void solveP1() {
        int totalFlashes  = 0;
        int[][] octopuses = cloneOctopuses();

        for (int i=1; i <= 100; ++i) {
            totalFlashes += solveStep(octopuses);
        }

        System.out.println("Total flashes: " + totalFlashes);
    }

    public void solveP2() {
        int[][] octopuses = cloneOctopuses();
        int totalSteps    = 0;

        while (solveStep(octopuses) != 100) {
            totalSteps++;
        }

        System.out.println("Synchronized at step: " + (totalSteps + 1));
    }
}