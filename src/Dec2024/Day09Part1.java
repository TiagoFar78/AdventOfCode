package Dec2024;

import java.util.Scanner;

import main.Challenge;

public class Day09Part1 extends Challenge {
    
    private long solve(int[] diskMap) {
        int n = diskMap.length;
        
        long checkSum = 0;
        int l = 0;
        int r = n - 1;
        int index = 0;
        
        while (l < r) {
            int id = l / 2;
            checkSum += id * sumSeq(index, diskMap[l]);
            index += diskMap[l];
            l++;
            
            while (l < r && diskMap[l] > diskMap[r]) {                
                id = r / 2;
                checkSum += id * sumSeq(index, diskMap[r]);
                diskMap[l] -= diskMap[r];
                index += diskMap[r];
                r -= 2;
            }
            
            if (l >= r) {
                break;
            }
            
            id = r / 2;
            checkSum += id * sumSeq(index, diskMap[l]);
            index += diskMap[l];
            if (diskMap[r] == diskMap[l]) {
                r -= 2;
            }
            else {
                diskMap[r] -= diskMap[l];
            }
            l++;
        }
        
        return checkSum + (l / 2) * sumSeq(index, diskMap[l]);
    }
    
    private long sumSeq(int s, int n) {
        return n * (s + s + n - 1) / 2;
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        reader.close();
        
        int[] diskMap = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
            diskMap[i] = line.charAt(i) - '0';
        }
        
        return solve(diskMap);
    }

}
