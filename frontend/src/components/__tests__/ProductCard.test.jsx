import { describe, it, expect, vi } from 'vitest'
import { render, screen, fireEvent } from '@testing-library/react'
import ProductCard from '../ProductCard'

describe('ProductCard', () => {
  const mockProduct = {
    id: 1,
    name: 'Producto de Prueba',
    price: 15000,
    category: 'Electrónica',
    imageUrl: 'https://example.com/product.jpg'
  }

  const mockFormatPrice = (price) => `$${price.toLocaleString('es-CL')}`
  const mockOnAddToCart = vi.fn()

  it('debe renderizar correctamente la información del producto', () => {
    render(
      <ProductCard
        product={mockProduct}
        onAddToCart={mockOnAddToCart}
        formatPrice={mockFormatPrice}
      />
    )

    expect(screen.getByText('Producto de Prueba')).toBeInTheDocument()
    expect(screen.getByText('$15.000')).toBeInTheDocument()
    expect(screen.getByText('Electrónica')).toBeInTheDocument()
  })

  it('debe llamar onAddToCart cuando se hace clic en el botón Agregar', () => {
    render(
      <ProductCard
        product={mockProduct}
        onAddToCart={mockOnAddToCart}
        formatPrice={mockFormatPrice}
      />
    )

    const addButton = screen.getByText('Agregar')
    fireEvent.click(addButton)

    expect(mockOnAddToCart).toHaveBeenCalledWith(mockProduct)
    expect(mockOnAddToCart).toHaveBeenCalledTimes(1)
  })

  it('debe mostrar la imagen del producto con alt correcto', () => {
    render(
      <ProductCard
        product={mockProduct}
        onAddToCart={mockOnAddToCart}
        formatPrice={mockFormatPrice}
      />
    )

    const image = screen.getByAltText('Producto de Prueba')
    expect(image).toBeInTheDocument()
    expect(image).toHaveAttribute('src', mockProduct.imageUrl)
  })

  it('debe manejar error de imagen y usar placeholder', () => {
    render(
      <ProductCard
        product={mockProduct}
        onAddToCart={mockOnAddToCart}
        formatPrice={mockFormatPrice}
      />
    )

    const image = screen.getByAltText('Producto de Prueba')
    fireEvent.error(image)

    // Verifica que el src cambió después del error
    expect(image.src).toContain('via.placeholder.com')
  })
})
