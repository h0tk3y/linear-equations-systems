/**
 * Solves linear equations systems with Seidel method.
 * <p>
 * Created by Sergey on 20.02.2015.
 */
public class SeidelSolver implements Solver {

    public final int iterationsLimit;

    public SeidelSolver(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }

    @Override
    public Solution solve(LinearEquationsSystem s, double epsilon) {
        Vector x = Vector.zero(s.b.getDimensions());
        Vector nextX = x;
        int iterations = 0;
        do {
            x = nextX;
            nextX = seidelProduct(s.a, x, s.b);
            iterations++;
            if (nextX.hasNaN() || iterations > iterationsLimit)
                return null;
        } while (s.a.multiply(nextX).subtract(s.b).norm() > epsilon);
        return new Solution(iterations, nextX);
    }

    protected Vector seidelProduct(Matrix a, Vector x, Vector b) {
        double[] components = new double[a.getHeight()];
        for (int i = 0; i < components.length; ++i) {
            double component = 0;
            for (int j = 0; j < i; ++j) {
                component += components[j] * a.get(i, j);
            }
            for (int j = i + 1; j < a.getWidth(); ++j) {
                component += x.get(j) * a.get(i, j);
            }
            components[i] = (b.get(i) - component) / a.get(i, i);
        }
        return new Vector(components);
    }
}
