public class OffByN implements CharacterComparator {
    private int amount;
    
    public OffByN(int N) {
         amount = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == amount || x - y == -amount) {
            return true;
        } else {
            return false;
        }
    }
}