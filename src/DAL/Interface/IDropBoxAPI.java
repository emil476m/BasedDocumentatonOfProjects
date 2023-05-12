package DAL.Interface;

import com.dropbox.core.DbxException;


public interface IDropBoxAPI {
    void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException;

    void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException;

    void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException;

    void readFilesFromDropBox(String folderPath) throws DbxException;

    void readAllFilesFromDropBox() throws DbxException;
}
