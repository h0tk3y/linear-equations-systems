/**
 * Created by Sergey on 21.02.2015.
 */
public class SorSolver extends SeidelSolver {

    public SorSolver(int iterationsLimit) {
        super(iterationsLimit);
    }

    @Override
    public Solution solve(LinearEquationsSystem s, double epsilon) {
        while (currentRelaxation > 1e-5) {
            Solution solution = super.solve(s, epsilon);
            if (solution != null)
                return solution;
            currentRelaxation /= 1.25;
        }
        return null;
    }

    double currentRelaxation = 2.0;

    @Override
    protected Vector seidelProduct(Matrix a, Vector x, Vector b) {
        return super.seidelProduct(a, x, b).multiply(currentRelaxation).add(x.multiply(1 - currentRelaxation));
    }
}
