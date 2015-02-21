/**
 * Solves a linear equations system with gradient method.
 *
 * Created by Sergey on 21.02.2015.
 */
public class GradientSolver implements Solver {

    public final int iterationsLimit;

    public GradientSolver(int iterationsLimit) {
        this.iterationsLimit = iterationsLimit;
    }

    @Override
    public Solution solve(LinearEquationsSystem s, double epsilon) {
        Matrix at = s.a.transponed();
        double[] firstX = new double[s.b.getDimensions()];
        for (int i = 0; i < s.b.getDimensions(); ++i) {
            firstX[i] = -s.b.get(i) / s.a.get(i, i);
        }
        Vector x = new Vector(firstX);
        Vector rp = s.a.multiply(x).subtract(s.b);
        int iterations = 0;
        while (rp.norm() > epsilon) {
            Vector w = s.a.multiply(at).multiply(rp);
            double mu = rp.product(w) / w.norm() / w.norm();
            x = x.subtract(s.a.multiply(rp).multiply(mu));
            if (++iterations == iterationsLimit || x.hasNaN())
                return null;
            rp = s.a.multiply(x).subtract(s.b);
        }
        return new Solution(iterations, x);
    }
}
