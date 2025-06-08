package year2018;

import java.util.Scanner;

import main.Challenge;

public class Day11Part1 extends Challenge {
    
    private int[] solve(int serialNumber) {
        int[][] grid = new int[300][300];
        for (int x = 1; x <= 300; x++) {
            for (int y = 1; y <= 300; y++) {
                grid[x - 1][y - 1] = powerLevel(x, y, serialNumber);
            }
        }
        
        int power = 0;
        int[] maxCell = null;
        for (int x = 0; x < 298; x++) {
            for (int y = 0; y < 298; y++) {
                int totalPower = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        totalPower += grid[x + i][y + j];
                    }
                }
                
                if (totalPower > power) {
                    power = totalPower;
                    maxCell = new int[] { x + 1, y + 1 };
                }
            }
        }
        
        return maxCell;
    }
    
    private int powerLevel(int x, int y, int serialNumber) {
        int rackId = x + 10;
        int powerLevel = rackId * y;
        powerLevel += serialNumber;
        powerLevel *= rackId;
        powerLevel = (powerLevel / 100) % 10;
        powerLevel -= 5;
        
        return powerLevel;
    }
    
    @Override
    public String solveString() {
        Scanner reader = getInputFile();
        int serialNumber = Integer.parseInt(reader.nextLine());
        reader.close();
        
        int[] cell = solve(serialNumber);
        return cell[0] + "," + cell[1];
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
