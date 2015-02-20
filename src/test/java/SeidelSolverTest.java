import org.junit.Assert;
import org.junit.Test;

public class SeidelSolverTest {

    public static final double EPSILON = 1e-8;

    @Test
    public void testGoodConditionality() throws Exception {
        SeidelSolver solver = new SeidelSolver(100000);

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


        Matrix a2 = new Matrix(new double[] {50, 12, 1},
                               new double[] {11, 53, 17},
                               new double[] {10, 20, 50});
        Vector b2 = new Vector(105, 15, 20);
        LinearEquationsSystem system2 = new LinearEquationsSystem(a2, b2);
        Solution solution2 = solver.solve(system2, EPSILON);
        Assert.assertNotNull(solution2);
        Vector answer2 = new Vector( 23687 / 11063d,
                                    -1933  / 11063d,
                                     461   / 11063d);
        Assert.assertTrue(solution2.vector.equals(answer2, EPSILON));

    }

    @Test
    public void testAverageConditionality() throws Exception {
        SeidelSolver solver = new SeidelSolver(100000);

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
        Assert.assertTrue(solution1.vector.equals(answer1, EPSILON * 100));
    }

}