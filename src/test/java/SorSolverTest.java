import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SorSolverTest {

    public static final double EPSILON = 1e-8;

    @Test
    public void testGoodConditionality() throws Exception {
        SorSolver solver = new SorSolver(100000);

        Matrix a1 = new Matrix(new double[] {5, 2, 1},
                               new double[] {2, 5, 2},
                               new double[] {1, 2, 5});
        Vector b1 = new Vector(10, 15, 20);
        LinearEquationsSystem system1 = new LinearEquationsSystem(a1, b1);
        Solution solution1 = solver.solve(system1, EPSILON);
        Assert.assertNotNull(solution1);
        Vector answer1 = new Vector(35  / 44d,
                                    15  / 11d,
                                    145 / 44d);
        Assert.assertTrue(solution1.vector.equals(answer1, EPSILON));
        System.out.println(a1 + "Iterations: " + solution1.iterations);


        Matrix a2 = new Matrix(new double[] {50, 12, 1},
                               new double[] {11, 53, 17},
                               new double[] {10, 20, 50});
        Vector b2 = new Vector(105, 15, 20);
        LinearEquationsSystem system2 = new LinearEquationsSystem(a2, b2);
        Solution solution2 = solver.solve(system2, EPSILON);
        Assert.assertNotNull(solution2);
        Vector answer2 = new Vector( 23687 / 11063d,
                                     -1933 / 11063d,
                                       461 / 11063d);
        Assert.assertTrue(solution2.vector.equals(answer2, EPSILON));
        System.out.println(a2 + "Iterations: " + solution2.iterations);
    }

    @Test
    public void testAverageConditionality() throws Exception {
        SorSolver solver = new SorSolver(100000);

        Matrix a1 = new Matrix(new double[] {17, 15, 15},
                               new double[] {10, 12, 11},
                               new double[] {15, 10, 13});
        Vector b1 = new Vector(10, 15, 20);
        LinearEquationsSystem system1 = new LinearEquationsSystem(a1, b1);
        Solution solution1 = solver.solve(system1, EPSILON);
        Assert.assertNotNull(solution1);
        Vector answer1 = new Vector(-515 / 107d,
                -450 / 107d,
                1105 / 107d);
        Assert.assertTrue(solution1.vector.equals(answer1, EPSILON));
        System.out.println(a1 + "Iterations: " + solution1.iterations);
    }

    @Test
    public void testBadConditionality() throws Exception {
        SorSolver solver = new SorSolver(1000000);

        Matrix a1 = new Matrix(new double[] {30, 21, 21},
                               new double[] {23, 25, 23},
                               new double[] {24, 24, 27});
        Vector b1 = new Vector(10, 15, 20);
        LinearEquationsSystem system1 = new LinearEquationsSystem(a1, b1);
        Solution solution1 = solver.solve(system1, EPSILON);
        Assert.assertNotNull(solution1);
        Vector answer1 = new Vector(  -185 / 411d,
                                       -80 / 411d,
                                       180 / 137d);
        Assert.assertTrue(solution1.vector.equals(answer1, EPSILON));
        System.out.println(a1 + "Iterations: " + solution1.iterations);
    }
}