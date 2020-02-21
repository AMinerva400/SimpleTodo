package minerva.anthony.simpletodo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={Item.class}, version=1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
}
