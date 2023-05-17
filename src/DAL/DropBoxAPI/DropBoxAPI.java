package DAL.DropBoxAPI;

import DAL.DBUtil.LocalFileHandler;
import DAL.Interface.IDropBoxAPI;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DropBoxAPI implements IDropBoxAPI {
    private static final String PROP_FILE = ".idea/Config/DataBase.Settings";
    private DbxRequestConfig config;
    private DbxClientV2 client;


    /**
     * Creates Dropbox client from by using the Access token in the settings file.
     */
    public DropBoxAPI(){
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(new File(PROP_FILE)));
            String access_token = databaseProperties.getProperty("ACCESS_TOKEN");

            final String ACCESS_TOKEN = access_token;

            // Create Dropbox client
            config = DbxRequestConfig.newBuilder("dropbox/BasedDocumentationOfProjects").build();
            client = new DbxClientV2(config, ACCESS_TOKEN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException {
        client.files().delete(dropBoxFilePath);
    }

    /**
     * Uploads the file at localFilePath to the dropbox in the folder at dropBoxFilePath.
     * @param localFilePath the filepath of the file that is going to be uploaded.
     * @param dropBoxFilePath the location in the dropbox the file is going to be uploaded to.
     * @throws DbxException
     */
    @Override
    public void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException {
        try (InputStream in = new FileInputStream(localFilePath)) {
            client.files().uploadBuilder(dropBoxFilePath).uploadAndFinish(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Downloads the file at dropBoxFilePath in the dropbox to the folder DownloadedDropBoxFiles and rename the file to filename.
     * @param dropBoxFilePath the location of the file in the dropbox.
     * @param fileName the file name.
     * @throws DbxException
     */
    @Override
    public void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException {
        DbxDownloader<FileMetadata> downloader = client.files().download(dropBoxFilePath);
        try {
            FileOutputStream out = new FileOutputStream("DownloadedDropBoxFiles/"+fileName);
            downloader.download(out);
            out.close();
        } catch (DbxException | IOException ex) {
            throw new RuntimeException("Failed to download files from the dropbox",ex);
        }
    }

    /**
     * Read files from the folderpath location in dropbox, used to get images for a specific project.
     * @param folderPath the dropbox path to the folder for the project.
     * @return metadata that is used to get the images in the folder.
     * @throws DbxException
     */
    @Override
    public List<Metadata> readFilesFromDropBox(String folderPath) throws DbxException {
        List<Metadata> onlineFilePaths = new ArrayList<>();

        ListFolderResult result = client.files().listFolder(folderPath);
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                onlineFilePaths.add(metadata);
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
        return onlineFilePaths;
    }

    /**
     * reads the metadata from the super folder that contains all the folders with images from the projects.
     * @return returns a list with metadata.
     * @throws DbxException
     */
    @Override
    public List<Metadata> readAllFilesFromDropBox() throws DbxException {
        List<Metadata> onlineFilePaths = new ArrayList<>();

        //Get all folder files
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                onlineFilePaths.add(metadata);
            }

            if (!result.getHasMore()) {
                break;
            }
            result = client.files().listFolderContinue(result.getCursor());
        }
        return onlineFilePaths;
    }

    /**
     * Deletes local files with the LocalFileHandler.
     * @param localFilePaths a list of filepaths
     * @throws Exception
     */
    @Override
    public void deleteLocalFiles(List<String> localFilePaths) throws Exception {
        for (String s: localFilePaths){
            LocalFileHandler.deleteLocalFile(s);
        }
    }

    /**
     * Creates new files in the DownloadedDropBoxFiles from the file param.
     * @param file its a file.
     * @return the new file that was created.
     * @throws Exception
     */
    @Override
    public File createLocalFile(File file) throws Exception {
        File newFile = new File(String.valueOf(LocalFileHandler.createLocalFile(file.getPath())));
        return newFile;
    }
}