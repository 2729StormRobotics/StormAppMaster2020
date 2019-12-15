package org.stormroboticsnj.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    @ColumnInfo(name = "team_num")
    private int team;
    @ColumnInfo(name = "match_num")
    private int match;
    @ColumnInfo(name = "alliance")
    private boolean alliance;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name="score_two")
    private int scoreTwo;

    public Whoosh(int t, int m) {
        team = t;
        match = m;
    }

    public Whoosh(){}

    public int getTeam() {
        return team;
    }

    public int getMatch() {
        return match;
    }

    public int getScore() {
        return score;
    }

    public int getScoreTwo() {
        return scoreTwo;
    }

    public boolean isAlliance() {
        return alliance;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public void setAlliance(boolean alliance) {
        this.alliance = alliance;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScoreTwo(int scoreTwo) {
        this.scoreTwo = scoreTwo;
    }

    @NonNull
    @Override
    public String toString() {
        return          team
                + "," + match
                + "," + (alliance ? "r" : "b")
                + "," + score
                + "," + scoreTwo
                + "|";
    }
}
