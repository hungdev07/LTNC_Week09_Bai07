import java.nio.file.Paths;

public class PathService {
  public String getPathFailed(String folder, String filename) {
    return folder + "\\" + filename;
  }

  public String getPathSuccess(String folder, String filename) {
    // Paths.get tự động xử lý dấu gạch chéo phù hợp với OS
    return Paths.get(folder, filename).toString();
  }
}
