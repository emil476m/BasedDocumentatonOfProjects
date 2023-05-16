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

    public DropBoxAPI(){
        try {
            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(new File(PROP_FILE)));
            String access_token = databaseProperties.getProperty("ACCESS_TOKEN");

            final String ACCESS_TOKEN = access_token;

            // Create Dropbox client
            config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            client = new DbxClientV2(config, ACCESS_TOKEN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException {
        Metadata metadata = client.files().delete(dropBoxFilePath);
    }

    @Override
    public void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException {
        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream(localFilePath)) {
            FileMetadata metadata = client.files().uploadBuilder(dropBoxFilePath)
                    .uploadAndFinish(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException {
        DbxDownloader<FileMetadata> downloader = client.files().download(dropBoxFilePath);
        try {
            FileOutputStream out = new FileOutputStream("DownloadedDropBoxFiles/"+fileName);
            downloader.download(out);
            out.close();
        } catch (DbxException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

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

    @Override
    public void deleteLocalFiles(List<String> localFilePaths) throws Exception {
        for (String s: localFilePaths){
            LocalFileHandler.deleteLocalFile(s);
        }
    }

    @Override
    public File createLocalFile(File file) throws Exception {
        File newFile = new File(String.valueOf(LocalFileHandler.createLocalFile(file.getPath())));
        return newFile;
    }
}