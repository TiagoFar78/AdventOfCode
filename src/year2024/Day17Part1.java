package year2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day17Part1 extends Challenge {
    
    private int solve(int registerA, int registerB, int registerC, List<Integer> program) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < program.size(); i += 2) {
            int operand = program.get(i + 1);
            switch (program.get(i)) {
                case 0:
                    registerA = registerA / (int) Math.pow(2, getCombo(operand, registerA, registerB, registerC));
                    break;
                case 1:
                    registerB = registerB ^ operand;
                    break;
                case 2:
                    registerB = getCombo(operand, registerA, registerB, registerC) % 8;
                    break;
                case 3:
                    if (registerA != 0) {
                        i = operand - 2;
                    }
                    break;
                case 4:
                    registerB = registerB ^ registerC;
                    break;
                case 5:
                    output.append((getCombo(operand, registerA, registerB, registerC) % 8) + ",");
                    break;
                case 6:
                    registerB = registerA / (int) Math.pow(2, getCombo(operand, registerA, registerB, registerC));
                    break;
                case 7:
                    registerC = registerA / (int) Math.pow(2, getCombo(operand, registerA, registerB, registerC));
                    break;
            }
        }
        
        System.out.println(output.toString());        
        return 0;
    }
    
    private int getCombo(int operand, int... registers) {
        if (0 <= operand && operand <= 3) {
            return operand;
        }
        
        return registers[operand - 4];
    }
    
    @Override
    public long solve() {
        int registerA = 0;
        int registerB = 0;
        int registerC = 0;
        List<Integer> program = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("-?\\d+");
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        registerA = Integer.parseInt(matcher.group());
        matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        registerB = Integer.parseInt(matcher.group());
        matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        registerC = Integer.parseInt(matcher.group());
        reader.nextLine();
        matcher = pattern.matcher(reader.nextLine());
        while (matcher.find()) {
            program.add(Integer.parseInt(matcher.group()));
        }
        reader.close();
        
        return solve(registerA, registerB, registerC, program);
    }

}
