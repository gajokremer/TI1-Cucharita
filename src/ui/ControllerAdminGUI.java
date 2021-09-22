package ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.AppManager;
import model.StaffMember;

public class ControllerAdminGUI {
	
	private AppManager manager;

	public ControllerAdminGUI() {
		
		manager = new AppManager();
	}
	
	@FXML
	private Pane mainPane;

	@FXML
	private TextField tfId; //ID

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
		
		if(manager.correctPassword(tfId.getText(), pfPassword.getText())) {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
			fxmlloader.setController(this);
			Parent menu = fxmlloader.load();
			mainPane.getChildren().setAll(menu);
			
		} else {

			String header = "Log In error";
			String message = "Incorrect ID or Password";
			showWarningDialogue(header, message);
		}
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
	void openCarte(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Carte.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	@FXML
	void openOrders(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(""));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

	}

	@FXML
	void openStaff(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Staff.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		initializeStaffTableView();
	}

	//_______________________________STAFF________________________________


	@FXML
	private TableView<StaffMember> tvAllStaff;

	@FXML
	private TableColumn<StaffMember, String> tcName;

	@FXML
	private TableColumn<StaffMember, String> tcID;

	@FXML
	private TableColumn<StaffMember, String> tcBirthdate;

	@FXML
	private Label txtCurrentUser;
	
    private ObservableList<StaffMember> observableList;
	
	public void initializeStaffTableView() {
		
		observableList = FXCollections.observableArrayList(manager.getStaff());

		tvAllStaff.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<StaffMember, String>("name"));   
		tcID.setCellValueFactory(new PropertyValueFactory<StaffMember, String>("id"));
		tcBirthdate.setCellValueFactory(new PropertyValueFactory<StaffMember, String>("birthdate"));
	}

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
    private TextField tfName;

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
    private TextField tfIngredientName;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfUnit;

    @FXML
    void btnAddThisIngredient(ActionEvent event) {

    }

    //_______________________________ModifyIngredients________________________________

    @FXML
    private TextField tfMIngredientName;

    @FXML
    private TextField tfMQuantity;

    @FXML
    void btnModifyThisIngredient(ActionEvent event) {

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


    public void showSuccessDialogue(String header, String message) {

    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("La Cucharita");
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
    
    public void showWarningDialogue(String header, String message) {
    	
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("La Cucharita");
    	alert.setHeaderText(header);
    	alert.setContentText(message);
    	alert.showAndWait();
    }
    
    @FXML
    void miLogOut(ActionEvent event) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
    }
    
    @FXML
    void close(ActionEvent event) {

		Platform.exit();
    }

}
