package unsw.loopmania;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.animation.Animation;
/*
* Controller for the sell screen part of the shop
*/
public class ShopSellController {
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
    private ToggleGroup group;

    @FXML
    private Button sell;
    @FXML
    private Button buyTab;

    @FXML
    private Label goldAmount;

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
    @FXML
    private Label dogCoinPrice;

    @FXML
    private Label quantity1;
    @FXML
    private Label quantity2;
    @FXML
    private Label quantity3;
    @FXML
    private Label quantity4;
    @FXML
    private Label quantity5;
    @FXML
    private Label quantity6;
    @FXML
    private Label quantity7;
    @FXML
    private GridPane gridPane;

    private Node[][] gridPaneArray;

    private MenuSwitcher mainMenuSwitcher;
    private MenuSwitcher shopBuySwitcher;
    private LoopManiaWorldController worldController;
    private LoopManiaWorld world;
    private Shop shop;
    private Timeline timeline;
    private Map<String, IntegerProperty> sellableItems;
    /**
     * calls the backend function to sell an item upon press
     * on button
     * @param event button press
     */
    @FXML
    private void handleSell(ActionEvent event) {
        if (item1.isSelected()) {
            shop.buyFromCharacter(world, "Sword");
        } else if (item2.isSelected()) {
            shop.buyFromCharacter(world, "Staff");
        } else if (item3.isSelected()) {
            shop.buyFromCharacter(world, "Stake");
        } else if (item4.isSelected()) {
            shop.buyFromCharacter(world, "Armour");
        } else if (item5.isSelected()) {
            shop.buyFromCharacter(world, "Shield");
        } else if (item6.isSelected()) {
            shop.buyFromCharacter(world, "Helmet");
        } else if (item7.isSelected()) {
            shop.buyFromCharacter(world, "DoggieCoin");
        }
        // System.out.println("SELLING??");
    }
    /*
    * timeline to update inventory
    */
    public void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            if (world != null && world.getCharacter().getGoldProperty() != null) {
                bindProperties();
                updateItemsToSell();
                updateFrontend();
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /**
     * Initialises the screen
     */
    @FXML
    public void initialize() {
        sellableItems = new LinkedHashMap<String, IntegerProperty>();
        sellableItems.put("Sword", new SimpleIntegerProperty(0));
        sellableItems.put("Staff", new SimpleIntegerProperty(0));
        sellableItems.put("Stake", new SimpleIntegerProperty(0));
        sellableItems.put("Armour", new SimpleIntegerProperty(0));
        sellableItems.put("Shield", new SimpleIntegerProperty(0));
        sellableItems.put("Helmet", new SimpleIntegerProperty(0));
        sellableItems.put("DoggieCoin", new SimpleIntegerProperty(0));

        gridPaneArray = new Node[7][5];
        int i = 0;
        int j = 0;
        for (Node node : gridPane.getChildren()) {
            node.managedProperty().bind(node.visibleProperty());
            gridPaneArray[i][j] = node;
            i++;
            if (i == 7) {
                i = 0;
                j++;
            }
        }

        // register on change event
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldToggle, Toggle newToggle) {
                // print new selected value after change
                System.out.println("Selected Radio Button: " + ((RadioButton) newToggle).getText());
            }
        });
        
    }

    public void setGameSwitcher(MenuSwitcher mainMenuSwitcher) {
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    public void setBuySwitcher(MenuSwitcher shopBuySwitcher) {
        this.shopBuySwitcher = shopBuySwitcher;
    }

    public void setWorldController(LoopManiaWorldController worldController) {
        this.worldController = worldController;
        this.world = worldController.getWorld();
        this.shop = world.getShop();
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

    @FXML
    private void switchToBuy() throws IOException {
        shopBuySwitcher.switchMenu();
    }

    private void exitShop() {
        shop.shopExit();
    }
    /**
     * Initialises the screen
     */
    private void updateItemsToSell() {
        setSalePrices();
        List<Entity> unequippedInventory = world.getUnequippedInventory();
        sellableItems.replaceAll((k, v) -> new SimpleIntegerProperty(0));
        for (Entity item : unequippedInventory) {
            String itemKey = getString(item);
            if (itemKey == null)
                continue;
            SimpleIntegerProperty updateQuantity = new SimpleIntegerProperty(sellableItems.get(itemKey).getValue() + 1);
            sellableItems.put(itemKey, updateQuantity);
        }

    }

    private void setSalePrices() {
        Sword tempWeapon = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        swordPrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        staffPrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        stakePrice.setText(Integer.toString(tempWeapon.getValueInShop()));
        Armour tempArmour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        armourPrice.setText(Integer.toString(tempArmour.getValueInShop()));
        shieldPrice.setText(Integer.toString(tempArmour.getValueInShop()));
        helmetPrice.setText(Integer.toString(tempArmour.getValueInShop()));
        
        List<Entity> unequippedInventoryItems = world.getUnequippedInventory();
        for (Entity e : unequippedInventoryItems) {
            if (e instanceof DoggieCoin) {
                dogCoinPrice.setText(Integer.toString(((DoggieCoin) e).getValueInShop()));
                break;
            }
        }
    }

    public String getString(Entity item) {
        if (item instanceof Sword) {
            return "Sword";
        } else if (item instanceof Staff) {
            return "Staff";
        } else if (item instanceof Stake) {
            return "Stake";
        } else if (item instanceof Armour) {
            return "Armour";
        } else if (item instanceof Shield) {
            return "Shield";
        } else if (item instanceof Helmet) {
            return "Helmet";
        } else if (item instanceof DoggieCoin) {
            return "DoggieCoin";
        }
        return null;
    }

    private void bindProperties() {
        IntegerProperty bindGold = world.getCharacter().getGoldProperty();
        goldAmount.textProperty().bind(bindGold.asString());
        quantity1.textProperty().bind(sellableItems.get("Sword").asString());
        quantity2.textProperty().bind(sellableItems.get("Staff").asString());
        quantity3.textProperty().bind(sellableItems.get("Stake").asString());
        quantity4.textProperty().bind(sellableItems.get("Armour").asString());
        quantity5.textProperty().bind(sellableItems.get("Shield").asString());
        quantity6.textProperty().bind(sellableItems.get("Helmet").asString());
        quantity7.textProperty().bind(sellableItems.get("DoggieCoin").asString());
    }

    private void updateFrontend() {
        int i = 0;
        for (Map.Entry<String, IntegerProperty> entry : sellableItems.entrySet()) {
            if (entry.getValue().getValue() > 0) {
                for (int j = 0; j < 5; j++) {
                    Node n = gridPaneArray[i][j];
                    n.setVisible(true);
                    gridPane.getRowConstraints().set(i, new RowConstraints(50));

                }
            } else {
                for (int j = 0; j < 5; j++) {
                    Node n = gridPaneArray[i][j];
                    n.setVisible(false);
                    gridPane.getRowConstraints().set(i, new RowConstraints(0));

                }
            }
            i++;
        }

    }
}
