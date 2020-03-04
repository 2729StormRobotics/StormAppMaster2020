package org.stormroboticsnj.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.stormroboticsnj.models.Whoosh;

import java.util.List;

/**
 * This interface creates methods that conduct SQL queries. There are only four basic ones in this
 * version, but more can easily be added for more advanced data tools. See Room guide at
 * https://developer.android.com/training/data-storage/room/ for more information
 */
@Dao
public interface StormDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWhooshes(Whoosh whoosh);  // Insert the annotated parameters into the whoosh database, delete duplicates
                                                // The parameters are those of the abstract method, provided by Room as core insert parameters
    @Update
    public void updateWhooshes(Whoosh whoosh); // Update the parameters in the database if they already exist

    @Delete
    public void deleteWhooshes(Whoosh whoosh); // Delete the parameters from the databases

    @Query("SELECT * FROM whooshes")
    public List<Whoosh> getAllWhooshes(); // Query, or inquiry, as method to access all Whoosh databases

    @Query("SELECT * FROM whooshes WHERE :colName=:searchVal")
    public List<Whoosh> filterWhooshes(String colName, int searchVal); // Query as method to filter data results by column and search value, useful for lookup

    @Query("SELECT * FROM whooshes WHERE team_num=:teamNum")
    public List<Whoosh> getByTeamNumber(int teamNum);

    @Query("SELECT * FROM whooshes WHERE match_num=:matchNum")
    public List<Whoosh> getByMatchNumber(int matchNum);


}
