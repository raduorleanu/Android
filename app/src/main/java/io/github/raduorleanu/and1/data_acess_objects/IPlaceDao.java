package io.github.raduorleanu.and1.data_acess_objects;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.raduorleanu.and1.models.Place;

@Dao
public interface IPlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Place... places);

    @Delete
    void delete(Place... places);

    @Update
    void update(Place... places);

    // todo: add specific queries below
    @Query("SELECT * from places ORDER BY name ASC")
    LiveData<List<Place>> getAllPlaces();

    @Query("DELETE from places")
    void deleteAll();

}
