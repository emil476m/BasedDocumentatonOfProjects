package BLL.Managers;

import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ITechnicianManager;
import DAL.DB.ProjectDAO_DB;
import DAL.Interface.IProjectDAO;

import java.io.IOException;
import java.util.List;

public class TechnicianManager implements ITechnicianManager
{
    IProjectDAO projectDAO;
    public TechnicianManager() throws IOException {
        projectDAO = new ProjectDAO_DB();
    }

    @Override
    public List<Project> getAllProjects() throws Exception {
        return projectDAO.getAllProjects();
    }

    @Override
    public List<Project> getMyProjects(User user) throws Exception {
        return projectDAO.getMyProjects(user);
    }
}
