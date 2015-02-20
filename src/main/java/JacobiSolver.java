/**
 * Solves linear equations systems using Jacobi method.
 *
 * Created by Sergey on 20.02.2015.
 */
public class JacobiSolver implements Solver {

    public final double q;
    public final int iterationsLimit;

    public JacobiSolver(double q, int iterationsLimit) {
        this.q = q;
        this.iterationsLimit = iterationsLimit;
    }

    @Override
    public Solution solve(LinearEquationsSystem s, double epsilon) {
        Matrix d = Matrix.byDiagonal(s.a.getDiagonal());
        Matrix dReversed = d.reversed();
        Matrix b = Matrix.unit(s.a.getHeight()).subtract(dReversed.multiply(s.a));
        Vector g = dReversed.multiply(s.b);
        Vector x = Vector.zero(s.b.getDimensions());
        Vector nextX = x;
        int iterations = 0;
        do {
            x = nextX;
            nextX = b.multiply(x).add(g);
            iterations++;
            if (nextX.hasNaN() || iterations > iterationsLimit)
                return null;
        } while (!x.equals(nextX, epsilon * (1 - q)));
        return new Solution(iterations, nextX);
    }
}
