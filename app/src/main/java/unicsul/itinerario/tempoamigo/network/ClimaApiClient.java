package unicsul.itinerario.tempoamigo.network;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import unicsul.itinerario.tempoamigo.dto.ClimaDTO;

public class ClimaApiClient {

    private final ClimaApi climaApi;

    public ClimaApiClient(ClimaApi climaApi) {
        this.climaApi = climaApi;
    }

    public CompletableFuture<ClimaDTO> buscarClima(double latitude, double longitude) {
        return climaApi.buscarClima(latitude, longitude, parametrosPadrao());
    }

    public CompletableFuture<ClimaDTO> buscarClima(double latitude, double longitude, Map<String, String> parametros) {
        return climaApi.buscarClima(latitude, longitude, parametros);
    }

    static Map<String, String> parametrosPadrao() {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("current",  "temperature_2m,relative_humidity_2m,wind_speed_10m,precipitation,weather_code");
        params.put("hourly",   "temperature_2m,precipitation_probability");
        params.put("daily",    "temperature_2m_max,temperature_2m_min,precipitation_sum");
        params.put("timezone", "America/Sao_Paulo");
        return params;
    }
}