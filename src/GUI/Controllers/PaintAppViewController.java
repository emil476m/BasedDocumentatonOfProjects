package GUI.Controllers;

import GUI.Util.AlertOpener;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaintAppViewController extends BaseController{
    @FXML
    private Button btnBrush, btnSave, btnClose, btnErase;

    @FXML
    private ColorPicker CpColorPicker;
    @FXML
    private TextField txtBrushSize;
    @FXML
    private Canvas canvas;
    boolean toolSelected;


    GraphicsContext brushTool;

    @Override
    public void setup() {
        canvas.setLayoutX(canvas.getParent().getLayoutX()/2);
        canvas.setLayoutY(canvas.getParent().getLayoutY()/2);
        buttonIconSetup();
        txtBrushSize.setText("5");
        if(canvas != null)
        {
         brushTool = canvas.getGraphicsContext2D();
         canvas.setOnMouseDragged( e -> {
         double size = Double.parseDouble(txtBrushSize.getText());
         double x = e.getX() - size / 2;
         double y = e.getY() - size / 2;

         if(toolSelected && !txtBrushSize.getText().isEmpty())
         {
             brushTool.setFill(CpColorPicker.getValue());
             brushTool.fillRoundRect(x,y,size,size,size,size);
         }

         });
        }
    }

    private void buttonIconSetup()
    {
        btnBrush.setGraphic(new ImageView(new Image("/GUI/Images/icons8-paint-brush-80.png")));
        btnClose.setGraphic(new ImageView(new Image("/GUI/Images/icons8-close-80.png")));
        btnErase.setGraphic(new ImageView(new Image("/GUI/Images/icons8-eraser-64.png")));
        btnSave.setGraphic(new ImageView(new Image("/GUI/Images/icons8-save-80.png")));
    }

    public void handleClose(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        if(AlertOpener.confirm("do you want to exit?", "Are you sure you want to close paint?"))
        {
            stage.close();
        }
    }

    public void handleNewCanvas(ActionEvent actionEvent)
    {
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
        createButton.setOnAction(event ->
        {
            if(!getCanvasHeight.getText().isEmpty() && !getCanvasWidth.getText().isEmpty())
            {
                canvas = new Canvas();
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
        root.setPrefWidth(200);
        root.setPrefHeight(200);
        root.getChildren().add(vBox);

        Scene canvasScene = new Scene(root);
        stage.setScene(canvasScene);
        stage.setTitle("New Canvas");
        stage.showAndWait();
    }

    public void handleBrush(ActionEvent actionEvent)
    {
        toolSelected = true;
        btnBrush.setDisable(true);
    }
}
