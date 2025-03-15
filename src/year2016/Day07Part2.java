package year2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private int solve(List<String> ips) {
        int supportTLS = 0;
        
        for (String ip : ips) {
            supportTLS += supportSSL(ip) ? 1 : 0;
        }
                
        return supportTLS;
    }
    
    private boolean supportSSL(String ip) {
        Set<String> abaSeen = new HashSet<>();
        Set<String> babSeen = new HashSet<>();
        boolean isInsideBrackets = false;
        
        for (int i = 0; i < ip.length() - 2; i++) {
            if (ip.charAt(i) == '[') {
                isInsideBrackets = true;
            }
            else if (ip.charAt(i) == ']') {
                isInsideBrackets = false;
            }
            else if (ip.charAt(i) == ip.charAt(i + 2) && ip.charAt(i) != ip.charAt(i + 1)) {
                String aba = isInsideBrackets ? ip.substring(i, i + 3) : "" + (char)ip.charAt(i + 1) + (char)ip.charAt(i) + (char)ip.charAt(i + 1);
                if (isInsideBrackets && abaSeen.contains(aba)) {
                    return true;
                }
                
                if (!isInsideBrackets && babSeen.contains(aba)) {
                    return true;
                }
                
                Set<String> seen = !isInsideBrackets ? abaSeen : babSeen;
                seen.add(aba);
            }
        }
        
        return false;
    }
    
    @Override
    public long solve() {
        System.out.println("a");
        List<String> ips = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            ips.add(reader.nextLine());
        }
        reader.close();
        
        return solve(ips);
    }
    
}
