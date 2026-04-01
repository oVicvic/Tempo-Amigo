package unicsul.itinerario.tempoamigo;

import static java.lang.String.*;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import unicsul.itinerario.tempoamigo.dto.ClimaDTO;
import unicsul.itinerario.tempoamigo.network.HttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://api.open-meteo.com/v1/forecast" +
            "?latitude=-23.65572127630479" +
            "&longitude=-46.71783742784523" +
            "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,precipitation,weather_code" +
            "&hourly=temperature_2m,precipitation_probability" +
            "&daily=temperature_2m_max,temperature_2m_min,precipitation_sum" +
            "&timezone=America%2FSao_Paulo";

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

        TextView textViewTemp    = findViewById(R.id.textViewTemp);
        TextView textViewUmidade = findViewById(R.id.textViewUmidade);
        TextView textViewVento   = findViewById(R.id.textViewVento);
        TextView textViewChuva   = findViewById(R.id.textViewChuva);

        HttpClient.get(URL, ClimaDTO.class,
                clima -> runOnUiThread(() -> {
                    textViewTemp   .setText(format("%s°C", clima.current.temperature2m));
                    textViewUmidade.setText(format("%d%%", clima.current.relativeHumidity2m));
                    textViewVento  .setText(format("%s km/h", clima.current.windSpeed10m));
                    textViewChuva  .setText(format("%s mm", clima.current.precipitation));
                }),
                erro -> Log.e("CLIMA", "Erro: " + erro)
        );
    }
}