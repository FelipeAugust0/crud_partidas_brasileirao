import { useEffect, useState } from "react";
import { getTimes, getTimesPorSerie } from "../api/Api";

export default function Times() {
  const [times, setTimes] = useState([]);
  const [serie, setSerie] = useState("");

  useEffect(() => {
    if (serie === "") {
      getTimes().then(setTimes);
    } else {
      getTimesPorSerie(serie).then(setTimes);
    }
  }, [serie]);

  return (
    <div>
      <h2>Times</h2>
      <select onChange={e => setSerie(e.target.value)}>
        <option value="">Todas as séries</option>
        <option value="A">Série A</option>
        <option value="B">Série B</option>
        <option value="C">Série C</option>
      </select>
      <ul>
        {times.map(t => (
          <li key={t.id_time}>{t.nome} — Série {t.serie}</li>
        ))}
      </ul>
    </div>
  );
}
