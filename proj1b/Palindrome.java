public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> charDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            charDeque.addLast(character);
        }
        return charDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> charDeque = wordToDeque(word);
        if (charDeque.size() == 0 || charDeque.size() == 1) {
            return true;
        }

        while (charDeque.size() > 1) {
            if (charDeque.removeFirst() != charDeque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> charDeque = wordToDeque(word);
        if (charDeque.size() == 0 || charDeque.size() == 1) {
            return true;
        }

        while (charDeque.size() > 1) {
            if (!cc.equalChars(charDeque.removeFirst(), charDeque.removeLast())) {
                return false;
            }
        }
        return true;
    }

}