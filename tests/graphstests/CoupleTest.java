package graphstests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphs.affectation.Couple;
import oop.Person;
import oop.Tutor;
import oop.Tutored;

public class CoupleTest {
    Couple c1, c2, c3;
    Tutored u1, u2, u3;
    Tutor t1, t2, t3;

    @BeforeEach
    public void initialize() {
        u1 = new Tutored("Anémone", 2, 'B');
        u2 = new Tutored("Béthanie", 3, 'A');
        u3 = new Tutored("Christian", 6, 'C');

        t1 = new Tutor("Adrien", 3, 2, 'A');
        t2 = new Tutor("Bill", 2, 7, 'A');
        t3 = new Tutor("Céline", 3, 0, 'C');

        c1 = new Couple(u1, t2);
        c2 = new Couple(u3, t1);
        c3 = new Couple(u2, t1);
    }

    @Test
    public void containsTest() {
        assertTrue(c1.contains(u1));
        assertTrue(c1.contains(t2));
        assertTrue(c2.contains(u3));
        assertTrue(c2.contains(t1));
        assertTrue(c3.contains(u2));
        assertTrue(c3.contains(t1));

        assertFalse(c1.contains(u2));
        assertFalse(c2.contains(u2));
        assertFalse(c1.contains(u3));
        assertFalse(c1.contains(t1));
        assertFalse(c2.contains(t3));
        assertFalse(c3.contains(t2));
    }

    @Test
    public void toStringTest() {
        Person.setShortName(true);
        assertEquals("(Anémone, Bill)", c1.toString());
        assertEquals("(Christian, Adrien)", c2.toString());
        assertEquals("(Béthanie, Adrien)", c3.toString());
    }

    @Test
    public void equalsTest() {
        assertTrue(c1.equals(u1, t2));
        assertTrue(c2.equals(u3, t1));
        assertTrue(c3.equals(u2, t1));

        assertFalse(c1.equals(u1, t3));
        assertFalse(c1.equals(u3, t2));
        assertFalse(c2.equals(u3, t2));
        assertFalse(c2.equals(u2, t1));
        assertFalse(c3.equals(u2, t2));
        assertFalse(c3.equals(u3, t1));
    }
}
