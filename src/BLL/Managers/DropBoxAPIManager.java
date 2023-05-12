package BLL.Managers;

import BLL.Interfaces.IDropBoxAPIManager;
import DAL.DropBoxAPI.DropBoxAPI;
import DAL.Interface.IDropBoxAPI;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

public class DropBoxAPIManager implements IDropBoxAPIManager {
    private IDropBoxAPI dropBoxAPI;

    public DropBoxAPIManager(){
        dropBoxAPI = new DropBoxAPI();
    }

    @Override
    public void deleteFilesFromDropBox() throws DbxException {
        dropBoxAPI.deleteFilesFromDropBox();
    }

    @Override
    public void uploadFilesFromDropBox() throws DbxException {
        dropBoxAPI.uploadFilesFromDropBox();
    }

    @Override
    public void downloadFilesFromDropBox() throws DbxException {
        dropBoxAPI.downloadFilesFromDropBox();
    }

    @Override
    public void readFilesFromDropBox() throws DbxException {
        dropBoxAPI.readFilesFromDropBox();
    }
}
