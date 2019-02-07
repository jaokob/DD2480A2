import static org.junit.jupiter.api.Assertions.*;

class DecideTest {

    /**
     * Descriptive text of said test, to be removed before merging with master
     */
    @org.junit.jupiter.api.Test
    void test_DECIDE() {
        Decide d = new Decide();
        // d.lic1();
        assertEquals(1, 1);
    }

    /**
     * Test of setupPUM, see "Example 1" in Requirements Specification
     */

    @org.junit.jupiter.api.Test
    void test_PUM() {
        Decide d = new Decide();
        Decide dCompare = new Decide();

        // Fill LCM Matrix
        for (int i = 0; i < d.LCM.length; i++) {
            for (int j = 0; j < d.LCM.length; j++) {
                d.LCM[i][j] = Decide.CONNECTORS.NOTUSED;
            }
        }

        d.LCM[0][0] = Decide.CONNECTORS.ANDD;
        d.LCM[0][1] = Decide.CONNECTORS.ANDD;
        d.LCM[0][2] = Decide.CONNECTORS.ORR;
        d.LCM[0][3] = Decide.CONNECTORS.ANDD;
        d.LCM[1][0] = Decide.CONNECTORS.ANDD;
        d.LCM[2][0] = Decide.CONNECTORS.ORR;
        d.LCM[3][0] = Decide.CONNECTORS.ANDD;

        d.LCM[1][1] = Decide.CONNECTORS.ANDD;
        d.LCM[1][2] = Decide.CONNECTORS.ORR;
        d.LCM[1][3] = Decide.CONNECTORS.ORR;
        d.LCM[2][1] = Decide.CONNECTORS.ORR;
        d.LCM[3][1] = Decide.CONNECTORS.ORR;

        d.LCM[2][2] = Decide.CONNECTORS.ORR;
        d.LCM[2][3] = Decide.CONNECTORS.ANDD;
        d.LCM[3][2] = Decide.CONNECTORS.ANDD;

        d.LCM[3][3] = Decide.CONNECTORS.ANDD;

        // Fill CMV vector
        d.CMV[0] = false;
        d.CMV[1] = true;
        d.CMV[2] = true;
        d.CMV[3] = true;
        d.CMV[4] = false;

        // Fill PUM
        for (int i = 0; i < dCompare.PUM.length; i++) {
            for (int j = 0; j < dCompare.PUM.length; j++) {
                dCompare.PUM[i][j] = true;
            }
        }

        dCompare.PUM[0][0] = false;
        dCompare.PUM[0][1] = false;
        dCompare.PUM[0][2] = true;
        dCompare.PUM[0][3] = false;
        dCompare.PUM[1][0] = false;
        dCompare.PUM[2][0] = true;
        dCompare.PUM[3][0] = false;

        dCompare.PUM[1][1] = true;
        dCompare.PUM[1][2] = true;
        dCompare.PUM[1][3] = true;
        dCompare.PUM[2][1] = true;
        dCompare.PUM[3][1] = true;

        dCompare.PUM[2][2] = true;
        dCompare.PUM[2][3] = true;
        dCompare.PUM[3][2] = true;

        dCompare.PUM[3][3] = true;

        d.setupPUM();

        // Compare
        for (int i = 0; i < d.PUM.length; i++) {
            for (int j = 0; j < d.PUM.length; j++) {
                assertEquals(d.PUM[i][j], dCompare.PUM[i][j]);
            }
        }
    }

