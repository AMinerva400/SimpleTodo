package minerva.anthony.simpletodo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Item {

    @ColumnInfo @PrimaryKey(autoGenerate=true)
    Long id;
    @ColumnInfo
    String itemDescription;
    @ColumnInfo
    String dueDate;

    public Item(){}

    public Item(String i, String d){
        itemDescription = i;
        dueDate = d;
    }

    public void setItem(Item i){
        itemDescription = i.itemDescription;
        dueDate = i.dueDate;
    }
}
