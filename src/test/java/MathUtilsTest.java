import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathUtilsTest {
  @Test
  void testMax() {
    MathUtils mt = new MathUtils();
    assertEquals(2, mt.max(2, 5));
  }
}
