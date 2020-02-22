package org.stormroboticsnj.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * This class is a blueprint for a Whoosh object, which will be the future name of what was once
 * "DeepSpace" and "PowerUp". These objects are Entities of the Room database. You can treat them
 * like rows for our purposes, though in reality they act more like tables. Each Whoosh contains all
 * of the data scouted in a single match on a single tablet. Whooshes are differentiated by their
 * team and match numbers. No two whooshes should have the same team and match number. This will
 * cause a Primary Key conflict.
 */
@Entity(tableName = "whooshes", primaryKeys = {"team_num", "match_num"})
public class Whoosh {

    @ColumnInfo(name = "team_num") // Column for team number
    private int team; // Private integer variable for team number
    @ColumnInfo(name = "match_num") // Column for match number
    private int match; // Private integer variable for match number
    @ColumnInfo(name = "alliance") // Column for alliance color ("red" or "blue"
    private boolean alliance; // Private boolean variable for alliance color
    @ColumnInfo(name = "bottom_port_auto") // Column for bottom port score auto
    private int aPowerCell1; // Private integer variable for bottom port score auto
    @ColumnInfo(name = "outer_port_auto") // Column for outer port score auto
    private int aPowerCell2; // Private integer variable for outer port score
    @ColumnInfo(name = "inner_port_auto") // Column for inner port score
    private int aPowerCell3; // Private integer variable for inner port score
    @ColumnInfo(name = "power_cells_picked_up_auto") // Column for power cell pickup auto
    private int aPowerCellPickup; // Private integer variable for power cell pickup auto
    @ColumnInfo(name = "bottom_port_teleop") // Column for bottom port score teleop
    private int tPowerCell1; // Private integer variable for bottom port score teleop
    @ColumnInfo(name = "outer_port_teleop") // Column for outer port score teleop
    private int tPowerCell2; // Private integer variable for outer port score teleop
    @ColumnInfo(name = "inner_port_teleop") // Column for inner port score teleop
    private int tPowerCell3; // Private integer variable for inner port score teleop
    @ColumnInfo(name = "rotation_control") // Column for rotation control panel
    private boolean rotationControl; // Private integer variable for rotation control panel
    @ColumnInfo(name = "position_control") // Column for position control panel
    private boolean positionControl; // Private integer variable for position control panel
    @ColumnInfo(name = "bottom_port_endgame") // Column for bottom port score endgame
    private int ePowerCell1; // Private integer variable for bottom port score endgame
    @ColumnInfo(name = "outer_port_endgame") // Column for outer port score endgame
    private int ePowerCell2; // Private integer variable for outer port score endgame
    @ColumnInfo(name = "inner_port_endgame") // Column for inner port score endgame
    private int ePowerCell3; // Private integer variable for inner port score endgame
    @ColumnInfo(name = "endgame_outcome") // Column for endgame outcome (park, hang, or hang level)
    private String endgameOutcome; // Private String variable for endgame outcome (park, hang, or hang level)
    @ColumnInfo(name = "locations") // Column for robot map scoring locations
    private String locations; // Private String variable for robot map scoring locations
    @ColumnInfo(name = "defenseSecs") // Column for robot total defense time
    private int defenseSecs; // Private integer variable for robot total defense time
    public int getClimbSecs() {
        return climbSecs;
    }

    public void setClimbSecs(int climbSecs) {
        this.climbSecs = climbSecs;
    }

    @ColumnInfo(name="climbSecs")
    private int climbSecs;

    /**
     * Whoosh class constructor
     *
     * @param t team number
     * @param m match number
     */
    public Whoosh(int t, int m) {
        team = t;
        match = m;
    }

    //probably just for testing purposes
    public Whoosh(int team, int match, boolean alliance, int aPowerCell1, int aPowerCell2, int aPowerCell3, int aPowerCellPickup, int tPowerCell1,
                  int tPowerCell2, int tPowerCell3, boolean rotationControl, boolean positionControl, int ePowerCell1, int ePowerCell2,
                  int ePowerCell3, String hang, String locations, int defense) {
        this.team = team;
        this.match = match;
        this.alliance = alliance;
        this.aPowerCell1 = aPowerCell1;
        this.aPowerCell2 = aPowerCell2;
        this.aPowerCell3 = aPowerCell3;
        this.aPowerCellPickup = aPowerCellPickup;
        this.tPowerCell1 = tPowerCell1;
        this.tPowerCell2 = tPowerCell2;
        this.tPowerCell3 = tPowerCell3;
        this.rotationControl = rotationControl;
        this.positionControl = positionControl;
        this.ePowerCell1 = ePowerCell1;
        this.ePowerCell2 = ePowerCell2;
        this.ePowerCell3 = ePowerCell3;
        this.endgameOutcome = hang;
        this.locations = locations;
        this.defenseSecs = defense;
    }


    public Whoosh() {
    }


    public int getTeam() { // Return team number
        return team;
    }

    public int getMatch() { // Return match number
        return match;
    }

    public boolean isAlliance() { // Return alliance color through boolean (true = red, false = blue)
        return alliance;
    }

    public int getAPowerCell1() { // Return bottom port power cell score auto
        return aPowerCell1;
    }

    public int getAPowerCell2() { // Return outer port power cell score auto
        return aPowerCell2;
    }

    public int getAPowerCell3() { // Return inner port power cell score auto
        return aPowerCell3;
    }

    public int getAPowerCellPickup() { // Return power cell pickup auto
        return aPowerCellPickup;
    }

