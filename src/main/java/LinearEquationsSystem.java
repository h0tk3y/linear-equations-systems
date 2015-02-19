/**
 * Created by Sergey on 20.02.2015.
 */
public class LinearEquationsSystem {

    public final Matrix a;
    public final Vector b;

    public LinearEquationsSystem(Matrix a) {
        this(a, Vector.zero(a.getHeight()));
    }

    public LinearEquationsSystem(Matrix a, Vector b) {
        if (a.getHeight() != b.getDimensions()) {
            throw new IllegalArgumentException("Vector should have size equal to matrix height");
        }

        this.a = a;
        this.b = b;
    }



}
