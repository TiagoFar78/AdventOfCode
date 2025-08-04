package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day05Part1 extends Challenge {
    
    private int solve(List<String> seats) {
        int max = 0;
        for (String seat : seats) {
            max = Math.max(max, seatId(seat));
        }
        
        return max;
    }
    
    private int seatId(String seat) {
        int row = binary(seat, 0, 7, 127, 'F');
        int col = binary(seat, 7, seat.length(), 7, 'L');
        return row * 8 + col;
    }
    
    private int binary(String seat, int s, int e, int max, char above) {
        int l = 0;
        int r = max;
        for (int i = s; i < e; i++) {
            int mid = (l + r) / 2;
            if (seat.charAt(i) != above) {
                l = mid;
            }
            else {
                r = mid;
            }
        }
        
        return r;
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
