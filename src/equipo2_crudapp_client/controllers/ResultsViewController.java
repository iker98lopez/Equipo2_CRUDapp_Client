package equipo2_crudapp_client.controllers;

import equipo2_crudapp_classes.classes.Offer;
import equipo2_crudapp_classes.classes.Software;
import equipo2_crudapp_classes.classes.User;
import equipo2_crudapp_classes.enumerators.SoftwareType;
import equipo2_crudapp_client.clients.SoftwareClient;
import equipo2_crudapp_client.controllers.ListViewResultsCell;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Adrián García
 */
public class ResultsViewController extends GenericSideBarController {

    private ObservableList<Software> results = null;
    private static final SoftwareClient SOFTWARE_CLIENT = new SoftwareClient();
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
        //results = getResultsData();
        results = FXCollections.observableArrayList();
        printResultsOnList(results);
    }

    /**
     * Method that returns an observable list with filtered results
     */
    private ObservableList<Software> getResultsData() {
        ObservableList<Software> data = FXCollections.observableArrayList();
        try {
            if (textFieldSearchBar.getText() != null || textFieldSearchBar.getText() != "") {
                data = SOFTWARE_CLIENT.findSoftwaresByName(new GenericType<ObservableList<Software>>() {
                }, searchText);

            } else {
                data = SOFTWARE_CLIENT.findAllSoftwares(new GenericType<ObservableList<Software>>() {
                });
            }
        } catch (ClientErrorException clientErrorException) {
            clientErrorException.printStackTrace();
        }
        return data;
    }

    /**
     * Method that populates the results ListView
     */
    private void printResultsOnList(ObservableList<Software> data) {
        List<Offer> offers = new ArrayList<>();
        Software sp = new Software();
        data.add(new Software(1, "s1", "p1", "d", Date.from(Instant.EPOCH.minusSeconds(1)), SoftwareType.PROGRAM, offers, sp));
        data.add(new Software(1, "s2", "p2", "d", Date.from(Instant.EPOCH.minusSeconds(2)), SoftwareType.GAME, offers, sp));
        data.add(new Software(1, "s3", "p3", "d", Date.from(Instant.EPOCH.minusSeconds(3)), SoftwareType.GAME, offers, sp));
        listViewResults.setItems(results);
        listViewResults.setCellFactory(new Callback<ListView<Software>, ListCell<Software>>() {
            @Override
            public ListCell<Software> call(ListView<Software> listView) {
                return new ListViewResultsCell();
            }
        });
    }

    /**
     * Method that searches Software based on filters applied
     */
    public void handleButtonSearchAction(ActionEvent event) {
        ObservableList<Software> filteredSoftwares = results;
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
     * This method sets the stage
     *
     * @param stage Stage to be setted
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that sets active user
     */
    public void setUser(User user) {

    }

    /**
     * Method that sets searchText from MainView
     *
     * @param searchText
     */
    public void setSearchText(String searchText) {

    }

}
