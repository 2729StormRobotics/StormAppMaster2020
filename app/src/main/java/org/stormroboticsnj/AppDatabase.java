package org.stormroboticsnj;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;


/**
 * Abstract class that is used to get to the StormDao interface
 */
@Database(entities = {Whoosh.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StormDao stormDao();
}
