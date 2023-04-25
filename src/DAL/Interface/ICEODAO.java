package DAL.Interface;

import BE.Project;

import java.util.List;

public interface ICEODAO {
    List<Project> getAllProjects() throws Exception;
}
