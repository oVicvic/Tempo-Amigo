package unicsul.itinerario.tempoamigo.network;

import android.os.Handler;
import android.os.Looper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String BASE_URL = "https://api.open-meteo.com/";
    public static final Handler mainThread = new Handler(Looper.getMainLooper());

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T create(Class<T> api) {
        return retrofit.create(api);
    }
}