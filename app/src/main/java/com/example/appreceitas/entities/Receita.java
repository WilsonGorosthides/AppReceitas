package com.example.appreceitas.entities;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "receitas")
public class Receita implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "titulo")
    private String title;

    @NonNull
    @ColumnInfo(name = "ingredientes")
    private String ingredients;

    @NonNull
    @ColumnInfo(name = "passos")
    private String steps;

    @NonNull
    @ColumnInfo(name = "tipo")
    private String type;

    @ColumnInfo(name = "foto")
    private String photo;

    @ColumnInfo(name = "calorias")
    private Integer calories;

    public Receita() {}

    public Receita(@NonNull String title, @NonNull String ingredients, @NonNull String steps, @NonNull String type, String photo, Integer calories) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.type = type;
        this.photo = photo;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NonNull String ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    public String getSteps() {
        return steps;
    }

    public void setSteps(@NonNull String steps) {
        this.steps = steps;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    protected Receita(Parcel in) {
        id = in.readInt();
        title = in.readString();
        ingredients = in.readString();
        steps = in.readString();
        type = in.readString();
        photo = in.readString();
        if (in.readByte() == 0) {
            calories = null;
        } else {
            calories = in.readInt();
        }
    }

    public static final Creator<Receita> CREATOR = new Creator<Receita>() {
        @Override
        public Receita createFromParcel(Parcel in) {
            return new Receita(in);
        }

        @Override
        public Receita[] newArray(int size) {
            return new Receita[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(ingredients);
        parcel.writeString(steps);
        parcel.writeString(type);
        parcel.writeString(photo);
        if (calories == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(calories);
        }
    }
}
