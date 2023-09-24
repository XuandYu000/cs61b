public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int len = word.length();
        Deque<Character> res = new LinkedListDeque<>();
        for(int i = 0; i < len; i++){
            res.addLast(word.charAt(i));
        }
        return res;
    }

    public boolean isPalindrome(String word) {
        if(word == null || word.length() <= 1){
            return true;
        }
        int len = word.length();
        for(int i = 0; i < len / 2; i++){
            if(word.charAt(i) != word.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        if(word == null || word.length() <= 1){
            return true;
        }
        int len = word.length();
        for(int i = 0; i < len / 2; i++){
            if(!cc.equalChars(word.charAt(i), word.charAt(len - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
