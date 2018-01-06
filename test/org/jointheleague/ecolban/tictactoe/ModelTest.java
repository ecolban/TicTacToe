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

        Model.Mark[] marks = model.getBoard();
        for (Model.Mark mark : marks) {
            assertEquals(Model.Mark.Blank, mark);
        }
    }

    @Test
    public void testMark() {
        assertEquals(Model.Player.X, model.getPlayer());
        try {
            model.play(Model.Player.X, 9);
            fail("play(X, 9) is not possible");
        } catch (IllegalArgumentException e) {
            assertEquals("pos must be between 0 (inclusive) and 9 (exclusive).", e.getMessage());
        }

        try {
            model.play(Model.Player.O, 4);
            fail("It's not O's turn");
        } catch (IllegalStateException e) {
            assertEquals("It's not O's turn.", e.getMessage());
        }
        model.play(Model.Player.X, 0);
        assertEquals(Model.Mark.X, model.getBoard()[0]);
        assertEquals(Model.Player.O, model.getPlayer());
        try {
            model.play(Model.Player.O, 0);
            fail("Two times play(0) is not possible");
        } catch (IllegalStateException e) {
            assertEquals("pos must be blank.", e.getMessage());
        }
        assertFalse(model.isGameOver());
        assertFalse(model.isDraw());
    }

    @Test
    public void testReset() {
        model.play(Model.Player.X, 0);
        assertNotEquals(Model.Mark.X, model.getPlayer());
        model.reset(Model.Player.X);
        for (Model.Mark mark : model.getBoard()) {
            assertEquals(Model.Mark.Blank, mark);
        }
        assertEquals(Model.Player.X, model.getPlayer());
        model.reset(Model.Player.O);
        assertEquals(Model.Player.O, model.getPlayer());
    }

    @Test
    public void testObserversAreNotified() {
        model.addObserver((o, arg) -> observersNotified = true);
        observersNotified = false;
        model.play(model.getPlayer(), 0);
        assertTrue(observersNotified);
        observersNotified = false;
        model.reset(Model.Player.X);
        assertTrue(observersNotified);
    }
}