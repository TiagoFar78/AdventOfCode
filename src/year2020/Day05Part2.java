package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private int solve(List<String> seats) {
        Set<Integer> seatIds = new HashSet<>();
        for (String seat : seats) {
            seatIds.add(seatId(seat));
        }
        
        List<Integer> empty = new ArrayList<>();
        for (int i = 1; i < 127 * 8 + 7 - 1; i++) {
            if (seatIds.contains(i - 1) && !seatIds.contains(i) && seatIds.contains(i + 1)) {
                return i;
            }
        }
        
        System.out.println(empty);
        
        throw new IllegalAccessError("None missing");
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
