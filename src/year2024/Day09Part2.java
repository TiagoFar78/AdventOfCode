package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day09Part2 extends Challenge {
    
    private long solve(int[] diskMapArray) {
        List<Integer> diskMap = new ArrayList<>();
        List<Integer> diskIds = new ArrayList<>();
        for (int diskPart : diskMapArray) {
            diskMap.add(diskPart);
        }
        
        for (int i = 0; i < diskMap.size(); i += 2) {
            diskIds.add(i / 2);
        }
        
        int r = diskMap.size() - 1;
        while (r > 1) {
            int l = 1;
            while (l < r && diskMap.get(l) < diskMap.get(r)) {
                l += 2;
            }
            
            if (l >= r) {
                r -= 2;
                continue;
            }
            
            int amount = diskMap.get(r);
            diskMap.set(r - 1, diskMap.get(r - 1) + diskMap.get(r) + (r + 1 < diskMap.size() ? diskMap.get(r + 1) : 0));
            diskMap.remove(r);
            if (r < diskMap.size()) {
                diskMap.remove(r);
            }
            
            diskMap.set(l, diskMap.get(l) - amount);
            diskMap.add(l, amount);
            diskMap.add(l, 0);

            int id = diskIds.get(r / 2);
            diskIds.remove(r / 2);
            diskIds.add((l + 1) / 2, id);
        }
        
        long checkSum = 0;
        int index = diskMap.get(0);
        for (int i = 1; i < diskMap.size() - 1; i++) {
            index += diskMap.get(i);
            i++;
            checkSum += diskIds.get(i / 2) * sumSeq(index, diskMap.get(i));
            index += diskMap.get(i);
        }
        
        return checkSum;
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
