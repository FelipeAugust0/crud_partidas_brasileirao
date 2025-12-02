package model;

public class Time {
    private Long id_time;
    private String nome;
    private String serie;

    public Time() {
    }

    public Time(Long id_time) {
        this.id_time = id_time;
    }

    public Time(Long id_time, String nome, String serie) {
        this.id_time = id_time;
        this.nome = nome;
        this.serie = serie;
    }

    public Long getId_time() {
        return id_time;
    }

    public void setId_time(Long id_time) {
        this.id_time = id_time;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return "Time [id_time=" + id_time + ", nome=" + nome + ", serie=" + serie + "]";
    }
}