    void test_FUV() {
        Decide d = new Decide();

        // Fill PUM
        for (int i = 0; i < d.PUM.length; i++) {
            for (int j = 0; j < d.PUM.length; j++) {
                d.PUM[i][j] = true;
            }
        }

        d.PUM[0][0] = false;
        d.PUM[0][1] = false;
        d.PUM[0][2] = true;
        d.PUM[0][3] = false;
        d.PUM[1][0] = false;
        d.PUM[2][0] = true;
        d.PUM[3][0] = false;

        d.PUM[1][1] = true;
        d.PUM[1][2] = true;
        d.PUM[1][3] = true;
        d.PUM[2][1] = true;
        d.PUM[3][1] = true;

        d.PUM[2][2] = true;
        d.PUM[2][3] = true;
        d.PUM[3][2] = true;

        d.PUM[3][3] = true;

        d.PUV[0] = true;
        d.PUV[1] = false;
        d.PUV[2] = true;
        d.PUV[3] = true;
        d.PUV[4] = true;
        d.PUV[5] = true;
        d.PUV[6] = true;
        d.PUV[7] = true;
        d.PUV[8] = true;
        d.PUV[9] = true;
        d.PUV[10] = true;
        d.PUV[11] = true;
        d.PUV[12] = true;
        d.PUV[13] = true;
        d.PUV[14] = true;

        // NOTE: Requirements Specificaton paper has wrong example value FUV[3}
        boolean compareFUV[] = { false, true, true, false, true, true, true, true, true, true, true, true, true, true,
                true };

        d.setupFUV();

        // Compare
        for (int i = 0; i < d.PUM.length; i++) {
            assertEquals(compareFUV[i], d.FUV[i]);
        }
    }

    /**
     * Test of Launch.
     */
    @org.junit.jupiter.api.Test
    void test_launch() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();

        // initial should be false
        d.LAUNCH = d.launch();
        assertEquals(d.LAUNCH, false);

        d.FUV[0] = true;
        d.LAUNCH = d.launch();
        assertEquals(d.LAUNCH, false);

