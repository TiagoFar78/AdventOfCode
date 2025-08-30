package year2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day20Part1 extends Challenge {
    
    private long solve(List<Tile> tiles) {
        Map<String, Integer> borders = new HashMap<>();
        for (Tile tile : tiles) {
            for (String border : getBorders(tile)) {
                if (borders.containsKey(border)) {
                    borders.put(border, borders.get(border) + 1);
                    continue;
                }
                
                border = reverse(border);
                if (borders.containsKey(border)) {
                    borders.put(border, borders.get(border) + 1);
                    continue;
                }
                
                borders.put(border, 1);
            }
            
        }
        
        long prod = 1;
        for (Tile tile : tiles) {
            int bordersFrequency = 0;
            for (String border : getBorders(tile)) {
                bordersFrequency += borders.getOrDefault(border, 0) + borders.getOrDefault(reverse(border), 0);
            }
            
            if (bordersFrequency == 6) {
                prod *= tile.id;
            }
        }
        
        return prod;
    }
    
    private String[] getBorders(Tile tile) {
        String[] borders = new String[4];
        for (int i = 0; i < 4; i++) {
            borders[i] = "";
        }
        
        for (int i = 0; i < tile.image.size(); i++) {
            borders[0] += tile.image.get(i).charAt(0);
            borders[1] += tile.image.get(i).charAt(tile.image.get(0).length() - 1);
        }
        
        for (int i = 0; i < tile.image.get(0).length(); i++) {
            borders[2] += tile.image.get(0).charAt(i);
            borders[3] += tile.image.get(tile.image.size() - 1).charAt(i);
        }
        
        return borders;
    }
    
    private String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    
    @Override
    public long solve() {
        List<Tile> tiles = new ArrayList<>();
        
        Pattern pattern = Pattern.compile("Tile (\\d+):");
        
        Scanner reader = getInputFile();
        while (reader.hasNextLine()) {
            Matcher matcher = pattern.matcher(reader.nextLine());
            matcher.matches();
            int id = Integer.parseInt(matcher.group(1));
            List<String> image = new ArrayList<>();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.equals("")) {
                    break;
                }
                
                image.add(line);
            }
            
            tiles.add(new Tile(id, image));
        }
        reader.close();
        
        return solve(tiles);
    }
    
    private class Tile {
        
        private int id;
        private List<String> image;
        
        public Tile(int id, List<String> image) {
            this.id = id;
            this.image = image;
        }
        
    }

}
