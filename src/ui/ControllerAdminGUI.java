package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.AppManager;
import model.Combo;
import model.Ingredient;
import model.Order;
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
	
	@FXML
	private ImageView iv1;
	
	@FXML
	private ImageView iv2;
	
	@FXML
	private ImageView iv3;
	
	@FXML
	private ImageView iv4;
	
	@FXML
	private ImageView iv5;
	
	@FXML
	private ImageView iv6;
	
	@FXML
	private ImageView iv7;
	
	@FXML
	private ImageView iv8;
	
	@FXML
	private ImageView iv9;
	
	@FXML
	private ImageView iv10;
	
	
//	private String IMAGES_ROUTE = "img/";
	

	@FXML
	void start() throws IOException, ClassNotFoundException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);

		manager.importStaffData();
		manager.importInventoryData();
		manager.loadComboData();
		manager.loadOrderData();

		Image logo = new Image("LaCucharita.png");
		ivMainMenuLogo.setImage(logo);

		Image image1 = new Image("tenedor 3.png");
		iv1.setImage(image1);
		
		Image image2 = new Image("comida 1.jpg");
		iv2.setImage(image2);
	}


	@FXML
	public void btnLogIn(ActionEvent event) throws IOException{

		if (tfId.getText().trim().isEmpty() || pfPassword.getText().trim().isEmpty() ) {

			String header = "Log In error";
			String message = "Enter the id and password";
			showWarningDialogue(header, message);

		} else {

			if(manager.correctPassword(tfId.getText(), pfPassword.getText())) {

				FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
				fxmlloader.setController(this);
				Parent menu = fxmlloader.load();
				mainPane.getChildren().setAll(menu);

				setCurrentUser(tfId.getText());
				
				Image logo = new Image("LaCucharita.png");
				ivMainMenuLogo.setImage(logo);
				
				Image image3 = new Image("ss.png");
				iv3.setImage(image3);

			} else {

				String header = "Log In error";
				String message = "Incorrect ID or Password";
				showWarningDialogue(header, message);
			}
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
		
		Image image4 = new Image("sss.jpg");
		iv4.setImage(image4);
	}

	@FXML
	void openCarte(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Carte.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image image7 = new Image("menu.jpg");
		iv7.setImage(image7);
		
		initializeCarteListView();
		txtCurrentUser.setText(getCurrentUser());
	}

	@FXML
	void openOrders(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Orders.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
    	combosForOrder.removeAll(combosForOrder);
		initializeAllCombosListView();
		initializeOrdersTableView();
		
		txtCurrentUser.setText(getCurrentUser());
	}

	@FXML
	void openStaff(ActionEvent event) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Staff.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image image8 = new Image("staff 3.png");
		iv8.setImage(image8);
		
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
		
		Image image10 = new Image("register.gif");
		iv10.setImage(image10);
		
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
	}

	@FXML
	void btnChangePassword(ActionEvent event) throws IOException {


			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
			fxmlloader.setController(this);
			DialogPane dialoguePane = fxmlloader.load();
			
			Image image9 = new Image("password.png");
			iv9.setImage(image9);

			Dialog<ButtonType> dialog = new Dialog<ButtonType>();
			dialog.setDialogPane(dialoguePane);
			dialog.showAndWait();
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

		//PENDIENTE dpBirthday

		if (tfId.getText().trim().isEmpty() ||tfName.getText().trim().isEmpty() /*|| dpBirthdate.getValue()*/  || pfConfirmPassword.getText().trim().isEmpty() ) {

			String header = "Add Staff error";
			String message = "Enter all the information";
			showWarningDialogue(header, message);

		} else {


			if(password.equals(pfConfirmPassword.getText())) {

				StaffMember m = new StaffMember(name, id, password, birthdate);

				if(manager.addStaffMember(m)) {

					String header = "Sucess";
					String message = "Staff member added successfully";
					showSuccessDialogue(header, message);


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

		if (tfOldPassword.getText().trim().isEmpty() || pfNewPassword.getText().trim().isEmpty()  || pfConfirmChangePassword.getText().trim().isEmpty() ) {

			String header = "Change password error";
			String message = "Enter all the asked passwords";
			showWarningDialogue(header, message);

		} else {

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
   		
   		manager.sortInventoryByName();
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
		
		Image image5 = new Image("porfa.jpg");
		iv5.setImage(image5);

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
    }

    @FXML
    void btnModifyIngredient(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ModifyIngredient.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();
		
		Image image6 = new Image("carne.jpg");
		iv6.setImage(image6);

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
    	
    	//PENDIENTE QUANTITY
    	if (tfIngredientName.getText().trim().isEmpty() || tfQuantity.getText().trim().isEmpty() || tfUnit.getText().trim().isEmpty()) {

    		String header = "Add Ingredient error";
    		String message = "Enter all ingredients";
    		showWarningDialogue(header, message);

    	} else {

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
    	
    	//PENDIENTE tfMQuantity
    	if (tfMIngredientName.getText().trim().isEmpty() || tfMQuantity.getText().trim().isEmpty()) {

    		String header = "Modify Ingredient error";
    		String message = "Enter all ingredients";
    		showWarningDialogue(header, message);

    	} else {

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
    	}

    	tfMIngredientName.setText("");
    	tfMQuantity.setText("");

    	manager.exportInventoryData();
    	openInventory(event);
    	initializeInventoryTableView();
    }
    
  //_______________________________Carte________________________________

    @FXML
    private ListView<String> lvMenuList;
    
    @FXML
    private TextField tfNewComboName;
    
    private ObservableList<String> observableList2;

	@FXML
    void btnCreateCombo(ActionEvent event) throws IOException {
		
    	if (tfNewComboName.getText().trim().isEmpty()) {
    		
    		String header = "Combo name error";
    		String message = "Write the combo name before trying to create a combo";
    		showWarningDialogue(header, message);
    	
    	} else {
    		
    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddMenu.fxml"));
    		fxmlloader.setController(this);
    		DialogPane dialoguePane = fxmlloader.load();

    		lbNewComboName.setText(tfNewComboName.getText());
    		createChoiceBox();
    		
    		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
    		dialog.setDialogPane(dialoguePane);
    		dialog.showAndWait();
    		
    		tfNewComboName.setText("");
    		initializeCarteListView();
    	}
    }
	
	public void initializeCarteListView() {

		observableList2 = FXCollections.observableArrayList(manager.comboNames());
		
		lvMenuList.setItems(observableList2);
	}
	
	  @FXML
	    void removeAllCombos(ActionEvent event) throws IOException {

		  manager.getCombos().removeAll(manager.getCombos());
		  manager.saveComboData();
		  openCarte(event);
	    }
    
    //_______________________________AddCombo________________________________
    
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
    	int quantity = Integer.parseInt(tfAddQuantity.getText());
    	String unit = tfAddUnits.getText();

    	if (tfAddQuantity.getText().trim().isEmpty() || tfAddUnits.getText().trim().isEmpty() 
    			|| cb.getSelectionModel().getSelectedItem().isEmpty()) {

    		String header = "Add ingredient error";
    		String message = "Enter all the engredients before trying to add a engredient";
    		showWarningDialogue(header, message);

    	} else {

    		if(quantity >= 0){

    			manager.itemIsAvailable(name, quantity);
    			
    			taIngredientsList.appendText(name + ";" + quantity + ";" + unit + "\n");
    			String header = "Ingredient add successful";
    			String message = "The ingredient was  succesfully added";
    			showSuccessDialogue(header, message);
    			
    		} else {

    			String header = "Add ingredient error";
    			String message = "Ingredient quantity cannot be a negative number";
    			showWarningDialogue(header, message);
    		}
    	}

    	cb.setValue(null);
    	tfAddQuantity.setText("");
    	tfAddUnits.setText("");
    }

    @FXML
    void btnAddNewCombo(ActionEvent event) throws IOException {

    	if (tfAddPrice.getText().trim().isEmpty() ) {

    		String header = "Add new combo error";
    		String message = "Enter the price";
    		showWarningDialogue(header, message);

    	} else if (taIngredientsList.getText().trim().isEmpty() ) {

    		String header = "Add new combo error";
    		String message = "Enter all the engredients before adding a combo";
    		showWarningDialogue(header, message);

    	} else {
    		
    		List<Ingredient> list = manager.ingredientsForCombo(taIngredientsList.getText());
    		
    		double price = Double.parseDouble(tfAddPrice.getText());
    		
    		Combo combo = new Combo(lbNewComboName.getText(), list, price);
    		
    		manager.addCombo(combo);
    		manager.saveComboData();

    		String header = "Combo created";
    		String message = "Combo successfully created";
    		showSuccessDialogue(header, message);

    		taIngredientsList.setText("");
    		tfAddPrice.setText("");
    	}
    }

    
    //_______________________________Orders________________________________
    

    @FXML
    private TableView<Order> tvOrders;

    @FXML
    private TableColumn<Order, String> tcUuid;

    @FXML
    private TableColumn<Order, String> tcDate;

    @FXML
    private TableColumn<Order, String> tcStatus;

    @FXML
    private TableView<Combo> tvCombosSelected;

    @FXML
    private TableColumn<Combo, String> tcComboName;

    @FXML
    private TableColumn<Combo, String> tcComboQuantity;

    @FXML
    private TableColumn<Combo, String> tcComboTotalPrice;
    
    private ObservableList<String> observableList3;
    
    private ObservableList<Combo> observableList4;
    
    private ObservableList<Order> observableList5;
    
    
    private List<Combo> combosForOrder = new ArrayList<Combo>();

    public void initializeAllCombosListView() {
    	
    	observableList3 = FXCollections.observableArrayList(manager.comboNames());
		
		lvMenuList.setItems(observableList3);
    }
    
    public void initializeOrderCombosTableView() {
    	
    	observableList4 = FXCollections.observableArrayList(combosForOrder);

    	tvCombosSelected.setItems(observableList4);
    	tcComboName.setCellValueFactory(new PropertyValueFactory<Combo, String>("name"));   
    	tcComboTotalPrice.setCellValueFactory(new PropertyValueFactory<Combo, String>("price"));
    }
    
    public void initializeOrdersTableView() {
    	
    	observableList5 = FXCollections.observableArrayList(manager.getOrders());
    	
    	tvOrders.setItems(observableList5);
    	tcUuid.setCellValueFactory(new PropertyValueFactory<Order, String>("uuid"));   
    	tcDate.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
    	tcStatus.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
    }
    
    @FXML
    void btnAddCombo(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("AddComboMenu.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();
		
		txtComboName.setText(lvMenuList.getSelectionModel().getSelectedItem());
		initializeIngredientsOfThisComboTableView();

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setDialogPane(dialoguePane);
		dialog.showAndWait();
    }

    @FXML
    void btnAddOrder(ActionEvent event) throws FileNotFoundException, IOException {

    	if(!tvCombosSelected.getItems().isEmpty()) {

    		UUID uuid = UUID.randomUUID();
    		List<Combo> combos = combosForOrder;
    		String status = "PENDING";

    		Date currentDate = new Date();

    		SimpleDateFormat timeFormat =  new SimpleDateFormat("hh:mm:ss");
    		//    	System.out.println(timeFormat.format(currentDate));

    		SimpleDateFormat dateFormat =  new SimpleDateFormat("yyy-MM-dd");
    		//    	System.out.println(dateFormat.format(currentDate));

    		String dateAndTime = dateFormat.format(currentDate) + " at " + timeFormat.format(currentDate); 

    		Order o = new Order(uuid.toString(), combos, status, dateAndTime);
    		////
    		manager.addOrder(o);

    		manager.saveOrderData();
    		initializeOrdersTableView();

    		tvCombosSelected.setItems(null);
    		
    	} else {
    		
    		String header = "Order creation error";
    		String message = "There must be combos to be ordered";
    		showSuccessDialogue(header, message);
    	}
    }
    	
    
    @FXML
    void btnEmptyCombos(ActionEvent event) throws IOException {
    	
    	manager.restoreIngredientValues(combosForOrder);
    	combosForOrder.removeAll(combosForOrder);
    	initializeOrderCombosTableView();
    }

    @FXML
    void btnModifyOrderStatus(ActionEvent event) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ModifyStatus.fxml"));
		fxmlloader.setController(this);
		DialogPane dialoguePane = fxmlloader.load();
		
		if(!tvOrders.getSelectionModel().isEmpty()) {
			
			tfUuid.setText(tvOrders.getSelectionModel().getSelectedItem().getUuid());
			
			Dialog<ButtonType> dialog = new Dialog<ButtonType>();
			dialog.setDialogPane(dialoguePane);
			dialog.showAndWait();
			
		} else {
			
			String header = "Change status error";
			String message = "No Order selected";
			showWarningDialogue(header, message);
		}
    }

    @FXML
    void btnOrderDetails(ActionEvent event) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("OrderDetails.fxml"));
    	fxmlloader.setController(this);
    	DialogPane dialoguePane = fxmlloader.load();

    	if(!tvOrders.getSelectionModel().isEmpty()) {

    		tfUuid.setText(tvOrders.getSelectionModel().getSelectedItem().getUuid());
    		initializeDetailsListView();
    		
    		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
    		dialog.setDialogPane(dialoguePane);
    		dialog.showAndWait();

    	} else {

    		String header = "Change status error";
    		String message = "No Order selected";
    		showWarningDialogue(header, message);
    	}
    }
    
    @FXML
    void btnRemoveDelivered(ActionEvent event) throws IOException {

    	for(int i = 0; i < manager.getOrders().size(); i++) {
    		
    		if(manager.getOrders().get(i).getStatus().equalsIgnoreCase("DELIVERED")) {
    			
    			manager.getOrders().remove(i);
    		}
    	}
    	
    	manager.saveOrderData();
    	openOrders(event);
    }

    @FXML
    void btnRemoveAll(ActionEvent event) throws IOException {
    	
    	manager.getOrders().removeAll(manager.getOrders());
    	manager.saveOrderData();
    	openOrders(event);
    }
    
    @FXML
    void btnSpecialBack(ActionEvent event) throws IOException {

    	if(tvCombosSelected.getItems().isEmpty()) {

    		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
    		fxmlloader.setController(this);
    		Parent menu = fxmlloader.load();
    		mainPane.getChildren().setAll(menu);
    		
    		Image logo = new Image("LaCucharita.png");
			ivMainMenuLogo.setImage(logo);
			
			Image image3 = new Image("ss.png");
			iv3.setImage(image3);

    	} else {

    		String header = "Interface error";
    		String message = "Combos selected must be empty to go back"
    				+ "\n"
    				+ "Press 'Empty Combos' button";
    		showWarningDialogue(header, message);
    	}
    }
    
    
    //_______________________________AddComboMenu________________________________
    
    @FXML
    private TableView<Ingredient> tvListOfComboForMenu;

    @FXML
    private TableColumn<Ingredient, String> tcIngredient;
    
    @FXML
    private TableColumn<Ingredient, String> tcIngQuantity;

    @FXML
    private TableColumn<Ingredient, String> tcIngUnit;

    @FXML
    private Label txtComboName;
    
    @FXML
    private TextField tfAddOrSub;

    private ObservableList<Ingredient> observableList6;
    
    private List<Ingredient> inventoryCopy;
    
    public void initializeIngredientsOfThisComboTableView() {
    	
    	observableList6 = FXCollections.observableArrayList(manager.comboIngredientsList(txtComboName.getText()));
    	
    	tvListOfComboForMenu.setItems(observableList6);
    	tcIngredient.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));  
    	tcIngQuantity.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("quantity"));
    	tcIngUnit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
    }
    
    @FXML
    void btnAddComboToMenu(ActionEvent event) throws IOException {
    	
    	boolean allAvailable = true;
    	
    	inventoryCopy = manager.getInventory();

    	for(int i = 0; i < Integer.parseInt(tfAddOrSub.getText()) && allAvailable; i++) {
    		
    		Combo c  = manager.findThisCombo(txtComboName.getText());

    		allAvailable = manager.allItemsAreAvailable(c, inventoryCopy, allAvailable);
    		
    		if(allAvailable) {
    			
//    			System.out.println("=====" + manager.allItemsAreAvailable(c, inventoryCopy));
    			
    			combosForOrder.add(c);
    			
    		} else {
    			
    			String header = "Combo addition error";
    			String message = "Insuffiecient quantity of an ingredient";
    			showWarningDialogue(header, message);
    			allAvailable = false;
    		}
    	}
    	
    	tfAddOrSub.setText("0");
    	manager.organizeCombosByPriceInsertionSort(combosForOrder);
    	
    	initializeOrderCombosTableView();
    }
    
    // Add and Sub Buttons
    
    @FXML
    void btnAddTotalCombo(ActionEvent event) {
    	
    	int newValue = Integer.parseInt(tfAddOrSub.getText()) + 1;
    	
    	if(newValue >= 0) {
    		
    		tfAddOrSub.setText(String.valueOf(newValue));
    	}
    }

    @FXML
    void btnSubTotalCombo(ActionEvent event) {

    	int newValue = Integer.parseInt(tfAddOrSub.getText()) - 1;
    	
    	if(newValue >= 0) {
    		
    		tfAddOrSub.setText(String.valueOf(newValue));
    		
    	} else {
    		
    		String header = "Numeric error";
    		String message = "Value can't be under 0";
    		showWarningDialogue(header, message);
    	}
    }
    
    //_______________________________ModifyStatus_______________________________

    @FXML
    private TextField tfUuid;
    
    @FXML
    private ToggleGroup tgStatus;

    @FXML
    private RadioButton rbPending;

    @FXML
    private RadioButton rbDelivered;

    @FXML
    private RadioButton rbInProcess;
    
    @FXML
    void btnChangeStatus(ActionEvent event) throws IOException {

    	String status = "";
    	
    	if(rbPending.isSelected()) {
    		
    		status = "PENDING";
    		
    	} else if(rbInProcess.isSelected()) {
    		
    		status = "IN PROGRESS";
    		
    	} else if(rbDelivered.isSelected()) {
    		
    		status = "DELIVERED";
    	}
    	
    	if(status != null) {
    		
    		manager.changeOrderStatus(tfUuid.getText(), status);
    	}
    	
    	manager.saveOrderData();
    	openOrders(event);
    }
    
    //_______________________________OrderDetails________________________________
    
    @FXML
    private ListView<String> lvOrderCombos;

    @FXML
    private TableView<Ingredient> tvComboIngredients;
    
    private ObservableList<String> observableList7;

    private ObservableList<Ingredient> observableList8;
    
