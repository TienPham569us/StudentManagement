import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtility {
    public static String copyFileFromSourceFolderToRootFolder(String sourceFolder, String fileName) {
        String rootFolder = getRootProjectDirectory();

        try {
            Path sourcePath = Path.of(sourceFolder);
            Path rootPath = Path.of(rootFolder+"/Assets/"+fileName);
            Path path = Files.copy(sourcePath, rootPath, StandardCopyOption.REPLACE_EXISTING);
            return path.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String getRootProjectDirectory() {
        String rootDirectory = System.getProperty("user.dir");
        //System.out.println("Root project directory: " + rootDirectory);
        return rootDirectory;
    }
}
