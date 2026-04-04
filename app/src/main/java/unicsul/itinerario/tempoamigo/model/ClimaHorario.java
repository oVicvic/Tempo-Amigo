package unicsul.itinerario.tempoamigo.model;

public class ClimaHorario {

    private final String horario;
    private final double temperatura;
    private final int probabilidadeChuva;

    public ClimaHorario(String horario, double temperatura, int probabilidadeChuva) {
        this.horario = horario;
        this.temperatura = temperatura;
        this.probabilidadeChuva = probabilidadeChuva;
    }

    public String getHorario() { return horario; }
    public double getTemperatura() { return temperatura; }
    public int getProbabilidadeChuva() { return probabilidadeChuva; }
}