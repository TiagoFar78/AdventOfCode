package year2015;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Challenge;

public class Day22Part2 extends Challenge {
    
    private enum Spell {
        
        MAGIC_MISSILE(4, 0, 53, 0, 0, 0, 0),
        DRAIN(2, 0, 73, 2, 0, 0, 0),
        SHIEL(0, 0, 113, 0, 7, 0, 6),
        POISON(0, 3, 173, 0, 0, 0, 6),
        RECHARGE(0, 0, 229, 0, 0, 101, 5);
        
        private int _damage;
        private int _damageDrain;
        private int _mana;
        private int _heal;
        private int _armor;
        private int _manaRegen;
        private int _turns;
        
        Spell(int instantDamage, int damageDrain, int mana, int heal, int armor, int manaRegen, int turns) {
            _damage = instantDamage;
            _damageDrain = damageDrain;
            _mana = mana;
            _heal = heal;
            _armor = armor;
            _manaRegen = manaRegen;
            _turns = turns;
        }
        
        public int getDamage() {
            return _damage;
        }
        
        public int getDamageDrain() {
            return _damageDrain;            
        }
        
        public int getManaCost() {
            return _mana;
        }
        
        public int getHeal() {
            return _heal;
        }
        
        public int getArmor() {
            return _armor;
        }
        
        public int getManaRegen() {
            return _manaRegen;
        }
        
        public int getTurns() {
            return _turns;
        }
    }
    
    private final static Spell[] SPELLS = { Spell.MAGIC_MISSILE, Spell.DRAIN, Spell.SHIEL, Spell.POISON, Spell.RECHARGE };
    private final static int MAX_MANA = 100000;
    
    private int _minMana = MAX_MANA;
    
    private int solve(int hp, int mana, int bossHp, int bossDamage) {
        findMinMana(hp, mana, bossHp, bossDamage, new int[SPELLS.length], 0);
        return _minMana;
    }
    
    private void findMinMana(int hp, int mana, int bossHp, int bossDamage, int[] spellsDuration, int currentManaSpent) {
        if (bossHp <= 0) {
            _minMana = Math.min(_minMana, currentManaSpent);
            return;
        }

        hp--;
        if (hp <= 0 || currentManaSpent >= _minMana) {
            return;
        }
        
        for (int i = 0; i < SPELLS.length; i++) {
            if (spellsDuration[i] > 0) {
                bossHp -= SPELLS[i].getDamageDrain();
                hp += SPELLS[i].getHeal();
                mana += SPELLS[i].getManaRegen();
                spellsDuration[i]--;
            }
        }
        
        for (int i = 0; i < SPELLS.length; i++) {
            if (spellsDuration[i] == 0 && mana >= SPELLS[i].getManaCost()) {
                int tempBossHp = bossHp - SPELLS[i].getDamage();
                int tempHp = hp + SPELLS[i].getHeal();
                int tempMana = mana - SPELLS[i].getManaCost();
                int[] tempSpellsDuration = Arrays.copyOf(spellsDuration, SPELLS.length);
                tempSpellsDuration[i] = SPELLS[i].getTurns();
                
                int armor = 0;
                for (int j = 0; j < SPELLS.length; j++) {
                    if (tempSpellsDuration[j] > 0) {
                        tempBossHp -= SPELLS[j].getDamageDrain();
                        tempHp += SPELLS[j].getHeal();
                        armor += SPELLS[j].getArmor();
                        tempMana += SPELLS[j].getManaRegen();
                        tempSpellsDuration[j]--;
                    }
                }
                
                tempHp -= Math.max(1, bossDamage - armor);
                findMinMana(tempHp, tempMana, tempBossHp, bossDamage, tempSpellsDuration, currentManaSpent + SPELLS[i].getManaCost());
            }
        }
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
        int playerHp = 50;
        int playerMana = 500;
        return solve(playerHp, playerMana, bossHp, bossDamage);
    }

}
