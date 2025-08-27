package year2020;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.Challenge;

public class Day18Part2 extends Challenge {
    
    private long solve(List<String> expressions) {
        long sum = 0;
        for (String expression : expressions) {
            String[] tokens = expression.replace("(", "( ").replace(")", " )").split(" ");
            sum += evaluateLine(tokens);
        }
        
        return sum;
    }
    
    private long evaluateLine(String[] tokens) {
        List<String> rpn = toRPN(tokens);
        return evalRPN(rpn);
    }

    private List<String> toRPN(String[] tokens) {
        List<String> output = new ArrayList<>();
        Deque<String> ops = new ArrayDeque<>();

        Map<String, Integer> prec = new HashMap<>();
        prec.put("+", 2);
        prec.put("*", 1);

        for (String t : tokens) {
            if (t.matches("\\d+")) {
                output.add(t);
            } 
            else if (t.equals("+") || t.equals("*")) {
                while (!ops.isEmpty() && !ops.peek().equals("(") && prec.get(ops.peek()) >= prec.get(t)) {
                    output.add(ops.pop());
                }
                
                ops.push(t);
            } 
            else if (t.equals("(")) {
                ops.push(t);
            } 
            else if (t.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    output.add(ops.pop());
                }
                
                ops.pop();
            }
        }
        
        while (!ops.isEmpty()) {
            output.add(ops.pop());
        }
        
        return output;
    }

    private long evalRPN(List<String> rpn) {
        Deque<Long> stack = new ArrayDeque<>();
        for (String t : rpn) {
            if (t.matches("\\d+")) {
                stack.push(Long.parseLong(t));
            } 
            else {
                long b = stack.pop(), a = stack.pop();
                stack.push(t.equals("+") ? a + b : a * b);
            }
        }
        
        return stack.pop();
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
