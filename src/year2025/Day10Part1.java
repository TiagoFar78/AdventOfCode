package year2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import main.Challenge;

public class Day10Part1 extends Challenge {
    
    private long solve(List<Machine> machines) {
        int sum = 0;
        for (Machine machine : machines) {
            sum += solve(machine);
        }
        
        return sum;
    }
    
    private long solve(Machine machine) {
        Queue<DiagramState> pq = new PriorityQueue<>((a,b) -> a.moves - b.moves);
        pq.add(new DiagramState(machine.target.length));
        while (true) {
            DiagramState state = pq.poll();
            for (List<Integer> button : machine.buttons) {
                DiagramState newState = new DiagramState(state);
                newState.moves++;
                for (int number : button) {
                    newState.current[number] = !newState.current[number];
                }
                
                if (matches(newState.current, machine.target)) {
                    return newState.moves;
                }
                
                pq.add(newState);
            }
        }
    }
    
    private boolean matches(boolean[] current, boolean[] target) {
        for (int i = 0; i < current.length; i++) {
            if (current[i] != target[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public long solve() {
        List<Machine> machines = new ArrayList<>();
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            machines.add(new Machine(line.substring(0, line.indexOf("{") - 1).split(" ")));
        }
        reader.close();
        
        return solve(machines);
    }
    
    private class DiagramState {
        
        public int moves;
        public boolean[] current;
        
        public DiagramState(int size) {
            moves = 0;
            current = new boolean[size];
        }
        
        public DiagramState(DiagramState state) {
            moves = state.moves;
            current = Arrays.copyOf(state.current, state.current.length);
        }
        
    }
    
    private class Machine {
        
        public boolean[] target;
        public List<List<Integer>> buttons;
        
        public Machine(String[] elements) {
            target = createTarget(elements[0].substring(1, elements[0].length()));
            
            buttons = new ArrayList<>();
            for (int i = 1; i < elements.length; i++) {
                buttons.add(createButton(elements[i].substring(1, elements[i].length() - 1)));
            }
        }
        
        private boolean[] createTarget(String diagram) {
            boolean[] t = new boolean[diagram.length()];
            for (int i = 0; i < diagram.length(); i++) {
                t[i] = diagram.charAt(i) == '#';
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
