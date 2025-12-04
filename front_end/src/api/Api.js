export const API_URL = "http://localhost:4567";

export async function getTimes() {
    const res = await fetch(`${API_URL}/times`);
    return res.json();
}

export async function getTimesPorSerie(serie) {
    const res = await fetch(`${API_URL}/times/serie/${serie}`);
    return res.json();
}

export async function getPartidas() {
    const res = await fetch(`${API_URL}/partidas/serie/A`); // exemplo
    return res.json();
}

export async function postPartida(partida) {
    return fetch(`${API_URL}/partidas`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(partida)
    });
}
