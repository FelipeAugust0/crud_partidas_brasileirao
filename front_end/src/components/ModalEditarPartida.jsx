import { useState } from "react";
import api from "../services/api";
import "./ModalEditarPartida.css";

// Modal para editar os horários de uma partida
export default function ModalEditarPartida({ partida, fechar, recarregar }) {

  // Estados para os valores iniciais da partida
  const [inicio, setInicio] = useState(partida.dataHoraInicio);
  const [fim, setFim] = useState(partida.dataHoraFim);

  // Envia as alterações para o backend
  function salvar() {
    const body = {
      ...partida,
      dataHoraInicio: inicio,
      dataHoraFim: fim
    };

    fetch(`${api}/partidas/${partida.id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    })
      .then(() => {
        recarregar();  // atualiza a lista no front
        fechar();      // fecha o modal
      })
      .catch(() => alert("Erro ao salvar edição."));
  }

  return (
    <div className="modal-overlay">
      <div className="modal-box">

        {/* Título do modal */}
        <h3>Editar Partida #{partida.id}</h3>

        {/* Campo de início da partida */}
        <label>Início</label>
        <input
          type="datetime-local"
          value={inicio}
          onChange={e => setInicio(e.target.value)}
        />

        {/* Campo de fim da partida */}
        <label>Fim</label>
        <input
          type="datetime-local"
          value={fim}
          onChange={e => setFim(e.target.value)}
        />

        {/* Botões do modal */}
        <div className="modal-actions">
          <button className="btn-cancel" onClick={fechar}>Cancelar</button>
          <button className="btn-save" onClick={salvar}>Salvar</button>
        </div>

      </div>
    </div>
  );
}
