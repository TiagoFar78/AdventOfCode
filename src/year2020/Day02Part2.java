package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day02Part2 extends Challenge {
    
    private class PasswordPolicy {
        
        private int min;
        private int max;
        private char c;
        private String password;
        
        public PasswordPolicy(int min, int max, char c, String password) {
            this.min = min;
            this.max = max;
            this.c = c;
            this.password = password;
        }
        
    }
    
    private int solve(List<PasswordPolicy> passwords) {
        int valid = 0;
        for (PasswordPolicy password : passwords) {
            if (isValid(password)) {
                valid++;
            }
        }
        
        return valid;
    }
    
    private boolean isValid(PasswordPolicy password) {
        return (password.password.charAt(password.min - 1) == password.c) != (password.password.charAt(password.max - 1) == password.c);
    }

    @Override
    public long solve() {
        List<PasswordPolicy> passwords = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (.): (.*)");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            int min = Integer.parseInt(matcher.group(1));
            int max = Integer.parseInt(matcher.group(2));
            char c = matcher.group(3).charAt(0);
            passwords.add(new PasswordPolicy(min, max, c, matcher.group(4)));
        }
        reader.close();
        
        return solve(passwords);
    }

}
