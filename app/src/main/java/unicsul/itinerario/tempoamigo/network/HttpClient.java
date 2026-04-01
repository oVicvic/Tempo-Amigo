package unicsul.itinerario.tempoamigo.network;

import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;

public class HttpClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public interface OnSuccess<T> { void run(T result); }
    public interface OnError { void run(String error); }

    // ─── GET ──────────────────────────────────────────────────────────────────

    public static <T> void get(String url, Class<T> dtoClass, OnSuccess<T> onSuccess, OnError onError) {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError.run(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess.run(gson.fromJson(response.body().string(), dtoClass));
                } else {
                    onError.run("Erro HTTP: " + response.code());
                }
            }
        });
    }

    // ─── POST ─────────────────────────────────────────────────────────────────

    public static <T> void post(String url, Object requestDto, Class<T> responseClass, OnSuccess<T> onSuccess, OnError onError) {
        RequestBody body = RequestBody.create(gson.toJson(requestDto), JSON);
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError.run(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess.run(gson.fromJson(response.body().string(), responseClass));
                } else {
                    onError.run("Erro HTTP: " + response.code());
                }
            }
        });
    }
}