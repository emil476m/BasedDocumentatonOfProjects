package BLL.Interfaces;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

public interface IDropBoxAPIManager {

    void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException;

    void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException;

    void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException;

    void readFilesFromDropBox(String folderPath) throws DbxException;

    void readAllFilesFromDropBox() throws DbxException;
}
