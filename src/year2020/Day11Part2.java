package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day11Part2 extends Challenge {
    
    private final static int FLOOR = 0;
    private final static int EMPTY = 1;
    private final static int OCCUPIED = 2;
    private final static int[][] DIRS = {
            { -1, 0 },
            { -1, 1 },
            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, -1 },
            { 0, -1 },
            { -1, -1 }
    };
    
    private int solve(List<String> seatsString) {
        int[][] seats = toArray(seatsString);
        while (true) {
            int[][] newSeats = updateSeats(seats);
            if (!isChanged(seats, newSeats)) {
                break;
            }
            
            seats = newSeats;
        }
        
        return countOccupied(seats);
    }
    
    private int[][] toArray(List<String> seatsString) {
        int n = seatsString.size();
        int m = seatsString.get(0).length();
        int[][] seats = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = seatsString.get(i).charAt(j);
                seats[i][j] = c == '.' ? FLOOR : c == 'L' ? EMPTY : OCCUPIED;
            }
        }
        
        return seats;
    }
    
    private int[][] updateSeats(int[][] seats) {
        int n = seats.length;
        int m = seats[0].length;
        int[][] newSeats = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int adjacentOccupied = adjacentOccupied(seats, i, j, n, m);
                if (seats[i][j] == EMPTY && adjacentOccupied == 0) {
                    newSeats[i][j] = OCCUPIED;
                }
                else if (seats[i][j] == OCCUPIED && adjacentOccupied >= 5) {
                    newSeats[i][j] = EMPTY;
                }
                else {
                    newSeats[i][j] = seats[i][j];
                }
            }
        }
        
        return newSeats;
    }
    
    private int adjacentOccupied(int[][] seats, int i, int j, int n, int m) {
        int adjacentOccupied = 0;
        for (int[] dir : DIRS) {
            int row = i + dir[0];
            int col = j + dir[1];
            while (row >= 0 && row < n && col >= 0 && col < m) {
                if (seats[row][col] != FLOOR) {
                    if (seats[row][col] == OCCUPIED) {
                        adjacentOccupied++;
                    }
                    
                    break;
                }
                
                row += dir[0];
                col += dir[1];
            }
        }
        
        return adjacentOccupied;
    }
    
    private boolean isChanged(int[][] s1, int[][] s2) {
        int n = s1.length;
        int m = s1[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s1[i][j] != s2[i][j]) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private int countOccupied(int[][] seats) {
        int n = seats.length;
        int m = seats[0].length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (seats[i][j] == OCCUPIED) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    @Override
    public long solve() {
        List<String> seats = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            seats.add(reader.nextLine());
        }
        reader.close();
        
        return solve(seats);
    }

}
