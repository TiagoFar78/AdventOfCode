package year2021;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day04Part2 extends Challenge {
    
    private int solve(List<Integer> nums, List<Board> boards) {
        Set<Integer> winners = new HashSet<>();
        int winnerValue = -1;
        int unselectedSum = 0;
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < boards.size(); j++) {
                Board board = boards.get(j);
                if (board.update(nums.get(i))) {
                    if (!winners.contains(j)) {
                        unselectedSum = board.unselectedSum();
                        winnerValue = nums.get(i);
                    }
                    winners.add(j);
                }
            }
        }
        
        return unselectedSum * winnerValue;
    }

    @Override
    public long solve() {
        List<Integer> nums = new ArrayList<>();
        
        Scanner reader = getInputFile();
        String[] numsString = reader.nextLine().split(",");
        for (String numString : numsString) {
            nums.add(Integer.parseInt(numString));
        }
        
        List<Board> boards = new ArrayList<>();
        while (reader.hasNextLine()) {
            reader.nextLine();
            int[][] content = new int[5][5];
            for (int i = 0; i < 5; i++) {
                content[i] = toIntArr(reader.nextLine().trim().split("\\s+"));
            }
            boards.add(new Board(content));
        }
        reader.close();
        
        return solve(nums, boards);
    }
    
    private int[] toIntArr(String[] sArr) {
        int[] iArr = new int[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            iArr[i] = Integer.parseInt(sArr[i]);
        }
        
        return iArr;
    }
    
    private class Board {
        
        private static final int SIZE = 5;
        
        private int[][] content;
        private boolean[][] seen = new boolean[SIZE][SIZE];
        
        public Board(int[][] content) {
            this.content = content;
        }
        
        public boolean update(int value) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (content[i][j] == value) {
                        seen[i][j] = true;
                        return isRowCompleted(i) || isColCompleted(j);
                    }
                }
            }
            
            return false;
        }
        
        private boolean isRowCompleted(int r) {
            for (int i = 0; i < SIZE; i++) {
                if (!seen[r][i]) {
                    return false;
                }
            }
            
            return true;
        }
        
        private boolean isColCompleted(int c) {
            for (int i = 0; i < SIZE; i++) {
                if (!seen[i][c]) {
                    return false;
                }
            }
            
            return true;
        }
        
        public int unselectedSum() {
            int sum = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!seen[i][j]) {
                        sum += content[i][j];
                    }
                }
            }
            
            return sum;
        }
    }

}
