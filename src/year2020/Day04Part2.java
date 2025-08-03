package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import main.Challenge;

public class Day04Part2 extends Challenge {
    
    private int solve(List<String> passports) {
        List<Map<String, String>> passportsFields = new ArrayList<>();
        Map<String, String> curr = new HashMap<>();
        for (String line : passports) {
            if (line.length() == 0) {
                passportsFields.add(curr);
                curr = new HashMap<>();
            }
            else {
                for (String field : line.split(" ")) {
                    String[] fields = field.split(":");
                    curr.put(fields[0], fields[1]);
                }
            }
        }
        passportsFields.add(curr);
        
        int valid = 0;
        for (Map<String, String> passport : passportsFields) {
            if (isValid(passport)) {
                valid++;
            }
        }
        
        return valid;
    }
    
    private boolean isValid(Map<String, String> passport) {
        return isValidByr(passport) && isValidIyr(passport) && isValidEyr(passport) && isValidHgt(passport) &&
                isValidHcl(passport) && isValidEcl(passport) && isValidPid(passport);
    }
    
    private boolean isValidByr(Map<String, String> passport) {
        String key = "byr";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        int value = Integer.parseInt(passport.get(key));
        return value >= 1920 && value <= 2002;
    }
    
    private boolean isValidIyr(Map<String, String> passport) {
        String key = "iyr";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        int value = Integer.parseInt(passport.get(key));
        return value >= 2010 && value <= 2020; 
    }
    
    private boolean isValidEyr(Map<String, String> passport) {
        String key = "eyr";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        int value = Integer.parseInt(passport.get(key));
        return value >= 2020 && value <= 2030; 
    }
    
    private boolean isValidHgt(Map<String, String> passport) {
        String key = "hgt";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        String value = passport.get(key);
        if (value.length() <= 2) {
            return false;
        }
        
        String unit = value.substring(value.length() - 2);
        int num = Integer.parseInt(value.substring(0, value.length() - 2));
        if (unit.equals("cm")) {
            return num >= 150 && num <= 193;
        }
        else if (unit.equals("in")) {
            return num >= 59 && num <= 76;
        }
        
        return false; 
    }
    
    private boolean isValidHcl(Map<String, String> passport) {
        String key = "hcl";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        return Pattern.matches("#[0-9a-f]{6}", passport.get(key)); 
    }
    
    private boolean isValidEcl(Map<String, String> passport) {
        String key = "ecl";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        String[] validColors = new String[] { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" };
        for (String validColor : validColors) {
            if (passport.get(key).equals(validColor)) {
                return true;
            }
        }
        
        return false; 
    }
    
    private boolean isValidPid(Map<String, String> passport) {
        String key = "pid";
        if (!passport.containsKey(key)) {
            return false;
        }
        
        return Pattern.matches("[0-9]{9}", passport.get(key)); 
    }

    @Override
    public long solve() {
        List<String> passports = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            passports.add(reader.nextLine());        
        }
        reader.close();
        
        return solve(passports);
    }

}
