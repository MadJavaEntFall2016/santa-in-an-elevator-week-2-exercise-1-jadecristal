package edu.madisoncollege.entjava;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by cwinebrenner on 9/20/16.
 */
public class TestSantaInAnElevator {

    private static SantaInAnElevator santa;
    private final Logger logger = Logger.getLogger(this.getClass());

    private static final String SANTA_DIRECTION_FILENAME = "SantaUpDown.txt";

    @Before
    public void setup() {
        santa = new SantaInAnElevator();
    }

    @Test
    public void testSantaIsReal() {
        assertTrue("Silly programmer, everyone knows Santa isn't real.", (santa instanceof SantaInAnElevator));
    }

    @Test
    public void testSantaHasEnteredTheBuilding() {
        assertEquals("Santa is lost in time and space!", 0, santa.getFloor());
    }

    @Test
    public void testSantaCanGoUp() {
        int floor = santa.getFloor();
        santa.up();
        assertEquals("The elevator won't go up!", floor + 1, santa.getFloor());
    }

    @Test
    public void testSantaCanGoDown() {
        int floor = santa.getFloor();
        santa.down();
        assertEquals("The elevator won't go down!", floor - 1, santa.getFloor());
    }

    @Test
    public void testTellSantaToGoUp() {
        String santaUpDirections = "(";
        int startingFloor = santa.getFloor();

        santa.giveDirections(santaUpDirections);
        assertEquals("Santa didn't follow the up directions.", startingFloor + 1, santa.getFloor());
    }

    @Test
    public void testTellSantaToGoDown() {
        String santaDownDirections = ")";
        int startingFloor = santa.getFloor();

        santa.giveDirections(santaDownDirections);
        assertEquals("Santa didn't follow the down directions.", startingFloor - 1, santa.getFloor());
    }

    @Test
    public void testSantaDirectionsEnd() {
        String santaDirections = "\n";
        int startingFloor = santa.getFloor();

        santa.giveDirections(santaDirections);
        assertEquals("Santa is in a time loop where the directions can't end!", startingFloor, santa.getFloor());
    }

    @Test(expected = IllegalStateException.class)
    public void testSantaGivenInvalidMovementInstruction() {
        String santaDirections = "Get back to the North Pole.";

        santa.giveDirections(santaDirections);
    }

    @Test
    public void testSantaInstructionOrderDoesNotMatter() {
        String santaDirectionsToFloorThreeA = "(((()";
        String santaDirectionsToFloorThreeB = "(()((";
        final int santaDirectionsAreToFloor = 3;

        santa = new SantaInAnElevator();
        santa.giveDirections(santaDirectionsToFloorThreeA);
        int floorFromDirectionsToThreeA = santa.getFloor();
        assertEquals("Santa didn't make it to floor three via A directions!", santaDirectionsAreToFloor, santa.getFloor());

        santa = new SantaInAnElevator();
        santa.giveDirections(santaDirectionsToFloorThreeB);
        int floorFromDirectionsToThreeB = santa.getFloor();
        assertEquals("Santa didn't make it to floor three via B directions!", santaDirectionsAreToFloor, santa.getFloor());

        assertEquals("Santa didn't make it to the same floor from the same buttons!", floorFromDirectionsToThreeA, floorFromDirectionsToThreeB);
    }

    @Test
    public void testSantaCanReadDirections() {
        try {
            assertTrue(santa.readSantaDirections(SANTA_DIRECTION_FILENAME) instanceof String);
        } catch (Exception ioe) {
            fail("Should be able to read the defined file name, otherwise, what's Santa doing in the building?");
        }
    }

    // I have a problem. *I KNOW! I'll use regular expressions!*
    // Santa might not want to do anything but get out of the elevator, or might want to go up or down some.
    @Test
    public void testSantaDirectionsAreValid() {
        final String VALID_SANTA_DIRECTIONS_PATTERN = "^[()]*\\s*$";
        try {
            assertTrue("Santa's directions contain some funny things...", santa.readSantaDirections(SANTA_DIRECTION_FILENAME).matches(VALID_SANTA_DIRECTIONS_PATTERN));
        } catch (Exception ioe) {
            fail("Should not give Santa bad directions; this class doesn't test for that.");
        }
    }

    // TODO: Ask Paula WHY this versus the IllegalStateException test differ in how I do them.
    @Test
    public void testSantaCanNotFindDirections() {
        try {
            santa.readSantaDirections("BlankPaperForSanta.txt");
        } catch (Exception e) {
            assertTrue("You can't give Santa papers that don't exist!", true);
        }
    }

    @Test
    public void testWhatFloorSantaIsOn()  {
        String santaDirections = null;
        try {
            santaDirections = santa.readSantaDirections(SANTA_DIRECTION_FILENAME);
        } catch (Exception e) {
            fail("Unexpected exception inside testWhatFloorSantaIsOn.");
        }
        santa.giveDirections(santaDirections);
        logger.info("Santa is on floor " + santa.getFloor());
    }

}