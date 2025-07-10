package year2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day12Part2 extends Challenge {
    
    private long solve(List<int[]> positions) {
        long lcm = 1;
        for (int i = 0; i < 3; i++) {
            lcm = lcm(lcm, axisCycle(new ArrayList<>(positions), i));
        }
        
        return lcm;
    }
    
    private long lcm(long n1, long n2) {
        return (n1 / gdc(n1, n2)) * n2;
    }
    
    private long gdc(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gdc(n2, n1 % n2);
    }
    
    private long axisCycle(List<int[]> positions, int axis) {
        int[] pos = new int[positions.size()];
        int[] vel = new int[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            pos[i] = positions.get(i)[axis];
        }

        int[] initialPos = pos.clone();
        int[] initialVel = vel.clone();

        long steps = 0;
        while (true) {
            steps++;

            updateVelocities(pos, vel);
            updatePositions(pos, vel);

            if (matches(initialPos, pos) && matches(initialVel, vel)) {
                return steps;
            }
        }
    }
    
    private void updateVelocities(int[] positions, int[] velocities) {
        for (int i = 0; i < velocities.length; i++) {
            for (int j = 0; j < positions.length; j++) {
                velocities[i] += gravityForce(positions[i], positions[j]);
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
    
    private void updatePositions(int[] positions, int[] velocities) {
        for (int i = 0; i < positions.length; i++) {
            positions[i] += velocities[i];
        }
    }
    
    private boolean matches(int[] a1, int[] a2) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        
        return true;
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
