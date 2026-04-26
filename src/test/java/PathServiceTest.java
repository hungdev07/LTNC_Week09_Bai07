import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PathServiceTest {
  @Test
  void test() {
    PathService ps = new PathService();
    String result = ps.getPathSuccess("data", "config.txt");
    assertEquals("data\\config.txt", result);
  }
}
