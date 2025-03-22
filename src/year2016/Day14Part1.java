package year2016;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day14Part1 extends Challenge {
    
    private final static int MAX_DISTANCE = 1000;
    
    private int solve(String salt, int keysTarget) {
        List<Integer> keys = new ArrayList<>();
        Map<Integer, List<Integer>> keysIndex = new HashMap<>();
        int i = 0;
        while (true) {
            String hash = buildHash(salt + i);
            Set<Integer> quintuplets = getQuintuplets(hash);
            List<Integer> indexes = new ArrayList<>();
            for (int quintuplet : quintuplets) {
                for (int index : keysIndex.getOrDefault(quintuplet, new ArrayList<>())) {
                    if (i <= index + MAX_DISTANCE) {
                        indexes.add(index);
                    }
                }
            }
            
            Collections.sort(indexes);
            for (int index : indexes) {
                keys.add(index);
                Collections.sort(keys);
            }
            
            if (keys.size() >= keysTarget && keys.get(keysTarget - 1) < i - MAX_DISTANCE) {
                return keys.get(keysTarget - 1);
            }
            
            char triplet = getFirstTriple(hash);
            if (triplet != 'z') {
                if (!keysIndex.containsKey((int) triplet)) {
                    keysIndex.put((int) triplet, new ArrayList<>());
                }
                
                keysIndex.get((int) triplet).add(i);
            }
            
            i++;
        }
    }
    
    private Character getFirstTriple(String s) {
        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i - 2) == s.charAt(i) && s.charAt(i - 1) == s.charAt(i)) {
                return s.charAt(i);
            }
        }
        
        return 'z';
    }
    
    private Set<Integer> getQuintuplets(String s) {
        Set<Integer> quintuplets = new HashSet<>();
        
        for (int i = 4; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.charAt(i - 4) == c && s.charAt(i - 3) == c &&  s.charAt(i - 2) == c && s.charAt(i - 1) == c) {
                quintuplets.add((int) c);
            }
        }
        
        return quintuplets;
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
        String salt = reader.nextLine();
        reader.close();
        
        int keysTarget = 64;
        return solve(salt, keysTarget);
    }

}
