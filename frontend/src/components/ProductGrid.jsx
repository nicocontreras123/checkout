import ProductCard from './ProductCard'
import './ProductGrid.css'

export default function ProductGrid({ products, onAddToCart, formatPrice }) {
  return (
    <section className="products-section">
      <h2>Productos</h2>
      <div className="products-grid">
        {products.map(product => (
          <ProductCard
            key={product.id}
            product={product}
            onAddToCart={onAddToCart}
            formatPrice={formatPrice}
          />
        ))}
      </div>
    </section>
  )
}
