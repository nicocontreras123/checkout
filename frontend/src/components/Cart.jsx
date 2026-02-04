import CartItem from './CartItem'
import './Cart.css'

export default function Cart({
  cart,
  savedItems,
  paymentMethods,
  selectedPayment,
  checkoutResult,
  onUpdateQuantity,
  onRemoveFromCart,
  onSaveForLater,
  onMoveToCart,
  onPaymentChange,
  onCheckout,
  formatPrice,
  calculateFreeShippingProgress,
  getRemainingForFreeShipping,
  calculateWalmartPoints
}) {
  return (
    <section className="cart-section">
      <h2>Carro ({cart.length} {cart.length === 1 ? 'producto' : 'productos'})</h2>

      {cart.length === 0 ? (
        <div className="cart-empty">
          <div className="cart-empty-icon">🛒</div>
          <p>Tu carro está vacío</p>
          <p>Agrega productos para comenzar</p>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {cart.map(item => (
              <CartItem
                key={item.product.id}
                item={item}
                onUpdateQuantity={onUpdateQuantity}
                onRemove={onRemoveFromCart}
                onSaveForLater={onSaveForLater}
                formatPrice={formatPrice}
              />
            ))}
          </div>

          {savedItems.length > 0 && (
            <div className="saved-items">
              <h3>Guardados para después ({savedItems.length})</h3>
              {savedItems.map(item => (
                <div key={item.product.id} className="saved-item">
                  <img
                    className="cart-item-img"
                    src={item.product.imageUrl || 'https://via.placeholder.com/50'}
                    alt={item.product.name}
                  />
                  <div className="cart-item-info">
                    <div className="cart-item-name">{item.product.name}</div>
                    <div className="cart-item-price">
                      {formatPrice(item.product.price)}
                    </div>
                  </div>
                  <button
                    className="move-to-cart-btn"
                    onClick={() => onMoveToCart(item)}
                  >
                    Mover al carro
                  </button>
                </div>
              ))}
            </div>
          )}

          {checkoutResult && getRemainingForFreeShipping() > 0 && (
            <div className="free-shipping-banner">
              <div className="free-shipping-text">
                Retiro sin costo o agrega {formatPrice(getRemainingForFreeShipping())} para obtener <strong>despacho gratis</strong>
              </div>
              <div className="progress-bar">
                <div 
                  className="progress-fill" 
                  style={{ width: `${calculateFreeShippingProgress()}%` }}
                ></div>
              </div>
            </div>
          )}

          {checkoutResult && getRemainingForFreeShipping() === 0 && (
            <div className="free-shipping-achieved">
              ✓ ¡Felicidades! Tienes despacho gratis
            </div>
          )}

          <div className="payment-section">
            <h3>Medio de Pago</h3>
            <div className="payment-options">
              {paymentMethods.map(method => (
                <label
                  key={method.code}
                  className={`payment-option ${selectedPayment === method.code ? 'selected' : ''}`}
                >
                  <input
                    type="radio"
                    name="payment"
                    value={method.code}
                    checked={selectedPayment === method.code}
                    onChange={() => onPaymentChange(method.code)}
                  />
                  <div className="payment-info">
                    <div className="payment-name">{method.name}</div>
                    {method.discountPercentage > 0 && (
                      <div className="payment-discount">
                        Ahorra {method.discountPercentage}%
                      </div>
                    )}
                  </div>
                </label>
              ))}
            </div>
          </div>

          {checkoutResult && (
            <div className="summary">
              <h3>Resumen de Compra</h3>
              
              <div className="summary-row">
                <span>Productos ({checkoutResult.totalItems})</span>
                <span>{formatPrice(checkoutResult.subtotal)}</span>
              </div>

              {checkoutResult.totalDiscount > 0 && (
                <div className="summary-row discount-row">
                  <span>Descuento productos</span>
                  <span className="discount-value">-{formatPrice(checkoutResult.totalDiscount)}</span>
                </div>
              )}

              {checkoutResult.discounts?.length > 0 && (
                <div className="discounts-detail">
                  {checkoutResult.discounts.map((discount, idx) => (
                    <div key={idx} className="discount-detail-item">
                      <span>{discount.description}</span>
                    </div>
                  ))}
                </div>
              )}

              <div className="summary-row subtotal-row">
                <span>Subtotal</span>
                <span>{formatPrice(checkoutResult.total)}</span>
              </div>

              <div className="summary-divider"></div>

              <div className="summary-row total-row">
                <span>Total estimado</span>
                <span className="total-amount">{formatPrice(checkoutResult.total)}</span>
              </div>

              {selectedPayment && (
                <div className="payment-summary">
                  <span>Pagando con</span>
                  <span className="payment-method-name">
                    {paymentMethods.find(m => m.code === selectedPayment)?.name}
                  </span>
                </div>
              )}

              {calculateWalmartPoints() > 0 && (
                <div className="rewards-banner">
                  Además, recibirás <strong>{calculateWalmartPoints()} puntos Walmart+</strong>
                </div>
              )}
            </div>
          )}

          <button
            className="checkout-btn"
            onClick={onCheckout}
            disabled={!selectedPayment || cart.length === 0}
          >
            Confirmar Compra
          </button>
        </>
      )}
    </section>
  )
}
