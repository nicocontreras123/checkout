import './CheckoutModal.css'

export function CheckoutLoading() {
  return (
    <div className="modal-overlay">
      <div className="checkout-loading">
        <div className="loading-logo">
          <img src="/logo.png" alt="Walmart Logo" className="spinning-logo" />
        </div>
        <h3>Procesando tu compra...</h3>
        <p>Por favor espera un momento</p>
      </div>
    </div>
  )
}

export function Voucher({ confirmation, cart, onClose, formatPrice, calculateWalmartPoints }) {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="voucher" onClick={e => e.stopPropagation()}>
        <div className="voucher-header">
          <img src="/logo.png" alt="Walmart Logo" className="voucher-logo" />
          <div className="modal-success">✓</div>
          <h2>¡Compra Confirmada!</h2>
          <p className="voucher-thanks">Gracias por tu compra</p>
        </div>
        
        <div className="voucher-code">
          <div className="code-label">Código de confirmación</div>
          <div className="confirmation-code">
            {confirmation.confirmationCode}
          </div>
        </div>

        <div className="voucher-section">
          <h3>Productos Comprados</h3>
          <div className="voucher-items">
            {cart.map(item => (
              <div key={item.product.id} className="voucher-item">
                <div className="voucher-item-info">
                  <span className="voucher-item-name">{item.product.name}</span>
                  <span className="voucher-item-qty">x{item.quantity}</span>
                </div>
                <span className="voucher-item-price">{formatPrice(item.product.price * item.quantity)}</span>
              </div>
            ))}
          </div>
        </div>

        <div className="voucher-section">
          <h3>Resumen de Pago</h3>
          <div className="voucher-summary">
            <div className="voucher-row">
              <span>Subtotal</span>
              <span>{formatPrice(confirmation.subtotal)}</span>
            </div>
            {confirmation.totalDiscount > 0 && (
              <div className="voucher-row discount">
                <span>Descuentos aplicados</span>
                <span>-{formatPrice(confirmation.totalDiscount)}</span>
              </div>
            )}
            <div className="voucher-row total">
              <span>Total Pagado</span>
              <span>{formatPrice(confirmation.total)}</span>
            </div>
            <div className="voucher-payment">
              <span>Método de pago: <strong>{confirmation.paymentMethod}</strong></span>
            </div>
          </div>
        </div>

        {confirmation.totalDiscount > 0 && (
          <div className="voucher-savings">
            <span className="savings-icon">🎉</span>
            <span>¡Ahorraste {formatPrice(confirmation.totalDiscount)} en esta compra!</span>
          </div>
        )}

        {calculateWalmartPoints() > 0 && (
          <div className="voucher-rewards">
            <span className="rewards-icon">⭐</span>
            <span>Has acumulado <strong>{calculateWalmartPoints()} puntos Walmart+</strong></span>
          </div>
        )}

        <div className="voucher-footer">
          <p>Fecha: {new Date().toLocaleString('es-CL', { dateStyle: 'long', timeStyle: 'short' })}</p>
        </div>

        <button className="modal-btn" onClick={onClose}>
          Seguir Comprando
        </button>
      </div>
    </div>
  )
}
