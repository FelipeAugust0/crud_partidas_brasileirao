package api;

import static spark.Spark.*;

import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.*;

import dao.PartidaDAO;
import dao.TimeDAO;
import model.Partida;
import model.Time;

public class ApiBrasileirao {

    // DAOs usados pela API
    private static final TimeDAO timeDAO = new TimeDAO();
    private static final PartidaDAO partidaDAO = new PartidaDAO();

    // Configuração do Gson para lidar com LocalDateTime
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                            LocalDateTime.parse(json.getAsString()))
            .create();

    public static void main(String[] args) {

        port(4567);
        enableCORS();

        // Define JSON como formato padrão de resposta
        after((req, res) -> res.type("application/json"));

        // =====================================================
        // ROTAS DE TIMES
        // =====================================================

        // Lista todos os times
        get("/times", (req, res) -> gson.toJson(timeDAO.buscarTodos()));

        // Busca time pelo ID
        get("/times/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Time t = timeDAO.buscarPorId(id);

                if (t == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Time não encontrado\"}";
                }
                return gson.toJson(t);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        // Lista times por série
        get("/times/serie/:serie", (req, res) -> {
            try {
                String serie = req.params(":serie").toUpperCase();
                return gson.toJson(timeDAO.buscarPorSerie(serie));

            } catch (Exception e) {
                res.status(500);
                return "[]";
            }
        });

        // =====================================================
        // ROTAS DE PARTIDAS
        // =====================================================

        // Busca partida por ID
        get("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));
                Partida p = partidaDAO.buscarPorId(id);

                if (p == null) {
                    res.status(404);
                    return "{\"mensagem\":\"Partida não encontrada\"}";
                }

                return gson.toJson(p);

            } catch (Exception e) {
                res.status(400);
                return "{\"mensagem\":\"ID inválido\"}";
            }
        });

        // Lista partidas de uma série
        get("/partidas/serie/:serie", (req,res) -> {
            try {
                return gson.toJson(partidaDAO.buscarPorSerie(req.params(":serie").toUpperCase()));
            } catch (Exception e) {
                return "[]";
            }
        });

        // =====================================================
        // CADASTRAR PARTIDA (com regras de negócio)
        // =====================================================

        post("/partidas", (req, res) -> {
            try {
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);

                // IDs enviados pelo front
                Long idCasa = json.getAsJsonObject("timeCasa").get("id_time").getAsLong();
                Long idFora = json.getAsJsonObject("timeFora").get("id_time").getAsLong();

                Time casa = timeDAO.buscarPorId(idCasa);
                Time fora = timeDAO.buscarPorId(idFora);

                // Validações dos times
                if (casa == null || fora == null) {
                    res.status(400);
                    return "{\"mensagem\":\"Time inválido enviado\"}";
                }

                if (idCasa.equals(idFora)) {
                    res.status(400);
                    return "{\"mensagem\":\"Um time não pode jogar contra ele mesmo\"}";
                }

                if (!casa.getSerie().equalsIgnoreCase(fora.getSerie())) {
                    res.status(400);
                    return "{\"mensagem\":\"Times de séries diferentes não podem se enfrentar\"}";
                }

                // Montagem do horário
                LocalDateTime inicio = LocalDateTime.parse(json.get("dataHoraInicio").getAsString());
                int minutos = json.get("duracaoMinutos").getAsInt();
                LocalDateTime fim = inicio.plusMinutes(minutos);

                // Lista de partidas já existentes para validar conflitos
                List<Partida> existentes = partidaDAO.buscarPorSerie(casa.getSerie());

                // Regra para impedir horários sobrepostos
                boolean conflito = existentes.stream().anyMatch(p ->
                        (
                            p.getTimeCasa().getId_time().equals(casa.getId_time()) ||
                            p.getTimeFora().getId_time().equals(casa.getId_time()) ||
                            p.getTimeCasa().getId_time().equals(fora.getId_time()) ||
                            p.getTimeFora().getId_time().equals(fora.getId_time())
                        )
                        &&
                        (inicio.isBefore(p.getDataHoraFim()) && fim.isAfter(p.getDataHoraInicio()))
                );

                if (conflito) {
                    res.status(400);
                    return "{\"mensagem\":\"O time já está em outra partida nesse horário.\"}";
                }

                // Criação final da partida
                Partida nova = new Partida(casa, fora, inicio, fim);
                partidaDAO.inserir(nova);

                res.status(201);
                return gson.toJson(nova);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return "{\"mensagem\":\"Erro ao cadastrar partida\"}";
            }
        });

        // =====================================================
        // ATUALIZAR PARTIDA
        // =====================================================

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
                return "{\"mensagem\":\"Erro ao atualizar\"}";
            }
        });

        // =====================================================
        // DELETAR PARTIDA
        // =====================================================

        delete("/partidas/:id", (req, res) -> {
            try {
                Long id = Long.parseLong(req.params(":id"));

                if (!partidaDAO.deletar(id))
                    res.status(404);

                res.status(204);
                return "";

            } catch (Exception e) {
                res.status(500);
                return "{\"mensagem\":\"Erro ao excluir\"}";
            }
        });
    }

    // =====================================================
    // CORS
    // =====================================================

    private static void enableCORS() {

        // Permite requisições prévias do navegador
        options("/*", (req, res) -> {
            String headers = req.headers("Access-Control-Request-Headers");
            if (headers != null) res.header("Access-Control-Allow-Headers", headers);

            String method = req.headers("Access-Control-Request-Method");
            if (method != null) res.header("Access-Control-Allow-Methods", method);

            return "OK";
        });

        // Libera acesso da API para o front
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        });
    }
}