//    @FXML
//    private TableColumn<?, ?> tcIngredientName;
//
//    @FXML
//    private TableColumn<?, ?> tcQuantity;
//
//    @FXML
//    private TableColumn<?, ?> tcUnit;

    @FXML
    void btnShowComboIngredients(ActionEvent event) {
    	
    	initializeDetailsTableView();
    }
    
    public void initializeDetailsListView() {

    	observableList7 = FXCollections.observableArrayList(manager.combosOfAnOrder(tfUuid.getText()));

    	lvOrderCombos.setItems(observableList7);
    }

    public void initializeDetailsTableView() {

    	observableList8 = FXCollections.observableArrayList(manager.comboIngredientsList(lvOrderCombos.getSelectionModel().getSelectedItem()));

    	tvComboIngredients.setItems(observableList8);
    	tcIngredientName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));  
    	tcQuantity.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("quantity"));
    	tcUnit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
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
	void btnBack(ActionEvent event) throws IOException{

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MenuOptions.fxml"));
		fxmlloader.setController(this);
		Parent menu = fxmlloader.load();
		mainPane.getChildren().setAll(menu);
		
		Image logo = new Image("LaCucharita.png");
		ivMainMenuLogo.setImage(logo);
		
		Image image3 = new Image("ss.png");
		iv3.setImage(image3);
	}
    
    @FXML
    void close(ActionEvent event) {

		Platform.exit();
    }
}