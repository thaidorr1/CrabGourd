
package crabgourd;




import java.util.Random;

public class CrabGourd {
    private String[] animals = {"Nai", "Bầu", "Cua", "Cá", "Gà", "Tôm"};
    private String[] results = new String[3];
    private Random random = new Random();

    public void rollDice() {
        for (int i = 0; i < 3; i++) {
            results[i] = animals[random.nextInt(animals.length)];
        }
    }

    public String[] getResults() {
        return results;
    }

    public int calculateWinnings(String betAnimal, double betAmount) {
        int count = 0;
        for (String result : results) {
            if (result.equals(betAnimal)) {
                count++;
            }
        }
        return count;
    }
}
