package unicsul.itinerario.tempoamigo.service;

import java.util.ArrayList;
import java.util.List;

import unicsul.itinerario.tempoamigo.dto.ClimaDTO;
import unicsul.itinerario.tempoamigo.model.Alerta;

public class AlertaClimaticoService {

    private static final double TEMPERATURA_CALOR_EXTREMO = 35.0;
    private static final double TEMPERATURA_FRIO_EXTREMO = 10.0;
    private static final int UMIDADE_ALTA_EXTREMA = 95;
    private static final int UMIDADE_BAIXA_EXTREMA = 20;
    private static final double VENTO_EXTREMO = 60.0;
    private static final double CHUVA_EXTREMA = 50.0;
    private static final int PROBABILIDADE_CHUVA_EXTREMA = 90;

    private final ClimaDTO clima;

    public AlertaClimaticoService(ClimaDTO clima) {
        this.clima = clima;
    }

    public List<Alerta> verificarAlertas() {
        List<Alerta> alertas = new ArrayList<>();

        verificarTemperatura(alertas);
        verificarUmidade(alertas);
        verificarVento(alertas);
        verificarChuva(alertas);
        verificarProbabilidadeChuva(alertas);

        return alertas;
    }

    private void verificarTemperatura(List<Alerta> alertas) {
        double temp = clima.current.temperature2m;
        if (temp > TEMPERATURA_CALOR_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.CALOR, Alerta.Severidade.CRITICO, temp));
        } else if (temp < TEMPERATURA_FRIO_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.FRIO, Alerta.Severidade.CRITICO, temp));
        }
    }

    private void verificarUmidade(List<Alerta> alertas) {
        int umidade = clima.current.relativeHumidity2m;
        if (umidade > UMIDADE_ALTA_EXTREMA) {
            alertas.add(new Alerta(Alerta.Tipo.UMIDADE_ALTA, Alerta.Severidade.PERIGO, umidade));
        } else if (umidade < UMIDADE_BAIXA_EXTREMA) {
            alertas.add(new Alerta(Alerta.Tipo.UMIDADE_BAIXA, Alerta.Severidade.PERIGO, umidade));
        }
    }

    private void verificarVento(List<Alerta> alertas) {
        double vento = clima.current.windSpeed10m;
        if (vento > VENTO_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.VENTO, Alerta.Severidade.CRITICO, vento));
        }
    }

    private void verificarChuva(List<Alerta> alertas) {
        List<String> datas = clima.daily.getTimeFormatado();
        for (int i = 0; i < clima.daily.time.size(); i++) {
            double chuva = clima.daily.precipitationSum.get(i);
            if (chuva > CHUVA_EXTREMA) {
                alertas.add(new Alerta(Alerta.Tipo.CHUVA, Alerta.Severidade.CRITICO, chuva, datas.get(i)));
            }
        }
    }

    private void verificarProbabilidadeChuva(List<Alerta> alertas) {
        List<String> horas = clima.hourly.getTimeFormatado();
        for (int i = 0; i < clima.hourly.time.size(); i++) {
            int prob = clima.hourly.precipitationProbability.get(i);
            if (prob > PROBABILIDADE_CHUVA_EXTREMA) {
                alertas.add(new Alerta(Alerta.Tipo.PROBABILIDADE_CHUVA, Alerta.Severidade.PERIGO, prob, horas.get(i)));
            }
        }
    }
}