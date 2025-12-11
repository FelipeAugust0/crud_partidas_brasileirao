package model;

public class Time {

    private Long id_time;
    private String nome;
    private String serie;
    private String escudo; // caminho do escudo usado no front

    public Time() {
    }

    // Construtor que recebe apenas o ID
    public Time(Long id_time) {
        this.id_time = id_time;
    }

    // Construtor completo (geralmente usado ao montar a resposta do BD)
    public Time(Long id_time, String nome, String serie, String escudo) {
        this.id_time = id_time;
        this.nome = nome;
        this.serie = serie;
        this.escudo = escudo;
    }

    // Getters e setters comuns
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

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    // Facilita a visualização em logs
    @Override
    public String toString() {
        return "Time [id_time=" + id_time + ", nome=" + nome +
               ", serie=" + serie + ", escudo=" + escudo + "]";
    }
}
