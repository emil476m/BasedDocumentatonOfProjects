package BLL.Managers;

import BE.CostumerType;
import BE.Device;
import BE.Project;
import BLL.Interfaces.IDocumentationManager;
import DAL.DB.CostumerTypeDAO_DB;
import DAL.DB.ProjectDAO_DB;
import DAL.Interface.ICostumerTypeDAO;
import DAL.Interface.IProjectDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DocumentationManager implements IDocumentationManager {
    private ICostumerTypeDAO costumerTypeDAO;
    public IProjectDAO projectDAO;
    public DocumentationManager() throws IOException {
        costumerTypeDAO = new CostumerTypeDAO_DB();
        projectDAO = new ProjectDAO_DB();
    }
    @Override
    public List<CostumerType> getAllCostumerTypes() throws Exception {
        return costumerTypeDAO.getAllCostumerTypes();
    }

    @Override
    public Project createProject(Project project, List<Device> devices) throws SQLException {
        return projectDAO.createProject(project,devices);
    }
}
