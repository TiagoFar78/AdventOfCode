package year2015;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day21Part1 extends Challenge {
    
    private enum Item {

        DAGGER(8, 4, 0),
        SHORTSWORD(10, 5, 0),
        WARHAMMER(25, 6, 0),
        LONGSWORD(40, 7, 0),
        GREATAXE(74, 8, 0),
        ARMOR_DUMMY(0, 0, 0),
        LEATHER(13, 0, 1),
        CHAINMAIL(31,0, 2),
        SPLINTMAIL(53, 0, 3),
        BANDEDMAIL(75, 0, 4),
        PLATEMAIL(102, 0, 5),
        RING_DUMMY_1(0, 0, 0),
        RING_DUMMY_2(0, 0, 0),
        DAMAGE_1(25, 1, 0),
        DAMAGE_2(50, 2, 0),
        DAMAGE_3(100, 3, 0),
        DEFENSE_1(20, 0, 1),
        DEFENSE_2(40, 0, 2),
        DEFENSE_3(80, 0, 3);
        
        private int _cost;
        private int _damage;
        private int _armor;
        
        Item(int cost, int damage, int armor) {
            _cost = cost;
            _damage = damage;
            _armor = armor;
        }
        
        public int getCost() {
            return _cost;
        }
        
        public int getDamage() {
            return _damage;
        }
        
        public int getArmor() {
            return _armor;
        }
    }
    
    private int solve(int hp, int bossHp, int bossDamage, int bossArmor) {
        Item[] weapons = { Item.DAGGER, Item.SHORTSWORD, Item.WARHAMMER, Item.LONGSWORD, Item.GREATAXE };
        Item[] armors = { Item.ARMOR_DUMMY, Item.LEATHER, Item.CHAINMAIL, Item.SPLINTMAIL, Item.BANDEDMAIL, Item.PLATEMAIL };
        Item[] rings = { Item.RING_DUMMY_1, Item.RING_DUMMY_2, Item.DAMAGE_1, Item.DAMAGE_2, Item.DAMAGE_3, Item.DEFENSE_1, Item.DEFENSE_2, Item.DEFENSE_3 };
        
        List<Item[]> roundItems = new ArrayList<>();
        for (int i = 0; i < weapons.length; i++) {
            for (int j = 0; j < armors.length; j++) {
                for (int k = 0; k < rings.length - 1; k++) {
                    for (int l = k + 1; l < rings.length; l++) {
                        roundItems.add(new Item[]{ weapons[i], armors[j], rings[k], rings[k] });
                    }                    
                }
            }
        }
        roundItems.sort((a, b) -> totalCost(a) - totalCost(b));
        
        for (Item[] items : roundItems) {
            int cost = 0;
            int playerDamage = 0;
            int playerArmor = 0;

            for (Item item : items) {
                playerDamage += item.getDamage();
                playerArmor += item.getArmor();
                cost += item.getCost();
            }
            
            if (simulateGame(hp, playerDamage, playerArmor, bossHp, bossDamage, bossArmor)) {
                return cost;
            }
        }
        
        return -1;
    }
    
    private int totalCost(Item[] items) {
        int cost = 0;

        for (Item item : items) {
            cost += item.getCost();
        }
        
        return cost;
    }
    
    private boolean simulateGame(int playerHp, int playerDamage, int playerArmor, int bossHp, int bossDamage, int bossArmor) {
        int turnsToKillBoss = turnsToKill(bossHp, playerDamage, bossArmor);
        int turnsToKillPlayer = turnsToKill(playerHp, bossDamage, playerArmor);
        
        return turnsToKillBoss <= turnsToKillPlayer;
    }
    
    private int turnsToKill(int hp, int damage, int armor) {
        return (int) Math.ceil((double)hp / Math.max((double)1, (double)damage - (double)armor));
    }
    
    @Override
    public long solve() {
        Pattern pattern = Pattern.compile("\\d+");        
        
        Scanner reader = getInputFile();
        Matcher matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        int bossHp = Integer.parseInt(matcher.group());
        matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        int bossDamage = Integer.parseInt(matcher.group());
        matcher = pattern.matcher(reader.nextLine());
        matcher.find();
        int bossArmor = Integer.parseInt(matcher.group());
        
        reader.close();
        
        int playerHp = 100;
        return solve(playerHp, bossHp, bossDamage, bossArmor);
    }

}
