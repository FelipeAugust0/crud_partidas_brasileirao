import { useEffect, useState } from "react";
import "./GerenciarPartidas.css";
import api from "../services/api";
import ModalEditarPartida from "../components/ModalEditarPartida";

export default function GerenciarPartidas() {

    // Série atual exibida
    const [serie, setSerie] = useState("A");

    // Lista de partidas da API
    const [partidas, setPartidas] = useState([]);

    // Controle do modal
    const [modalAberto, setModalAberto] = useState(false);
    const [partidaSelecionada, setPartidaSelecionada] = useState(null);

    // Busca por ID
    const [buscaId, setBuscaId] = useState("");
    const [resultadoBusca, setResultadoBusca] = useState(null);

    // FILTRO POR TIME
    const [filtroTime, setFiltroTime] = useState("");

    function carregar() {
        fetch(`${api}/partidas/serie/${serie}`)
            .then(res => res.json())
            .then(setPartidas)
            .catch(() => alert("Erro ao carregar partidas."));
    }

    useEffect(() => {
        carregar();
        setFiltroTime(""); // limpa filtro ao trocar série
    }, [serie]);

    function excluir(id) {
        if (!window.confirm("Tem certeza que deseja excluir esta partida?")) return;

        fetch(`${api}/partidas/${id}`, { method: "DELETE" })
            .then(() => carregar())
            .catch(() => alert("Erro ao excluir partida."));
    }

    function abrirEditar(p) {
        setPartidaSelecionada(p);
        setModalAberto(true);
    }

    // Buscar por ID
    function buscarPorId() {
        if (!buscaId) {
            alert("Digite um ID para buscar.");
            return;
        }

        fetch(`${api}/partidas/${buscaId}`)
            .then(async res => {
                if (!res.ok) {
                    const erro = await res.json().catch(() => null);
                    alert(erro?.mensagem || "Erro ao buscar partida.");
                    setResultadoBusca(null);
                    return null;
                }
                return res.json();
            })
            .then(data => data && setResultadoBusca(data))
            .catch(() => alert("Erro ao buscar partida."));
    }

    function limparBusca() {
        setResultadoBusca(null);
        setBuscaId("");
    }

    // ============================
    // LISTA DE TIMES PARA O SELECT
    // ============================
    const listaTimes = [...new Set(
        partidas.flatMap(p => [
            p.timeCasa?.nome,
            p.timeFora?.nome
        ])
    )].filter(Boolean);

    // ============================
    // FILTRAGEM DAS PARTIDAS
    // ============================
    const partidasFiltradas = partidas.filter(p => {
        if (!filtroTime) return true;

        return (
            p.timeCasa?.nome === filtroTime ||
            p.timeFora?.nome === filtroTime
        );
    });

    return (
        <div className="gerenciar-container">

            {/* TOPO */}
            <div className="topo">
                <h2>Gerenciar Partidas</h2>

                <div className="filtros-row">

                    {/* FILTRO DE SÉRIE */}
                    <div className="serie-pill-gerenciar">
                        <span>Série</span>
                        <select
                            value={serie}
                            onChange={(e) => setSerie(e.target.value)}
                        >
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                        </select>
                    </div>

                    {/* ⭐ FILTRO POR TIME */}
                    <div className="time-pill-gerenciar">
                        <span>Time</span>
                        <select
                            value={filtroTime}
                            onChange={(e) => setFiltroTime(e.target.value)}
                        >
                            <option value="">Todos</option>
                            {listaTimes.map((t, i) => (
                                <option key={i} value={t}>{t}</option>
                            ))}
                        </select>
                    </div>

                </div>
            </div>

            {/* BUSCA POR ID */}
            <div className="buscar-por-id">
                <input
                    type="number"
                    placeholder="Buscar partida pelo ID..."
                    value={buscaId}
                    onChange={(e) => setBuscaId(e.target.value)}
                />

                <button onClick={buscarPorId}>
                    Buscar
                </button>

                {resultadoBusca && (
                    <button className="btn-limpar" onClick={limparBusca}>
                        Limpar
                    </button>
                )}
            </div>

            {/* CARD DO RESULTADO DA BUSCA */}
            {resultadoBusca && (
                <div className="card-busca fade-slide">
                    <div className="card-busca-header">
                        <h3>Partida #{resultadoBusca.id}</h3>
                    </div>

                    <div className="card-busca-body">

                        <div className="card-busca-times">

                            <div className="time-box">
                                <img
                                    src={`/Escudos/${resultadoBusca.timeCasa.escudo}`}
                                    alt={resultadoBusca.timeCasa.nome}
                                    className="escudo-busca"
                                />
                                <span className="time-nome">{resultadoBusca.timeCasa.nome}</span>
                            </div>

                            <div className="vs-card">VS</div>

                            <div className="time-box">
                                <img
                                    src={`/Escudos/${resultadoBusca.timeFora.escudo}`}
                                    alt={resultadoBusca.timeFora.nome}
                                    className="escudo-busca"
                                />
                                <span className="time-nome">{resultadoBusca.timeFora.nome}</span>
                            </div>

                        </div>

                        <div className="card-busca-infos">
                            <p><strong>Início:</strong> {resultadoBusca.dataHoraInicio.replace("T", " ")}</p>
                            <p><strong>Fim:</strong> {resultadoBusca.dataHoraFim.replace("T", " ")}</p>
                        </div>

                    </div>

                    <div className="card-busca-actions">
                        <button className="btn-edit" onClick={() => abrirEditar(resultadoBusca)}>
                            Editar
                        </button>

                        <button className="btn-delete" onClick={() => excluir(resultadoBusca.id)}>
                            Excluir
                        </button>
                    </div>
                </div>
            )}

            {/* TABELA PRINCIPAL */}
            <table className="tabela-premium">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Mandante</th>
                        <th>Visitante</th>
                        <th>Início</th>
                        <th>Fim</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <tbody>
                    {partidasFiltradas.map(p => (
                        <tr key={p.id}>
                            <td>{p.id}</td>
                            <td>{p.timeCasa?.nome}</td>
                            <td>{p.timeFora?.nome}</td>
                            <td>{p.dataHoraInicio?.replace("T", " ")}</td>
                            <td>{p.dataHoraFim?.replace("T", " ")}</td>

                            <td className="acoes">
                                <button className="btn-edit" onClick={() => abrirEditar(p)}>
                                    Editar
                                </button>

                                <button className="btn-delete" onClick={() => excluir(p.id)}>
                                    Excluir
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* MODAL */}
            {modalAberto && (
                <ModalEditarPartida
                    partida={partidaSelecionada}
                    fechar={() => setModalAberto(false)}
                    recarregar={carregar}
                />
            )}

        </div>
    );
}
