package year2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day16Part1 extends Challenge {
    
    private int solve(List<TicketField> rules, List<int[]> tickets) {
        int errorRate = 0;
        for (int[] ticket : tickets) {
            errorRate += calculateErrorRate(rules, ticket);
        }
        
        return errorRate;
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
        
        Pattern pattern = Pattern.compile(".*: (\\d+)-(\\d+) or (\\d+)-(\\d+)");
        
        Scanner reader = getInputFile();
        String line = reader.nextLine();
        while (!line.equals("")) {
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            rules.add(new TicketField(
                    Integer.parseInt(matcher.group(1)), 
                    Integer.parseInt(matcher.group(2)), 
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
            ));
            
            line = reader.nextLine();
        }
        
        reader.nextLine();
        reader.nextLine();
        reader.nextLine();
        reader.nextLine();
        while (reader.hasNextLine()) {
            String[] ticketValuesString = reader.nextLine().split(",");
            int n = ticketValuesString.length;
            int[] ticket = new int[n];
            for (int i = 0; i < n; i++) {
                ticket[i] = Integer.parseInt(ticketValuesString[i]);
            }
            tickets.add(ticket);
        }
        reader.close();
        
        return solve(rules, tickets);
    }
    
    private class TicketField {
        
        private int l1;
        private int r1;
        private int l2;
        private int r2;
        
        public TicketField(int l1, int r1, int l2, int r2) {
            this.l1 = l1;
            this.r1 = r1;
            this.l2 = l2;
            this.r2 = r2;
        }
        
    }

}
