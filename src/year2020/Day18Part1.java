package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.Challenge;

public class Day18Part1 extends Challenge {
    
    private long solve(List<String> expressions) {
        long sum = 0;
        for (String expression : expressions) {
            String[] tokens = (expression + ")").replace("(", "( ").replace(")", " )").split(" ");
            sum += evaluate(tokens, 0)[0];
        }
        
        return sum;
    }
    
    private long[] evaluate(String[] expression, int i) {
        int n = expression.length;
        long[] firstEval = evaluateDigit(expression, i);
        long res = firstEval[0];
        i = (int) firstEval[1];
        while (i < n) {
            if (expression[i + 1].equals(")")) {
                return new long[] { res, i + 1 };
            }
            
            long[] eval = evaluateDigit(expression, i + 2);
            if (expression[i + 1].equals("+")) {
                res += eval[0];
            }
            else if (expression[i + 1].equals("*")) {
                res *= eval[0];
            }
            i = (int) eval[1];
        }
        
        return new long[] { res, i };
    }
    
    private long[] evaluateDigit(String[] expression, int i) {
        if (expression[i].equals("(")) {
            return evaluate(expression, i + 1);
        }
        
        return new long[] { Integer.parseInt(expression[i]), i };
    }
    
    @Override
    public long solve() {
        List<String> expressions = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            expressions.add(reader.nextLine());
        }
        reader.close();
        
        return solve(expressions);
    }

}
