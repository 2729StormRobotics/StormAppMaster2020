package org.stormroboticsnj;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;


/**
 * Abstract class that is used to get to the StormDao interface
 */
@Database(entities = {Whoosh.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StormDao stormDao();
    public static final String DB_NAME = "storm";

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE whooshes ADD COLUMN climbSecs INTEGER");
        }
    };

    public static AppDatabase getDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build(); //build database
    }
}
