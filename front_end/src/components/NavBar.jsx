import { Link } from "react-router-dom";

export default function NavBar() {
  return (
    <nav style={{display:"flex", gap:20, padding:10, background:"#eee"}}>
      <Link to="/">Times</Link>
      <Link to="/partidas">Partidas</Link>
      <Link to="/nova-partida">Cadastrar Partida</Link>
    </nav>
  );
}
