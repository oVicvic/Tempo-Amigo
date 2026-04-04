package unicsul.itinerario.tempoamigo.model;

public class ClimaDiario {

    private final String data;
    private final double temperaturaMax;
    private final double temperaturaMin;
    private final double precipitacao;

    public ClimaDiario(String data, double temperaturaMax, double temperaturaMin, double precipitacao) {
        this.data = data;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.precipitacao = precipitacao;
    }

    public String getData() { return data; }
    public double getTemperaturaMax() { return temperaturaMax; }
    public double getTemperaturaMin() { return temperaturaMin; }
    public double getPrecipitacao() { return precipitacao; }
}