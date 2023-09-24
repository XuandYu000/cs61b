import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    /* Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testOffByOne(){
        boolean fla = true;
        // true tests
        fla &= offByOne.equalChars('a', 'b');
        fla &= offByOne.equalChars('r', 'q');
        fla &= offByOne.equalChars('&', '%');
        fla &= offByOne.equalChars('1', '2');
        fla &= offByOne.equalChars('/', '0');

        assertTrue(fla);

        // false tests
        fla = false;
        fla |= offByOne.equalChars('a', 'A');
        fla |= offByOne.equalChars('a', 'B');
        fla |= offByOne.equalChars('<', '>');
        fla |= offByOne.equalChars('a', 'z');
        fla |= offByOne.equalChars('a', 'a');

        assertFalse(fla);
    }

}
