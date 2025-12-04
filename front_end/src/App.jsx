import { BrowserRouter, Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar";
import Times from "./pages/Times";
import Partidas from "./pages/Partidas";
import NovaPartida from "./pages/NovaPartida";

export default function App() {
  return (
    <BrowserRouter>
      <NavBar />

      <Routes>
        <Route path="/" element={<Times />} />
        <Route path="/partidas" element={<Partidas />} />
        <Route path="/nova-partida" element={<NovaPartida />} />
      </Routes>
    </BrowserRouter>
  );
}
