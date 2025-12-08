package year2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day08Part2 extends Challenge {
    
    private long solve(List<int[]> positions) {
        Queue<int[][]> pairs = new PriorityQueue<>((a, b) -> compare(distance(a[0], a[1]), distance(b[0], b[1])));
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                pairs.add(new int[][] { positions.get(i), positions.get(j) });
            }
        }
        
        List<Set<String>> circuits = new ArrayList<>();
        int maxSize = 0;
        long lastXMult = 0;
        while (maxSize < positions.size()) {
            int[][] pair = pairs.poll();
            String position1 = pair[0][0] + " " + pair[0][1] + " " + pair[0][2];
            String position2 = pair[1][0] + " " + pair[1][1] + " " + pair[1][2];
            
            int pIndex1 = indexOf(circuits, position1);
            int pIndex2 = indexOf(circuits, position2);
            if (pIndex1 == pIndex2) {
                continue;
            }
            
            Set<String> circuit1 = circuits.get(pIndex1);
            Set<String> circuit2 = circuits.remove(pIndex2);
            circuit1.addAll(circuit2);
            maxSize = Math.max(maxSize, circuit1.size());
            lastXMult = (long) pair[0][0] * pair[1][0];
        }
        
        return lastXMult;
    }
    
    private int compare(long a, long b) {
        return a > b ? 1 : a == b ? 0 : -1;
    }
    
    private int indexOf(List<Set<String>> circuits, String position) {
        int index = -1;
        for (int i = 0; i < circuits.size(); i++) {
            if (circuits.get(i).contains(position)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            index = circuits.size();
            circuits.add(new HashSet<>());
            circuits.get(index).add(position);
        }
        
        return index;
    }
    
    private long distance(int[] p1, int[] p2) {
        return sqr(p1[0] - p2[0]) + sqr(p1[1] - p2[1]) + sqr(p1[2] - p2[2]);
    }
    
    private long sqr(long num) {
        return num * num;
    }
    
    @Override
    public long solve() {
        List<int[]> positions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {            
            positions.add(toInt(reader.nextLine().split(",")));
        }
        reader.close();
        
        return solve(positions);
    }
    
    private int[] toInt(String[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = Integer.parseInt(arr[i]);
        }
        
        return ans;
    }

}
