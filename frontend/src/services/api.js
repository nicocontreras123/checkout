const API_BASE = import.meta.env.VITE_API_URL || '/api'

export const apiService = {
  async getProducts() {
    const response = await fetch(`${API_BASE}/products`)
    return response.json()
  },

  async getPaymentMethods() {
    const response = await fetch(`${API_BASE}/payment-methods`)
    return response.json()
  },

  async getPromotions() {
    const response = await fetch(`${API_BASE}/promotions`)
    return response.json()
  },

  async previewCheckout(items, paymentMethodCode) {
    const response = await fetch(`${API_BASE}/checkout/preview`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        items: items.map(item => ({
          productId: item.product.id,
          quantity: item.quantity
        })),
        paymentMethodCode
      })
    })
    return response.json()
  },

  async confirmCheckout(items, paymentMethodCode) {
    const response = await fetch(`${API_BASE}/checkout/confirm`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        items: items.map(item => ({
          productId: item.product.id,
          quantity: item.quantity
        })),
        paymentMethodCode
      })
    })
    return response.json()
  }
}
