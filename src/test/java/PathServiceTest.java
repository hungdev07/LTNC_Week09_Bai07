import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PathServiceTest {
  @Test
  void test() {
    PathService ps = new PathService();
    String expected = Paths.get("data", "config.txt").toString();
    String actual = ps.getPathSuccess("data", "config.txt");
    assertEquals(expected, actual);
  }
}
