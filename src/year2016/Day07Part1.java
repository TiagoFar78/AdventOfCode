package year2016;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day07Part1 extends Challenge {
    
    private int solve(List<String> ips) {
        int supportTLS = 0;
        
        for (String ip : ips) {
            supportTLS += supportTLS(ip) ? 1 : 0;
        }
                
        return supportTLS;
    }
    
    private boolean supportTLS(String ip) {
        boolean isInsideBrackets = false;
        boolean hasABBA = false;
        for (int i = 0; i < ip.length() - 3; i++) {
            if (ip.charAt(i) == '[') {
                isInsideBrackets = true;
            }
            else if (ip.charAt(i) == ']') {
                isInsideBrackets = false;
            }
            else if (ip.charAt(i) == ip.charAt(i + 3) && ip.charAt(i + 1) == ip.charAt(i + 2) && ip.charAt(i) != ip.charAt(i + 1)) {
                if (isInsideBrackets) {
                    return false;
                }
                
                hasABBA = true;
            }
        }
        
        return hasABBA;
    }
    
    @Override
    public long solve() {
        List<String> ips = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            ips.add(reader.nextLine());
        }
        reader.close();
        
        return solve(ips);
    }
    
}
