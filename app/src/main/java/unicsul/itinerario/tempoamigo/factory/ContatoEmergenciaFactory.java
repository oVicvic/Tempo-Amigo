package unicsul.itinerario.tempoamigo.factory;

import android.content.Context;

import java.util.concurrent.CompletableFuture;

import unicsul.itinerario.tempoamigo.database.AppDatabase;
import unicsul.itinerario.tempoamigo.model.ContatoEmergencia;

public class ContatoEmergenciaFactory {

    private final Context context;

    public ContatoEmergenciaFactory(Context context) {
        this.context = context;
    }

    public CompletableFuture<ContatoEmergencia> buscar() {
        AppDatabase db = AppDatabase.getInstance(context);
        return CompletableFuture.supplyAsync(() -> db.contatoEmergenciaDao().buscarUltimo());
    }
}