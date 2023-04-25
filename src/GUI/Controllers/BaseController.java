package GUI.Controllers;
import GUI.Models.ModelsHandler;

public abstract class BaseController {
    private ModelsHandler modelsHandler;

    public void setModel(ModelsHandler modelsHandler) {
        this.modelsHandler = modelsHandler;
    }

    public ModelsHandler getModelsHandler() {
        return modelsHandler;
    }

    public abstract void setup();
}
