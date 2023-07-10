public class Palindrome {
    public Deque<Character> wordToDeque(String word)
    {
        Deque<Character> WordToDeque=new ArrayDeque<>();
        char[] charArray = word.toCharArray();
        for(char c:charArray)
        {
            WordToDeque.addLast(c);
        }
        return WordToDeque;
    }

    private boolean isPalindrome(Deque<Character> wordInDeque) {
        while (wordInDeque.size() > 1) {
            return wordInDeque.removeFirst() == wordInDeque.removeLast() && isPalindrome(wordInDeque);
        }
        return true;
    }

    public boolean isPalindrome(String word)
    {
        return isPalindrome(wordToDeque(word));
    }

    private boolean isPalindrome(Deque<Character> wordInDeque, CharacterComparator cc)
    {
        while (wordInDeque.size() > 1) {
            return cc.equalChars(wordInDeque.removeFirst(), wordInDeque.removeLast()) && isPalindrome(wordInDeque, cc);
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }
}
