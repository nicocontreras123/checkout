import { describe, it, expect, vi } from 'vitest'
import { render, screen, fireEvent } from '@testing-library/react'
import CartItem from '../CartItem'

describe('CartItem', () => {
  const mockItem = {
    product: {
      id: 1,
      name: 'Laptop HP',
      price: 500000,
      imageUrl: 'https://example.com/laptop.jpg'
    },
    quantity: 2
  }

  const mockFormatPrice = (price) => `$${price.toLocaleString('es-CL')}`
  const mockOnUpdateQuantity = vi.fn()
  const mockOnRemove = vi.fn()
  const mockOnSaveForLater = vi.fn()

  it('debe renderizar la información del item correctamente', () => {
    render(
      <CartItem
        item={mockItem}
        onUpdateQuantity={mockOnUpdateQuantity}
        onRemove={mockOnRemove}
        onSaveForLater={mockOnSaveForLater}
        formatPrice={mockFormatPrice}
      />
    )

    expect(screen.getByText('Laptop HP')).toBeInTheDocument()
    expect(screen.getByText('$500.000 c/u')).toBeInTheDocument()
    expect(screen.getByText('2')).toBeInTheDocument()
  })

  it('debe incrementar cantidad cuando se presiona +', () => {
    render(
      <CartItem
        item={mockItem}
        onUpdateQuantity={mockOnUpdateQuantity}
        onRemove={mockOnRemove}
        onSaveForLater={mockOnSaveForLater}
        formatPrice={mockFormatPrice}
      />
    )

    const incrementButton = screen.getByText('+')
    fireEvent.click(incrementButton)

    expect(mockOnUpdateQuantity).toHaveBeenCalledWith(1, 1)
  })

  it('debe decrementar cantidad cuando se presiona -', () => {
    render(
      <CartItem
        item={mockItem}
        onUpdateQuantity={mockOnUpdateQuantity}
        onRemove={mockOnRemove}
        onSaveForLater={mockOnSaveForLater}
        formatPrice={mockFormatPrice}
      />
    )

    const decrementButton = screen.getByText('−')
    fireEvent.click(decrementButton)

    expect(mockOnUpdateQuantity).toHaveBeenCalledWith(1, -1)
  })

  it('debe llamar onRemove cuando se hace clic en Eliminar', () => {
    render(
      <CartItem
        item={mockItem}
        onUpdateQuantity={mockOnUpdateQuantity}
        onRemove={mockOnRemove}
        onSaveForLater={mockOnSaveForLater}
        formatPrice={mockFormatPrice}
      />
    )

    const removeButton = screen.getByText('Eliminar')
    fireEvent.click(removeButton)

    expect(mockOnRemove).toHaveBeenCalledWith(1)
  })

  it('debe llamar onSaveForLater cuando se hace clic en Guardar para después', () => {
    render(
      <CartItem
        item={mockItem}
        onUpdateQuantity={mockOnUpdateQuantity}
        onRemove={mockOnRemove}
        onSaveForLater={mockOnSaveForLater}
        formatPrice={mockFormatPrice}
      />
    )

    const saveButton = screen.getByText('Guardar para después')
    fireEvent.click(saveButton)

    expect(mockOnSaveForLater).toHaveBeenCalledWith(mockItem)
  })
})
