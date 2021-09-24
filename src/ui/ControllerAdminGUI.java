package ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.AppManager;
import model.Combo;
import model.Ingredient;
import model.StaffMember;

public class ControllerAdminGUI {
	
	private AppManager manager;

	public ControllerAdminGUI() {
		
		manager = new AppManager();
	}
	
	
	private String currentUser = "";
	
	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	
	@FXML
	private Pane mainPane;

	@FXML
	private TextField tfId; //ID

	@FXML
	private PasswordField pfPassword;
	
	@FXML
	private ImageView ivMainMenuLogo;
	
//	private String IMAGES_ROUTE = "img/";
	

	@FXML
	void start() throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		manager.importStaffData();
		manager.importInventoryData();
		
//		Image logo = new Image("../data/LaCucharita.png");
//		ivMainMenuLogo.setImage(logo);
	}


	@FXML
	public void btnLogIn(ActionEvent event) throws IOException{
		
		if(manager.correctPassword(tfId.getText(), pfPassword.getText())) {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
			fxmlloader.setController(this);
			Parent menu = fxmlloader.load();
			mainPane.getChildren().setAll(menu);
			
			setCurrentUser(tfId.getText());
			
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

		initializeInventoryTableView();
		txtCurrentUser.setText(getCurrentUser());
	}

	@FXML
	void openCarte(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Carte.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

		txtCurrentUser.setText(getCurrentUser());
	}

	@FXML
	void openOrders(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(null));
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
		txtCurrentUser.setText(getCurrentUser());
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
	private DatePicker dpBirthdate;

	@FXML
	private PasswordField pfConfirmPassword;

	@FXML
	void btnAddNewStaffMember(ActionEvent event) throws IOException {

		String name = "";
		String id = "";
		String password = "";
		String birthdate = "";

		name = tfName.getText();
		id = tfId.getText();

		LocalDate aDate = dpBirthdate.getValue();
		birthdate = aDate.toString();

		password = pfPassword.getText();

		//PENDIENTE
		if(name != "" && id != "" && birthdate != "" && password != "") {

			if(password.equals(pfConfirmPassword.getText())) {

				StaffMember m = new StaffMember(name, id, password, birthdate);
				
				if(manager.addStaffMember(m)) {

					String header = "Sucess";
					String message = "Staff member added successfully";
					showSuccessDialogue(header, message);
				}

			} else {

				String header = "Registration error";
				String message = "Password are not the same";
				showWarningDialogue(header, message);
			}

		} else {

			String header = "Registration error";
			String message = "There is an empty field";
			showWarningDialogue(header, message);
		}

		tfName.setText("");
		tfId.setText("");
		dpBirthdate.setAccessibleText("");
		pfPassword.setText("");
		pfConfirmPassword.setText("");

		manager.exportStaffData();
		initializeStaffTableView();
	}

	//_______________________________ChangePassword________________________________

	@FXML
	private TextField tfOldPassword;

	@FXML
	private PasswordField pfNewPassword;

	@FXML
	private PasswordField pfConfirmChangePassword;

	@FXML
	void btnSetNewPassword(ActionEvent event) {
		
		String oldPass = tfOldPassword.getText();
		String newPass = pfNewPassword.getText();
		String cNewPass = pfConfirmChangePassword.getText();

		for(int i = 0; i < manager.getStaff().size(); i++) {
			
			if(manager.getStaff().get(i).getId().equals(getCurrentUser())) {
				
				if(manager.correctPassword(getCurrentUser(), oldPass)) {
					
					if(!oldPass.equals(newPass)) {
						
						if(newPass.equals(cNewPass)) {
							
							manager.getStaff().get(i).setPassword(pfNewPassword.getText());
							
							String header = "Password change successfull";
							String message = "Password has been changed successfully";
							showSuccessDialogue(header, message);
							
							tfOldPassword.setText("");
							pfNewPassword.setText("");
							pfConfirmChangePassword.setText("");
							
						} else {
							
							String header = "Password change error";
							String message = "Confirm password does not match new password";
							showWarningDialogue(header, message);
						}
						
					} else {
						
						String header = "Password change error";
						String message = "New password is the same as old one";
						showWarningDialogue(header, message);
					}
					
				} else {
					
					String header = "Password change error";
					String message = "Old password is incorrect";
					showWarningDialogue(header, message);
				}
			}
		}
		
	}
	
	
	
	//_______________________________Inventory________________________________
	
    @FXML
    private TableView<Ingredient> tvInventory;

    @FXML
    private TableColumn<Ingredient, String> tcIngredientName;

    @FXML
    private TableColumn<Ingredient, String> tcQuantity;

    @FXML
    private TableColumn<Ingredient, String> tcUnit;

    private ObservableList<Ingredient> observableList1;
	
   	public void initializeInventoryTableView() {
   		
   		observableList1 = FXCollections.observableArrayList(manager.getInventory());

   		tvInventory.setItems(observableList1);
   		tcIngredientName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));   
   		tcQuantity.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("quantity"));
   		tcUnit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
   	}
    
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
    void btnAddThisIngredient(ActionEvent event) throws IOException {

    	String name = "";
    	int quantity = 0;
    	String unit = "";
    	
    	name = tfIngredientName.getText();
    	quantity = Integer.parseInt(tfQuantity.getText());
    	unit = tfUnit.getText();
    	
    	Ingredient ing = new Ingredient(name, quantity, unit);
    	
    	boolean exists = false;
    	
    	for(int i = 0; i < manager.getInventory().size() && !exists; i++) {

    		if(manager.getInventory().get(i).getName().equalsIgnoreCase(name)) {

    			int add = manager.getInventory().get(i).getQuantity();
    			manager.getInventory().get(i).setQuantity(add + quantity);
    			exists = true;
    			
    			String header = "Addition successfull";
    			String message = "Ingredient already exists. Additional quantity has been "
    					+ "successfully added to it";
    			showSuccessDialogue(header, message);
    			
    		} else {
    			
    			if(manager.addIngredient(ing)) {
    				
    				String header = "Addition successfull";
    				String message = "Ingredient has been successfully added to inventory";
    				showSuccessDialogue(header, message);
    				
    				exists = true;
    			}
    		}
    	}
    	
    	tfIngredientName.setText("");
    	tfQuantity.setText("");
    	tfUnit.setText("");
 
		manager.exportInventoryData();
		openInventory(event);
		initializeInventoryTableView();
		
		
		//PENDIENTE
//    	if(name != "" && quantity >= 0 && unit != "") {
//    		
//    	}
    }

    //_______________________________ModifyIngredients________________________________

    @FXML
    private TextField tfMIngredientName;

    @FXML
    private TextField tfMQuantity;

    @FXML
    void btnModifyThisIngredient(ActionEvent event) throws IOException {

    	String ingredientName = tfMIngredientName.getText();
    	int newQuantity = Integer.parseInt(tfMQuantity.getText());

    	boolean exists = false;
    	
    	for(int i = 0; i < manager.getInventory().size() && !exists; i++) {

    		if(manager.getInventory().get(i).getName().equalsIgnoreCase(ingredientName)) {
    			
    			exists = true;

    			if(newQuantity >= 0) {

    				manager.getInventory().get(i).setQuantity(newQuantity);

    				String header = "Ingredient modification successful";
    				String message = "New ingredient quantity has been set";
    				showSuccessDialogue(header, message);

    			} else {

    				String header = "Ingredient modification error";
    				String message = "New ingredient quantity cannot be a negative number";
    				showWarningDialogue(header, message);
    			}
    		} 
    	}
    	
    	if(exists == false) {
    		
    		String header = "Ingredient modification error";
    		String message = "Ingredient is not in inventory";
    		showWarningDialogue(header, message);
    	}
    	
    	tfMIngredientName.setText("");
    	tfMQuantity.setText("");
    	
    	manager.exportInventoryData();
    	openInventory(event);
		initializeInventoryTableView();
    }
    
  //_______________________________Carte________________________________

    @FXML
    private ListView<?> lvMenuList;
    
    @FXML
    private TextField tfNewComboName;

	@FXML
    void btnCreateCombo(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddMenu.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();

		lbNewComboName.setText(tfNewComboName.getText());
		createChoiceBox();
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
		
    }
    
    //_______________________________AddMenu________________________________
    
    @FXML
    private ChoiceBox<String> cbAddIngredient1;
    
