package unsw.loopmania;

import javafx.scene.image.Image;

import java.io.File;
/*
* Loads images for cards
*/
public class ImageFactory {

    public Image imageGet(String type) {

        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("VampireCastleCard")) {
            return new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("ZombiePitCard")) {
            return new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("BarracksCard")) {
            return new Image((new File("src/images/barracks_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("CampfireCard")) {
            return new Image((new File("src/images/campfire_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("TowerCard")) {
            return new Image((new File("src/images/tower_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("TrapCard")) {
            return new Image((new File("src/images/trap_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("VillageCard")) {
            return new Image((new File("src/images/village_card.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("BasicEnemy")) {
            return new Image((new File("src/images/slug.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("VampireEnemy")) {
            return new Image((new File("src/images/vampire.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("ZombieEnemy")) {
            return new Image((new File("src/images/zombie.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("VampireCastle")) {
            return new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("ZombiePit")) {
            return new Image((new File("src/images/zombie_pit.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Tower")) {
            return new Image((new File("src/images/tower.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("AllySoldier")) {
            return new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Village")) {
            return new Image((new File("src/images/village.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Barracks")) {
            return new Image((new File("src/images/barracks.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Trap")) {
            return new Image((new File("src/images/trap.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Campfire")) {
            return new Image((new File("src/images/campfire.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("HeroCastle")) {
            return new Image((new File("src/images/heros_castle.png")).toURI().toString());
        }

        else if (type.equalsIgnoreCase("Sword")) {
            return new Image((new File("src/images/basic_sword.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Staff")) {
            return new Image((new File("src/images/staff.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Stake")) {
            return new Image((new File("src/images/stake.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Armour")) {
            return new Image((new File("src/images/armour.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Shield")) {
            return new Image((new File("src/images/shield.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Helmet")) {
            return new Image((new File("src/images/helmet.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("Gold")) {
            return new Image((new File("src/images/gold_pile.png")).toURI().toString());
        } else if (type.equalsIgnoreCase("HealthPotion")) {
            return new Image(new File("src/images/brilliant_blue_new.png").toURI().toString());
        } else if (type.equalsIgnoreCase("DoggieCoin")) {
            return new Image(new File("src/images/doggiecoin.png").toURI().toString());
        } else if (type.equalsIgnoreCase("OneRing")) {
            return new Image(new File("src/images/the_one_ring.png").toURI().toString());
        } else if (type.equalsIgnoreCase("Anduril")) {
            return new Image(new File("src/images/anduril_flame_of_the_west.png").toURI().toString());
        } else if (type.equalsIgnoreCase("TreeStump")) {
            return new Image(new File("src/images/tree_stump.png").toURI().toString());
        } else if (type.equalsIgnoreCase("Heart")) {
            return new Image(new File("src/images/heart.png").toURI().toString());

        } else if (type.equalsIgnoreCase("Doggie")) {
            return new Image(new File("src/images/doggie.png").toURI().toString());

        } else if (type.equalsIgnoreCase("ElanMuske")) {
            return new Image(new File("src/images/ElanMuske.png").toURI().toString());

        } else if (type.equalsIgnoreCase("Covid")) {
            return new Image(new File("src/images/covid.jpg").toURI().toString());

        }
        return null;

    }

}
