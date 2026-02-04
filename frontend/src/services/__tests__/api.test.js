import { describe, it, expect, vi, beforeEach } from 'vitest'
import { apiService } from '../api'

// Mock de fetch global
global.fetch = vi.fn()

describe('apiService', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  describe('getProducts', () => {
    it('debe obtener productos correctamente', async () => {
      const mockProducts = [
        { id: 1, name: 'Producto 1', price: 10000 },
        { id: 2, name: 'Producto 2', price: 20000 }
      ]

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockProducts
      })

      const result = await apiService.getProducts()

      expect(global.fetch).toHaveBeenCalledWith('/api/products')
      expect(result).toEqual(mockProducts)
    })

    it('debe lanzar error si falla la petición', async () => {
      global.fetch.mockRejectedValueOnce(new Error('Network error'))

      await expect(apiService.getProducts()).rejects.toThrow('Network error')
    })
  })

  describe('getPaymentMethods', () => {
    it('debe obtener métodos de pago correctamente', async () => {
      const mockPaymentMethods = [
        { code: 'CREDIT', name: 'Tarjeta de Crédito' }
      ]

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockPaymentMethods
      })

      const result = await apiService.getPaymentMethods()

      expect(global.fetch).toHaveBeenCalledWith('/api/payment-methods')
      expect(result).toEqual(mockPaymentMethods)
    })
  })

  describe('previewCheckout', () => {
    it('debe calcular preview correctamente', async () => {
      const mockCart = [
        { product: { id: 1 }, quantity: 2 }
      ]
      const mockResult = {
        subtotal: 20000,
        total: 20000,
        totalItems: 2
      }

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResult
      })

      const result = await apiService.previewCheckout(mockCart, 'CREDIT')

      expect(global.fetch).toHaveBeenCalledWith('/api/checkout/preview', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          items: [{ productId: 1, quantity: 2 }],
          paymentMethodCode: 'CREDIT'
        })
      })
      expect(result).toEqual(mockResult)
    })
  })

  describe('confirmCheckout', () => {
    it('debe confirmar checkout correctamente', async () => {
      const mockCart = [
        { product: { id: 1 }, quantity: 1 }
      ]
      const mockResult = {
        confirmationCode: 'ABC123',
        total: 10000
      }

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResult
      })

      const result = await apiService.confirmCheckout(mockCart, 'DEBIT')

      expect(global.fetch).toHaveBeenCalledWith('/api/checkout/confirm', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          items: [{ productId: 1, quantity: 1 }],
          paymentMethodCode: 'DEBIT'
        })
      })
      expect(result).toEqual(mockResult)
    })
  })
})
