import java.lang.Math;

public class Decide {

    enum CONNECTORS {
        ANDD, ORR, NOTUSED
    }

    enum COMTYPES {
        LT, EQ, GT
    }

    final double PI = 3.1415926535;
    double coordinate_x[] = new double[100];
    double coordinate_y[] = new double[100];
    static double coordinate_x2[] = new double[100];
    static double coordinate_y2[] = new double[100];
    CONNECTORS LCM[][] = new CONNECTORS[15][15];
    static CONNECTORS LCM2[][] = new CONNECTORS[15][15];
    boolean PUM[][] = new boolean[15][15];
    static boolean PUM2[][] = new boolean[15][15];
    boolean CMV[] = new boolean[15];
    static boolean CMV2[] = new boolean[15];
    boolean FUV[] = new boolean[15];
    static boolean FUV2[] = new boolean[15];
    boolean PUV[] = new boolean[15];
    static boolean PUV2[] = new boolean[15];
    boolean LAUNCH;
    static boolean LAUNCH2;
    int NUMPOINTS;
    static int NUMPOINTS2;

    class Parameters_T {
        double LENGTH1; // Length in LICs 0,7 , 12
        double RADIUS1; // Radius in LICs 1 , 8 , 13
        double EPSILON; // Deviation from PI in LICs 2 , 9
        double AREA1; // Area in LICs 3, 10 , 14
        int QPTS; // No. of consecutive points in LIC 4
        int QUADS; // No. of quadrants in LIC 4
        double DIST; // Distance in LIC 6
        int NPTS; // No. of consecutive pts. in LIC 6
        int KPTS; // No. of int. pts. in LICs 7 , 12
        int APTS; // No. of int. pts. in LICs 8 , 13
        int BPTS; // No. of int. pts. in LICs 8 , 13
        int CPTS; // No. of int. pts. in LIC 9
        int DPTS; // No. of int. pts. in LIC 9
        int EPTS; // No. of int. pts. in LICs 1 0 , 14
        int FPTS; // No. of int. pts. in LICs 1 0 , 14
        int GPTS; // No. of int. pts. in LIC 11
        double LENGTH2; // Maximum length in LIC 12
        double RADIUS2; // Maximum radius in LIC 13
        double AREA2; // Maximum area in LIC 14
    }

    void DECIDE() {

    }

    boolean lic0(Parameters_T variables) {
        int i = 0;
        int j = i + 1;
        while (i < NUMPOINTS && j < NUMPOINTS) {
            double distance = Math.sqrt(Math.pow((coordinate_x[j] - coordinate_x[i]), 2)
                    + Math.pow((coordinate_y[j] - coordinate_y[i]), 2));
            if (distance > variables.LENGTH1) {
                return true;
            } else {
                i++;
                j++;
            }
        }
        return false;
    }

    boolean lic1(Parameters_T vars) {
        if (!(0 <= vars.RADIUS1)) {
            return false;
        }
        for (int i = 0; i < NUMPOINTS - 2; i++) {

            if (circleRadius(coordinate_x[i], coordinate_x[i + 1], coordinate_x[i + 2], coordinate_y[i],
                    coordinate_y[i + 1], coordinate_y[i + 2], vars.RADIUS1)) {
                return true;
            }
        }
        return false;
    }

