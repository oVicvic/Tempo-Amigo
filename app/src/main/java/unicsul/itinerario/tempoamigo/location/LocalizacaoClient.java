package unicsul.itinerario.tempoamigo.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.content.ContextCompat;

import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.concurrent.CompletableFuture;

public class LocalizacaoClient {

    private final FusedLocationProviderClient fusedClient;
    private final Context context;

    public LocalizacaoClient(Context context) {
        this.context = context;
        fusedClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public CompletableFuture<Location> obterLocalizacao() {
        if (!temPermissao()) {
            CompletableFuture<Location> future = new CompletableFuture<>();
            future.completeExceptionally(new SecurityException("Permissão de localização não concedida"));
            return future;
        }

        return buscarLocalizacao();
    }

    private boolean temPermissao() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    private CompletableFuture<Location> buscarLocalizacao() {
        CompletableFuture<Location> future = new CompletableFuture<>();

        fusedClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null && !estaDesatualizada(location)) {
                        future.complete(location);
                    } else {
                        solicitarLocalizacaoAtual(future);
                    }
                })
                .addOnFailureListener(e -> solicitarLocalizacaoAtual(future));

        return future;
    }

    private boolean estaDesatualizada(Location location) {
        long umHoraEmMs = 60 * 60 * 1000;
        return System.currentTimeMillis() - location.getTime() > umHoraEmMs;
    }

    @SuppressLint("MissingPermission")
    private void solicitarLocalizacaoAtual(CompletableFuture<Location> future) {
        CurrentLocationRequest request = new CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMaxUpdateAgeMillis(0)
                .build();

        fusedClient.getCurrentLocation(request, null)
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        future.complete(location);
                    } else {
                        future.completeExceptionally(new Exception("Localização indisponível"));
                    }
                })
                .addOnFailureListener(future::completeExceptionally);
    }
}