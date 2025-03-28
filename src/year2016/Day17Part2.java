package year2016;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day17Part2 extends Challenge {
    
    private class Cell {
        
        public int x;
        public int y;
        public String path;
        
        public Cell(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
        
    }
    
    private static final int[][] DIRECTIONS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private static final char[] DIRECTION_LABEL = { 'U', 'D', 'L', 'R' };
    
    private int solve(String passcode, int targetX, int targetY) {
        Queue<Cell> pq = new PriorityQueue<>((a, b) -> a.path.length() - b.path.length());
        pq.add(new Cell(0, 0, ""));
        
        int maxLength = 0;
        while (!pq.isEmpty()) {
            Cell cell = pq.poll();
            if (cell.x == targetX && cell.y == targetY) {
                maxLength = Math.max(maxLength, cell.path.length());
                continue;
            }
            
            String hash = buildHash(passcode + cell.path);
            for (int i = 0; i < DIRECTIONS.length; i++) {
                int[] dir = DIRECTIONS[i];
                int newX = cell.x + dir[0];
                int newY = cell.y + dir[1];
                if (isOpen(hash.charAt(i)) && newX >= 0 && newX <= targetX && newY >= 0 && newY <= targetY) {
                    pq.add(new Cell(newX, newY, cell.path + DIRECTION_LABEL[i]));
                }
            }
        }
        
        return maxLength;
    }
    
    private boolean isOpen(char c) {
        return c >= 'b' && c <= 'f';
    }
    
    private String buildHash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
    
            byte[] digest = md.digest();
            BigInteger no = new BigInteger(1, digest);
            String hashText = no.toString(16);
    
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
    
            return hashText;
        
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    @Override
    public long solve() {
        Scanner reader = getInputFile();
        String passcode = reader.nextLine();
        reader.close();
        
        int targetX = 3;
        int targetY = 3;
        return solve(passcode, targetX, targetY);
    }

}
