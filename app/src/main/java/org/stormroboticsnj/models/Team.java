package org.stormroboticsnj.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Team {

    private int team;
    private List<Whoosh> matches = new ArrayList<>();

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public List<Whoosh> getMatches() {
        return matches;
    }

    public void setMatches(List<Whoosh> matches) {
        this.matches = matches;
    }

    public double getAvgAPowerCells1() {

        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getAPowerCell1();
        }

        return total / matches.size();
    }

    public double getAvgAPowerCells2() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getAPowerCell2();
        }

        return total / matches.size();
    }

    public double getAvgAPowerCells3() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getAPowerCell3();
        }

        return total / matches.size();
    }

    public double getAvgPowerCellPickup() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getAPowerCellPickup();
        }

        return total / matches.size();
    }

    public double getAvgTPowerCells1() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getTPowerCell1();
        }

        return total / matches.size();
    }

    public double getAvgTPowerCells2() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getTPowerCell2();
        }

        return total / matches.size();
    }

    public double getAvgTPowerCells3() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getTPowerCell3();
        }

        return total / matches.size();
    }

    public double getRotationControlFrequency() {
        int success = 0;

        for (Whoosh whoosh : matches) {
            success = (whoosh.isRotationControl()) ? (success + 1) : success;
        }

        return success / matches.size();
    }

    public double getPositionControlFrequency() {
        int success = 0;

        for (Whoosh whoosh : matches) {
            success = (whoosh.isPositionControl()) ? (success + 1) : success;
        }

        return success / matches.size();
    }

    public double getAvgEPowerCells1() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getEPowerCell1();
        }

        return total / matches.size();
    }

    public double getAvgEPowerCells2() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getEPowerCell2();
        }

        return total / matches.size();
    }

    public double getAvgEPowerCells3() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getEPowerCell3();
        }

        return total / matches.size();
    }

    public double getHangFrequency() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total = (whoosh.getEndgameOutcome() == "H" || whoosh.getEndgameOutcome() == "L") ?
                    (total + 1) : total;
        }

        return total / matches.size();
    }

    public double getLevelHangFrequency() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total = (whoosh.getEndgameOutcome() == "L") ?
                    (total + 1) : total;
        }

        return total / matches.size();
    }

    //totals
    public double getAvgEndgamePowerCells() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getEPowerCell1();
            total += whoosh.getEPowerCell2();
            total += whoosh.getEPowerCell3();
        }

        return total / matches.size();
    }

    public double getAvgTeleopPowerCells() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getTPowerCell1();
            total += whoosh.getTPowerCell2();
            total += whoosh.getTPowerCell3();
        }

        return total / matches.size();
    }

    public double getAvgAutoPowerCells() {
        int total = 0;

        for (Whoosh whoosh : matches) {
            total += whoosh.getAPowerCell1();
            total += whoosh.getAPowerCell2();
            total += whoosh.getAPowerCell3();
        }

        return total / matches.size();
    }

    public double getAvgOffenseSecondsPerPC() {
        int pcScored = 0;
        int secondsDefense = 0;

        for (Whoosh whoosh : matches) {
            pcScored += whoosh.getTPowerCell1();
            pcScored += whoosh.getTPowerCell2();
            pcScored += whoosh.getTPowerCell3();

            secondsDefense += whoosh.getDefenseSecs();
        }

        return ((105.0 * matches.size()) - secondsDefense) / pcScored;
    }

    public int getRotationControlMatchesCount() {
        int output = 0;

        for (Whoosh whoosh : matches) {
            if (whoosh.isRotationControl()) output++;
        }

        return output;
    }
    public int getPositionControlMatchesCount() {
        int output = 0;

        for (Whoosh whoosh : matches) {
            if (whoosh.isRotationControl()) output++;
        }

        return output;
    }

    public int getParkMatchesCount() {
        int output = 0;

        for (Whoosh whoosh : matches) {
            if (whoosh.getEndgameOutcome().equals("P")) output++;
        }

        return output;
    }
    public int getHangMatchesCount() {
        int output = 0;

        for (Whoosh whoosh : matches) {
            if (whoosh.getEndgameOutcome().equals("L") || whoosh.getEndgameOutcome().equals("H")) output++;
        }

        return output;
    }

    public int getLevelMatchesCount() {
        int output = 0;

        for (Whoosh whoosh : matches) {
            if (whoosh.getEndgameOutcome().equals("L")) output++;
        }

        return output;
    }

    public int[] getLocationsFrequency() {
        // format: {BS, FS, BW, FW, BL, FL, SZ}
        int[] totals = {0, 0, 0, 0, 0, 0, 0};

        for (Whoosh whoosh : matches) {
            List<String> locations = Arrays.asList(whoosh.getLocations().split(Pattern.quote(".")));
            if (locations.contains("BS")) totals[0]++;
            if (locations.contains("FS")) totals[1]++;
            if (locations.contains("BW")) totals[2]++;
            if (locations.contains("FW")) totals[3]++;
            if (locations.contains("BL")) totals[4]++;
            if (locations.contains("FL")) totals[5]++;
            if (locations.contains("SZ")) totals[6]++;
        }

        return totals;
    }
}
