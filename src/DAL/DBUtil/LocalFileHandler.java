package DAL.DBUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class LocalFileHandler {
    /**
     * Attempts to delete a local file from path.
     * @param path The path of the file to delete.
     * @throws IOException If it failed to delete the file.
     */
    public static void deleteLocalFile(String path) throws Exception {
        try {
            Path newPath = Paths.get(path);
            Files.delete(newPath);
        }
        catch (IOException e) {
            e.printStackTrace();

            // No need to throw an exception if the file doesn't exist.
            if (!(e instanceof NoSuchFileException)) {
                throw new Exception("Failed to delete local file", e);
            }
        }
    }

    /**
     * Gets the local path for the file,
     * then creates a new file in the data package and copies the original over in the new file.
     * @param path The path of the file to create.
     * @return The new path for the file.
     * @throws Exception If it fails to create the local file.
     */
    public static Path createLocalFile(String path) throws Exception {
        try {
            File localFilePath = new File(path);
            String fileName = localFilePath.getName();

            String relativeFilePath = "data//";

            Path originalFile = Paths.get(path);
            File file = new File(relativeFilePath + fileName);
            Path finalFile = Paths.get(file.getPath());
            Files.copy(originalFile, finalFile, REPLACE_EXISTING);
            return finalFile;

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to create file", e);
        }
    }
}
