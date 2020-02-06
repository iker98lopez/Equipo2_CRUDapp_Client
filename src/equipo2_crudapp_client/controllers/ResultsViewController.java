package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.enumerators.SoftwareType;
import equipo2_crudapp_client.clients.SoftwareClient;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Adrián García
 */
public class ResultsViewController extends GenericSideBarController {
    /**
     * List that stores the the software results
     */
    private ObservableList<Software> results = FXCollections.observableArrayList();
    private Set<Software> softwares = new HashSet<Software>();
    /**
     * Software client to make CRUD operations
     */
    private static final SoftwareClient SOFTWARE_CLIENT = new SoftwareClient();
    /**
     * Logger for the controller
     */
    private static final Logger LOGGER = Logger.getLogger("equipo2_crudapp_client.controllers.ResultsViewController");
    /**
     * Scene of the controller
     */
    private Scene scene;

    /**
     * Text to search passed from MainView
     */
    private String searchText;
    /**
     * TextField that gets a text introduced by user to search
     */
    @FXML
    private TextField textFieldSearchBar;

    /**
     * Button that launches the search
     */
    @FXML
    private Button buttonSearch;
    /**
     * RadioButton that filters by name of Software
     */
    @FXML
    private RadioButton radioButtonName;
    /**
     * RadioButton that filters by publisher of Software
     */
    @FXML
    private RadioButton radioButtonPublisher;
    /**
     * RadioButton that filters by number of offers of Software
     */
    @FXML
    private RadioButton radioButtonNumOfOffers;
    /**
     * RadioButton that filters by release date of Software
     */
    @FXML
    private RadioButton radioButtonReleaseDate;
    /**
     * CheckBox that filters by programs
     */
    @FXML
    private CheckBox checkBoxProgram;
    /**
     * CheckBox that filters by extensions
     */
    @FXML
    private CheckBox checkBoxExtension;
    /**
     * CheckBox that filters by games
     */
    @FXML
    private CheckBox checkBoxGame;
    /**
     * RadioButton that filters by ascending order
     */
    @FXML
    private RadioButton radioButtonAscending;
    /**
     * RadioButton that filters by descending order
     */
    @FXML
    private RadioButton radioButtonDescending;
    /**
     * ListView that shows all matched results
     */
    @FXML
    private ListView listViewResults;

    /**
     * This method initializes the stage and shows the window, sets the
     * visibility of the components and assigns the listeners.
     *
     * @param root Root to assign to the scene
     */
    @Override
    public void initStage(Parent root) {
        super.initStage(root);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Results");
        stage.show();

        buttonSearch.setOnAction(this::handleButtonSearchAction);
        ToggleGroup toggleGroupOrderBy = new ToggleGroup();
        ToggleGroup toggleGroupOrder = new ToggleGroup();
        radioButtonName.setToggleGroup(toggleGroupOrderBy);
        radioButtonName.setSelected(true);
        radioButtonPublisher.setToggleGroup(toggleGroupOrderBy);
        radioButtonNumOfOffers.setToggleGroup(toggleGroupOrderBy);
        radioButtonReleaseDate.setToggleGroup(toggleGroupOrderBy);
        radioButtonAscending.setToggleGroup(toggleGroupOrder);
        radioButtonAscending.setSelected(true);
        radioButtonDescending.setToggleGroup(toggleGroupOrder);
        /**
          * Modificacion Adrian Garcia 06/02/2020
          */
        results = FXCollections.observableArrayList();
        results = getResultsData();
        printResultsOnList(results);
    }

    /**
     * Method that returns an observable list with filtered results
     */
    private ObservableList<Software> getResultsData() {
        ObservableList<Software> data = FXCollections.observableArrayList();
        try {
            /**
             * Modificacion Adrian Garcia 06/02/2020
            */
            if (!textFieldSearchBar.getText().isEmpty()) {
                softwares = SOFTWARE_CLIENT.findSoftwaresByName(new GenericType<Set<Software>>() {
                }, textFieldSearchBar.getText());

            } else {
                softwares = SOFTWARE_CLIENT.findAllSoftwares(new GenericType<Set<Software>>() {
                });
            }
        } catch (ClientErrorException clientErrorException) {
            clientErrorException.printStackTrace();
        }
        data.addAll(softwares);
        return data;
    }

