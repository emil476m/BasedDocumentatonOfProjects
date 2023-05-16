package GUI.Controllers;

import BE.CanvasPane;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static javafx.scene.paint.Color.*;

public class PaintAppViewController extends BaseController{
    @FXML
    private CanvasPane canvasPane;
    @FXML
    private Button btnBrush, btnSave, btnClose, btnErase, btnScreenSize,btnSpeaker,btnCC,btnProjcetor,btnWifi,btnAmp;

    @FXML
    private ColorPicker CpColorPicker;
    @FXML
    private TextField txtBrushSize;


    GraphicsContext brushTool;

    @Override
    public void setup() {
        CpColorPicker.setValue(BLACK);
        buttonIconSetup();
        txtBrushSize.setText("5");
        toolListener();
    }

    /**
     * checks what tool is selected and dos its action.
     */
    private void toolListener()
    {
        if(canvasPane.getCanvas() != null)
        {
            brushTool = canvasPane.getCanvas().getGraphicsContext2D();
            canvasPane.getCanvas().setOnMouseDragged(e -> {
                double size = Double.parseDouble(txtBrushSize.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;
                if (btnBrush.isDisable() && !txtBrushSize.getText().isEmpty())
                {
                   brushTool.setFill(CpColorPicker.getValue());
                    brushTool.fillRoundRect(x, y, size, size, size, size);
                }
                else if (btnErase.isDisable() && !txtBrushSize.getText().isEmpty())
                {
                    brushTool.clearRect(x, y, size, size);
                }

            });
            canvasPane.getCanvas().setOnMouseClicked(ev -> {
                double size = Double.parseDouble(txtBrushSize.getText());
                double x = ev.getX() - size/2;
                double y = ev.getY() - size/2;
                if (btnAmp.isDisable()) {
                    Image image = new Image(String.valueOf(getClass().getResource("/GUI/Images/AMP.png")));
                    brushTool.drawImage(image,x,y);
                }
                if (btnCC.isDisable()) {

                    Image image = new Image(String.valueOf(getClass().getResource("/GUI/Images/cc.png")));
                    brushTool.drawImage(image,x,y);
                }
                if (btnWifi.isDisable()) {
                    Image image = new Image(String.valueOf(getClass().getResource("/GUI/Images/wifi.png")));
                    brushTool.drawImage(image,x,y);
                }
                if (btnProjcetor.isDisable()) {
                    Image image = new Image(String.valueOf(getClass().getResource("/GUI/Images/Projector.png")));
                    brushTool.drawImage(image,x,y);
                }
                if (btnSpeaker.isDisable()) {
                    Image image = new Image(String.valueOf(getClass().getResource("/GUI/Images/speaker.png")));
                    brushTool.drawImage(image,x,y);
                }
            });
        }
    }


    /**
     * Sets the icons for all the buttons in the window.
     */
    private void buttonIconSetup()
    {
        btnBrush.setGraphic(new ImageView(new Image("/GUI/Images/icons8-paint-brush-80.png")));
        btnClose.setGraphic(new ImageView(new Image("/GUI/Images/icons8-close-80.png")));
        btnErase.setGraphic(new ImageView(new Image("/GUI/Images/icons8-eraser-64.png")));
        btnSave.setGraphic(new ImageView(new Image("/GUI/Images/icons8-save-80.png")));
        btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/icons8-full-screen-80.png")));
        btnCC.setGraphic(new ImageView(new Image("/GUI/Images/icons8-ipad-pro-50.png")));
        btnAmp.setGraphic(new ImageView(new Image("/GUI/Images/icons8-amplifier-64.png")));
        btnSpeaker.setGraphic(new ImageView(new Image("/GUI/Images/icons8-speaker-80.png")));
        btnWifi.setGraphic(new ImageView(new Image("/GUI/Images/icons8-wi-fi-80.png")));
        btnProjcetor.setGraphic(new ImageView(new Image("/GUI/Images/icons8-video-projector-80.png")));
    }

    /**
     * Closes the window when pressed
     * @param actionEvent
     */
    public void handleClose(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        if(AlertOpener.confirm("do you want to exit?", "Are you sure you want to close paint?"))
        {
            stage.close();
        }
    }

    /**
     * opens a dynamically created window to create a new canvas when a button is pressed:
     * @param actionEvent
     */
    public void handleNewCanvas(ActionEvent actionEvent)
    {
        // Create JavaFX Nodes
        TextField getCanvasWidth = new TextField();
        getCanvasWidth.setPromptText("Width");
        getCanvasWidth.setPrefWidth(150);
        getCanvasWidth.setAlignment(Pos.CENTER);

        TextField getCanvasHeight = new TextField();
        getCanvasHeight.setPromptText("Height");
        getCanvasHeight.setPrefWidth(150);
        getCanvasHeight.setAlignment(Pos.CENTER);

        Button createButton = new Button();
        createButton.setText("Create");

        // Sets the onAction for the createButton.
        createButton.setOnAction(event ->
        {
            if(!getCanvasHeight.getText().isEmpty() && !getCanvasWidth.getText().isEmpty())
            {
                Canvas canvas = canvasPane.getCanvas();
                canvas.setWidth(Double.parseDouble(getCanvasWidth.getText()));
                canvas.setHeight(Double.parseDouble(getCanvasHeight.getText()));
                Stage stage = (Stage) createButton.getScene().getWindow();
                stage.close();
            }
        });

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(getCanvasWidth,getCanvasHeight,createButton);

        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(150);
        root.setPrefHeight(100);
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        stage.setScene(canvasScene);
        stage.setTitle("New Canvas");
        stage.showAndWait();
    }

    /**
     * Resets buttons
     */
    private void resetButton()
    {
        if(btnBrush.isDisable())
        {
            btnBrush.setDisable(false);
        }
        if(btnErase.isDisable())
        {
            btnErase.setDisable(false);
        }
        if(btnProjcetor.isDisable())
        {
            btnProjcetor.setDisable(false);
        }
        if(btnWifi.isDisable())
        {
            btnWifi.setDisable(false);
        }
        if(btnSpeaker.isDisable())
        {
            btnSpeaker.setDisable(false);
        }
        if(btnCC.isDisable())
        {
            btnCC.setDisable(false);
        }
        if(btnAmp.isDisable())
        {
            btnAmp.setDisable(false);
        }
    }

    /**
     * Handles the selection of the brush tool.
     * @param actionEvent
     */
    public void handleBrush(ActionEvent actionEvent)
    {
        resetButton();
        btnBrush.setDisable(true);
    }

    /**
     * Handles the tool selection for the eraser tool
     * @param actionEvent
     */
    public void handleErase(ActionEvent actionEvent)
    {
        resetButton();
        btnErase.setDisable(true);
    }

    /**
     * Handles the resizing of the window when a button is pressed.
     * @param actionEvent
     */
    public void handleScreenSize(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnScreenSize.getScene().getWindow();
        if(stage.isMaximized())
        {
            stage.setMaximized(false);
            btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/icons8-full-screen-80.png")));
        } else if (!stage.isMaximized())
        {
            stage.setMaximized(true);
            btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/icons8-normal-screen-80.png")));
        }
    }


    /**
     * Saves the drawing the user created as a .png file
     * @param actionEvent
     */
    public void handleSave(ActionEvent actionEvent)
    {
        FileChooser saveFile = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".png", "*.png");
        saveFile.getExtensionFilters().add(extFilter);
        saveFile.setInitialFileName("*"+extFilter.getDescription());
        File file = saveFile.showSaveDialog(btnSave.getScene().getWindow());

        if(file != null)

        {
            try {
                WritableImage writableImage = new WritableImage((int) canvasPane.getCanvas().getWidth(), (int) canvasPane.getHeight());
                canvasPane.getCanvas().snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
                File ddb = new File("DownloadedDropBoxFiles//" + file.getName());
                Files.copy(file.toPath(),ddb.toPath());
                getModelsHandler().getDocumentationModel().getImagesToBeSaved().add(ddb);
                AlertOpener.confirm("Has been saved", "Your image has been saved");
            } catch (IOException e) {
                ExceptionHandler.displayError(new RuntimeException("Failed to save the file please try again", e));
            }
        }
    }

    public void handleSpeaker(ActionEvent actionEvent) {
        resetButton();
        btnSpeaker.setDisable(true);
    }

    public void handleWifi(ActionEvent actionEvent) {
        resetButton();
        btnWifi.setDisable(true);
    }

    public void handleControlCenter(ActionEvent actionEvent) {
        resetButton();
        btnCC.setDisable(true);
    }

    public void handleProjcetor(ActionEvent actionEvent) {
        resetButton();
        btnProjcetor.setDisable(true);
    }

    public void handleAmp(ActionEvent actionEvent) {
        resetButton();
        btnAmp.setDisable(true);
    }
}
