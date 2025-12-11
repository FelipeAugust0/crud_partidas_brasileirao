import "./Header.css";
import { Link } from "react-router-dom";

// Componente do topo do site, com título e botões de navegação
export default function Header() {
  return (
    <header className="top-header">
      
      {/* Parte esquerda: título + caixa da Betano */}
      <div className="header-left-group">
        <span className="header-title">PARTIDAS DO BRASILEIRÃO</span>
        <div className="betano-box">Betano</div>
      </div>

      {/* Parte direita: botões que levam para as páginas principais */}
      <nav className="header-right">
        {/* Botão para a tela de cadastro */}
        <Link className="nav-btn nav-primary" to="/">
          Cadastro
        </Link>

        {/* Botão para a tela de listagem/gerenciamento */}
        <Link className="nav-btn nav-secondary" to="/gerenciar">
          Gerenciar Partidas
        </Link>
      </nav>

    </header>
  );
}