        for (int i = 0; i < d.FUV.length; i++) {
            d.FUV[i] = true;
        }
        d.LAUNCH = d.launch();
        assertEquals(d.LAUNCH, true);
    }

    /**
     * Test of Lic0.
     */
    @org.junit.jupiter.api.Test
    void test_Lic0() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.LENGTH1 = 4;
        d.NUMPOINTS = 3;
        d.coordinate_x[0] = -3;
        d.coordinate_y[0] = 4;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 1;
        d.coordinate_y[2] = -4;
        // Tests that there is atleast 1 set of point more than LENGTH1 apart
        d.CMV[0] = d.lic0(test);
        assertEquals(d.CMV[0], true);
    }

    /**
     * Test of Lic1.
     */
    @org.junit.jupiter.api.Test
    void test_Lic1() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.EPSILON = 0.0000001;
        test.RADIUS1 = 4;
        d.NUMPOINTS = 3;
        d.coordinate_x[0] = -3;
        d.coordinate_y[0] = 4;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 1;
        d.coordinate_y[2] = -4;
        // Tests that the three points (Area of 5) cannot be contained within an circle
        // of area 4
        assertEquals(true, d.lic1(test));
        d.coordinate_x[3] = 4;
        d.coordinate_y[3] = 5;
        d.coordinate_x[1] = 1;
        d.coordinate_y[1] = -4;
        d.coordinate_x[2] = 1;
        d.coordinate_y[2] = 40;
        d.coordinate_x[0] = 20;
        d.coordinate_y[0] = 30;
        // test.RADIUS1 = 5;
        d.NUMPOINTS = 4;
        test.RADIUS1 = 50;
        // Added one point, now should return true since radius of 22 can and 55 cannot
        // be contained within 50
        assertEquals(true, d.lic1(test));
        d.NUMPOINTS = 3;
        d.coordinate_x[0] = -3;
        d.coordinate_y[0] = 4;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 1;
        d.coordinate_y[2] = -4;
        test.RADIUS1 = 5;
        assertEquals(false, d.lic1(test));
    }

    /**
     * Test of Lic2.
     */
    @org.junit.jupiter.api.Test
    void test_lic2() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.EPSILON = 0.0000001;
        d.NUMPOINTS = 8;
        d.coordinate_x[0] = 143098.509;
        d.coordinate_y[0] = 855368.983;
        d.coordinate_x[1] = -486139.466;
        d.coordinate_y[1] = -978699.884;
        d.coordinate_x[2] = -797455.071;
        d.coordinate_y[2] = 399387.456;
        d.coordinate_x[3] = 23223.96;
        d.coordinate_y[3] = 898137.168;
        d.coordinate_x[4] = -207721.063;
        d.coordinate_y[4] = 616197.11;
        d.coordinate_x[5] = -731354.24;
        d.coordinate_y[5] = -559211.04;
        d.coordinate_x[6] = -717683.928;
        d.coordinate_y[6] = 652027.248;
        d.coordinate_x[7] = 445046.026;
        d.coordinate_y[7] = 103778.97;
        assertEquals(d.CMV[2], false);
        d.CMV[2] = d.lic2(test);
        assertEquals(d.CMV[2], true);
    }

    /**
     * Test of Lic3.
     */
    @org.junit.jupiter.api.Test
    void test_lic3() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.AREA1 = 40;
        d.NUMPOINTS = 6;
        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = 5.0;
        d.coordinate_y[1] = 0.0;
        d.coordinate_x[2] = 5.0;
        d.coordinate_y[2] = 5.0;
        d.coordinate_x[3] = 0.0;
        d.coordinate_y[3] = 0.0;
        d.coordinate_x[4] = -10;
        d.coordinate_y[4] = 0;
        d.coordinate_x[5] = -10;
        d.coordinate_y[5] = -10;
        // True that area greater than 40 can be created from three cons. points
        assertEquals(true, d.lic3(test));
        Decide d2 = new Decide();
        Decide.Parameters_T test2 = d2.new Parameters_T();
        test2.AREA1 = 400;
        d2.NUMPOINTS = 6;
        d2.coordinate_x[0] = 0.0;
        d2.coordinate_y[0] = 0.0;
        d2.coordinate_x[1] = 5.0;
        d2.coordinate_y[1] = 0.0;
        d2.coordinate_x[2] = 5.0;
        d2.coordinate_y[2] = 5.0;
        d2.coordinate_x[3] = 0.0;
        d2.coordinate_y[3] = 0.0;
        d2.coordinate_x[4] = -10;
        d2.coordinate_y[4] = 0;
        d2.coordinate_x[5] = -10;
        d2.coordinate_y[5] = -10;
        // Area greater than 400 can't be created from same points
        assertEquals(false, d2.lic3(test2));
    }

    /**
     * Test of Lic4.
     */
    @org.junit.jupiter.api.Test
    void test_lic4() {
        // Test edge cases
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.QPTS = 4;
        test.QUADS = 3;
        d.NUMPOINTS = 4;
        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = -1.0;
        d.coordinate_y[1] = 0.0;
        d.coordinate_x[2] = 0.0;
        d.coordinate_y[2] = -1.0;
        d.coordinate_x[3] = 1.0;
        d.coordinate_y[3] = -1.0;
        assertEquals(false, d.CMV[4]);
        d.CMV[4] = d.lic4(test);
        assertEquals(true, d.CMV[4]);

        // Test of parameter requirements
        Decide d2 = new Decide();
        Decide.Parameters_T test2 = d2.new Parameters_T();
        test2.QPTS = 1;
        d2.NUMPOINTS = 4;
        d2.coordinate_x[0] = 0.0;
        d2.coordinate_y[0] = 0.0;
        d2.coordinate_x[1] = -1.0;
        d2.coordinate_y[1] = 0.0;
        d2.coordinate_x[2] = 0.0;
        d2.coordinate_y[2] = -1.0;
        d2.coordinate_x[3] = 1.0;
        d2.coordinate_y[3] = -1.0;
        assertEquals(false, d2.lic4(test2));
        test2.QPTS = 5;
        assertEquals(false, d2.lic4(test2));
        test2.QUADS = 4;
        test2.QPTS = 3;
        assertEquals(false, d2.lic4(test2));
        test2.QUADS = 3;
        test2.QPTS = 4;
        assertEquals(true, d2.lic4(test2));
    }

    /**
     * Test of Lic5 with sequential points that are strictly increasing. Should
     * return false.
     */
    @org.junit.jupiter.api.Test
    void test_lic5_false() {
        Decide d = new Decide();
        d.NUMPOINTS = 100;
        double[] x = new double[d.NUMPOINTS];
        for (int i = 0; i < d.NUMPOINTS; i++) {
            x[i] = i;
        }
        d.coordinate_x = x;
        assertFalse(d.lic5());
    }

    /**
     * Test of Lic5 with points that fulfill the requirement, i.e. some set of two
     * sequential nodes are decreasing. Should return true.
     */
    @org.junit.jupiter.api.Test
    void test_lic5_true() {
        Decide d = new Decide();
        d.NUMPOINTS = 100;
        double[] x = new double[d.NUMPOINTS];
        for (int i = 0; i < d.NUMPOINTS; i++) {
            x[d.NUMPOINTS - 1 - i] = i;
        }
        d.coordinate_x = x;
        assertTrue(d.lic5());
    }

    /**
     * Test of Lic5 with only the two first points fulfilling the requirement.
     * Should return true.
     */
    @org.junit.jupiter.api.Test
    void test_lic5_first_points() {
        Decide d = new Decide();
        d.NUMPOINTS = 100;
        double[] x = new double[d.NUMPOINTS];
        for (int i = 0; i < d.NUMPOINTS; i++) {
            x[i] = i;
        }
        x[0] = 1000.0;
        d.coordinate_x = x;
        assertTrue(d.lic5());
    }

    /**
     * Test of Lic5 with only the two last points fulfilling the requirement. Should
     * return true.
     */
    @org.junit.jupiter.api.Test
    void test_lic5_last_ponts() {
        // Test with only the two last points fulfilling the requirement.
        Decide d = new Decide();
        d.NUMPOINTS = 100;
        double[] x = new double[d.NUMPOINTS];
        for (int i = 0; i < d.NUMPOINTS; i++) {
            x[i] = i;
        }
        x[98] = 1000.0;
        d.coordinate_x = x;
        assertTrue(d.lic5());

    }

    /**
     * Test of Lic5 with only 5 points. Less than 5 will cause DECIDE to fail.
     * Should return true.
     */
    @org.junit.jupiter.api.Test
    void test_lic5() {
        // Test with only 5 points. Less than 5 will cause DECIDE to fail.
        Decide d = new Decide();
        d.NUMPOINTS = 5;
        double[] x = new double[d.NUMPOINTS];
        for (int i = 0; i < d.NUMPOINTS; i++) {
            x[d.NUMPOINTS - 1 - i] = i;
        }
        d.coordinate_x = x;
        assertTrue(d.lic5());
    }

    /**
     * Test of Lic6
     */
    @org.junit.jupiter.api.Test
    void test_Lic6() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();

        // Consecutive nodes have a distance greater than DIST to a line parallel to the
        // x-axis, NPTS = NUMPOINTS
        assertEquals(1, 1);

        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = 3.0;
        d.coordinate_y[1] = 10.0;
        d.coordinate_x[2] = 5.0;
        d.coordinate_y[2] = 0.0;

        d.NUMPOINTS = 3;

        test.DIST = 1.0;
        test.NPTS = 3;

        assertTrue(d.lic6(test));

        // Consecutive nodes have a distance greater than DIST to a line parallel to the
        // x-axis, NPTS != NUMPOINTS

        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = 3.0;
        d.coordinate_y[1] = 10.0;
        d.coordinate_x[2] = 5.0;
        d.coordinate_y[2] = 0.0;
        d.coordinate_x[3] = 5.0;
        d.coordinate_y[3] = 0.0;

        d.NUMPOINTS = 4;

        test.DIST = 2.0;
        test.NPTS = 3;

        assertTrue(d.lic6(test));

        // Consecutive nodes doesn't have distance greater than DIST to a line parallel
        // to the x-axis NPTS = NUMPOINTS

        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = 3.0;
        d.coordinate_y[1] = 1.0;
        d.coordinate_x[2] = 5.0;
        d.coordinate_y[2] = 0.0;

        d.NUMPOINTS = 3;

        test.DIST = 5.0;
        test.NPTS = 3;

        assertFalse(d.lic6(test));

        // Consecutive nodes doesn't have distance greater than DIST to a line parallel
        // to the x-axis NPTS != NUMPOINTS

        d.coordinate_x[0] = 0.0;
        d.coordinate_y[0] = 0.0;
        d.coordinate_x[1] = 3.0;
        d.coordinate_y[1] = 1.0;
        d.coordinate_x[2] = 5.0;
        d.coordinate_y[2] = 0.0;
        d.coordinate_x[3] = 5.0;
        d.coordinate_y[3] = 0.0;

        d.NUMPOINTS = 4;

        test.DIST = 5.0;
        test.NPTS = 3;

        assertFalse(d.lic6(test));

    }

    @org.junit.jupiter.api.Test
    void test_lic7() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.LENGTH1 = 10000000000000000.0;
        test.KPTS = 2;
        // FAIL numpoint requirement
        d.NUMPOINTS = 2;
        assertEquals(false, d.lic7(test));
        d.coordinate_x[0] = 143098.509;
        d.coordinate_y[0] = 855368.983;
        d.coordinate_x[1] = -486139.466;
        d.coordinate_y[1] = -978699.884;
        d.coordinate_x[2] = -797455.071;
        d.coordinate_y[2] = 399387.456;
        d.coordinate_x[3] = 23223.96;
        d.coordinate_y[3] = 898137.168;
        d.coordinate_x[4] = -207721.063;
        d.coordinate_y[4] = 616197.11;
        d.coordinate_x[5] = -731354.24;
        d.coordinate_y[5] = -559211.04;
        d.coordinate_x[6] = -717683.928;
        d.coordinate_y[6] = 652027.248;
        d.coordinate_x[7] = 445046.026;
        d.coordinate_y[7] = 103778.97;

        // change numpoint but still fail due to big length1
        d.NUMPOINTS = 8;
        assertEquals(false, d.lic7(test));
        test.LENGTH1 = 1000;
        assertEquals(true, d.lic7(test));
    }

    @org.junit.jupiter.api.Test
    void test_lic8() {
        Decide d = new Decide();
        Decide.Parameters_T test = d.new Parameters_T();
        test.BPTS = 0;
        test.APTS = 2;
        d.NUMPOINTS = 20;
        // Fail due to bad input (BTPS = 0)
        assertEquals(false, d.lic8(test));
        test.BPTS = 2;
        test.APTS = 0;
        // Still fail due to APTS = 0
        assertEquals(false, d.lic8(test));
        test.APTS = 2;
        d.NUMPOINTS = 3;
        // fail due to NUMPOINTS being too small
        assertEquals(false, d.lic8(test));
        d.NUMPOINTS = 6;
        // APTS + BPTS >= NUMPOINTS -3
        assertEquals(false, d.lic8(test));
        d.coordinate_x[0] = 1.0;
        d.coordinate_y[0] = 1.0;
        d.coordinate_x[1] = 2.0;
        d.coordinate_y[1] = 2.0;
        d.coordinate_x[2] = 3.0;
        d.coordinate_y[2] = 3.0;
        d.coordinate_x[3] = 0.0;
        d.coordinate_y[3] = 0.0;
        d.coordinate_x[4] = 0.0;
        d.coordinate_y[4] = 0.0;
        d.coordinate_x[5] = 0.0;
        d.coordinate_y[5] = 0.0;
        d.NUMPOINTS = 6;
        test.APTS = 1;
        test.BPTS = 1;
        test.RADIUS1 = (2 * Math.sqrt(2)) / 2 - 0.1;
        // true since they cannot be contained within this radius
        assertEquals(true, d.lic8(test));
        test.RADIUS1 = 40000;
        // false since the bigger circle can contain them
        assertEquals(false, d.lic8(test));
    }

    /**
     * Test of Lic9.
     */
    @org.junit.jupiter.api.Test
    void test_lic9() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();

        // initial should be false
        assertEquals(d.CMV[9], false);
        vars.EPSILON = 0.0000001;
        // vars.EPSILON = 0.001;
        d.NUMPOINTS = 8;
        d.coordinate_x[0] = 143098.509;
        d.coordinate_y[0] = 855368.983;
        d.coordinate_x[1] = -486139.466;
        d.coordinate_y[1] = -978699.884;
        d.coordinate_x[2] = -797455.071;
        d.coordinate_y[2] = 399387.456;
        d.coordinate_x[3] = 23223.96;
        d.coordinate_y[3] = 898137.168;
        d.coordinate_x[4] = -207721.063;
        d.coordinate_y[4] = 616197.11;
        d.coordinate_x[5] = -731354.24;
        d.coordinate_y[5] = -559211.04;
        d.coordinate_x[6] = -717683.928;
        d.coordinate_y[6] = 652027.248;
        d.coordinate_x[7] = 445046.026;
        d.coordinate_y[7] = 103778.97;

        // parameter test

        vars.CPTS = 2;
        vars.DPTS = 0;
        d.CMV[9] = d.lic9(vars);
        assertEquals(d.CMV[9], false);

        vars.CPTS = 0;
        vars.DPTS = 2;
        d.CMV[9] = d.lic9(vars);
        assertEquals(d.CMV[9], false);

        vars.CPTS = 1;
        vars.DPTS = 1;
        d.CMV[9] = d.lic9(vars);
        assertEquals(d.CMV[9], true);
    }

    /**
     * Test of Lic10.
     */
    @org.junit.jupiter.api.Test
    void test_lic10() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();

        // input parameters check
        // 1) Numpoints >= 5

        vars.EPTS = 2;
        vars.FPTS = 3;
        d.NUMPOINTS = 4;
        d.CMV[10] = d.lic10(vars);
        assertEquals(d.CMV[10], false);

        // 2) (EPTS + FPTS) <= (NUMPOINTS - 3)
        vars.EPTS = 2;
        vars.FPTS = 3;
        d.NUMPOINTS = 6;
        d.CMV[10] = d.lic10(vars);
        assertEquals(d.CMV[10], false);

        // 3) EPTS >= 1 && FPTS >= 1
        vars.EPTS = -3;
        vars.FPTS = -4;
        d.NUMPOINTS = 7;
        d.CMV[10] = d.lic10(vars);
        assertEquals(d.CMV[10], false);

        // check that if parameter conditions are met and area is true, the function
        // returns true

        vars.EPTS = 2;
        vars.FPTS = 3;
        d.NUMPOINTS = 8;
        vars.AREA1 = 210.0;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 1;
        d.coordinate_y[1] = 2;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 18;
        d.coordinate_x[4] = 4;
        d.coordinate_y[4] = 5;
        d.coordinate_x[5] = 6;
        d.coordinate_y[5] = 8;
        d.coordinate_x[6] = 1;
        d.coordinate_y[6] = 3;
        d.coordinate_x[7] = 24;
        d.coordinate_y[7] = 0;
        d.CMV[10] = d.lic10(vars);
        assertEquals(d.CMV[10], true);

        // Check that the if points are positioned as required but the area requirement
        // is not fulfilled results in false
        vars.EPTS = 2;
        vars.FPTS = 3;
        d.NUMPOINTS = 8;
        vars.AREA1 = 217.0;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 1;
        d.coordinate_y[1] = 2;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 18;
        d.coordinate_x[4] = 4;
        d.coordinate_y[4] = 5;
        d.coordinate_x[5] = 6;
        d.coordinate_y[5] = 8;
        d.coordinate_x[6] = 1;
        d.coordinate_y[6] = 3;
        d.coordinate_x[7] = 24;
        d.coordinate_y[7] = 0;
        d.CMV[10] = d.lic10(vars);
        assertEquals(d.CMV[10], false);
    }

    /**
     * Test of Lic11.
     */
    @org.junit.jupiter.api.Test
    void test_lic11() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();
        // input parameters check
        // 1) Numpoints >= 3
        vars.GPTS = 2;
        d.NUMPOINTS = 2;
        d.CMV[11] = d.lic11(vars);
        assertEquals(d.CMV[11], false);

        // 2) GPTS >= 1 && GPTS <= NUMPOINTS - 2
        vars.GPTS = 4;
        d.NUMPOINTS = 5;
        d.CMV[11] = d.lic11(vars);
        assertEquals(d.CMV[11], false);

        // Check that the if Points are GPTS points apart and x[j] - x[i] <0 , it
        // returns true
        vars.GPTS = 2;
        d.NUMPOINTS = 5;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 18;
        d.coordinate_x[4] = 3;
        d.coordinate_y[4] = 5;
        d.CMV[11] = d.lic11(vars);
        assertEquals(d.CMV[11], true);
    }

       /**
     * Test of Lic12.
     */
    @org.junit.jupiter.api.Test
    void test_lic12() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();
        // input parameters check
        // 1) Numpoints >= 3
        vars.LENGTH1 = 1;
        vars.LENGTH2 = 2;
        vars.KPTS = 2;
        d.NUMPOINTS = 2;
        d.CMV[12] = d.lic12(vars);
        assertEquals(d.CMV[12], false);

        // 2) Length2 >= 0
        vars.LENGTH1 = 1;
        vars.LENGTH2 = -2;
        vars.KPTS = 2;
        d.NUMPOINTS = 2;
        d.CMV[12] = d.lic12(vars);
        assertEquals(d.CMV[12], false);

        // Check that the if parameters are correct, condition 1 is true but condition2
        // is false, function returns false
        vars.LENGTH1 = 1;
        vars.LENGTH2 = 1;
        vars.KPTS = 2;
        d.NUMPOINTS = 4;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 2;
        d.CMV[12] = d.lic12(vars);
        assertEquals(d.CMV[12], false);

        // Check that the if parameters are correct, condition 1 is flase but condition2
        // is true, function returns false
        vars.LENGTH1 = 3;
        vars.LENGTH2 = 3;
        vars.KPTS = 2;
        d.NUMPOINTS = 4;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 2;
        d.CMV[12] = d.lic12(vars);
        assertEquals(d.CMV[12], false);

        // Check that the if parameters are correct, and both conditions true, function
        // returns true
        vars.LENGTH1 = 1;
        vars.LENGTH2 = 3;
        vars.KPTS = 2;
        d.NUMPOINTS = 4;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 3;
        d.coordinate_y[2] = 4;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 2;
        d.CMV[12] = d.lic12(vars);
        assertEquals(d.CMV[12], true);

    }
    
    /**
     * Test of Lic13.
     */
    @org.junit.jupiter.api.Test
    void test_lic13() {
        Decide d = new Decide();
        Decide.Parameters_T vars = d.new Parameters_T();

        // input parameters check
        // 1) Numpoints >= 5
        vars.APTS = 1;
        vars.BPTS = 2;
        vars.RADIUS1 = 1;
        vars.RADIUS2 = 2;
        d.NUMPOINTS = 4;
        d.CMV[13] = d.lic13(vars);
        assertEquals(d.CMV[13], false);

        // 2) Radius2 >= 0
        vars.APTS = 1;
        vars.BPTS = 2;
        vars.RADIUS1 = 1;
        vars.RADIUS2 = -2;
        d.NUMPOINTS = 6;
        d.CMV[13] = d.lic13(vars);
        assertEquals(d.CMV[13], false);

        // Check that the if parameters are correct, as well as both conditions, returns
        // true
        vars.APTS = 1;
        vars.BPTS = 2;
        vars.RADIUS1 = 1;
        vars.RADIUS2 = 3;
        d.NUMPOINTS = 6;
        d.coordinate_x[0] = 0;
        d.coordinate_y[0] = 0;
        d.coordinate_x[1] = 4;
        d.coordinate_y[1] = 5;
        d.coordinate_x[2] = 2;
        d.coordinate_y[2] = 0;
        d.coordinate_x[3] = 0;
        d.coordinate_y[3] = 2;
        d.coordinate_x[4] = 4;
        d.coordinate_y[4] = 5;
        d.coordinate_x[5] = 3;
        d.coordinate_y[5] = 0;
        d.CMV[13] = d.lic13(vars);
        assertEquals(d.CMV[13], true);

    }

}
