package year2016;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import main.Challenge;

public class Day05Part2 extends Challenge {
    
    private String solve(String input) {
        char[] password = new char[8];
        for (int i = 0; i < 8; i++) {
            password[i] = 0;
        }
        
        int j = 0;
        while (!isPasswordCompleted(password)) {
            while (!isValidHash(input + j)) {
                j++;
            }
            String hash = buildHash(input + j);
            if (hash.charAt(5) >= '0' && hash.charAt(5) <= '7' && password[hash.charAt(5) - '0'] == 0) {
                password[hash.charAt(5) - '0'] = hash.charAt(6);
            }
            j++;
        }
        
        return String.valueOf(password);
    }
    
    private boolean isPasswordCompleted(char[] password) {
        for (char c : password) {
            if (c == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isValidHash(String key) {
        String hash = buildHash(key);
        return hash.startsWith("00000");
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
    public String solveString() {
        Scanner reader = getInputFile();
        String input = reader.nextLine();
        reader.close();
        
        return solve(input);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }
    
}
