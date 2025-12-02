package api;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import com.google.gson.Gson;

import dao.PartidaDAO;
import dao.TimeDAO;
import model.Partida;
import model.Time;

public class ApiBrasileirao {

    private static final TimeDAO timeDAO = new TimeDAO();
    private static final PartidaDAO partidaDAO = new PartidaDAO();
    private static final Gson gson = new Gson();
    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {

        port(4567);

        after((request, response) -> response.type(APPLICATION_JSON));

        // =============================
        // ROTAS TIME
        // =============================

        // GET /times
        get("/times", (req, res) -> gson.toJson(timeDAO.buscarTodos()));

        // GET /times/:id
        get("/times/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Time time = timeDAO.buscarPorId(id);

                if (time != null) {
                    return gson.toJson(time);
                }

                res.status(404);
                return "{\"mensagem\":\"Time não encontrado\"}";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        // GET /times/serie/:serie
        get("/times/serie/:serie", (req, res) -> {
            try {
                String serie = req.params(":serie"); // "A", "B" ou "C"
                List<Time> times = timeDAO.buscarPorSerie(serie); // chama o método do DAO

                if (times != null && !times.isEmpty()) {
                    res.type("application/json");
                    return gson.toJson(times);
                }

                res.status(404);
                return "{\"mensagem\":\"Nenhum time encontrado para a série " + serie + "\"}";

            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao buscar times por série\"}";
            }
        });

        // =============================
        // ROTAS PARTIDA
        // =============================

        // GET /partidas/serie/:serie
        get("/partidas/serie/:serie", (req, res) -> {
            try {
                String serie = req.params(":serie");
                List<Partida> partidas = partidaDAO.buscarPorSerie(serie);

                if (partidas != null && !partidas.isEmpty()) {
                    res.type("application/json");
                    return gson.toJson(partidas);
                }

                res.status(404);
                return "{\"mensagem\":\"Nenhuma partida encontrada para essa série\"}";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"Erro ao buscar por série\"}";
            }
        });

        // GET /partidas/:id
        get("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Partida p = partidaDAO.buscarPorId(id);

                if (p != null) {
                    return gson.toJson(p);
                }

                res.status(404);
                return "{\"mensagem\":\"Partida não encontrada\"}";

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        // POST /partidas
        post("/partidas", (req, res) -> {
            try {
                Partida nova = gson.fromJson(req.body(), Partida.class);
                partidaDAO.inserir(nova);
                res.status(201);
                return gson.toJson(nova);

            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao inserir partida\"}";
            }
        });

        // PUT /partidas/:id
        put("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (partidaDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Partida não encontrada\"}";
                }

                Partida nova = gson.fromJson(req.body(), Partida.class);
                nova.setId(id);
                partidaDAO.atualizar(nova);

                return gson.toJson(nova);

            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao atualizar partida\"}";
            }
        });

        // DELETE /partidas/:id
        delete("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (partidaDAO.buscarPorId(id) == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Partida não encontrada\"}";
                }

                partidaDAO.deletar(id);
                res.status(204);
                return "";

            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao excluir partida\"}";
            }
        });

        System.out.println("API BRASILEIRÃO rodando em http://localhost:4567/");
        System.out.println("/times");
        System.out.println("/partidas");
    }
}
