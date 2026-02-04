import './ProductCard.css'

export default function ProductCard({ product, onAddToCart, formatPrice }) {
  return (
    <div className="product-card">
      <img
        src={product.imageUrl || `https://via.placeholder.com/150?text=${encodeURIComponent(product.name)}`}
        alt={product.name}
        onError={(e) => {
          e.target.src = `https://via.placeholder.com/150/0071dc/ffffff?text=${encodeURIComponent(product.name.substring(0, 10))}`
        }}
      />
      <h3>{product.name}</h3>
      <div className="product-price">{formatPrice(product.price)}</div>
      <div className="product-category">{product.category}</div>
      <button className="add-btn" onClick={() => onAddToCart(product)}>
        Agregar
      </button>
    </div>
  )
}
