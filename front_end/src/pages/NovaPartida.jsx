import { useEffect, useState } from "react";
import { API_URL } from "../api/Api";

export default function NovaPartida() {
  const [serie, setSerie] = useState("A");
  const [times, setTimes] = useState([]);
  const [timeCasa, setTimeCasa] = useState("");
  const [timeFora, setTimeFora] = useState("");
  const [dataHoraInicio, setDataHoraInicio] = useState("");
  const [minutos, setMinutos] = useState("");

  // buscar times da série selecionada
  useEffect(() => {
    fetch(`${API_URL}/times/serie/${serie}`)
      .then((res) => res.json())
      .then((data) => {
        setTimes(data);
        setTimeCasa("");
        setTimeFora("");
      });
  }, [serie]);

  function cadastrar() {
    if (!timeCasa || !timeFora || !dataHoraInicio || !minutos) {
      alert("Preencha todos os campos!");
      return;
    }

    if (timeCasa === timeFora) {
      alert("Um time não pode jogar contra ele mesmo!");
      return;
    }

    const payload = {
      timeCasa: { id_time: Number(timeCasa), serie: serie },
      timeFora: { id_time: Number(timeFora), serie: serie },
      dataHoraInicio: dataHoraInicio + ":00", // react envia "2025-12-02T15:00"
      duracaoMinutos: Number(minutos),
    };

    console.log("PAYLOAD ENVIADO: ", payload);

    fetch(`${API_URL}/partidas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        const body = await res.json();
        if (!res.ok) {
          alert(body.mensagem || "Erro ao cadastrar partida");
          return;
        }
        alert("Partida cadastrada com sucesso!");
      })
      .catch((err) => {
        alert("Erro na requisição");
        console.error(err);
      });
  }

  return (
    <div>
      <h2>Nova Partida</h2>

      <label>Série:</label>
      <select value={serie} onChange={(e) => setSerie(e.target.value)}>
        <option value="A">Série A</option>
        <option value="B">Série B</option>
        <option value="C">Série C</option>
      </select>
      <br />
      <br />

      <label>Time da Casa:</label>
      <select value={timeCasa} onChange={(e) => setTimeCasa(e.target.value)}>
        <option value="">Selecione...</option>
        {times.map((t) => (
          <option key={t.id_time} value={t.id_time}>
            {t.nome}
          </option>
        ))}
      </select>
      <br />
      <br />

      <label>Time Visitante:</label>
      <select value={timeFora} onChange={(e) => setTimeFora(e.target.value)}>
        <option value="">Selecione...</option>
        {times.map((t) => (
          <option key={t.id_time} value={t.id_time}>
            {t.nome}
          </option>
        ))}
      </select>
      <br />
      <br />

      <label>Data/Hora de início:</label>
      <input
        type="datetime-local"
        value={dataHoraInicio}
        onChange={(e) => setDataHoraInicio(e.target.value)}
      />
      <br />
      <br />

      <label>Duração (minutos):</label>
      <input
        type="number"
        min="1"
        value={minutos}
        onChange={(e) => setMinutos(e.target.value)}
      />
      <br />
      <br />

      <button onClick={cadastrar}>Cadastrar Partida</button>
    </div>
  );
}
