package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day12Part1 extends Challenge {
    
    private int solve(List<int[]> positions) {
        List<int[]> velocities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            velocities.add(new int[3]);
        }
        
        for (int steps = 0; steps < 1000; steps++) {
            updateVelocities(positions, velocities);
            updatePositions(positions, velocities);
        }
        
        int totalEnergy = 0;
        for (int i = 0; i < positions.size(); i++) {
            totalEnergy += sum(positions.get(i)) * sum(velocities.get(i));
        }
        
        return totalEnergy;
    }
    
    private void updateVelocities(List<int[]> positions, List<int[]> velocities) {
        for (int i = 0; i < velocities.size(); i++) {
            for (int j = 0; j < positions.size(); j++) {
                for (int k = 0; k < velocities.get(i).length; k++) {
                    velocities.get(i)[k] += gravityForce(positions.get(i)[k], positions.get(j)[k]);
                }
            }
        }
    }
    
    private int gravityForce(int pulled, int puller) {
        if (pulled < puller) {
            return 1;
        }
        
        if (pulled > puller) {
            return -1;
        }
        
        return 0;
    }
    
    private void updatePositions(List<int[]> positions, List<int[]> velocities) {
        for (int i = 0; i < positions.size(); i++) {
            for (int j = 0; j < positions.get(i).length; j++) {
                positions.get(i)[j] += velocities.get(i)[j];
            }
        }
    }
    
    private int sum(int[] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.abs(a[i]);
        }
        
        return sum;
    }

    @Override
    public long solve() {
        List<int[]> positions = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            int[] position = new int[3];
            for (int i = 0; i < 3; i++) {
                position[i] = Integer.parseInt(matcher.group(i + 1));
            }
            
            positions.add(position);
        }
        reader.close();
        
        return solve(positions);
    }

}
