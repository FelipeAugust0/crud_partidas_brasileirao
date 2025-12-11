package model;

import java.time.LocalDateTime;

public class Partida {

    private Long id;
    private Time timeCasa;
    private Time timeFora;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public Partida() {}

    // Construtor usado quando a partida vem do banco
    public Partida(Long id, Time timeCasa, Time timeFora,
                   LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {

        this.id = id;
        this.timeCasa = timeCasa;
        this.timeFora = timeFora;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    // Construtor usado ao criar uma nova partida
    public Partida(Time timeCasa, Time timeFora,
                   LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {

        this.timeCasa = timeCasa;
        this.timeFora = timeFora;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    // ---- GETTERS ----
    public Long getId() { return id; }
    public Time getTimeCasa() { return timeCasa; }
    public Time getTimeFora() { return timeFora; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }

    // ---- SETTERS ----
    public void setId(Long id) { this.id = id; }
    public void setTimeCasa(Time timeCasa) { this.timeCasa = timeCasa; }
    public void setTimeFora(Time timeFora) { this.timeFora = timeFora; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    // Facilita a visualização no console
    @Override
    public String toString() {
        return "Partida { id=" + id +
               ", casa=" + timeCasa +
               ", fora=" + timeFora +
               ", inicio=" + dataHoraInicio +
               ", fim=" + dataHoraFim + " }";
    }
}
