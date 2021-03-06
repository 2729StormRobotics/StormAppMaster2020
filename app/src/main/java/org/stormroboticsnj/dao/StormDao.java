package org.stormroboticsnj.dao;

import android.database.Cursor;

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
    public void insertWhooshes(Whoosh whoosh);

    @Update
    public void updateWhooshes(Whoosh whoosh);

    @Delete
    public void deleteWhooshes(Whoosh whoosh);

    @Query("SELECT * FROM whooshes")
    public List<Whoosh> getAllWhooshes();

    @Query("SELECT * FROM whooshes WHERE team_num=:teamNum")
    public List<Whoosh> getByTeamNumber(int teamNum);

    @Query("SELECT * FROM whooshes WHERE match_num=:matchNum")
    public List<Whoosh> getByMatchNumber(int matchNum);

    @Query("SELECT * FROM whooshes WHERE :colName=:searchVal ORDER BY :sortColName")
    public List<Whoosh> filterWhooshes(String colName, int searchVal, String sortColName);

    @Query("SELECT * FROM whooshes")
    public Cursor getCursor();

    @Query("SELECT * FROM whooshes ORDER BY team_num")
    public List<Whoosh> getWhooshesByTeam();


}
