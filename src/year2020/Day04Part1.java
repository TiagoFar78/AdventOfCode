package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.Challenge;

public class Day04Part1 extends Challenge {
    
    private int solve(List<String> passports) {
        List<Set<String>> passportsFields = new ArrayList<>();
        Set<String> curr = new HashSet<>();
        for (String line : passports) {
            if (line.length() == 0) {
                passportsFields.add(curr);
                curr = new HashSet<>();
            }
            else {
                for (String field : line.split(" ")) {
                    curr.add(field.split(":")[0]);
                }
            }
        }
        passportsFields.add(curr);
        
        int valid = 0;
        for (Set<String> passport : passportsFields) {
            if (isValid(passport)) {
                valid++;
            }
        }
        
        return valid;
    }
    
    private boolean isValid(Set<String> passport) {
        String[] requiredFields = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };
        for (String requiredField : requiredFields) {
            if (!passport.contains(requiredField)) {
                return false;
            }
        }
        
        return true;
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
