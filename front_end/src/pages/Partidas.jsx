import { useEffect, useState } from "react";
import { API_URL } from "../api/Api";

export default function Partidas() {
  const [partidas, setPartidas] = useState([]);
  const [serie, setSerie] = useState("A");

  useEffect(() => {
    fetch(`${API_URL}/partidas/serie/${serie}`)
      .then(res => res.json())
      .then(data => {
        console.log("RESPOSTA DA API:", data);
        
        // se vier objeto com campo partidas
        if (Array.isArray(data.partidas)) {
          setPartidas(data.partidas);
        }
        // se vier array direto
        else if (Array.isArray(data)) {
          setPartidas(data);
        }
        // se vier mensagem ao invés de lista
        else {
          setPartidas([]);  // <-- evita erro no map
        }
      })
      .catch(err => {
        console.error("Erro no fetch:", err);
        setPartidas([]); // segurança adicional
      });
  }, [serie]);

  return (
    <div>
      <h2>Partidas — Série {serie}</h2>

      <label>Selecione a série:</label>
      <select value={serie} onChange={e => setSerie(e.target.value)}>
        <option value="A">Série A</option>
        <option value="B">Série B</option>
        <option value="C">Série C</option>
      </select>

      {partidas.length === 0 && <p>Nenhuma partida encontrada.</p>}

      <ul>
        {partidas.map(p => (
          <li key={p.id}>
            {p.timeCasa?.nome} x {p.timeFora?.nome} — {p.dataHoraInicio}
          </li>
        ))}
      </ul>
    </div>
  );
}
