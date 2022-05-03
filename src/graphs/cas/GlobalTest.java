package graphs.cas;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import graphs.rapport.Assignment;
import graphs.rapport.Tutor;
import graphs.rapport.Tutored;

public class GlobalTest {
    public Tutored n1, n2, n3, n4, n5, n6, n7;
    public Tutor f1, f2, f3, f4, f5, f6, f7;
    public Assignment  assignment1, assignment2;
    public List<Tutor> tutorList;
    public List<Tutored> tutoredList;

    @BeforeEach
    public void initialize() {
        n1 = new Tutored("Claude", 9.8);
        n2 = new Tutored("Madeleine", 6.9);
        n3 = new Tutored("Sabine", 12.7);
        n4 = new Tutored("Hugues", 0.2);
        n5 = new Tutored("Lucas", 17.3);
        n6 = new Tutored("Alexandria", 12.5);
        n7 = new Tutored("Anouk", 10.5);

        f1 = new Tutor("Vincent", 9.3, 2, 1);
        f2 = new Tutor("Jacqueline", 13.2, 2, 1);
        f3 = new Tutor("Pénélope", 13.2 , 2, 1);
        f4 = new Tutor("Édouard", 13.9, 3, 1);
        f5 = new Tutor("Olivier", 11.3, 3, 1);
        f6 = new Tutor("Inès", 9.3, 3, 1);
        f7 = new Tutor("Franck", 11.9, 3, 1);
        
        tutorList = new ArrayList<>();
        tutoredList = new ArrayList<>();
    }

    @Test
    public void CasAutant() {
        
    }

}
