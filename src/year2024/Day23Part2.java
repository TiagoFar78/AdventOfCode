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

public class Day23Part2 extends Challenge {
    
    private long solve(List<String[]> connections) {
        Set<Set<String>> computerSets = new HashSet<>();
        
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
            
            Set<String> computerSet = new HashSet<>();
            computerSet.add(computer1);
            computerSet.add(computer2);
            computerSets.add(computerSet);
        }
        
        for (String key : computersConnections.keySet()) {
            for (Set<String> computerSet : computerSets) {
                if (computerSet.contains(key)) {
                    continue;
                }
                
                Set<String> connectedComputers = computersConnections.get(key);
                if (connectsToAll(connectedComputers, computerSet)) {
                    computerSet.add(key);
                }                
            }
        }
        
        Set<String> largestComputerSet = null;
        for (Set<String> computerSet : computerSets) {
            if (largestComputerSet == null || computerSet.size() > largestComputerSet.size()) {
                largestComputerSet = computerSet;
            }
        }
        
        String[] array = largestComputerSet.stream().toArray(String[]::new);
        Arrays.sort(array);
        System.out.println(String.join(",", array));
        
        return 0;
    }
    
    private boolean connectsToAll(Set<String> connectedComputers, Set<String> computerSet) {
        for (String computer : computerSet) {
            if (!connectedComputers.contains(computer)) {
                return false;
            }
        }
        
        return true;
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
