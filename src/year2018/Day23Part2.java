
package year2018;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day23Part2 extends Challenge {

    static class Cube implements Comparable<Cube> {
        int x, y, z, size;
        int botsInRange;
        int distanceToOrigin;

        Cube(int x, int y, int z, int size, int botsInRange) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.size = size;
            this.botsInRange = botsInRange;
            this.distanceToOrigin = Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        @Override
        public int compareTo(Cube other) {
            if (this.botsInRange != other.botsInRange)
                return Integer.compare(other.botsInRange, this.botsInRange);
            
            if (this.distanceToOrigin != other.distanceToOrigin)
                return Integer.compare(this.distanceToOrigin, other.distanceToOrigin);
            
            return Integer.compare(this.size, other.size);
        }
    }

    static int botsInRange(List<int[]> nanobots, int x, int y, int z, int size) {
        int count = 0;
        for (int[] nanobot : nanobots) {
            int dx = Math.max(0, Math.abs(nanobot[0] - x) - size);
            int dy = Math.max(0, Math.abs(nanobot[1] - y) - size);
            int dz = Math.max(0, Math.abs(nanobot[2] - z) - size);
            if (dx + dy + dz <= nanobot[3]) {
                count++;
            }
        }
        return count;
    }
    
    private int solve(List<int[]> nanobots) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;

        for (int[] nanobot : nanobots) {
            minX = Math.min(minX, nanobot[0]);
            maxX = Math.max(maxX, nanobot[0]);
            minY = Math.min(minY, nanobot[1]);
            maxY = Math.max(maxY, nanobot[1]);
            minZ = Math.min(minZ, nanobot[2]);
            maxZ = Math.max(maxZ, nanobot[2]);
        }

        int size = 1;
        while (size < (maxX - minX) || size < (maxY - minY) || size < (maxZ - minZ)) {
            size *= 2;
        }

        PriorityQueue<Cube> queue = new PriorityQueue<>();
        int initialCount = botsInRange(nanobots, minX, minY, minZ, size);
        queue.add(new Cube(minX, minY, minZ, size, initialCount));

        while (!queue.isEmpty()) {
            Cube cube = queue.poll();

            if (cube.size == 0) {
                return cube.distanceToOrigin;
            }

            int newSize = cube.size / 2;
            for (int dx = 0; dx <= 1; dx++) {
                for (int dy = 0; dy <= 1; dy++) {
                    for (int dz = 0; dz <= 1; dz++) {
                        int nx = cube.x + dx * newSize;
                        int ny = cube.y + dy * newSize;
                        int nz = cube.z + dz * newSize;
                        int count = botsInRange(nanobots, nx, ny, nz, newSize);
                        queue.add(new Cube(nx, ny, nz, newSize, count));
                    }
                }
            }
        }

        throw new IllegalArgumentException("Shouldn't reach here");
    }
    
    @Override
    public long solve() {
        List<int[]> nanobots = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            int[] nanobot = new int[4];
            for (int i = 0; i < 4; i++) {
                nanobot[i] = Integer.parseInt(matcher.group(i + 1));
            }
            nanobots.add(nanobot);
        }
        reader.close();
        
        return solve(nanobots);
    }
    
}
