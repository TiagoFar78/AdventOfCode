package year2019;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day22Part2 extends Challenge {
    
    private static final int DEAL_INTO_NEW_STACK = 0;
    private static final int CUT = 1;
    private static final int DEAL_WITH_INCREMENT = 2;
    
    // Obviously taken from the Internet, no way I could solve this by myself :skull:
    private String solve(List<int[]> instructions) {
        BigInteger runs = new BigInteger("101741582076661");
        BigInteger size = new BigInteger("119315717514047");
        BigInteger target = new BigInteger("2020");
        
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;
        
        // f(x) = ax + b (mod size), find a and b
        for (int i = instructions.size() - 1; i >= 0; i--) {
            int[] instruction = instructions.get(i);
            switch (instruction[0]) {
                case DEAL_INTO_NEW_STACK:
                    a = a.negate().mod(size);
                    b = b.negate().subtract(BigInteger.ONE).mod(size);
                    break;
                case CUT:
                    b = b.add(BigInteger.valueOf(instruction[1])).mod(size);
                    break;
                case DEAL_WITH_INCREMENT:
                    BigInteger z = BigInteger.valueOf(instruction[1]);
                    BigInteger inv = z.modInverse(size);
                    a = a.multiply(inv).mod(size);
                    b = b.multiply(inv).mod(size);
                    break;
            }
        }
        
        // Now apply the formula: f^n(x) = a^n * x + b * (1 - a^n) / (1 - a) mod size
        BigInteger an = a.modPow(runs, size);
        BigInteger oneMinusA = BigInteger.ONE.subtract(a).mod(size);
        BigInteger invOneMinusA = oneMinusA.modInverse(size);
        BigInteger geom = b.multiply(BigInteger.ONE.subtract(an)).multiply(invOneMinusA).mod(size);

        BigInteger result = an.multiply(target).add(geom).mod(size);
        return result.toString();
    }

    @Override
    public String solveString() {
        System.out.println("a");
        String[] availableInstructions = { "deal into new stack", "cut", "deal with increment" };
        List<int[]> instructions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String instruction = reader.nextLine();
            if (instruction.startsWith(availableInstructions[0])) {
                instructions.add(new int[] { DEAL_INTO_NEW_STACK });
            }
            else if (instruction.startsWith(availableInstructions[1])) {
                instructions.add(new int[] { CUT, Integer.parseInt(instruction.substring(availableInstructions[1].length() + 1)) });
            }
            else if (instruction.startsWith(availableInstructions[2])) {
                instructions.add(new int[] { DEAL_WITH_INCREMENT, Integer.parseInt(instruction.substring(availableInstructions[2].length() + 1)) });
            }
            
        }
        reader.close();
        
        return solve(instructions);
    }
    
    @Override
    public long solve() {
        // Empty
        return 0;
    }

}
