package controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.tm.UserTM;

import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserSettingsController {
    public JFXTextField txtUserId;
    public JFXTextField txtAddress;
    public JFXTextField txtUserName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public ComboBox<String> cmbPositionType;
    public TableView tblUser;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colPosition;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colSalary;
    public JFXTextField txtFullName;
    public CheckBox checkBox;
    public JFXPasswordField txtPassword;
    public JFXTextField txtPasswordS;
    public TableColumn colFullName;


    public void initialize(){
        txtPasswordS.setVisible(false);
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("name"));
        String[] position = {"ADMIN","USER"};
        List<String> itemName = new ArrayList(Arrays.asList(position));
        cmbPositionType.getItems().addAll(itemName);
        loadDataToTable();
        try {
            txtUserId.setText(getUserId());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadDataToTextFields((UserTM) newValue);
            checkBoxOnAction();
        });

    }

    public void textFields_Key_Released(KeyEvent keyEvent) {

    }

    public void addOnAction(ActionEvent actionEvent) {
        try {
            if(cmbPositionType.getValue()==null){new Alert(Alert.AlertType.WARNING,"Select your position").show();return;}
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO `user` VALUES (?,?,?,?,?,?,?,?)");
            pst.setObject(1,txtUserId.getText());
            pst.setObject(2,txtFullName.getText());
            pst.setObject(3,txtUserName.getText());
            pst.setObject(4,cmbPositionType.getValue());
            pst.setObject(5,txtPassword.getText());
            pst.setObject(6,txtAddress.getText());
            pst.setObject(7,txtContact.getText());
            pst.setObject(8,txtSalary.getText());
            if (pst.executeUpdate()>0){
                txtUserId.setText(getUserId());
                new Alert(Alert.AlertType.CONFIRMATION,txtUserId.getText()+" added successfully").show();
                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again").show();
            }
        } catch (SQLException | ClassNotFoundException |NullPointerException throwables) {
            throwables.printStackTrace();
        }
        txtFullName.clear();
    }

    private void loadDataToTable(){
        ObservableList list= FXCollections.observableArrayList();
        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `user`").executeQuery();
            while(rst.next()){
                list.add(new UserTM(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
                        rst.getString(5), rst.getString(6),rst.getString(7),rst.getDouble(8)));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
     tblUser.setItems(list);
    }

    public String getUserId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT `UserId` FROM `user` ORDER BY `UserId` DESC LIMIT 1").executeQuery();
        if (rst.next()){
            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "U-00"+tempId;
            }else if(tempId<=99){
                return "U-0"+tempId;
            }else{
                return "U-"+tempId;
            }
        }else{
            return "U-001";
        }
    }

    private void loadDataToTextFields(UserTM user){
        txtUserId.setText(user.getUserId());
        txtFullName.setText(user.getName());
        txtUserName.setText(user.getUserName());
        txtPassword.setText(user.getPassword());
        txtAddress.setText(user.getAddress());
        txtContact.setText(user.getContact());
        txtSalary.setText(String.valueOf(user.getSalary()));
        cmbPositionType.setValue(user.getPosition());
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        try {
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `user` WHERE `UserId`=?");
            pst.setObject(1,txtUserId.getText());
            if(pst.executeUpdate()>0){
                getUserId();
                loadDataToTable();
                new Alert(Alert.AlertType.CONFIRMATION,txtUserId.getText()+" user deleted successfully").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void checkBoxOnAction() {
        if (checkBox.isSelected()){
            txtPasswordS.setVisible(true);
            txtPasswordS.setText(txtPassword.getText());
        }else{
            txtPasswordS.setVisible(false);
        }
    }

    public void passwordShower(KeyEvent keyEvent) {
        if (!checkBox.isSelected()){return;}
        checkBoxOnAction();
    }
}
