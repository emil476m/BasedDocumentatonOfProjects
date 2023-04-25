package GUI.Models;

import java.lang.reflect.Field;

public class ModelsHandler {
     private static ModelsHandler instance;

     private LoginModel loginModel;

    private ModelsHandler() throws Exception
    {
        loginModel = new LoginModel();
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
}
