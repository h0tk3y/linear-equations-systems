import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void determinantTest() {
        Matrix m1 = Matrix.unit(3);
        Assert.assertEquals(m1.determinant(), 1.0, 1e-6);

        Matrix m2 = new Matrix(new double[]{1, 2, 3}, new double[]{1, 3, 5}, new double[]{1, 4, 8});
        Assert.assertEquals(m2.determinant(), 1.0, 1e-6);
        Assert.assertEquals(m2.multiply(2).determinant(), 8.0, 1e-6);
    }

    @Test
    public void testReverse() {
        Matrix m1 = Matrix.unit(2);
        Matrix r1 = m1.reversed();
        Assert.assertTrue(m1.equals(r1, 1e-5));

        Matrix m2 = new Matrix(new double[]{1, 2, 3}, new double[]{1, 3, 5}, new double[]{1, 4, 8});
        Matrix r2 = m2.reversed();
        Assert.assertTrue(r2.equals(new Matrix(new double[]{4, -4, 1}, new double[]{-3, 5, -2}, new double[]{1, -2, 1}), 1e-6));
        Assert.assertTrue(m2.multiply(r2).equals(Matrix.unit(3), 1e-6));

        Matrix m3 = new Matrix(new double[]{1, 2, 3}, new double[]{1, 2, 3}, new double[]{1, 2, 3});
        Assert.assertNull(m3.reversed());
    }

}