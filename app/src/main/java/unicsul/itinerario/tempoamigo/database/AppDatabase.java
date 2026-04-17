package unicsul.itinerario.tempoamigo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import unicsul.itinerario.tempoamigo.model.ContatoEmergencia;

@Database(entities = {ContatoEmergencia.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instancia;

    public abstract ContatoEmergenciaDao contatoEmergenciaDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "tempo_amigo_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}