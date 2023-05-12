package BLL.Managers;

import BLL.Interfaces.IDropBoxAPIManager;
import DAL.DropBoxAPI.DropBoxAPI;
import DAL.Interface.IDropBoxAPI;
import com.dropbox.core.DbxException;

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
    public void readFilesFromDropBox(String folderPath) throws DbxException {
        dropBoxAPI.readFilesFromDropBox(folderPath);
    }

    @Override
    public void readAllFilesFromDropBox() throws DbxException {
        dropBoxAPI.readAllFilesFromDropBox();
    }
}
