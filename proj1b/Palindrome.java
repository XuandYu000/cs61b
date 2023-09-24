public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        if(word == null) return null;
        Deque<Character> res = new LinkedListDeque<>();
        for(int i = 0; i < word.length(); i++){
            res.addLast(word.charAt(i));
        }
        return res;
    }

    public boolean isPalindrome(String word){
        Deque<Character> words = wordToDeque(word);
        while(words.size() > 1){
            Character front = words.removeFirst();
            Character tail = words.removeLast();
            if(!front.equals(tail)){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> words = wordToDeque(word);
        while(words.size() > 1){
            Character front = words.removeFirst();
            Character tail = words.removeLast();
            if(!cc.equalChars(front, tail)) {
                return false;
            }
        }
        return true;
    }
}
