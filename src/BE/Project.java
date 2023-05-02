package BE;

import java.time.LocalDate;

public class Project {
    private int projectId;
    private String costumerName;
    private LocalDate projectDate;
    private String projectLocation;
    private String projectDescription;
    private int projectCreatorId;
    private boolean projectIsDeleted;
    private int lastEditedBy;
    private boolean canBeEditedByTech;
    private LocalDate lastEdited;
    private int costumerType;
    private String address;
    private String zipCode;

    private String addressAndZipCode;

    public Project(String costumerName, LocalDate projectDate, String projectLocation, String projectDescription, int projectCreatorId, boolean projectIsDeleted, int lastEditedBy, boolean canBeEditedByTech, LocalDate lastEdited, int costumerType, String address, String zipCode) {
        this.costumerName = costumerName;
        this.projectDate = projectDate;
        this.projectLocation = projectLocation;
        this.projectDescription = projectDescription;
        this.projectCreatorId = projectCreatorId;
        this.projectIsDeleted = projectIsDeleted;
        this.canBeEditedByTech = canBeEditedByTech;
        this.lastEdited = lastEdited;
        this.lastEditedBy = lastEditedBy;
        this.costumerType = costumerType;
        this.address = address;
        this.zipCode = zipCode;
        this.addressAndZipCode = address + " " + "(" + zipCode + ")";
    }

    public Project(int projectId, String costumerName, LocalDate projectDate, String projectLocation, String projectDescription, int projectCreatorId, boolean projectIsDeleted, int lastEditedBy, boolean canBeEditedByTech, LocalDate lastEdited, int costumerType, String address, String zipCode) {
        this.projectId = projectId;
        this.costumerName = costumerName;
        this.projectDate = projectDate;
        this.projectLocation = projectLocation;
        this.projectDescription = projectDescription;
        this.projectCreatorId = projectCreatorId;
        this.projectIsDeleted = projectIsDeleted;
        this.canBeEditedByTech = canBeEditedByTech;
        this.lastEdited = lastEdited;
        this.lastEditedBy = lastEditedBy;
        this.costumerType = costumerType;
        this.address = address;
        this.zipCode = zipCode;
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

    public boolean getProjectIsDeleted() {
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

    public void setProjectIsDeleted(boolean projectIsDeleted) {
        this.projectIsDeleted = projectIsDeleted;
    }

    public int getLastEditedBy() {return lastEditedBy;}

    public boolean getCanBeEditedByTech() {return canBeEditedByTech;}

    public void setCanBeEditedByTech(boolean canBeEditedByTech) {this.canBeEditedByTech = canBeEditedByTech;}

    public LocalDate getLastEdited() {return lastEdited;}

    public int getCostumerType() {return costumerType;}

    public String getAddress() {return address;}

    public String getZipCode() {return zipCode;}

}
