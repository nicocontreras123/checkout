const API_BASE = import.meta.env.VITE_API_URL || '/api'

// Log para debug en Railway
console.log('🔧 API Configuration:')
console.log('  VITE_API_URL:', import.meta.env.VITE_API_URL)
console.log('  API_BASE:', API_BASE)
console.log('  Mode:', import.meta.env.MODE)

export const apiService = {
  async getProducts() {
    const url = `${API_BASE}/products`
    console.log('📡 Fetching:', url)
    const response = await fetch(url)
    return response.json()
  },

  async getPaymentMethods() {
    const url = `${API_BASE}/payment-methods`
    console.log('📡 Fetching:', url)
    const response = await fetch(url)
    return response.json()
  },

  async getPromotions() {
    const url = `${API_BASE}/promotions`
    console.log('📡 Fetching:', url)
    const response = await fetch(url)
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
