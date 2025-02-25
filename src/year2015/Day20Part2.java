package year2015;

import java.util.Scanner;

import main.Challenge;

public class Day20Part2 extends Challenge {

    private int solve(int presents) {
        int maxHouseEstimative = presents * 2;
        int[] houses = new int[maxHouseEstimative + 1];
        presents /= 11;
        presents--;
        
        int minHouse = maxHouseEstimative;
        for (int elf = 2; elf < maxHouseEstimative / 50; elf++) {
            for (int j = 1; j <= 50; j++) {
                houses[j * elf] += elf;
                
                if (houses[j * elf] >= presents) {
                    minHouse = Math.min(minHouse, j * elf);
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
