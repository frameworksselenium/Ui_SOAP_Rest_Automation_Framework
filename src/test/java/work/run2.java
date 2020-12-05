package work;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class run2 {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void testCasetwo() {

        System.out.println("*** test case two started ***");
        softAssert.assertEquals("Hello", "Hello", "First soft assert failed - testCasetwo");
        System.out.println("Soft assert success....");
        softAssert.assertTrue("Hello".equals("hello"), "Second soft assert failed - testCasetwo");
        softAssert.assertTrue("Welcome".equals("welcomeeee"), "Third soft assert failed - testCasetwo");
        System.out.println("*** test case two executed successfully ***");
        softAssert.assertAll();
    }

    @Test
    public void testCaseThree() {

        System.out.println("*** test case three started ***");
        softAssert.assertEquals("Hello", "Hello", "First soft assert failed - testCaseThree");
        System.out.println("Soft assert success....");
        softAssert.assertTrue("Hello".equals("hello"), "Second soft assert failed - testCaseThree");
        softAssert.assertTrue("Welcome".equals("welcomeeee"), "Third soft assert failed - testCaseThree");
        System.out.println("*** test case three executed successfully ***");
        softAssert.assertAll();
    }

}
