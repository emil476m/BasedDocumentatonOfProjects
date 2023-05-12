package DAL.DropBoxAPI;

import DAL.Interface.IDropBoxAPI;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.*;
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
            DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFilesFromDropBox() throws DbxException {
        String pictureName = "/WUAV.png";
        Metadata metadata = client.files().delete(pictureName);
    }

    @Override
    public void uploadFilesFromDropBox() throws DbxException {
        // Upload "test.txt" to Dropbox
        try (InputStream in = new FileInputStream("C:\\Users\\emilw\\IdeaProjects\\BasedDocumentationOfProjects\\src\\GUI\\Images\\WUAV.png")) {
            FileMetadata metadata = client.files().uploadBuilder("/WUAV.png")
                    .uploadAndFinish(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadFilesFromDropBox() throws DbxException {
        String pictureName = "/WUAV.png";

        DbxDownloader<FileMetadata> downloader = client.files().download(pictureName);
        try {
            FileOutputStream out = new FileOutputStream("DownloadedDropBoxFiles/WUAV.png");
            downloader.download(out);
            out.close();
        } catch (DbxException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void readFilesFromDropBox() throws DbxException {
        // Get current account info
        FullAccount account = client.users().getCurrentAccount();
        System.out.println(account.getName().getDisplayName());

        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());

            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
    }
}