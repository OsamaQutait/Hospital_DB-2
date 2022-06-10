package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import DatabaseConnector.DBConnector;
import Hospital.Identity;
import Hospital.MedicalStaff;
import Hospital.MedicalStaff2Surgeries;
import Hospital.MedicalStaff2Tests;
import Hospital.Phone2ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class medicalStaffController implements Initializable {

	@FXML
	private Button patientButton;

	@FXML
	private JFXTextField idNum;

	@FXML
	private JFXTextField fullName;

	@FXML
	private JFXComboBox<String> bloodType;

	@FXML
	private JFXComboBox<String> gender;

	@FXML
	private JFXDatePicker dateOfBirth;

	@FXML
	private JFXButton idClear;

	@FXML
	private JFXButton addPhoneNumbers;

	@FXML
	private JFXComboBox<String> address;

	@FXML
	private JFXComboBox<String> StaffRole;

	@FXML
	private JFXTextField StaffSpecialization;

	@FXML
	private JFXTextField StaffRating;

	@FXML
	private JFXComboBox<String> surgeryName;

	@FXML
	private JFXButton addSurgery;

	@FXML
	private JFXButton sClear;

	@FXML
	private JFXComboBox<String> testName;

	@FXML
	private JFXButton addTest;

	@FXML
	private JFXButton tClear;

	@FXML
	private JFXButton register;

	@FXML
	private JFXButton StaffClear;

	@FXML
	private TableView<MedicalStaff> Tstaff;

	@FXML
	private TableColumn<MedicalStaff, Integer> TstaffId;

	@FXML
	private TableColumn<MedicalStaff, String> TstaffRole;

	private static ArrayList<String> phoneNumbers = null;
	ArrayList<String> genderList;
	ArrayList<String> bloodList;
	ArrayList<String> StaffRoleList;
	ArrayList<String> cityList;
	ArrayList<String> allSurgeries;
	ArrayList<String> allTests;
	
	ArrayList<MedicalStaff> allStaff;
	private ObservableList<MedicalStaff> dataLisStaff;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAll();
		try {
			comboBoxesInitializing();
			assignComboBoxesValues();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		tableView();
		StaffRole.setOnAction((ActionEvent e) -> {
			testClear();
			surgeryClear();
			if (StaffRole.getSelectionModel().isSelected(0)) {
				disableSurgery(false);
				disableTest(true);
			} else if (StaffRole.getSelectionModel().isSelected(1)) {
				disableSurgery(true);
				disableTest(false);
			}
		});
		idClear.setOnAction((ActionEvent e) -> {
			identityClear();
		});
		sClear.setOnAction((ActionEvent e) -> {
			surgeryClear();
		});
		tClear.setOnAction((ActionEvent e) -> {
			testClear();
		});
		StaffClear.setOnAction((ActionEvent e) -> {
			StaffClear();
		});
		addPhoneNumbers.setOnAction((ActionEvent e) -> {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../screens/phoneNumbers.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		});
		register.setOnAction((ActionEvent e) -> {
			if (identityValidation() && staffValidation()) {
				try {
					if (datesValidation()) {
						setData();
					}
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} catch (ClassNotFoundException classNotFoundException) {
					classNotFoundException.printStackTrace();
				} catch (ParseException parseException) {
					parseException.printStackTrace();
				}
			}
		});
		
		addSurgery.setOnAction((ActionEvent e) -> {
            if (identityValidation() && staffValidation() && surgeryValidation() == 1 ) {
                try {
                    if (identityExistence(Integer.parseInt(idNum.getText()))){
                        surgeryName.setUnFocusColor(Paint.valueOf("black"));
                        insertSurgery();                     
                    }else{
                        surgeryName.setUnFocusColor(Paint.valueOf("red"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        addTest.setOnAction((ActionEvent e) -> {
            if (identityValidation() && staffValidation()  &&  testValidation() == 1) {
                try {
                    if (identityExistence(Integer.parseInt(idNum.getText()))){
                        testName.setUnFocusColor(Paint.valueOf("black"));
                        insertTest();
                        
                    }else{
                        testName.setUnFocusColor(Paint.valueOf("red"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });
	}

	public void clearAll() {
		identityClear();
		StaffClear();
		surgeryClear();
		testClear();
		disableSurgery(true);
		disableTest(true);
	}

	private void disableSurgery(Boolean b) {
		addSurgery.setDisable(b);
		sClear.setDisable(b);
		surgeryName.setDisable(b);
	}

	private void disableTest(Boolean b) {
		tClear.setDisable(b);
		addTest.setDisable(b);
		testName.setDisable(b);
	}

	private void identityClear() {
		try {
			idNum.clear();
			fullName.clear();
			dateOfBirth.setValue(null);
			address.setValue(null);
			gender.setValue(null);
			bloodType.setValue(null);
			dateOfBirth.setPromptText("");
			dateOfBirth.setDefaultColor(Paint.valueOf("black"));
			bloodType.setUnFocusColor(Paint.valueOf("black"));
			gender.setUnFocusColor(Paint.valueOf("black"));
			address.setUnFocusColor(Paint.valueOf("black"));
			fullName.setUnFocusColor(Paint.valueOf("black"));
			idNum.setUnFocusColor(Paint.valueOf("black"));
			addPhoneNumbers.setStyle("-fx-background-color: #3b5998");
		} catch (Exception e) {
			System.out.println("");
		}
	}

	private void StaffClear() {
		try {
			StaffSpecialization.clear();
			StaffRating.clear();
			StaffRole.setValue(null);

			StaffSpecialization.setUnFocusColor(Paint.valueOf("black"));
			StaffRating.setUnFocusColor(Paint.valueOf("black"));
			StaffRole.setUnFocusColor(Paint.valueOf("black"));

			disableTest(true);
			disableSurgery(true);
		} catch (Exception e) {
			System.out.println("");
		}
	}

	private void surgeryClear() {
		surgeryName.setUnFocusColor(Paint.valueOf("black"));
		surgeryName.setValue(null);
	}

	private void testClear() {
		testName.setUnFocusColor(Paint.valueOf("black"));
		testName.setValue(null);
	}

	public void comboBoxesInitializing() throws ClassNotFoundException, SQLException {
		genderList = new ArrayList<>();
		genderList.add("Male");
		genderList.add("Female");

		bloodList = new ArrayList<>();
		bloodList.add("O-");
		bloodList.add("O+");
		bloodList.add("A-");
		bloodList.add("A+");
		bloodList.add("B-");
		bloodList.add("B+");
		bloodList.add("AB-");
		bloodList.add("AB+");

		StaffRoleList = new ArrayList<>();
		StaffRoleList.add("Doctor");
		StaffRoleList.add("Nurse");

		cityList = new ArrayList<>();
		cityList.add("Salfit");
		cityList.add("Nablus");
		cityList.add("Ramallah");
		cityList.add("Jenin");
		cityList.add("Tulkarm");
		cityList.add("Jerusalem");
		cityList.add("Jericho");
		cityList.add("Qalqilya");
		cityList.add("Bethlehem");
		cityList.add("Hebron");
		
		testsAndSurgeries();
	}

	public void testsAndSurgeries() throws ClassNotFoundException, SQLException{
		allSurgeries = new ArrayList<String>();
		allTests = new ArrayList<String>();
		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");

		// add the Surgeries
		SQL = "select surgery_name from Surgeries;";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next())
			allSurgeries.add(rs.getString(1));

		rs.close();
		stmt.close();

		// add the tests
		SQL = "select test_name from Tests;";
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);

		while (rs.next())
			allTests.add(rs.getString(1));

		rs.close();
		stmt.close();
		DBConnector.getCon().close();
	}
	
	// in this method we assign values to ComboBoxes
		public void assignComboBoxesValues() {
			gender.setItems(FXCollections.observableArrayList(genderList));
			address.setItems(FXCollections.observableArrayList(cityList));
			bloodType.setItems(FXCollections.observableArrayList(bloodList));
			StaffRole.setItems(FXCollections.observableArrayList(StaffRoleList));
			surgeryName.setItems(FXCollections.observableArrayList(allSurgeries));
			testName.setItems(FXCollections.observableArrayList(allTests));
		}
		
	private boolean identityValidation() {
		boolean flag = true;
		String str = "";
		if (idNum.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText())) {
			idNum.setUnFocusColor(Paint.valueOf("RED"));
			str += "- Invalid identity number!\n";
			flag = false;
		} else {
			idNum.setUnFocusColor(Paint.valueOf("black"));
		}
		if (phoneNumbers.isEmpty()) {
			addPhoneNumbers.setStyle("-fx-background-color: #ff0000");
			str += "- You should insert phone number!\n";
			flag = false;
		} else {
			addPhoneNumbers.setStyle("-fx-background-color: #3b5998");
		}

		if (!Pattern.matches("[A-Za-z-' ]+", fullName.getText())) {
			fullName.setUnFocusColor(Paint.valueOf("RED"));
			str += "- Invalid Staff name!\n";
			flag = false;
		} else {
			fullName.setUnFocusColor(Paint.valueOf("black"));
		}
		if (address.getSelectionModel().isEmpty()) {
			address.setUnFocusColor(Paint.valueOf("RED"));
			str += "- You should insert staff address!\n";
			flag = false;
		} else {
			address.setUnFocusColor(Paint.valueOf("black"));
		}
		if (gender.getSelectionModel().isEmpty()) {
			gender.setUnFocusColor(Paint.valueOf("red"));
			str += "- You should insert staff gender!\n";
			flag = false;
		} else {
			gender.setUnFocusColor(Paint.valueOf("black"));
		}
		if (bloodType.getSelectionModel().isEmpty()) {
			bloodType.setUnFocusColor(Paint.valueOf("red"));
			str += "- You should insert staff blood type!\n";
			flag = false;
		} else {
			bloodType.setUnFocusColor(Paint.valueOf("black"));
		}
		if (dateOfBirth.getValue() == null) {
			dateOfBirth.setDefaultColor(Paint.valueOf("red"));
			dateOfBirth.setPromptText("ERROR");
			flag = false;
		} else {
			dateOfBirth.setDefaultColor(Paint.valueOf("black"));
		}
		if (!flag)
			errorPop(str);
		return flag;
	}

	public boolean staffValidation() {
		boolean flag = true;
		String str = "";
		if (StaffSpecialization.getText().length() > 32) {
			StaffSpecialization.setUnFocusColor(Color.RED);
			StaffSpecialization.clear();
			str += ("- The entered Specialization is more than 32 characters!\n");
			flag = false;
		} else {
			StaffSpecialization.setUnFocusColor(Color.BLACK);
		}
		if (StaffRole.getSelectionModel().isEmpty()) {
			StaffRole.setUnFocusColor(Color.RED);
			StaffRole.setValue(null);
			str += ("- Staff Role wasn't chosen!\n");
			flag = false;
		} else {
			StaffRole.setUnFocusColor(Color.BLACK);
		}
		if (StaffRating.getText().length() > 9 || !Pattern.matches("[0-9]+", StaffRating.getText())) {
			StaffRating.setUnFocusColor(Color.RED);
			StaffRating.clear();
			str += ("- Invalid  Staff Rating, please get sure to insert an integer number with 9 digits max!\n");
			flag = false;
		} else {
			StaffRating.setUnFocusColor(Color.BLACK);
		}
		if (!flag)
			errorPop(str);
		return flag;
	}

	private int testValidation() {
		if (!testName.isDisabled()) {
			boolean flag = false;
			if (testName.getSelectionModel().isEmpty()) {
				testName.setUnFocusColor(Paint.valueOf("red"));
				errorPop("- You didn't choose any test!\n");
				flag = true;
			} else {
				testName.setUnFocusColor(Paint.valueOf("black"));
			}
			return !flag ? 1 : 0;
		}
		return -1;
	}

	private int surgeryValidation() {
		if (!surgeryName.isDisabled()) {
			boolean flag = false;
			if (surgeryName.getSelectionModel().isEmpty()) {
				surgeryName.setUnFocusColor(Paint.valueOf("red"));
				errorPop("- You didn't choose any surgery!\n");
				flag = true;
			} else {
				surgeryName.setUnFocusColor(Paint.valueOf("black"));
			}
			return !flag ? 1 : 0;
		}
		return -1;
	}

	private boolean datesValidation() {
		try {
			boolean flag = true;
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getValue().toString());
			Date currentDate = new Date();

			if (currentDate.compareTo(dob) <= 0) {
				dateOfBirth.setDefaultColor(Paint.valueOf("red"));
				dateOfBirth.setValue(null);
				dateOfBirth.setPromptText("ERROR");
				flag = false;
			} else {
				dateOfBirth.setDefaultColor(Paint.valueOf("black"));
			}
			return flag;
		} catch (ParseException parseException) {
			parseException.printStackTrace();
		}
		return false;
	}

	// this method is used to show the error pop message
	public void errorPop(String str) {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../screens/error.fxml"));
		primaryStage.setTitle("error");
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ErrorMessage alertController = loader.getController();
		alertController.setErrorLabel(str);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void setPhoneNumbers(ArrayList<String> pNums) {
		medicalStaffController.phoneNumbers = pNums;
	}

	public ArrayList<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setData() throws NumberFormatException, ParseException, ClassNotFoundException, SQLException {
		Identity id = new Identity(Integer.parseInt(idNum.getText()), fullName.getText(), gender.getValue(),
				new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue())), bloodType.getValue(),
				address.getValue());
		if (identityExistence(id.getIdentityNumber())) {
			idNum.setUnFocusColor(Paint.valueOf("RED"));
			errorPop("This identity number is already exist!\n");
		} else {
			idNum.setUnFocusColor(Paint.valueOf("black"));
			MedicalStaff Staff = new MedicalStaff(StaffRole.getValue(), StaffSpecialization.getText(),
					Integer.parseInt(StaffRating.getText()), Integer.parseInt(idNum.getText()));
			ArrayList<Phone2ID> phones = new ArrayList<>();
			String phones2Str = "";
			if (phoneNumbers.size() == 1) {
				phones2Str = "(" + id.getIdentityNumber() + ", " + phoneNumbers.get(0) + ");";
			} else {
				int i = 0;
				for (i = 0; i < phoneNumbers.size() - 1; i++) {
					phones.add(new Phone2ID(id.getIdentityNumber(), Integer.parseInt(phoneNumbers.get(i))));
					phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbers.get(i) + "),\n";
				}
				phones.add(new Phone2ID(id.getIdentityNumber(), Integer.parseInt(phoneNumbers.get(i))));
				phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbers.get(i) + ");";
			}

			DBConnector.connectDB();
			try {
				DBConnector.ExecuteStatement(
						"Insert into Identity (identity_number, full_name, gender, date_of_birth, blood_type, living_address) values("
								+ +id.getIdentityNumber() + ",'" + id.getFullName() + "', '" + id.getGender() + "', '"
								+ id.getDateOfBirthToString() + "', '" + id.getBloodType() + "', '"
								+ id.getLivingAddress() + "');");

				DBConnector.ExecuteStatement("Insert into MedicalStaff (staff_role, specialization, rating, identity_number) "
						+ "values("+ Staff.getRole() + "', '" + Staff.getSpecialization() + "', " + Staff.getRating() + ", "
						+ Staff.getIdentity_number() + "');");

				DBConnector
						.ExecuteStatement("Insert into Phone2ID (identity_number, phone_number) values" + phones2Str);

				DBConnector.getCon().close();
				tableView();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean identityExistence(int identityNum) throws SQLException, ClassNotFoundException {
		String SQL;
		DBConnector.connectDB();
		SQL = "select *\n" + "from identity id\n" + "where id.identity_number = " + identityNum + ";";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);
		boolean res = rs.next();
		rs.close();
		stmt.close();
		DBConnector.getCon().close();
		return res;
	}
	
	private void insertSurgery() throws SQLException, ClassNotFoundException, ParseException {
		DBConnector.connectDB();
		String SQL;
		SQL = "select S.staff_id\n" + "from MedicalStaff S, Identity id\n"+ "where id.identity_number=s.identity_number and "+
					"id.identity_number = " +idNum.getText()+";";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);
		int res = 0;
		if (rs.next())
			res = Integer.parseInt(rs.getString(1));
		rs.close();
		stmt.close();
        MedicalStaff2Surgeries M2S = null;

        M2S = new MedicalStaff2Surgeries(Integer.parseInt(surgeryName.getValue()),res);

     
        try {
            DBConnector.ExecuteStatement("Insert into MedicalStaff2Surgeries (surgery_id,staff_id) values("
                    + M2S.getSurgery_id() + ", "
                    + M2S.getStaff_id()+ ");");
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private void insertTest() throws SQLException, ClassNotFoundException, ParseException {
		DBConnector.connectDB();
		String SQL;
		SQL = "select S.staff_id\n" + "from MedicalStaff S, Identity id\n"+ "where id.identity_number=s.identity_number and "+
					"id.identity_number = " +idNum.getText()+";";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);
		int res = 0;
		if (rs.next())
			res = Integer.parseInt(rs.getString(1));
		rs.close();
		stmt.close();
		MedicalStaff2Tests M2T = null;

		M2T = new MedicalStaff2Tests(Integer.parseInt(testName.getValue()),res);

     
        try {
            DBConnector.ExecuteStatement("Insert into MedicalStaff2Tests (test_id,staff_id) values("
                    + M2T.getTest_id() + ", "
                    + M2T.getStaff_id()+ ");");
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void tableView(){
		try {
			getStaff();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		dataLisStaff = FXCollections.observableArrayList(allStaff);
		ShowStaff();
	}
	public void getStaff() throws ClassNotFoundException, SQLException{
		allStaff = new ArrayList<MedicalStaff>();
		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");

		// add the staff
		SQL = "select * from MedicalStaff;";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next())
			allStaff.add(new MedicalStaff(Integer.parseInt(rs.getString(1)), rs.getString(2),
					rs.getString(3), Integer.parseInt(rs.getString(4)),Integer.parseInt(rs.getString(5))));

		rs.close();
		stmt.close();

		DBConnector.getCon().close();
	}
	
	public void ShowStaff(){
		TstaffId.setCellValueFactory(new PropertyValueFactory<MedicalStaff, Integer>("staff_id"));
		
		TstaffRole.setCellValueFactory(new PropertyValueFactory<MedicalStaff, String>("role"));
		
		Tstaff.setItems(dataLisStaff);
	}
}
