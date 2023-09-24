import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testOffByN(){
        boolean fla = true;
        fla &= offBy5.equalChars('a', 'f');
        fla &= offBy5.equalChars((char)('r' + 4), 'q');
        fla &= offBy5.equalChars((char) ('&' + 4), '%');
        fla &= offBy5.equalChars('1', (char)('2' + 4));
        fla &= offBy5.equalChars('/', (char)('0' + 4));

        assertTrue(fla);

        // false tests
        fla = false;
        fla |= offBy5.equalChars('a', 'A');
        fla |= offBy5.equalChars('a', 'B');
        fla |= offBy5.equalChars('<', '>');
        fla |= offBy5.equalChars('a', 'z');
        fla |= offBy5.equalChars('a', 'a');

        assertFalse(fla);
    }
}
