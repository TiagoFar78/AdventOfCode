package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day07Part1 {
	
	private static Day07Part1 day7 = new Day07Part1();
	
	private enum HandType {
		FIVE_OF_A_KIND,
		FOUR_OF_A_KIND,
		FULL_HOUSE,
		THREE_OF_A_KIND,
		TWO_PAIR,
		ONE_PAIR,
		HIGH_CARD;		
	}
	
	private static HandType[] handTypeStrengthOrder = { HandType.HIGH_CARD, HandType.ONE_PAIR, HandType.TWO_PAIR, HandType.THREE_OF_A_KIND,
			HandType.FULL_HOUSE, HandType.FOUR_OF_A_KIND, HandType.FIVE_OF_A_KIND};
	
	private static String[] cardStrenghtOrder = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
 	
	private static int getStrenght(HandType handType) {
		for (int i = 0; i < handTypeStrengthOrder.length; i++) {
			if (handType == handTypeStrengthOrder[i]) {
				return i;
			}
		}
		
		System.out.println("SOME ERROR1");
		return 69;
	}
	
	private static int getStrenght(String card) {
		for (int i = 0; i < cardStrenghtOrder.length; i++) {
			if (card.equals(cardStrenghtOrder[i])) {
				return i;
			}
		}
		
		System.out.println("SOME ERROR2");
		return 69;
	}
	
	private class Hand implements Comparable<Hand> {
		
		private String _cards;
		private int _bid;
		
		public Hand(String cards, int bid) {
			_cards = cards;
			_bid = bid;
		}
		
		private String getCards() {
			return _cards;
		}
		
		private int getBid() {
			return _bid;
		}
		
		private HandType getHandType() {
			List<String> cards = new ArrayList<String>();
			List<Integer> amounts = new ArrayList<Integer>();
			
			for (int i = 0; i < _cards.length(); i++) {
				String charAt = Character.toString(_cards.charAt(i));
				
				boolean alreadyExists = false;
				for (int j = 0; j < cards.size(); j++) {
					if (cards.get(j).equals(charAt)) {
						alreadyExists = true;
						amounts.set(j, amounts.get(j) + 1);
						break;
					}
				}
				
				if (!alreadyExists) {
					cards.add(charAt);
					amounts.add(1);
				}
			}
			
			if (amounts.size() == 1) {
				return HandType.FIVE_OF_A_KIND;
			}
			
			if (amounts.size() == 2 && (amounts.get(0) == 1 || amounts.get(0) == 4)) {
				return HandType.FOUR_OF_A_KIND;
			}
			
			if (amounts.size() == 2 && (amounts.get(0) == 2 || amounts.get(0) == 3)) {
				return HandType.FULL_HOUSE;
			}
			
			if (amounts.size() == 3 && (amounts.get(0) == 3 || amounts.get(1) == 3 || amounts.get(2) == 3)) {
				return HandType.THREE_OF_A_KIND;
			}
			
			if (amounts.size() == 3 && (amounts.get(0) == 2 || amounts.get(1) == 2 || amounts.get(2) == 2)) {
				return HandType.TWO_PAIR;
			}
			
			if (amounts.size() == 4) {
				return HandType.ONE_PAIR;
			}
			
			return HandType.HIGH_CARD;
		}
		
		@Override
		public int compareTo(Hand o) {
			
			int handsStrenghtDiff = getStrenght(getHandType()) - getStrenght(o.getHandType());
			if (handsStrenghtDiff == 0) {
				String oCards = o.getCards();
				
				for (int i = 0; i < _cards.length(); i++) {
					String thisCard = Character.toString(_cards.charAt(i));
					String oCard = Character.toString(oCards.charAt(i));
					
					int cardsStrengthDiff = getStrenght(thisCard) - getStrenght(oCard);
					if (cardsStrengthDiff != 0) {
						return cardsStrengthDiff;
					}
				}
			}
			
			return handsStrenghtDiff;
		}
	}
	
	private static List<Hand> hands;
	
	public static void run() {
		initializeHands();
		
		Collections.sort(hands);
		
		int sum = 0;
		
		for (int i = 0; i < hands.size(); i++) {
			sum += (i + 1) * hands.get(i).getBid();
		}
		
		System.out.println(sum);
	}
	
	private static void initializeHands() {
		hands = new ArrayList<Hand>();
		
		try {
	        File myObj = new File("InputFiles/7.txt");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {	        	
	            String line = myReader.nextLine();
        		
        		String composition = "";
        		String cards = "";
	            
	            for (int i = 0; i < line.length(); i++) {
            		String charAt = Character.toString(line.charAt(i));
            		
            		if (charAt.equals(" ")) {
            			cards = composition;
            			composition = "";
            			continue;
            		}
            		
            		composition += charAt;
            		
            		if (i == line.length() - 1) {
            			int bid = stringToInt(composition);
            			
            			Hand hand = day7.new Hand(cards, bid);
            			
            			hands.add(hand);
            		}
            	}
	            
	        }
	        
	        myReader.close();
	    }
		catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	private static int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
