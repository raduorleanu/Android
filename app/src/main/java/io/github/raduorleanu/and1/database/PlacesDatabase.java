package io.github.raduorleanu.and1.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import io.github.raduorleanu.and1.data_acess_objects.IPlaceDao;
import io.github.raduorleanu.and1.models.Place;


@Database(entities = {Place.class}, version = 1)
public abstract class PlacesDatabase extends RoomDatabase {

    public abstract IPlaceDao placeDao();

    private static volatile PlacesDatabase INSTANCE;

    public static PlacesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlacesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlacesDatabase.class, "places")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // add dummy data
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final IPlaceDao mDao;

        PopulateDbAsync(PlacesDatabase db) {
            mDao = db.placeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Place p = new Place.PlaceBuilder("1")
                    .address("Address1")
                    .pictureUrl("url1")
                    .name("Location1").build();
            mDao.insert(p);
            return null;
        }
    }

}
