package DAL.Interface;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public interface IDropBoxAPI {
    void deleteFilesFromDropBox() throws DbxException;

    void uploadFilesFromDropBox() throws DbxException;

    void downloadFilesFromDropBox() throws DbxException;

    void readFilesFromDropBox() throws DbxException;
}
