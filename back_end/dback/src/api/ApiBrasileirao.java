package api;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.options;
import static spark.Spark.before;

import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import dao.PartidaDAO;
import dao.TimeDAO;
import model.Partida;
import model.Time;

public class ApiBrasileirao {

    private static final TimeDAO timeDAO = new TimeDAO();
    private static final PartidaDAO partidaDAO = new PartidaDAO();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime
                            .parse(json.getAsString()))
            .create();

    private static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {

        port(4567);

        enableCORS(); // <<<<<<<<<<<< ADICIONADO AQUI

        after((request, response) -> response.type(APPLICATION_JSON));

        System.out.println("API BRASILEIRÃO rodando em http://localhost:4567/");
        System.out.println("Rotas disponíveis:");
        System.out.println("GET  /times");
        System.out.println("GET  /times/:id");
        System.out.println("GET  /times/serie/:serie");
        System.out.println("GET  /partidas/:id");
        System.out.println("GET  /partidas/serie/:serie");
        System.out.println("POST /partidas");
        System.out.println("PUT  /partidas/:id");
        System.out.println("DELETE /partidas/:id");

        // ========= ROTAS ==============

        // GET /times
        get("/times", (req, res) -> gson.toJson(timeDAO.buscarTodos()));

        // GET /times/:id
        get("/times/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Time time = timeDAO.buscarPorId(id);

                if (time != null)
                    return gson.toJson(time);

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
                String serie = req.params(":serie").toUpperCase();
                List<Time> times = timeDAO.buscarPorSerie(serie);

                if (!times.isEmpty()) {
                    return gson.toJson(times);
                }

                res.status(404);
                return "{\"mensagem\":\"Nenhum time encontrado para a série " + serie + "\"}";
            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao buscar times por série\"}";
            }
        });

        // GET /partidas/serie/:serie
        get("/partidas/serie/:serie", (req, res) -> {
            res.type("application/json");

            try {
                String serie = req.params(":serie").toUpperCase();
                List<Partida> partidas = partidaDAO.buscarPorSerie(serie);

                // sempre retorna um array
                return gson.toJson(partidas);

            } catch (Exception e) {
                res.status(500);
                return "[]"; // retorna array vazio mesmo com erro
            }
        });

        // GET /partidas/:id
        get("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Partida p = partidaDAO.buscarPorId(id);

                if (p != null)
                    return gson.toJson(p);

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

                JsonObject json = gson.fromJson(req.body(), JsonObject.class);

                Time timeCasa = gson.fromJson(json.get("timeCasa"), Time.class);
                Time timeFora = gson.fromJson(json.get("timeFora"), Time.class);

                // impedir confronto consigo mesmo
                if (timeCasa.getId_time() == timeFora.getId_time()) {
                    res.status(400);
                    return "{\"mensagem\":\"Um time não pode jogar contra ele mesmo\"}";
                }

                // impedir confronto entre séries diferentes
                if (!timeCasa.getSerie().equalsIgnoreCase(timeFora.getSerie())) {
                    res.status(400);
                    return "{\"mensagem\":\"Times de séries diferentes não podem se enfrentar\"}";
                }

                LocalDateTime inicio = LocalDateTime.parse(json.get("dataHoraInicio").getAsString());
                int minutos = json.get("duracaoMinutos").getAsInt();
                LocalDateTime fim = inicio.plusMinutes(minutos);

                Partida nova = new Partida(timeCasa, timeFora, inicio, fim);

                partidaDAO.inserir(nova);

                res.status(201);
                res.header("Location", "/partidas/" + nova.getId());
                return gson.toJson(nova);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "{\"mensagem\":\"Erro interno ao inserir partida\"}";
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
    }

    // ===================================
    // HABILITAR CORS
    // ===================================
    private static void enableCORS() {
        options("/*", (request, response) -> {
            String headers = request.headers("Access-Control-Request-Headers");
            if (headers != null) {
                response.header("Access-Control-Allow-Headers", headers);
            }

            String method = request.headers("Access-Control-Request-Method");
            if (method != null) {
                response.header("Access-Control-Allow-Methods", method);
            }

            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        });
    }
}
