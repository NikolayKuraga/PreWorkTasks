package kuraga.invert_word;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvertWordTests {
    @Test
    void fst() {
        Assertions.assertEquals("!devdem en esvov ay ,akhcut ,akhcut ,akhcut aY",
                InvertWord.invert("Ya tuchka, tuchka, tuchka, ya vovse ne medved!"));
        System.out.println("passed test 1");
    }

    @Test
    void snd() {
        Assertions.assertEquals("8-800-555-35-35",
                InvertWord.invert("53-53-555-008-8"));
        System.out.println("passed test 2");
    }

    @Test
    void trd() {
        Assertions.assertEquals("",
                InvertWord.invert(""));
        System.out.println("passed test 3");
    }
}
