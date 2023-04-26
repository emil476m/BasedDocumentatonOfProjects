package BLL.Interfaces;

import BE.Project;

import java.util.List;

public interface ICEOManager {
    List<Project> getAllProjects() throws Exception;
}
