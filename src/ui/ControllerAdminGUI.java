package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class ControllerAdminGUI {

	@FXML
	private Pane mainPane;

	@FXML
	private TextField tfUsername;

	@FXML
	private PasswordField pfPassword;

	@FXML
	void start() throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
	}


	@FXML
	public void btnLogIn(ActionEvent event) throws IOException{

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	//_______________________________MENU-OPTIONS________________________________

	@FXML
	void openInventory(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	@FXML
	void oprenCarte(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Carte.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	@FXML
	void oprenOrders(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(""));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	@FXML
	void oprenStaff(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Staff.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	//_______________________________STAFF________________________________


	@FXML
	private TableView<?> tvAllStaff;

	@FXML
	private TableColumn<?, ?> tcName;

	@FXML
	private TableColumn<?, ?> tcID;

	@FXML
	private TableColumn<?, ?> tcBirthday;

	@FXML
	private Label txtCurrentUser;

	@FXML
	void btnAddMember(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddStaff.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
	}

	@FXML
	void btnChangePassword(ActionEvent event) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();

	}
	
	@FXML
	void btnBack(ActionEvent event) throws IOException{

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
	}

	//_______________________________AddStaff________________________________

	@FXML
	private TextField tfID;

	@FXML
	private DatePicker dpBirthday;


	@FXML
	private PasswordField pfConfirmPassword;



	//

	@FXML
	private TextField tfOldPassword;

	@FXML
	private PasswordField psNewPassword;

	@FXML
	private PasswordField psConfirmChangePassword;

	@FXML
	void btnSetNewPassword(ActionEvent event) {

	}
	
	//_______________________________Inventory________________________________
	
    @FXML
    private TableView<?> tvInventory;

    @FXML
    private TableColumn<?, ?> tcIngredientName;

    @FXML
    private TableColumn<?, ?> tcQuantity;

    @FXML
    private TableColumn<?, ?> tcUnit;

    @FXML
    void btnAddIngredient(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddIngredient.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();

    }

    @FXML
    void btnModifyIngredient(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ModifyIngredient.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();

    }


    //_______________________________AddIngredients________________________________

    @FXML
    private TextField tfIngridientName;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfUnit;

    @FXML
    void btnAddIngridients(ActionEvent event) {

    }

    //_______________________________AddIngredients________________________________

    @FXML
    private TextField tfMIngridientName;

    @FXML
    private TextField tfMQuantity;

    @FXML
    void btnAddMIngridients(ActionEvent event) {

    }
    
  //_______________________________Carte________________________________

  

    @FXML
    private TextArea taCreateMenu;

    @FXML
    private ListView<?> lvMenuList;

    @FXML
    void btnAddMenu(ActionEvent event) {

    }
  

    //_______________________________Methods________________________________


    public void alert(String title, String text) {

    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle(title);
    	alert.setContentText(text);
    	alert.showAndWait();
    }

}
