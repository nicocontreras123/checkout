import './Header.css'

export default function Header() {
  return (
    <header className="header">
      <div className="header-logo">
        <img src="/logo.png" alt="Walmart Logo" className="logo-img" />
        <h1>Walmart Chile</h1>
      </div>
      <div>Sistema de Checkout</div>
    </header>
  )
}
