package BE;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String costumerName;
    private LocalDate projectDate;
    private String projectLocation;
    private String projectDescription;
    private int projectCreatorId;
    private String projectIsDeleted;

    public Project(String costumerName, LocalDate projectDate, String projectLocation, String projectDescription, int projectCreatorId, String projectIsDeleted) {
        this.costumerName = costumerName;
        this.projectDate = projectDate;
        this.projectLocation = projectLocation;
        this.projectDescription = projectDescription;
        this.projectCreatorId = projectCreatorId;
        this.projectIsDeleted = projectIsDeleted;
    }

    public Project(int projectId, String costumerName, LocalDate projectDate, String projectLocation, String projectDescription, int projectCreatorId, String projectIsDeleted) {
        this.projectId = projectId;
        this.costumerName = costumerName;
        this.projectDate = projectDate;
        this.projectLocation = projectLocation;
        this.projectDescription = projectDescription;
        this.projectCreatorId = projectCreatorId;
        this.projectIsDeleted = projectIsDeleted;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public LocalDate getProjectDate() {
        return projectDate;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public int getProjectCreatorId() {
        return projectCreatorId;
    }

    public String getProjectIsDeleted() {
        return projectIsDeleted;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public void setProjectDate(LocalDate projectDate) {
        this.projectDate = projectDate;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setProjectCreatorId(int projectCreatorId) {
        this.projectCreatorId = projectCreatorId;
    }

    public void setProjectIsDeleted(String projectIsDeleted) {
        this.projectIsDeleted = projectIsDeleted;
    }
}
