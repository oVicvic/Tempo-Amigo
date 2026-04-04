package unicsul.itinerario.tempoamigo.service;

import java.util.ArrayList;
import java.util.List;

import unicsul.itinerario.tempoamigo.model.Alerta;
import unicsul.itinerario.tempoamigo.model.Clima;
import unicsul.itinerario.tempoamigo.model.ClimaDiario;
import unicsul.itinerario.tempoamigo.model.ClimaHorario;

public class AlertaClimaticoService {

    private static final double TEMPERATURA_CALOR_EXTREMO = 35.0;
    private static final double TEMPERATURA_FRIO_EXTREMO = 10.0;
    private static final int UMIDADE_ALTA_EXTREMA = 95;
    private static final int UMIDADE_BAIXA_EXTREMA = 20;
    private static final double VENTO_EXTREMO = 60.0;
    private static final double CHUVA_EXTREMA = 50.0;
    private static final int PROBABILIDADE_CHUVA_EXTREMA = 90;

    private final Clima clima;

    public AlertaClimaticoService(Clima clima) {
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
        double temp = clima.getTemperatura();
        if (temp > TEMPERATURA_CALOR_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.CALOR, Alerta.Severidade.CRITICO, temp));
        } else if (temp < TEMPERATURA_FRIO_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.FRIO, Alerta.Severidade.CRITICO, temp));
        }
    }

    private void verificarUmidade(List<Alerta> alertas) {
        int umidade = clima.getUmidade();
        if (umidade > UMIDADE_ALTA_EXTREMA) {
            alertas.add(new Alerta(Alerta.Tipo.UMIDADE_ALTA, Alerta.Severidade.PERIGO, umidade));
        } else if (umidade < UMIDADE_BAIXA_EXTREMA) {
            alertas.add(new Alerta(Alerta.Tipo.UMIDADE_BAIXA, Alerta.Severidade.PERIGO, umidade));
        }
    }

    private void verificarVento(List<Alerta> alertas) {
        double vento = clima.getVelocidadeVento();
        if (vento > VENTO_EXTREMO) {
            alertas.add(new Alerta(Alerta.Tipo.VENTO, Alerta.Severidade.CRITICO, vento));
        }
    }

    private void verificarChuva(List<Alerta> alertas) {
        for (ClimaDiario dia : clima.getPrevisaoDiaria()) {
            if (dia.getPrecipitacao() > CHUVA_EXTREMA) {
                alertas.add(new Alerta(Alerta.Tipo.CHUVA, Alerta.Severidade.CRITICO,
                        dia.getPrecipitacao(), dia.getData()));
            }
        }
    }

    private void verificarProbabilidadeChuva(List<Alerta> alertas) {
        for (ClimaHorario horario : clima.getPrevisaoHoraria()) {
            if (horario.getProbabilidadeChuva() > PROBABILIDADE_CHUVA_EXTREMA) {
                alertas.add(new Alerta(Alerta.Tipo.PROBABILIDADE_CHUVA, Alerta.Severidade.PERIGO,
                        horario.getProbabilidadeChuva(), horario.getHorario()));
            }
        }
    }
}