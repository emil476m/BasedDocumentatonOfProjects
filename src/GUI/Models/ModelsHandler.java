package GUI.Models;

import java.lang.reflect.Field;

public class ModelsHandler {
     private static ModelsHandler instance;

     private LoginModel loginModel;
     private CEOModel ceoModel;
     private TechnicianModel technicianModel;
     private ProjectManagerModel projectManagerModel;
     private DocumentationModel documentationModel;
     private SalesPersonModel salesPersonModel;

    private ModelsHandler() throws Exception
    {
        loginModel = new LoginModel();
        ceoModel = new CEOModel();
        technicianModel = new TechnicianModel();
        projectManagerModel = new ProjectManagerModel();
        documentationModel = new DocumentationModel();
        salesPersonModel = new SalesPersonModel();
    }

    public static ModelsHandler getInstance() throws Exception {
        if(instance == null)
        {
            instance = new ModelsHandler();
        }
        return instance;
    }

    public LoginModel getLoginModel()
    {
        return loginModel;
    }

    public CEOModel getCeoModel() {
        return ceoModel;
    }

    public SalesPersonModel getSalesPersonModel(){
        return salesPersonModel;
    }

    public TechnicianModel getTechnicianModel(){return technicianModel;}

    public ProjectManagerModel getProjectManagerModel(){return projectManagerModel;}

    public DocumentationModel getDocumentationModel(){return documentationModel;}

    public void shutdownModelsHandelder()
    {
        instance = null;
    }
}
