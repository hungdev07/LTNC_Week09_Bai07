import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathOperationTest {
  @Test
  void testGreater() {
    MathOperation m1 = new MathOperation();
    assertEquals(3, m1.SoSanh(1, 3));
  }

  @Test
  void testEqual() {
    MathOperation m2 = new MathOperation();
    assertEquals(2, m2.SoSanh(2, 2));
  }

  @Test
  void testSmaller() {
    MathOperation m3 = new MathOperation();
    assertEquals(5, m3.SoSanh(5, 2));
  }
}
