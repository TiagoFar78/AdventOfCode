package year2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

import main.Challenge;

public class Day10Part2 extends Challenge {
    
    private long solve(List<Machine> machines) {
        int sum = 0;
        for (Machine machine : machines) {
            sum += solve(machine);
        }
        
        return sum;
    }
    
    private long solve(Machine machine) {
        int n = machine.buttons.size();
        int[] maxes = new int[n];
        for (int i = 0; i < machine.buttons.size(); i++) {
            int max = Integer.MAX_VALUE;
            for (int number : machine.buttons.get(i)) {
                max = Math.min(max, machine.target[number]);
            }
            
            maxes[i] = max;
        }
        
        Model model = new Model();
        IntVar[] vars = new IntVar[n];
        for (int i = 0; i < n; i++) {
            vars[i] = model.intVar(0, maxes[i]);
        }
        
        for (int i = 0; i < machine.target.length; i++) {
            List<IntVar> joltageVars = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (machine.buttons.get(j).contains(i)) {
                    joltageVars.add(vars[j]);
                }
            }
            
            model.sum(joltageVars.toArray(new IntVar[0]), "=", machine.target[i]).post();
        }
        
        int minSum = 0;
        int maxSum = 0;
        for (int joltage : machine.target) {
            minSum = Math.max(minSum, joltage);
            maxSum += joltage;
        }
        
        IntVar sum = model.intVar(minSum, maxSum);
        model.sum(vars, "=", sum).post();
        
        Solver solver = model.getSolver();
        solver.setSearch(Search.domOverWDegRefSearch(vars));
        return solver.findOptimalSolution(sum, Model.MINIMIZE).getIntVal(sum);
    }
    
    @Override
    public long solve() {
        List<Machine> machines = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            machines.add(new Machine(line.substring(line.indexOf("]") + 2, line.length() - 1).split(" \\{")));
        }
        reader.close();
        
        return solve(machines);
    }
    
    private class Machine {
        
        public int[] target;
        public List<List<Integer>> buttons;
        
        public Machine(String[] elements) {
            target = createTarget(elements[1].split(","));
            
            buttons = new ArrayList<>();
            for (String element : elements[0].split(" ")) {
                List<Integer> button = createButton(element.substring(1, element.length() - 1));
                buttons.add(button);
            }
        }
        
        private int[] createTarget(String[] joltages) {
            int[] t = new int[joltages.length];
            for (int i = 0; i < joltages.length; i++) {
                t[i] = Integer.parseInt(joltages[i]);
            }
            
            return t;
        }
        
        private List<Integer> createButton(String button) {
            List<Integer> numbers = new ArrayList<>();
            for (String number : button.split(",")) {
                numbers.add(Integer.parseInt(number));
            }
            
            return numbers;
        }
        
    }

}
