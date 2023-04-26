package DAL.Interface;

import BE.Project;

import java.util.List;

public interface IProjectDAO {
    List<Project> getAllProjects() throws Exception;
}