    public int getTPowerCell1() { // Return bottom port power cell score teleop
        return tPowerCell1;
    }

    public int getTPowerCell2() { // Return outer port power cell score teleop
        return tPowerCell2;
    }

    public int getTPowerCell3() { // Return inner port power cell score teleop
        return tPowerCell3;
    }

    public boolean isRotationControl() { // Return rotation control (true = activated, false = not activated)
        return rotationControl;
    }

    public boolean isPositionControl() { // Return position control (true = activated, false = not activated)
        return positionControl;
    }

    public int getEPowerCell1() { // Return bottom port power cell score endgame
        return ePowerCell1;
    }

    public int getEPowerCell2() { // Return outer port power cell score endgame
        return ePowerCell2;
    }

    public int getEPowerCell3() { // Return inner port power cell score endgame
        return ePowerCell3;
    }

    public String getEndgameOutcome() { // Return: "H" for hang, "P" for park, "L" for level hang
        return endgameOutcome;
    }

    /**
     * Return:  "BS" for behind shield;
     * "FS" for front shield;
     * "BW" for behind wheel;
     * "FW" for front wheel;
     * "BL" for behind initiation line;
     * "FL" for front initiation line;
     * "SZ" for safe zone;
     */
    public String getLocations() {
        return locations;
    }

    public int getDefenseSecs() { // Return total defense time
        return defenseSecs;

    }

    public void setTeam(int team) { // Modify team number for Whoosh table
        this.team = team;
    }

    public void setMatch(int match) { // Modify match number for Whoosh table
        this.match = match;
    }

    public void setAlliance(boolean alliance) { // Modify alliance color for Whoosh table
        this.alliance = alliance;
    }


    public void setAPowerCell1(int aPowerCell1) { // Modify bottom port power cell auto score for Whoosh table
        this.aPowerCell1 = aPowerCell1;
    }

    public void setAPowerCell2(int aPowerCell2) { // Modify outer port power cell auto score for Whoosh table
        this.aPowerCell2 = aPowerCell2;
    }

    public void setAPowerCell3(int aPowerCell3) { // Modify inner port power cell auto score for Whoosh table
        this.aPowerCell3 = aPowerCell3;
    }

    public void setAPowerCellPickup(int aPowerCellPickup) { // Modify power cell auto pickup for Whoosh table
        this.aPowerCellPickup = aPowerCellPickup;
    }

    public void setTPowerCell1(int tPowerCell1) { // Modify bottom port power cell teleop score for Whoosh table
        this.tPowerCell1 = tPowerCell1;
    }

    public void setTPowerCell2(int tPowerCell2) { // Modify outer port power cell teleop score for Whoosh table
        this.tPowerCell2 = tPowerCell2;
    }

    public void setTPowerCell3(int tPowerCell3) { // Modify inner port power cell teleop score for Whoosh table
        this.tPowerCell3 = tPowerCell3;
    }

    public void setRotationControl(boolean rotationControl) { // Modify rotation control panel activation for Whoosh table
        this.rotationControl = rotationControl;
    }

    public void setPositionControl(boolean positionControl) { // Modify position control panel activation for Whoosh table
        this.positionControl = positionControl;
    }

    public void setEPowerCell1(int ePowerCell1) { // Modify bottom port power cell endgame score for Whoosh table
        this.ePowerCell1 = ePowerCell1;
    }

    public void setEPowerCell2(int ePowerCell2) { // Modify outer port power cell endgame score for Whoosh table
        this.ePowerCell2 = ePowerCell2;
    }

    public void setEPowerCell3(int ePowerCell3) { // Modify inner port power cell endgame score for Whoosh table
        this.ePowerCell3 = ePowerCell3;
    }

    public void setEndgameOutcome(String endgameOutcome) { // Modify endgameOutcome: "P" for Park, "H" for Hang, "L" for Level Hang
        this.endgameOutcome = endgameOutcome;
    }

    /**
     * Modify robot shooting locations
     *
     * @param locations "BS" for behind shield;
     *                  "FS" for front shield;
     *                  "BW" for behind wheel;
     *                  "FW" for front wheel;
     *                  "BL" for behind initiation line;
     *                  "FL" for front initiation line;
     *                  "SZ" for safe zone;
     */
    public void setLocations(String locations) {
        this.locations = locations;
    }

    public void setDefenseSecs(int defenseSecs) { // Modify defense timer
        this.defenseSecs = defenseSecs;

    }

    // public void setEPowerCell1(int ePowerCell1) { this.ePowerCell1 = ePowerCell1; }

    // public void setEPowerCell2(int ePowerCell2) { this.ePowerCell2 = ePowerCell2; }

    // public void setEPowerCell3(int ePowerCell3) { this.ePowerCell3 = ePowerCell3; }


    @NonNull
    @Override
    public String toString() {
        return          team //0
                + "," + match //1
                + "," + (alliance ? "r" : "b") //2
                + "," + aPowerCell1 //3
                + "," + aPowerCell2 //4
                + "," + aPowerCell3 //5
                + "," + aPowerCellPickup //6
                + "," + tPowerCell1 //7
                + "," + tPowerCell2 //8
                + "," + tPowerCell3 //9
                + "," + (rotationControl ? "y" : "n") //10
                + "," + (positionControl ? "y" : "n") //11
                + "," + ePowerCell1 //12
                + "," + ePowerCell2 //13
                + "," + ePowerCell3 //14
                + "," + locations //15
                + "," + endgameOutcome //16
                + "," + defenseSecs //17
                + "," + climbSecs //18
                + "|";
    }
}