    boolean lic2(Parameters_T vars) {

        // if correct input
        if (!(0 <= vars.EPSILON && vars.EPSILON < PI)) {
            // System.err.println("error in input");
            return false;
        }

        for (int i = 0; i < NUMPOINTS; i++) {
            for (int j = i + 1; j < NUMPOINTS; j++) {
                for (int k = j + 1; k < NUMPOINTS; k++) {

                    // current k
                    // center j
                    // previous i

                    double angle = Math
                            .toDegrees(Math.atan2(coordinate_x[k] - coordinate_x[j], coordinate_y[k] - coordinate_y[j])
                                    - Math.atan2(coordinate_x[i] - coordinate_x[j], coordinate_y[i] - coordinate_y[j]));

                    if (angle < PI - vars.EPSILON || angle > PI + vars.EPSILON) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean lic3(Parameters_T vars) {

        for (int i = 0; i < NUMPOINTS - 2; i++) {
            double c1 = coordinate_x[i] * (coordinate_y[i + 1] - coordinate_y[i + 2]);
            double c2 = coordinate_x[i + 1] * (coordinate_y[i + 2] - coordinate_y[i]);
            double c3 = coordinate_x[i + 2] * (coordinate_y[i] - coordinate_y[i + 1]);
            double area = Math.abs((c1 + c2 + c3) / 2);
            if (vars.AREA1 <= area) {
                return true;
            }
        }
        return false;
    }

    boolean lic4(Parameters_T vars) {

        if (!((2 <= vars.QPTS && vars.QPTS <= NUMPOINTS) || (1 <= vars.QUADS && vars.QUADS <= 3))) {
            return false;
        }
        for (int i = 0; i <= NUMPOINTS - vars.QPTS; i++) {
            int Q1 = 0;
            int Q2 = 0;
            int Q3 = 0;
            int Q4 = 0;
            for (int j = 0; j < vars.QPTS; j++) {
                if (coordinate_x[i + j] >= 0 && coordinate_y[i + j] >= 0) {
                    Q1 = 1;
                } else if (coordinate_x[i + j] < 0 && coordinate_y[i + j] >= 0) {
                    Q2 = 1;
                } else if (coordinate_x[i + j] <= 0 && coordinate_y[i + j] < 0) {
                    Q3 = 1;
                } else if (coordinate_x[i + j] > 0 && coordinate_y[i + j] < 0) {
                    Q4 = 1;
                }
            }
            int quadrants = Q1 + Q2 + Q3 + Q4;
            if (quadrants > vars.QUADS) {
                return true;
            }
        }
        return false;

    }

    /**
     * LIC5 checks if there exists at least one set of two consecutive data points,
     * (X[i],Y[i]) and (X[j],Y[j]), such that X[j] - X[i] < 0. (where i = j-1)
     */
    boolean lic5() {
        for (int i = 0, j = 1; j < NUMPOINTS; i++, j++) {
            if (coordinate_x[j] - coordinate_x[i] < 0.0) {
                return true;
            }
        }
        return false;
    }

    boolean lic6(Parameters_T vars) {
        if (NUMPOINTS < 3) {
            return false;
        }

        // Coordinates for the points in the set
        double x_coord_set[] = new double[vars.NPTS];
        double y_coord_set[] = new double[vars.NPTS];

        // Representation for the line in format ax + by + c = 0
        double a, b, c;
        double distance;
        // If last and first point in set aren't the same point.
        if (vars.NPTS != NUMPOINTS) {
            for (int i = 0; i < NUMPOINTS; i++) {
                // Build a set
                for (int j = 0; j < vars.NPTS; j++) {
                    x_coord_set[j] = coordinate_x[i + j];
                    y_coord_set[j] = coordinate_y[i + j];
                }

                // Line representatiation
                a = y_coord_set[0] - y_coord_set[vars.NPTS - 1];
                b = x_coord_set[0] - x_coord_set[vars.NPTS - 1];
                c = 0.0 - a * x_coord_set[0] - b * y_coord_set[0];

                // Check distance (dot product)
                for (int j = 0; j < vars.NPTS; j++) {
                    distance = Math.abs(a * x_coord_set[j] + b * y_coord_set[j] + c) / Math.sqrt(a * a + b * b);
                    if (distance > vars.DIST) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            // Calculate distance from first to all other points
            for (int i = 0; i < vars.NPTS; i++) {
                for (int j = 0; j < vars.NPTS; j++) {
                    // sqrt((x1-x2)^2 + (y1-y2)^2)
                    distance = Math.sqrt(Math.pow(coordinate_x[i] - coordinate_x[j], 2)
                            + Math.pow(coordinate_y[i] - coordinate_y[j], 2));
                    if (distance > vars.DIST) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    boolean lic7(Parameters_T vars) {
        if (NUMPOINTS < 3 || !(1 <= vars.KPTS && vars.KPTS <= (NUMPOINTS - 2))) {
            return false;
        }
        for (int i = 0; i < NUMPOINTS - vars.KPTS; i++) {
            double xdelta = coordinate_x[i] - coordinate_x[i + vars.KPTS];
            double ydelta = coordinate_y[i] - coordinate_y[i + vars.KPTS];
            double distance = Math.sqrt(Math.pow(xdelta, 2) + Math.pow(ydelta, 2));
            if (distance > vars.LENGTH1) {
                return true;
            }
        }
        return false;
    }

    boolean lic8(Parameters_T vars) {
        // Not enough points to run the function
        if (NUMPOINTS < 5) {
            return false;
        }
        if (!(1 <= vars.APTS) || !(1 <= vars.BPTS) || !(vars.APTS + vars.BPTS <= NUMPOINTS - 3)) {
            return false;
        }
        for (int i = 0; i < NUMPOINTS - 2 - vars.APTS - vars.BPTS; i++) {
            if (circleRadius(coordinate_x[i], coordinate_x[i + vars.APTS + 1], coordinate_x[i + vars.BPTS + 2],
                    coordinate_y[i], coordinate_y[i + vars.APTS + 1], coordinate_y[i + vars.BPTS + 2], vars.RADIUS1)) {
                return true;
            }
        }
        return false;
    }

    boolean lic9(Parameters_T vars) {
        // if correct input
        if (!(1 <= vars.CPTS && 1 <= vars.DPTS && vars.CPTS + vars.DPTS <= NUMPOINTS - 3)) {
            return false;
        }
        if (NUMPOINTS < 5)
            return false;

        int i = 0;
        int j = i + vars.CPTS + 1;
        int k = j + vars.DPTS + 1;

        for (i = 0; k < NUMPOINTS; i++) {
            j = i + vars.CPTS;
            k = j + vars.DPTS;

            // first i
            // second j
            // third k
            double angle = Math
                    .toDegrees(Math.atan2(coordinate_x[k] - coordinate_x[j], coordinate_y[k] - coordinate_y[j])
                            - Math.atan2(coordinate_x[i] - coordinate_x[j], coordinate_y[i] - coordinate_y[j]));

            if (angle < PI - vars.EPSILON || angle > PI + vars.EPSILON) {
                CMV[9] = true;
                return true;
            }
        }
        return false;
    }

    boolean lic10(Parameters_T variables) {
        if (NUMPOINTS >= 5 && variables.EPTS >= 1 && variables.FPTS >= 1
                && (variables.EPTS + variables.FPTS) <= (NUMPOINTS - 3)) {
            int i = 0;
            int j = i + variables.EPTS + 1;
            int k = j + variables.FPTS + 1;
            while (i < NUMPOINTS && j < NUMPOINTS && k < NUMPOINTS) {
                double sideLengthA = Math.sqrt(Math.pow((coordinate_x[j] - coordinate_x[i]), 2)
                        + Math.pow((coordinate_y[j] - coordinate_y[i]), 2));
                double sideLengthB = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[j]), 2)
                        + Math.pow((coordinate_y[k] - coordinate_y[j]), 2));
                double sideLengthC = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[i]), 2)
                        + Math.pow((coordinate_y[k] - coordinate_y[i]), 2));
                double s = (sideLengthA + sideLengthB + sideLengthC) / 2;
                double Area = Math.sqrt(s * (s - sideLengthA) * (s - sideLengthB) * (s - sideLengthC));
                if (Area > variables.AREA1) {
                    return true;
                } else {
                    i++;
                    j++;
                    k++;
                }
            }
        }
        return false;
    }

    boolean lic11(Parameters_T variables) {
        if (NUMPOINTS >= 3 && variables.GPTS >= 1 && variables.GPTS <= NUMPOINTS - 2) {
            int i = 0;
            int j = i + variables.GPTS + 1;
            while (i < NUMPOINTS && j < NUMPOINTS) {
                double difference = coordinate_x[j] - coordinate_x[i];
                if (difference < 0) {
                    return true;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return false;
    }

    boolean lic12(Parameters_T variables) {
        boolean condition1 = false;
        boolean condition2 = false;
        if (NUMPOINTS >= 3 && variables.LENGTH2 >= 0 && NUMPOINTS >= variables.KPTS + 2) {
            int i = 0;
            int j = i + variables.KPTS + 1;

            while (j < NUMPOINTS) {

                double distance = Math.sqrt(Math.pow((coordinate_x[j] - coordinate_x[i]), 2)
                        + Math.pow((coordinate_y[j] - coordinate_y[i]), 2));

                // check for condition 1
                if (distance > variables.LENGTH1) {
                    condition1 = true;
                }

                // check for condition 2
                if (distance < variables.LENGTH2) {
                    condition2 = true;
                }

                if (condition1 == true && condition2 == true) {
                    return true;
                }
                i++;
                j++;
            }
        }
        return false;
    }

    boolean lic13(Parameters_T variables) {
        boolean condition1 = false;
        boolean condition2 = false;
        if (NUMPOINTS < 5) {
            return false;

        } else if (NUMPOINTS >= 5 && variables.RADIUS2 >= 0 && NUMPOINTS >= variables.APTS + variables.BPTS + 3) {
            // first set of points
            int i = 0;
            int j = i + variables.APTS + 1;
            int k = j + variables.BPTS + 1;

            // second set of points
            int x = 0;
            int y = x + variables.APTS + 1;
            int z = y + variables.BPTS + 1;
            double distanceij = Math.sqrt(Math.pow((coordinate_x[j] - coordinate_x[i]), 2)
                    + Math.pow((coordinate_y[j] - coordinate_y[i]), 2));
            double distancejk = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[j]), 2)
                    + Math.pow((coordinate_y[k] - coordinate_y[j]), 2));
            double distanceik = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[i]), 2)
                    + Math.pow((coordinate_y[k] - coordinate_y[i]), 2));
            double diameter1 = variables.RADIUS1 * 2;
            double distancexy = Math.sqrt(Math.pow((coordinate_x[y] - coordinate_x[x]), 2)
                    + Math.pow((coordinate_y[y] - coordinate_y[x]), 2));
            double distanceyz = Math.sqrt(Math.pow((coordinate_x[z] - coordinate_x[y]), 2)
                    + Math.pow((coordinate_y[z] - coordinate_y[y]), 2));
            double distancexz = Math.sqrt(Math.pow((coordinate_x[z] - coordinate_x[x]), 2)
                    + Math.pow((coordinate_y[z] - coordinate_y[x]), 2));

            double diameter2 = variables.RADIUS2 * 2;

            while (k < NUMPOINTS && z < NUMPOINTS) {
                // first condition

                if (distanceij >= diameter1 || distancejk >= diameter1 || distanceik >= diameter1) {
                    condition1 = true;
                } else {
                    i++;
                    j++;
                    k++;
                }

                // second condition

                if (distancexy <= diameter2 && distanceyz <= diameter2 && distancexz <= diameter2) {
                    condition2 = true;
                } else {
                    x++;
                    y++;
                    z++;
                }
                if (condition1 == true && condition2 == true) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean lic14(Parameters_T variables) {
        boolean condition1 = false;
        boolean condition2 = false;
        
        if (NUMPOINTS >= 5 && variables.AREA2 >= 0) {

            // set of points
            int i = 0;
            int j = i + variables.EPTS + 1;
            int k = j + variables.FPTS + 1;

           

            while ( k < NUMPOINTS ) {
                // first condition
                double sideLengthA = Math.sqrt(Math.pow((coordinate_x[j] - coordinate_x[i]), 2) + Math.pow((coordinate_y[j] - coordinate_y[i]), 2));
                double sideLengthB = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[j]), 2) + Math.pow((coordinate_y[k] - coordinate_y[j]), 2));
                double sideLengthC = Math.sqrt(Math.pow((coordinate_x[k] - coordinate_x[i]), 2) + Math.pow((coordinate_y[k] - coordinate_y[i]), 2));
                double s = (sideLengthA + sideLengthB + sideLengthC) / 2;
                double Area = Math.sqrt(s * (s - sideLengthA) * (s - sideLengthB) * (s - sideLengthC));
               
                if (Area > variables.AREA1) {
                    condition1 = true;
                } 

               
                if (Area < variables.AREA2) {
                    condition2 = true;
                } 
                if (condition1 == true && condition2 == true) {
                    return true;
                }
                i++;
                j++;
                k++;
            }
        }
        return false;
    }

    void setupPUM() {
        for (int i = 0; i < PUM.length; i++) {
            for (int j = 0; j < PUM.length; j++) {
                if (LCM[i][j].equals(CONNECTORS.NOTUSED)) {
                    PUM[i][j] = true;
                } else if (LCM[i][j].equals(CONNECTORS.ANDD)) {
                    PUM[i][j] = (CMV[i] && CMV[j]);
                } else { // if(LCM[i][j].equals(CONNECTORS.ORR)){
                    PUM[i][j] = (CMV[i] || CMV[j]);
                }
            }
        }
    }

    void setupFUV() {
        for (int i = 0; i < FUV.length; i++) {
            if (PUV[i] == false) {
                FUV[i] = true;
                continue;
            }
            for (int j = 0; j < FUV.length; j++) {
                if (PUM[i][j] == false) {
                    FUV[i] = false;
                    break;
                }
                FUV[i] = true;
            }
        }
    }

    boolean launch() {
        LAUNCH = true;
        for (int i = 0; i < FUV.length; i++) {
            if (FUV[i] == false) {
                LAUNCH = false;
            }
        }
        return LAUNCH;
    }

    /**
     * Helper function for LIC1 and LIC8 Creates and returns a radius of a circle
     * containing three supplied points.
     */
    boolean circleRadius(double x1, double x2, double x3, double y1, double y2, double y3, double radius) {
        double diameter = radius * 2;
        double sideA = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double sideB = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
        double sideC = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));
        if (diameter < sideA) {
            return true;
        }
        if (diameter < sideB) {
            return true;
        }

        if (diameter < sideC) {
            return true;
        }
        double x = ((x1 * x1 + y1 * y1) * (y2 - y3) + (x2 * x2 + y2 * y2) * (y3 - y1) + (x3 * x3 + y3 * y3) * (y1 - y2))
                / (2 * (x1 * ((y2 - y3)) - y1 * (x2 - x3) + (x2 * y3) - (x3 * y2)));
        double y = ((x1 * x1 + y1 * y1) * (x3 - x2) + (x2 * x2 + y2 * y2) * (x1 - x3) + (x3 * x3 + y3 * y3) * (x2 - x1))
                / (2 * (x1 * ((y2 - y3)) - y1 * (x2 - x3) + (x2 * y3) - (x3 * y2)));
        double circleRad = Math.sqrt(Math.pow((x - x1), 2) + Math.pow((y - y1), 2));
        if (radius > circleRad) {
            return true;
        } else {
            return false;
        }
    }
}