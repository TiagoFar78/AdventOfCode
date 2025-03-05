package year2015;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import main.Challenge;

public class Day04Part2 extends Challenge {
    
    private long solve(String secretKey) {
        int i = 0;
        while (!isValidHash(secretKey + i)) {
            i++;
        }
        
        return i;
    }
    
    private boolean isValidHash(String key) {
        String hash = buildHash(key);
        return hash.startsWith("000000");
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
        String secretKey = reader.nextLine();
        reader.close();
        
        return solve(secretKey);
    }

}
