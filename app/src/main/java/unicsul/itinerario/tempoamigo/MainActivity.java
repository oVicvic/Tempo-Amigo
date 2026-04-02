package unicsul.itinerario.tempoamigo;

import static unicsul.itinerario.tempoamigo.network.HttpClient.mainThread;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import unicsul.itinerario.tempoamigo.location.LocalizacaoClient;
import unicsul.itinerario.tempoamigo.network.clima.ClimaApiClient;
import unicsul.itinerario.tempoamigo.service.AlertaClimaticoService;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSAO_LOCALIZACAO = 1;

    private ClimaApiClient api;
    private LocalizacaoClient localizacao;
    private TextView textViewTemp, textViewUmidade, textViewVento, textViewChuva, textViewAlertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewTemp     = findViewById(R.id.textViewTemp);
        textViewUmidade  = findViewById(R.id.textViewUmidade);
        textViewVento    = findViewById(R.id.textViewVento);
        textViewChuva    = findViewById(R.id.textViewChuva);
        textViewAlertas  = findViewById(R.id.textViewAlertas);

        api         = ClimaApiClient.criar();
        localizacao = new LocalizacaoClient(getApplicationContext());

        solicitarPermissaoEBuscarClima();
    }

    private void solicitarPermissaoEBuscarClima() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buscarClima(); // já tem permissão
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSAO_LOCALIZACAO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSAO_LOCALIZACAO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                buscarClima(); // usuário concedeu
            } else {
                Log.e("PERMISSAO", "Localização negada pelo usuário");
            }
        }
    }

    private void buscarClima() {
        localizacao.obterLocalizacao()
                .thenCompose(location -> api.buscarClima(
                        location.getLatitude(),
                        location.getLongitude()
                ))
                .thenAcceptAsync(clima -> {
                    textViewTemp.setText(clima.current.temperature2m + "°C");
                    textViewUmidade.setText(clima.current.relativeHumidity2m + "%");
                    textViewVento.setText(clima.current.windSpeed10m + " km/h");
                    textViewChuva.setText(clima.current.precipitation + " mm");

                    List<String> alertas = new AlertaClimaticoService(clima).verificarAlertas();
                    textViewAlertas.setText(String.join("\n", alertas));
                }, mainThread::post)
                .exceptionally(erro -> {
                    Log.e("CLIMA", erro.getMessage());
                    return null;
                });
    }
}