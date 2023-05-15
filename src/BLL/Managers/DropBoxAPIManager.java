package BLL.Managers;

import BLL.Interfaces.IDropBoxAPIManager;
import DAL.DropBoxAPI.DropBoxAPI;
import DAL.Interface.IDropBoxAPI;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;

import java.io.File;
import java.util.List;

public class DropBoxAPIManager implements IDropBoxAPIManager {
    private IDropBoxAPI dropBoxAPI;

    public DropBoxAPIManager(){
        dropBoxAPI = new DropBoxAPI();
    }

    @Override
    public void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException {
        dropBoxAPI.deleteFilesFromDropBox(dropBoxFilePath);
    }

    @Override
    public void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException {
        dropBoxAPI.uploadFilesFromDropBox(localFilePath, dropBoxFilePath);
    }

    @Override
    public void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException {
        dropBoxAPI.downloadFilesFromDropBox(dropBoxFilePath, fileName);
    }

    @Override
    public List<Metadata> readFilesFromDropBox(String folderPath) throws DbxException {
        return dropBoxAPI.readFilesFromDropBox(folderPath);
    }

    @Override
    public List<Metadata> readAllFilesFromDropBox() throws DbxException {
        return dropBoxAPI.readAllFilesFromDropBox();
    }
    @Override
    public void deleteLocalFiles(List<String> localFilePaths) throws Exception {
        dropBoxAPI.deleteLocalFiles(localFilePaths);
    }

    @Override
    public File createLocalFile(File file) throws Exception {
        return dropBoxAPI.createLocalFile(file);
    }

}
