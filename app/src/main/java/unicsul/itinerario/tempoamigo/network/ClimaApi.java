package unicsul.itinerario.tempoamigo.network;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import unicsul.itinerario.tempoamigo.dto.ClimaDTO;

public interface ClimaApi {

    @GET("v1/forecast")
    CompletableFuture<ClimaDTO> buscarClima(
            @Query("latitude")  double latitude,
            @Query("longitude") double longitude,
            @QueryMap Map<String, String> parametrosFixos
    );
}