package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day20Part1 extends Challenge {

    private int solve(int presents) {
        int maxHouseEstimative = 1200000;
        int[] houses = new int[maxHouseEstimative];
        presents /= 10;
        presents--;
        
        int minHouse = maxHouseEstimative;
        for (int elf = 2; elf < maxHouseEstimative; elf++) {
            for (int j = elf; j < maxHouseEstimative; j += elf) {
                houses[j] += elf;
                
                if (houses[j] >= presents) {
                    minHouse = Math.min(minHouse, j);
                }
            }
        }
        
        return minHouse;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        int presents = Integer.parseInt(reader.nextLine());
        reader.close();
        
        return solve(presents);
    }

}
