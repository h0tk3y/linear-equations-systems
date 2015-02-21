import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
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
        Vector answer1 = new Vector(839092143287068d/92571266157142257d,1722084794688938d/92571266157142257d,2595909981697204d/92571266157142257d,3461064400647445d/92571266157142257d,4325068989463663d/92571266157142257d,5188250759980078d/92571266157142257d,6048303324194155d/92571266157142257d,7009572406973305d/92571266157142257d,7977480288192230d/92571266157142257d,9057573992564350d/92571266157142257d);
        runTest(a1, b1, answer1);
    }
}