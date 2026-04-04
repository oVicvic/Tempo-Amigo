package unicsul.itinerario.tempoamigo.network.clima;

import java.util.concurrent.CompletableFuture;

import unicsul.itinerario.tempoamigo.model.Clima;

public interface ClimaApiClient {
    CompletableFuture<Clima> buscarClima(double latitude, double longitude);
}