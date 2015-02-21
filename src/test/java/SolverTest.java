import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tests all the solvers on different conditionality matrices.
 *
 * Created by Sergey on 21.02.2015.
 */
public class SolverTest {

    public static final int ITERATIONS_LIMIT = 100000;
    public static final double EPSILON = 1e-6;

    Map<String, Solver> solvers = new LinkedHashMap<>();

    {
        solvers.put("Jacobi method", new JacobiSolver(0.9, ITERATIONS_LIMIT));
        solvers.put("Seidel method", new SeidelSolver(ITERATIONS_LIMIT));
        solvers.put("SOR method", new SorSolver(ITERATIONS_LIMIT));
        solvers.put("Gauss method", new GaussSolver());
        solvers.put("Gradient method", new GradientSolver(ITERATIONS_LIMIT));
    }

    private void runTest(Matrix a, Vector b, Vector answer) {
        System.out.println("\n------------------------------");
        System.out.println("A = " + a);
        System.out.println("b = " + b);
        System.out.println("Cond A = " + a.conditionality());
        System.out.println();
        for (String name : solvers.keySet()) {
            System.out.print(name + ": ");
            Solver solver = solvers.get(name);
            LinearEquationsSystem system1 = new LinearEquationsSystem(a, b);
            Solution solution = solver.solve(system1, EPSILON);
            if (solution == null) {
                System.out.println("no convergence");
            } else {
                Assert.assertTrue(solution.vector.equals(answer, EPSILON));
                System.out.println("solved in " + solution.iterations + " iterations");
            }
        }
    }

    @Test
    public void testAverageConditionality() throws Exception {
        System.out.println("Testing average conditionality");
        Matrix a1 = Matrix
                .parse(" {12, 11, 11, 11}, " +
                        "{12, 13, 12, 11}, " +
                        "{17, 18, 19, 18}, " +
                        "{25, 23, 25, 26}");
        Vector b1 = new Vector(1, 2, 3, 4);
        Vector answer1 = new Vector(-6d/5, 7d/5, -9d/5, 9d/5);
        runTest(a1, b1, answer1);

        Matrix a2 = Matrix
                .parse(" {-5, 2, 5, 5}, " +
                        "{5, -7, 5, -4}, " +
                        "{-5, 4, -16, 16}, " +
                        "{23, 2, -24, 26}");
        Vector b2 = new Vector(-100, -5, 10, 15);
        Vector answer2 = new Vector(1085d/2323,-9025d/9292,-93655d/9292,-84235d/9292);
        runTest(a2, b2, answer2);

        Matrix a3 = Matrix
                .parse(" {-8, 2, 5, 5}, " +
                        "{5, -9, 5, -4}, " +
                        "{-5, 4, -20, 16}, " +
                        "{23, 2, -24, 40}");
        Vector b3 = new Vector(-100, -5, 10, 15);
        Vector answer3 = new Vector(166590d/32987,176795d/65974,-425895d/65974,-431215d/65974);
        runTest(a3, b3, answer3);
    }

    @Test
    public void testGoodConditionality() throws Exception {
        System.out.println("Testing good conditionality");
        Matrix a1 = Matrix
                .parse(" {99, 2, 1, 1, 0, 0, 0, 0, 0, 0}, " +
                        "{2, 99, 2, 1, 1, 0, 0, 0, 0, 0}, " +
                        "{1, 2, 99, 2, 1, 1, 0, 0, 0, 0}, " +
                        "{1, 1, 2, 99, 2, 1, 1, 0, 0, 0}, " +
                        "{0, 1, 1, 2, 99, 2, 1, 1, 0, 0}, " +
                        "{0, 0, 1, 1, 2, 99, 2, 1, 1, 0}, " +
                        "{0, 0, 0, 1, 1, 2, 99, 2, 1, 1}, " +
                        "{0, 0, 0, 0, 1, 1, 2, 99, 2, 1}, " +
                        "{0, 0, 0, 0, 0, 1, 1, 2, 99, 2}, " +
                        "{0, 0, 0, 0, 0, 0, 1, 1, 2, 99}");
        Vector b1 = new Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Vector answer1 = new Vector(839092143287068d / 92571266157142257d, 1722084794688938d / 92571266157142257d, 2595909981697204d / 92571266157142257d, 3461064400647445d / 92571266157142257d, 4325068989463663d / 92571266157142257d, 5188250759980078d / 92571266157142257d, 6048303324194155d / 92571266157142257d, 7009572406973305d / 92571266157142257d, 7977480288192230d / 92571266157142257d, 9057573992564350d / 92571266157142257d);
        runTest(a1, b1, answer1);
    }

    @Test
    public void testBadConditionality() throws Exception {
        System.out.println("Testing bad conditionality");
        Matrix a1 = Matrix
                .parse(" {0.5, 0.33, 0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09}, " +
                        "{0.33, 0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08}, " +
                        "{0.25, 0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08}, " +
                        "{0.2, 0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07}, " +
                        "{0.15, 0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07}, " +
                        "{0.14, 0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06}, " +
                        "{0.125, 0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06}, " +
                        "{0.11, 0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06}, " +
                        "{0.1, 0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06, 0.05}, " +
                        "{0.09, 0.08, 0.08, 0.07, 0.07, 0.06, 0.06, 0.06, 0.05, 0.05}");
        Vector b1 = new Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Vector answer = new Vector(1268800d/8173,-850800d/8173,-4422200d/24519,-2493400d/8173,-178000d/24519,-8647400d/24519,-4674200d/24519,14940700d/24519,12421700d/24519,5568500d/24519);
        runTest(a1, b1, answer);
    }


}