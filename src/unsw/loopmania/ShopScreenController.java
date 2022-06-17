package unsw.loopmania;

import java.io.IOException;
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.animation.Animation;

/*
* Controller for the buy screen shop
*/
public class ShopScreenController {

    @FXML
    private RadioButton item1;

    @FXML
    private RadioButton item2;

    @FXML
    private RadioButton item3;

    @FXML
    private RadioButton item4;

    @FXML
    private RadioButton item5;

    @FXML
    private RadioButton item6;

    @FXML
    private RadioButton item7;

    @FXML
    private ToggleGroup group; // COMBINE IN SCENEBUILDER??

    @FXML
    private Button purchase;
    @FXML
    private Button sellTab;


    @FXML
    private Label goldAmount;

    @FXML
    private Label potionPrice;
    @FXML
    private Label swordPrice;
    @FXML
    private Label staffPrice;
    @FXML
    private Label stakePrice;
    @FXML
    private Label armourPrice;
    @FXML
    private Label shieldPrice;
    @FXML
    private Label helmetPrice;


    private Timeline timeline;

    private MenuSwitcher mainMenuSwitcher;
    private MenuSwitcher shopSellSwitcher;

    private LoopManiaWorldController worldController;

    private LoopManiaWorld world;
    private Shop shop;

    /**
     * calls the backend function to purchase an item upon press
     * on button
     * @param event button press
     */
    @FXML
    private void handlePurchase(ActionEvent event) {
        if (item1.isSelected()) {
            shop.buyFromShop(worldController, "HealthPotion");
        } else if (item2.isSelected()) {
            shop.buyFromShop(worldController, "Sword");
        } else if (item3.isSelected()) {
            shop.buyFromShop(worldController, "Staff");
        } else if (item4.isSelected()) {
            shop.buyFromShop(worldController, "Stake");
        } else if (item5.isSelected()) {
            shop.buyFromShop(worldController, "Armour");
        } else if (item6.isSelected()) {
            shop.buyFromShop(worldController, "Shield");
        } else if (item7.isSelected()) {
            shop.buyFromShop(worldController, "Helmet");
        }
    }
    /**
     * Initialises the screen
     */
    @FXML
    public void initialize(){
        // goldAmount.setText(String.valueOf(world.getCharacter().getGold()));
        // goldAmount.setText("BROKE");

        //register on change event
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle)
            {
                //print new selected value after change
                System.out.println("Selected Radio Button: " + ((RadioButton)newToggle).getText());
                System.out.println(world.getCharacter().getGold());
            }
        });
        HealthPotion tempPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        potionPrice.setText(Integer.toString(tempPotion.getValueInShop()));
        Sword tempWeapon = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        swordPrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        staffPrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        stakePrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        Armour tempArmour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        armourPrice.setText(Integer.toString(tempArmour.getValueInShop()));
        shieldPrice.setText(Integer.toString(tempArmour.getValueInShop()));
        helmetPrice.setText(Integer.toString(tempArmour.getValueInShop()));
    }
    /**
     * Timeline to bind the gold value to the frontend
     */
    public void startTimer() {
		timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
			if (world != null && world.getCharacter().getGoldProperty() != null) {
                IntegerProperty bindGold = world.getCharacter().getGoldProperty();
                goldAmount.textProperty().bind(bindGold.asString()); 
                timeline.stop();
            }
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void setGameSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }
    /**
     * facilitates switching to main game upon button click
     * 
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        exitShop();
        mainMenuSwitcher.switchMenu();
    }

    private void exitShop() {
        shop.shopExit();
    }

    public void setSellSwitcher(MenuSwitcher shopSellSwitcher) {
        this.shopSellSwitcher = shopSellSwitcher;
    }

    @FXML
    private void switchToSell() throws IOException {
        shopSellSwitcher.switchMenu();
    }    

    public void setWorldController(LoopManiaWorldController worldController) {
        this.worldController = worldController;
        this.world = worldController.getWorld();
        this.shop = world.getShop();
    }


}
