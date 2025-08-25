package year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day16Part2 extends Challenge {
    
    // Not working, I don't understand why
    
    private long solve(List<TicketField> rules, int[] yourTicket, List<int[]> tickets) {
        int n = yourTicket.length;
        List<String> fieldsNames = new ArrayList<>();
        for (TicketField rule : rules) {
            fieldsNames.add(rule.name);
        }
        
        List<Set<String>> field = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            field.add(new HashSet<>(fieldsNames));
        }
        
        for (int[] ticket : tickets) {
            if (calculateErrorRate(rules, ticket) > 0) {
                continue;
            }
            
            for (int i = 0; i < n; i++) {
                Set<String> validFields = getValidFields(rules, ticket[i]);
                field.get(i).retainAll(validFields);
            }
        }
        
        for (int i = 0; i < n; i++) {
            Set<String> validFields = getValidFields(rules, yourTicket[i]);
            field.get(i).retainAll(validFields);
        }
        
        for (Set<String> a : field) {
            System.out.println(a);
        }
        
        System.out.println("------------------");
        
        boolean hadChange = true;
        while (hadChange) {
            hadChange = false;
            
            for (int i = 0; i < n; i++) {
                if (field.get(i).size() == 1) {
                    String element = field.get(i).iterator().next();
                    for (int j = 0; j < n; j++) {
                        if (i != j) {
                            hadChange = hadChange || field.get(j).remove(element);
                        }
                    }
                }
            }
        }
        
        for (Set<String> a : field) {
            System.out.println(a);
        }
        
        long res = 1;
        for (int i = 0; i < n; i++) {
            if (field.get(i).size() == 1 && field.get(i).iterator().next().startsWith("departure")) {
                res *= yourTicket[i];
            }
        }
        
        return res;
    }
    
    private Set<String> getValidFields(List<TicketField> rules, int value) {
        Set<String> validFields = new HashSet<>();
        for (TicketField rule : rules) {
            if ((rule.l1 <= value && value <= rule.r1) || (rule.l2 <= value && value <= rule.r2)) {
                validFields.add(rule.name);
            }
        }
        
        return validFields;
    }
    
    private int calculateErrorRate(List<TicketField> rules, int[] ticket) {
        int errorRate = 0;
        for (int value : ticket) {
            if (!matchesAnyRule(rules, value)) {
                errorRate += value;
            }
        }
        
        return errorRate;
    }
    
    private boolean matchesAnyRule(List<TicketField> rules, int value) {
        for (TicketField rule : rules) {
            if ((rule.l1 <= value && value <= rule.r1) || (rule.l2 <= value && value <= rule.r2)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public long solve() {
        List<TicketField> rules = new ArrayList<>();
        List<int[]> tickets = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)");
        
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        while (!line.equals("")) {
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            rules.add(new TicketField(
                    matcher.group(1),
                    Integer.parseInt(matcher.group(2)), 
                    Integer.parseInt(matcher.group(3)), 
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5))
            ));
            
            line = reader.nextLine();
        }
        
        reader.nextLine();
        String[] ticketValuesString = reader.nextLine().split(",");
        int n = ticketValuesString.length;
        int[] yourTicket = new int[n];
        for (int i = 0; i < n; i++) {
            yourTicket[i] = Integer.parseInt(ticketValuesString[i]);
        }
        
        reader.nextLine();
        reader.nextLine();
        while (reader.hasNextLine()) {
            ticketValuesString = reader.nextLine().split(",");
            int[] ticket = new int[n];
            for (int i = 0; i < n; i++) {
                ticket[i] = Integer.parseInt(ticketValuesString[i]);
            }
            tickets.add(ticket);
        }
        reader.close();
        
        return solve(rules, yourTicket, tickets);
    }
    
    private class TicketField {
        
        private String name;
        private int l1;
        private int r1;
        private int l2;
        private int r2;
        
        public TicketField(String name, int l1, int r1, int l2, int r2) {
            this.name = name;
            this.l1 = l1;
            this.r1 = r1;
            this.l2 = l2;
            this.r2 = r2;
        }
        
    }

}
