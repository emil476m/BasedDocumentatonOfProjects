package DAL.Interface;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;

import java.io.File;
import java.util.List;


public interface IDropBoxAPI {
    void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException;

    void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException;

    void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException;

    List<Metadata> readFilesFromDropBox(String folderPath) throws DbxException;

    List<Metadata> readAllFilesFromDropBox() throws DbxException;

    void deleteLocalFiles(List<String> localFilePaths) throws Exception;

    File createLocalFile(File file) throws Exception;
}
