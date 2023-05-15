package BLL.Interfaces;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.Metadata;

import java.util.List;

public interface IDropBoxAPIManager {

    void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException;

    void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException;

    void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException;

    List<Metadata> readFilesFromDropBox(String folderPath) throws DbxException;

    List<Metadata> readAllFilesFromDropBox() throws DbxException;
}
