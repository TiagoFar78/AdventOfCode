package year2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day23Part1 extends Challenge {
    
    private static final Character STARTING_C = 't';
    
    private long solve(List<String[]> connections) {
        Map<String, Set<String>> computersConnections = new HashMap<>();
        for (String[] connection : connections) {
            String computer1 = connection[0];
            String computer2 = connection[1];
            
            if (!computersConnections.containsKey(computer1)) {
                computersConnections.put(computer1, new HashSet<>());
            }
            computersConnections.get(computer1).add(computer2);
            
            if (!computersConnections.containsKey(computer2)) {
                computersConnections.put(computer2, new HashSet<>());
            }
            computersConnections.get(computer2).add(computer1);
        }
        
        Set<String> possibleGroups = new HashSet<>();
        for (String key : computersConnections.keySet()) {
            Set<String> computerAConnections = computersConnections.get(key);
            for (String connectedComputerA : computerAConnections) {
                for (String connectedComputerB : computersConnections.get(connectedComputerA)) {
                    if (computerAConnections.contains(connectedComputerB) &&
                            (key.charAt(0) == STARTING_C || connectedComputerA.charAt(0) == STARTING_C || connectedComputerB.charAt(0) == STARTING_C)) {
                        String[] group = new String[] { key, connectedComputerA, connectedComputerB };
                        Arrays.sort(group);
                        possibleGroups.add(group[0] + group[1] + group[2]);
                    }
                }
            }
        }
        
        return possibleGroups.size();
    }

    @Override
    public long solve() {
        List<String[]> connections = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("[a-z]{2}");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            String[] connection = new String[2];
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.find();
            connection[0] = matcher.group();
            matcher.find();
            connection[1] = matcher.group();
            connections.add(connection);
        }
        reader.close();
        
        return solve(connections);
    }
}
