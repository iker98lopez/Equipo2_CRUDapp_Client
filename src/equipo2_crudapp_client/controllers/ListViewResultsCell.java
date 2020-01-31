package equipo2_crudapp_client.controllers;


import equipo2_crudapp_classes.classes.Software;
import java.io.File;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author leioa
 */
public class ListViewResultsCell extends ListCell<Software> {

    private HBox content;
    private Text name;
    private ImageView imageView;

    public ListViewResultsCell() {
        super();
        name = new Text();
        imageView = new ImageView();
        VBox vBox = new VBox(name, imageView);
        vBox.setMaxSize(USE_PREF_SIZE, 100);
        content = new HBox(vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Software software, boolean empty) {
        super.updateItem(software, empty);
        if (software != null && !empty) { 
            name.setText(software.getName());
            File file = new File("src/logoOfertAPPs.png");
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}


