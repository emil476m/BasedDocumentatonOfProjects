package GUI.Models;

import java.lang.reflect.Field;

public class ModelsHandler {
     private static ModelsHandler instance;

     private LoginModel loginModel;
     private CEOModel ceoModel;

    private ModelsHandler() throws Exception
    {
        loginModel = new LoginModel();
        ceoModel = new CEOModel();
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
}
