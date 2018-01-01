package org.jointheleague.ecolban.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {

    private Model model;
    private boolean observersNotified;

    @Before
    public void setUp() {
        model = new Model();
        assertNotNull(model);
    }

    @Test
    public void testConstructor() {

        Model.Mark[] marks = model.getMarks();
        for (Model.Mark mark : marks) {
            assertEquals(Model.Mark.Blank, mark);
        }
    }

    @Test
    public void testMark() {
        assertEquals(Model.Mark.X, model.getPlayer());
        try {
            model.mark(9);
            fail("mark(9) is not possible");
        } catch (IllegalArgumentException e) {
            assertEquals("pos must be between 0 (inclusive) and 9 (exclusive).", e.getMessage());
        }
        model.mark(0);
        assertEquals(Model.Mark.X, model.getMarks()[0]);
        assertEquals(Model.Mark.O, model.getPlayer());
        try {
            model.mark(0);
            fail("Two times mark(0) is not possible");
        } catch (IllegalArgumentException e) {
            assertEquals("pos must be blank.", e.getMessage());
        }
        assertFalse(model.isGameOver());
        assertFalse(model.isDraw());
    }

    @Test
    public void testReset() {
        model.mark(0);
        assertNotEquals(Model.Mark.X, model.getPlayer());
        model.reset();
        for (Model.Mark mark : model.getMarks()) {
            assertEquals(Model.Mark.Blank, mark);
        }
        assertEquals(Model.Mark.X, model.getPlayer());
    }

    @Test
    public void testObserversAreNotified() {
        model.addObserver((o, arg) -> observersNotified = true);
        observersNotified = false;
        model.mark(0);
        assertTrue(observersNotified);
        observersNotified = false;
        model.reset();
        assertTrue(observersNotified);
    }
}