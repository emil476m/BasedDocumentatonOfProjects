package BLL.Interfaces;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

public interface IDropBoxAPIManager {
    void deleteFilesFromDropBox() throws DbxException;

    void uploadFilesFromDropBox() throws DbxException;

    void downloadFilesFromDropBox() throws DbxException;

    void readFilesFromDropBox() throws DbxException;
}
