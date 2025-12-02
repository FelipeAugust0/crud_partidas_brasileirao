package model;

import java.time.LocalDateTime;

public class Partida {

    private Long id;
    private Time timeCasa;
    private Time timeFora;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime duracaoPartida;

    // Construtor vazio
    public Partida() {
    }

    // Construtor para leitura do BD (com ID)
    public Partida(Long id, Time timeCasa, Time timeFora, LocalDateTime dataHoraInicio, LocalDateTime duracaoPartida) {
        this.id = id;
        this.timeCasa = timeCasa;
        this.timeFora = timeFora;
        this.dataHoraInicio = dataHoraInicio;
        this.duracaoPartida = duracaoPartida;
    }

    // Construtor sem ID (inserção)
    public Partida(Time timeCasa, Time timeFora, LocalDateTime dataHoraInicio, LocalDateTime duracaoPartida) {
        this.timeCasa = timeCasa;
        this.timeFora = timeFora;
        this.dataHoraInicio = dataHoraInicio;
        this.duracaoPartida = duracaoPartida;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public Time getTimeFora() {
        return timeFora;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDuracaoPartida() {
        return duracaoPartida;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimeCasa(Time timeCasa) {
        this.timeCasa = timeCasa;
    }

    public void setTimeFora(Time timeFora) {
        this.timeFora = timeFora;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public void setDuracaoPartida(LocalDateTime duracaoPartida) {
        this.duracaoPartida = duracaoPartida;
    }

    @Override
    public String toString() {
        return "Partida [id=" + id + ", timeCasa=" + timeCasa + ", timeFora=" + timeFora +
                ", inicio=" + dataHoraInicio + ", duracao=" + duracaoPartida + "]";
    }
}
