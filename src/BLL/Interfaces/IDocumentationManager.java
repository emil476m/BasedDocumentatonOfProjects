package BLL.Interfaces;

import BE.CostumerType;
import BE.Device;
import BE.Project;

import java.sql.SQLException;
import java.util.List;

public interface IDocumentationManager {
    List<CostumerType> getAllCostumerTypes() throws Exception;

    Project createProject(Project project, List<Device> devices) throws SQLException;

    void sendToPMOrTech(Project project) throws Exception;

    List<Device> devicesForProject(Project project) throws Exception;

    void updateProjectAndDevices(Project project, List<Device> newDevices) throws SQLException;
}