//    private ObservableList<String> ingredientsList = FXCollections.observableArrayList(manager.inventoryNamesInList());
//    private ChoiceBox<String> cb = new ChoiceBox<String>(ingredientsList);
    
    private ObservableList<String> ingredientsList;
    private ChoiceBox<String> cb;
    
    @FXML
    private HBox hBox;

    @FXML
    private TextField tfAddQuantity;

    @FXML
    private TextField tfAddUnits;
    
    @FXML
    private TextField tfAddPrice;

    @FXML
    private TextArea taIngredientsList;

    @FXML
    private Label lbNewComboName;

    public void createChoiceBox() {

    	ingredientsList = FXCollections.observableArrayList(manager.inventoryNamesInList());
    	cb = new ChoiceBox<String>(ingredientsList);
    	hBox.getChildren().add(cb);
    }
    
    @FXML
    void btnAddIngredientToCombo(ActionEvent event) {

    	String name = cb.getSelectionModel().getSelectedItem();
    	String quantity = tfAddQuantity.getText();
    	String unit = tfAddUnits.getText();
    	
    	taIngredientsList.appendText(name + ";" + quantity + ";" + unit + "\n");
    	
    	cb.setValue(null);
    	tfAddQuantity.setText("");
    	tfAddUnits.setText("");
    }

    @FXML
    void btnAddNewCombo(ActionEvent event) throws IOException {

    	List<Ingredient> list = manager.ingredientsForCombo(taIngredientsList.getText());
    	double price = Double.parseDouble(tfAddPrice.getText());

    	Combo combo = new Combo(lbNewComboName.getText(), list, price);

    	manager.addCombo(combo);
    	
    	manager.exportComboData();
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
