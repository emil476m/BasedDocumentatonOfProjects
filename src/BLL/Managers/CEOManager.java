package BLL.Managers;

import BE.Project;
import BLL.Interfaces.ICEOManager;
import DAL.DB.CEODAO_DB;
import DAL.Interface.ICEODAO;

import java.io.IOException;
import java.util.List;

public class CEOManager implements ICEOManager {
private ICEODAO databaseAccess;

    public CEOManager() throws IOException {
        databaseAccess = new CEODAO_DB();
    }

    @Override
    public List<Project> getAllProjects() throws Exception {
        return databaseAccess.getAllProjects();
    }
}