    /**
     * Method that populates the results ListView
     */
    private void printResultsOnList(ObservableList<Software> data) {
        listViewResults.setItems(data);
        listViewResults.setCellFactory(lv -> {
            ListCell<Software> cell = new ListCell<Software>() {
                @Override
                protected void updateItem(Software item, boolean empty) {
                    super.updateItem(item, empty);
                    //ListCell custom adapter
                    HBox content;
                    Text name;
                    ImageView imageView;

                    name = new Text();
                    imageView = new ImageView();
                    HBox elementsHBox = new HBox(imageView, name);
                    content = new HBox(elementsHBox);
                    content.setSpacing(10);
                    if (item != null && !empty) {
                        name.setText(item.getName());
                        File file = new File("src/softwareDefault.jpg");
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitHeight(100);
                        imageView.setFitWidth(100);
                        name.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
                        setGraphic(content);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/equipo2_crudapp_client/views/SoftwareView.fxml"));
                        Parent root = (Parent) loader.load();
                        SoftwareViewController controller = ((SoftwareViewController) loader.getController());
                        controller.setSoftwareId("" + cell.getItem().getSoftwareId());
                        controller.setUser(user);
                        controller.setStage(new Stage());
                        controller.initStage(root);
                    } catch (Exception ex) {
                        LOGGER.severe(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                e.consume();
            });
            return cell;
        });

        listViewResults.setOnMouseClicked(e -> {
            
        });
    }

    /**
     * Method that searches Software based on filters applied
     */
    public void handleButtonSearchAction(ActionEvent event) {
        ObservableList<Software> filteredSoftwares = getResultsData();
        if (textFieldSearchBar.getText() != null || textFieldSearchBar.getText() != "") {
            filteredSoftwares.removeIf(s -> !s.getName().toLowerCase().contains(textFieldSearchBar.getText().toLowerCase()));
        }
        if (!checkBoxProgram.isSelected() && !checkBoxExtension.isSelected() && !checkBoxGame.isSelected()) {

        } else {
            if (!checkBoxProgram.isSelected()) {
                filteredSoftwares.removeIf(s -> s.getSoftwareType() == SoftwareType.PROGRAM);
            }
            if (!checkBoxExtension.isSelected()) {
                filteredSoftwares.removeIf(s -> s.getSoftwareType() == SoftwareType.EXTENSION);
            }
            if (!checkBoxGame.isSelected()) {
                filteredSoftwares.removeIf(s -> s.getSoftwareType() == SoftwareType.GAME);
            }
        }

        //Check RadioButton and CheckBox filters to sort
        if (radioButtonName.isSelected()) {
            if (radioButtonAscending.isSelected()) {
                Comparator<Software> comparator = Comparator.comparing(Software::getName);
                FXCollections.sort(filteredSoftwares, comparator);
            } else {
                Comparator<Software> comparator = Comparator.comparing(Software::getName);
                FXCollections.sort(filteredSoftwares, comparator.reversed());
            }

        } else if (radioButtonPublisher.isSelected()) {
            if (radioButtonAscending.isSelected()) {
                Comparator<Software> comparator = Comparator.comparing(Software::getPublisher);
                FXCollections.sort(filteredSoftwares, comparator);
            } else {
                Comparator<Software> comparator = Comparator.comparing(Software::getPublisher);
                FXCollections.sort(filteredSoftwares, comparator.reversed());
            }

        } else if (radioButtonReleaseDate.isSelected()) {
            if (radioButtonAscending.isSelected()) {
                Comparator<Software> comparator = Comparator.comparing(Software::getReleaseDate);
                FXCollections.sort(results, comparator);
            } else {
                Comparator<Software> comparator = Comparator.comparing(Software::getReleaseDate);
                FXCollections.sort(results, comparator.reversed());
            }

        } else if (radioButtonNumOfOffers.isSelected()) {
            if (radioButtonAscending.isSelected()) {
                Comparator<Software> comparator = Comparator.comparingInt((s) -> {
                    return s.getOffers().size();
                });
                FXCollections.sort(results, comparator);
            } else {
                Comparator<Software> comparator = Comparator.comparingInt((s) -> {
                    return s.getOffers().size();
                });
                FXCollections.sort(results, comparator.reversed());
            }
        }
        listViewResults.getItems().clear();
        printResultsOnList(filteredSoftwares);
    }

    /**
     * Modificacion Adrian Garcia 06/02/2020
     */

    /**
     * Method that sets searchText from MainView
     *
     * @param searchText
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
        textFieldSearchBar.setText(searchText);
    }

}
