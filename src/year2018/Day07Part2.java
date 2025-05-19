package year2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day07Part2 extends Challenge {
    
    private static final int ALPHABET = 26;
    private static final int BASE_TIME = 60;
    private static final int WORKERS = 5;
    
    private int solve(Map<Character, List<Character>> unlocks, Map<Character, Integer> dependencySize) {
        Queue<Character> pq = new PriorityQueue<>();
        int[] timeLeft = new int[ALPHABET];
        for (int i = 0; i < ALPHABET; i++) {
            timeLeft[i] = BASE_TIME + i + 1;
        }
        
        char[] workerLetter = new char[WORKERS];
        for (int i = 0; i < WORKERS; i++) {
            workerLetter[i] = '0';
        }
        Set<Character> firstUnlocked = new HashSet<>(unlocks.keySet());
        firstUnlocked.removeAll(dependencySize.keySet());
        pq.addAll(firstUnlocked);
        
        int seconds = 0;
        while (true) {
            seconds++;
            boolean isWorking = false;
            for (int i = 0; i < WORKERS; i++) {
                isWorking = work(i, workerLetter, timeLeft, unlocks, dependencySize, pq) || isWorking;
            }
            
            if (!isWorking) {
                break;
            }
        }
        
        return seconds;
    }
    
    private boolean work(int id, char[] workerLetter, int[] timeLeft, Map<Character, List<Character>> unlocks, Map<Character, Integer> dependencySize, Queue<Character> pq) {
        if (workerLetter[id] == '0') {
            if (pq.isEmpty()) {
                return false;
            }

            workerLetter[id] = pq.poll();
        }
        
        char letter = workerLetter[id];
        
        timeLeft[letter - 'A']--;
        if (timeLeft[letter - 'A'] <= 0) {
            workerLetter[id] = '0';
            List<Character> unlock = unlocks.get(letter);
            if (unlock == null) {
                unlock = new ArrayList<>();
            }
            
            for (Character c : unlock) {
                dependencySize.put(c, dependencySize.get(c) - 1);
                if (dependencySize.get(c) == 0) {
                    pq.add(c);
                }
            }
            
            if (pq.isEmpty()) {
                return false;
            }
            
            workerLetter[id] = pq.poll();
        }
        
        return letter != '0';
    }
    
    @Override
    public long solve() {
        Map<Character, List<Character>> unlocks = new HashMap<>();
        Map<Character, Integer> dependencySize = new HashMap<>();
        
        Pattern pattern = Pattern.compile("Step (.) must be finished before step (.) can begin.");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            
            char key = matcher.group(1).charAt(0);
            char value = matcher.group(2).charAt(0);
            
            if (!unlocks.containsKey(key)) {
                unlocks.put(key, new ArrayList<>());
            }
            unlocks.get(key).add(value);
            
            dependencySize.put(value, dependencySize.getOrDefault(value, 0) + 1);
        }
        reader.close();
        
        return solve(unlocks, dependencySize);
    }

}
