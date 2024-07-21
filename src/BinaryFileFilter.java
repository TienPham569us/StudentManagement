import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class BinaryFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String extension = getExtension(file);
        return extension != null && extension.equals("bin");
    }

    @Override
    public String getDescription() {
        return "CSV Files (*.bin)";
    }

    private String getExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }
}
