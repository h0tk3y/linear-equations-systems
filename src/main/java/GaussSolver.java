/**
 * Created by viacheslav on 20.02.2015.
 */
public class GaussSolver implements Solver {

    public GaussSolver() {
    }

    @Override
    public Solution solve(LinearEquationsSystem s, double epsilon) {
        Matrix a = s.a;
        Vector b = s.b;

        int variables = a.getHeight();

        Matrix extended = Matrix.getExtended(a, b);
        for(int i=0; i<variables; ++i) {
            extended = extended.excludeVariableForward(i);
        }

        double value;

        double[] answer = extended.getDiagonal();

        for(int i=variables-1; i>=0; i--)
        {
            value = extended.get(i, variables)/extended.get(i, i);
            answer[i] = value;
            extended = extended.excludeVariableBackward(i);
        }

        Vector solution = new Vector(answer);

        return new Solution(1, solution);
    }
}
