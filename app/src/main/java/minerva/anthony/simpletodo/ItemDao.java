package minerva.anthony.simpletodo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT *  FROM Item")
    List<Item> getAllItems();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Item item);

    @Update
    void updateItem(Item item);

    @Delete
    void deleteItem(Item item);
}
