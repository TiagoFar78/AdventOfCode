package year2016;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day20Part2 extends Challenge {
    
    private static final long MAX = 4294967295L;
    
    private long solve(List<long[]> blockedIps) {
        Collections.sort(blockedIps, (a,b) -> a[0] > b[0] ? 1 : a[0] - b[0] == 0 ? 0 : -1);
        long largerIpBlocked = 0;
        int allowedIps = 0;
        for (long[] blockedIp : blockedIps) {
            if (blockedIp[0] > largerIpBlocked + 1) {
                allowedIps += blockedIp[0] - largerIpBlocked - 1;
            }
            
            largerIpBlocked = Math.max(largerIpBlocked, blockedIp[1]);
        }
        
        return allowedIps + Math.max(MAX - largerIpBlocked, 0);
    }
    
    @Override
    public long solve() {
        List<long[]> blockedIps = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            long[] blockedIp = new long[2];
            blockedIp[0] = Long.parseLong(matcher.group(1));
            blockedIp[1] = Long.parseLong(matcher.group(2));
            blockedIps.add(blockedIp);
        }
        reader.close();
        
        return solve(blockedIps);
    }

}
