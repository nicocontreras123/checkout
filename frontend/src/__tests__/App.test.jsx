import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import App from '../App'
import { apiService } from '../services/api'

// Mock del servicio API
vi.mock('../services/api', () => ({
  apiService: {
    getProducts: vi.fn(),
    getPaymentMethods: vi.fn(),
    getPromotions: vi.fn(),
    previewCheckout: vi.fn(),
    confirmCheckout: vi.fn()
  }
}))

describe('App', () => {
  const mockProducts = [
    { id: 1, name: 'Producto 1', price: 10000, category: 'Cat1' },
    { id: 2, name: 'Producto 2', price: 20000, category: 'Cat2' }
  ]

  const mockPaymentMethods = [
    { code: 'CREDIT', name: 'Tarjeta de Crédito', discountPercentage: 0 },
    { code: 'DEBIT', name: 'Tarjeta de Débito', discountPercentage: 5 }
  ]

  const mockPromotions = [
    { id: 1, name: 'Promo 1' }
  ]

  beforeEach(() => {
    // Reset de todos los mocks antes de cada test
    vi.clearAllMocks()

    // Configurar respuestas por defecto
    apiService.getProducts.mockResolvedValue(mockProducts)
    apiService.getPaymentMethods.mockResolvedValue(mockPaymentMethods)
    apiService.getPromotions.mockResolvedValue(mockPromotions)
  })

  it('debe mostrar el spinner de carga inicialmente', () => {
    render(<App />)
    expect(screen.getByRole('status')).toBeInTheDocument()
  })

  it('debe cargar y mostrar los datos correctamente', async () => {
    render(<App />)

    await waitFor(() => {
      expect(screen.getByText('Walmart Chile')).toBeInTheDocument()
    })

    expect(apiService.getProducts).toHaveBeenCalledTimes(1)
    expect(apiService.getPaymentMethods).toHaveBeenCalledTimes(1)
    expect(apiService.getPromotions).toHaveBeenCalledTimes(1)
  })

  it('debe mostrar el banner de promociones cuando hay promociones', async () => {
    render(<App />)

    await waitFor(() => {
      expect(screen.getByText(/Promociones Activas/)).toBeInTheDocument()
    })
  })

  it('debe mostrar el carro vacío inicialmente', async () => {
    render(<App />)

    await waitFor(() => {
      expect(screen.getByText('Tu carro está vacío')).toBeInTheDocument()
    })
  })

  it('debe manejar errores de carga de datos', async () => {
    const consoleErrorSpy = vi.spyOn(console, 'error').mockImplementation(() => {})
    
    apiService.getProducts.mockRejectedValue(new Error('Network error'))

    render(<App />)

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalled()
    })

    consoleErrorSpy.mockRestore()
  })
})
