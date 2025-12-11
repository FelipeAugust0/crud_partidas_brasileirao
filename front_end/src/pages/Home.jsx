import { useEffect, useState } from "react";
import "./Home.css";
import api from "../services/api";

export default function Home() {

    // Série atual e lista de times carregada do back-end
    const [serie, setSerie] = useState("A");
    const [times, setTimes] = useState([]);

    // IDs escolhidos nos selects
    const [timeCasa, setTimeCasa] = useState("");
    const [timeFora, setTimeFora] = useState("");

    // Objetos completos dos times (para mostrar o escudo)
    const [timeCasaObj, setTimeCasaObj] = useState(null);
    const [timeForaObj, setTimeForaObj] = useState(null);

    // Dados da partida
    const [dia, setDia] = useState("");
    const [hora, setHora] = useState("");
    const [duracao, setDuracao] = useState("");

    // Carrega os times conforme a série escolhida
    useEffect(() => {
        if (!serie) return;

        fetch(`${api}/times/serie/${serie}`)
            .then((res) => res.json())
            .then((dados) => setTimes(dados))
            .catch(() => {
                alert("Erro ao carregar times. Verifique o back-end.");
            });
    }, [serie]);

    // Quando troca o mandante
    function handleCasa(e) {
        const id = Number(e.target.value);
        setTimeCasa(id);
        const t = times.find((tm) => tm.id_time === id);
        setTimeCasaObj(t || null);
    }

    // Quando troca o visitante
    function handleFora(e) {
        const id = Number(e.target.value);
        setTimeFora(id);
        const t = times.find((tm) => tm.id_time === id);
        setTimeForaObj(t || null);
    }

    // Envia o cadastro da partida para a API
    function cadastrar(e) {
        e.preventDefault();

        // Validação simples
        if (!timeCasa || !timeFora || !dia || !hora || !duracao) {
            alert("Preencha todos os campos antes de cadastrar.");
            return;
        }

        // Formata a data e hora no formato padrão do back-end
        const inicio = `${dia}T${hora}:00`;

        const body = {
            timeCasa: { id_time: Number(timeCasa) },
            timeFora: { id_time: Number(timeFora) },
            dataHoraInicio: inicio,
            duracaoMinutos: Number(duracao),
        };

        fetch(`${api}/partidas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body),
        })
            .then(async (res) => {
                // Se ocorrer erro, mostra a mensagem real do servidor
                if (!res.ok) {
                    const erro = await res.json().catch(() => null);
                    let msg = erro?.mensagem || "Erro desconhecido";
                    alert(msg);
                    throw new Error(msg);
                }
                return res.json();
            })
            .then(() => {
                alert("Partida cadastrada com sucesso!");
            })
            .catch((e) => console.error("Erro:", e));
    }

    // Visitante não pode ser o mesmo time do mandante
    const timesVisitante = times.filter(
        (t) => t.id_time !== Number(timeCasa)
    );

    return (
        <div className="home-wrapper">
            <div className="home-main">

                {/* Cabeçalho da página */}
                <div className="home-top">
                    <div className="home-title">Cadastro de Partida</div>

                    <div className="serie-pill">
                        <span>Série</span>
                        <select
                            value={serie}
                            onChange={(e) => {
                                setSerie(e.target.value);
                                setTimeCasa("");
                                setTimeFora("");
                                setTimeCasaObj(null);
                                setTimeForaObj(null);
                            }}
                        >
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                        </select>
                    </div>
                </div>

                {/* Formulário principal */}
                <form className="home-form" onSubmit={cadastrar}>

                    {/* Escolha dos times */}
                    <div className="teams-row">

                        {/* Mandante */}
                        <div className="team-card">
                            <span className="team-chip">Mandante</span>

                            <select value={timeCasa} onChange={handleCasa}>
                                <option value="">Selecione o mandante...</option>
                                {times.map((t) => (
                                    <option key={t.id_time} value={t.id_time}>
                                        {t.nome}
                                    </option>
                                ))}
                            </select>

                            <div className="escudo-box">
                                {timeCasaObj ? (
                                    <img
                                        src={`/Escudos/${timeCasaObj.escudo}`}
                                        alt={timeCasaObj.nome}
                                        className="escudo-img"
                                    />
                                ) : (
                                    "ESCUDO"
                                )}
                            </div>
                        </div>

                        {/* VS */}
                        <div className="vs-big">VS</div>

                        {/* Visitante */}
                        <div className="team-card">
                            <span className="team-chip">Visitante</span>

                            <select value={timeFora} onChange={handleFora}>
                                <option value="">Selecione o visitante...</option>
                                {timesVisitante.map((t) => (
                                    <option key={t.id_time} value={t.id_time}>
                                        {t.nome}
                                    </option>
                                ))}
                            </select>

                            <div className="escudo-box">
                                {timeForaObj ? (
                                    <img
                                        src={`/Escudos/${timeForaObj.escudo}`}
                                        alt={timeForaObj.nome}
                                        className="escudo-img"
                                    />
                                ) : (
                                    "ESCUDO"
                                )}
                            </div>
                        </div>
                    </div>

                    {/* Dados da partida */}
                    <div className="details-row">

                        <div className="field-group">
                            <label>Dia</label>
                            <input
                                type="date"
                                value={dia}
                                onChange={(e) => setDia(e.target.value)}
                            />
                        </div>

                        <div className="field-group">
                            <label>Hora</label>
                            <input
                                type="time"
                                value={hora}
                                onChange={(e) => setHora(e.target.value)}
                            />
                        </div>

                        <div className="field-group">
                            <label>Duração (min)</label>
                            <input
                                type="number"
                                min="1"
                                value={duracao}
                                onChange={(e) => setDuracao(e.target.value)}
                            />
                        </div>

                        <button type="submit" className="btn-cadastrar">
                            Cadastrar
                        </button>
                    </div>

                </form>
            </div>
        </div>
    );
}
