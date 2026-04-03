package unicsul.itinerario.tempoamigo.dto;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClimaDTO {

    public double latitude;
    public double longitude;

    @SerializedName("generationtime_ms")
    public double generationtimeMs;

    @SerializedName("utc_offset_seconds")
    public int utcOffsetSeconds;

    public String timezone;

    @SerializedName("timezone_abbreviation")
    public String timezoneAbbreviation;

    public double elevation;

    @SerializedName("current_units")
    public CurrentUnits currentUnits;

    public Current current;

    @SerializedName("hourly_units")
    public HourlyUnits hourlyUnits;

    public Hourly hourly = new Hourly();

    @SerializedName("daily_units")
    public DailyUnits dailyUnits;

    public Daily daily = new Daily();


    public static class CurrentUnits {
        public String time;
        public String interval;

        @SerializedName("temperature_2m")
        public String temperature2m;

        @SerializedName("relative_humidity_2m")
        public String relativeHumidity2m;

        @SerializedName("wind_speed_10m")
        public String windSpeed10m;

        public String precipitation;

        @SerializedName("weather_code")
        public String weatherCode;
    }


    public static class Current {
        public String time;
        public int interval;

        @SerializedName("temperature_2m")
        public double temperature2m;

        @SerializedName("relative_humidity_2m")
        public int relativeHumidity2m;

        @SerializedName("wind_speed_10m")
        public double windSpeed10m;

        public double precipitation;

        @SerializedName("weather_code")
        public int weatherCode;
    }


    public static class HourlyUnits {
        public String time;

        @SerializedName("temperature_2m")
        public String temperature2m;

        @SerializedName("precipitation_probability")
        public String precipitationProbability;
    }


    public static class Hourly {
        public List<String> time = new ArrayList<>();

        @SerializedName("temperature_2m")
        public List<Double> temperature2m = new ArrayList<>();

        @SerializedName("precipitation_probability")
        public List<Integer> precipitationProbability = new ArrayList<>();

        public List<String> getTimeFormatado() {
            return time.stream()
                    .map(t -> LocalDateTime.parse(t)
                            .format(DateTimeFormatter.ofPattern("dd/MM HH:mm")))
                    .collect(Collectors.toList());
        }
    }


    public static class DailyUnits {
        public String time;

        @SerializedName("temperature_2m_max")
        public String temperature2mMax;

        @SerializedName("temperature_2m_min")
        public String temperature2mMin;

        @SerializedName("precipitation_sum")
        public String precipitationSum;
    }


    public static class Daily {
        public List<String> time = new ArrayList<>();

        @SerializedName("temperature_2m_max")
        public List<Double> temperature2mMax = new ArrayList<>();

        @SerializedName("temperature_2m_min")
        public List<Double> temperature2mMin = new ArrayList<>();

        @SerializedName("precipitation_sum")
        public List<Double> precipitationSum = new ArrayList<>();

        public List<String> getTimeFormatado() {
            return time.stream()
                    .map(t -> LocalDate.parse(t)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .collect(Collectors.toList());
        }
    }
}