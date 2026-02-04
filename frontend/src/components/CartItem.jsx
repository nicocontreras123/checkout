import './CartItem.css'

export default function CartItem({ item, onUpdateQuantity, onRemove, onSaveForLater, formatPrice }) {
  return (
    <div className="cart-item">
      <img
        className="cart-item-img"
        src={item.product.imageUrl || 'https://via.placeholder.com/50'}
        alt={item.product.name}
        onError={(e) => {
          e.target.src = 'https://via.placeholder.com/50/0071dc/ffffff?text=Item'
        }}
      />
      <div className="cart-item-info">
        <div className="cart-item-name">{item.product.name}</div>
        <div className="cart-item-price">
          {formatPrice(item.product.price)} c/u
        </div>
        <div className="cart-item-actions">
          <button
            className="action-link"
            onClick={() => onRemove(item.product.id)}
          >
            Eliminar
          </button>
          <span className="action-separator">|</span>
          <button
            className="action-link"
            onClick={() => onSaveForLater(item)}
          >
            Guardar para después
          </button>
        </div>
      </div>
      <div className="cart-item-qty">
        <button
          className="qty-btn"
          onClick={() => onUpdateQuantity(item.product.id, -1)}
        >
          −
        </button>
        <span>{item.quantity}</span>
        <button
          className="qty-btn"
          onClick={() => onUpdateQuantity(item.product.id, 1)}
        >
          +
        </button>
      </div>
    </div>
  )
}
