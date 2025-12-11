import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Header from "./components/Header.jsx";

import Home from "./pages/Home";
import GerenciarPartidas from "./pages/GerenciarPartidas";

export default function App() {
  return (
    <BrowserRouter>
      <Header />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/gerenciar" element={<GerenciarPartidas />} />
      </Routes>
    </BrowserRouter>
  );
}
