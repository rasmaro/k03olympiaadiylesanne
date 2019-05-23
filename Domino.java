import java.io.*;
import java.util.*;
import java.lang.Math;

public class Domino {
	
	public static void main(String arg[]) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("dj.sis"));
		String row = reader.readLine();
		reader.close();
		int dominos = 0;
		
		if(tryParseInt(row)) {
			dominos = Integer.parseInt(row);
		}
		if(dominos <= 0 || dominos > 28) {
			System.out.println("Viga: Dominode genereeritav arv on 0 või väiksem või suurem kui 28 või ei suudetud teha täisarvuks!");
			System.out.println("Programm lõpetatakse.");
			return;
		}
		
		// Loome programmi põhiosa jaoks arraylistid ja muutujad
		List<Integer> leftSide = new ArrayList<Integer>();
		List<Integer> rightSide = new ArrayList<Integer>();
		List<Integer> randomizeOrder = new ArrayList<Integer>();
		List<Integer> usedPairs = new ArrayList<Integer>();
		int totalIndex = 0;
	
		// Loome paaride sisud
		for(int i = 0; i <= 6; i++) {
			for(int ii = 0; ii <= 6; ii++) {
				leftSide.add(i);
				rightSide.add(ii);
				usedPairs.add(0);
				totalIndex++;
			}
		}
		
		// Eemaldame duplikaadid
		int totalPairs = leftSide.size();
		
		for(int i = 0; i < totalPairs; i++) {
			int countMatches = 0;
			int leftSideVal = leftSide.get(i);
			int rightSideVal = rightSide.get(i);
			
			for(int ii = 0; ii < totalPairs; ii++) {
				if((leftSide.get(ii) == leftSideVal && rightSide.get(ii) == rightSideVal) || (leftSide.get(ii) == rightSideVal && rightSide.get(ii) == leftSideVal)) {
					countMatches++;
					if(countMatches >= 2) {
						leftSide.remove(ii);
						rightSide.remove(ii);
						totalPairs--;
						break;
					}
				}
			}
		}
		
		for(int i = 0; i < totalPairs; i++) {
			randomizeOrder.add(randomWithRange(0, totalPairs-i)); // Määrame ära randomize orderi kuna vaja kahte arraylisti syncis hoida.
		}
		
		// Ajame järjekorra sassi arrayListidel vastavalt randomizeOrder'is olevatele random väärtustele.
		leftSide = shuffle(leftSide, randomizeOrder);
		rightSide = shuffle(rightSide, randomizeOrder);
		
		// Näitame paarid jadas välja.
		int lastIndex = 0;
		int lastRightSideVal = rightSide.get(lastIndex);
		int lastLeftSideVal = leftSide.get(lastIndex);
		
		PrintWriter writer = new PrintWriter(new FileWriter("dj.val"));
		
		for(int i = 0; i < dominos; i++) {
			int chosenIndex = -1;
			boolean flipped = false;
			
			for(int ii = 0; ii < totalPairs; ii++) {
				if(usedPairs.get(ii) == 0) {
					if(lastRightSideVal == leftSide.get(ii) || lastRightSideVal == rightSide.get(ii)) {
						chosenIndex = ii;
						usedPairs.set(ii, 1);
						flipped = (lastRightSideVal == rightSide.get(ii));
						break;
					}
				}
			}
			
			if(chosenIndex >= 0) {
				if(flipped) {
					lastRightSideVal = leftSide.get(chosenIndex);
					lastLeftSideVal = rightSide.get(chosenIndex);
				} else {
					lastRightSideVal = rightSide.get(chosenIndex);
					lastLeftSideVal = leftSide.get(chosenIndex);
				}
				System.out.println(lastLeftSideVal+" "+lastRightSideVal);
				writer.println(lastLeftSideVal+" "+lastRightSideVal);
				lastIndex = chosenIndex;
			} else {
				System.out.println("Viga: Ei leitud sobivat dominot.");
			}
		}
		writer.close();
		
	}

	public static boolean tryParseInt(String value) {  
		try {  
			Integer.parseInt(value);  
			return true;  
		} catch (NumberFormatException e) {  
			return false;  
		}  
	}
	
	// Randomize orderi loogika on Fisher-Yates (aka Knuth) Shuffle-st pärit. Kuid väiksed muudatused tehtud sisse, et hõlpsustada ülesande täitmist (st. randomize järjekord tuleb etteantud array väljudest)
	public static List<Integer> shuffle(List<Integer> array, List<Integer> randomOrder) {
		int currentIndex = array.size()-1, temporaryValue, randomIndex;
		
		while (0 != currentIndex) {
			randomIndex = (int)randomOrder.get(currentIndex);
			currentIndex -= 1;

			temporaryValue = array.get(currentIndex);
			array.set(currentIndex, array.get(randomIndex));
			array.set(randomIndex, temporaryValue);
		}

		return array;
	}
	
	public static int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
}