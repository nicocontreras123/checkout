import { useState, useEffect } from 'react'
import { apiService } from './services/api'
import Header from './components/Header'
import PromotionsBanner from './components/PromotionsBanner'
import ProductGrid from './components/ProductGrid'
import Cart from './components/Cart'
import LoadingSpinner from './components/LoadingSpinner'
import { CheckoutLoading, Voucher } from './components/CheckoutModal'

function App() {
  const [products, setProducts] = useState([])
  const [paymentMethods, setPaymentMethods] = useState([])
  const [promotions, setPromotions] = useState([])
  const [cart, setCart] = useState([])
  const [selectedPayment, setSelectedPayment] = useState('')
  const [checkoutResult, setCheckoutResult] = useState(null)
  const [confirmation, setConfirmation] = useState(null)
  const [loading, setLoading] = useState(true)
  const [checkoutLoading, setCheckoutLoading] = useState(false)
  const [savedItems, setSavedItems] = useState([])
  const FREE_SHIPPING_THRESHOLD = 30000

  useEffect(() => {
    Promise.all([
      apiService.getProducts(),
      apiService.getPaymentMethods(),
      apiService.getPromotions()
    ]).then(([prods, payments, promos]) => {
      setProducts(prods)
      setPaymentMethods(payments)
      setPromotions(promos)
      if (payments.length > 0) {
        setSelectedPayment(payments[0].code)
      }
      setLoading(false)
    }).catch(err => {
      console.error('Error loading data:', err)
      setLoading(false)
    })
  }, [])

  useEffect(() => {
    if (cart.length > 0 && selectedPayment) {
      calculatePreview()
    } else {
      setCheckoutResult(null)
    }
  }, [cart, selectedPayment])

  const calculatePreview = async () => {
    try {
      const result = await apiService.previewCheckout(cart, selectedPayment)
      setCheckoutResult(result)
    } catch (err) {
      console.error('Error calculating preview:', err)
    }
  }

  const addToCart = (product) => {
    setCart(prev => {
      const existing = prev.find(item => item.product.id === product.id)
      if (existing) {
        return prev.map(item =>
          item.product.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      }
      return [...prev, { product, quantity: 1 }]
    })
  }

  const updateQuantity = (productId, delta) => {
    setCart(prev => {
      return prev
        .map(item => {
          if (item.product.id === productId) {
            const newQty = item.quantity + delta
            return newQty > 0 ? { ...item, quantity: newQty } : null
          }
          return item
        })
        .filter(Boolean)
    })
  }

  const removeFromCart = (productId) => {
    setCart(prev => prev.filter(item => item.product.id !== productId))
  }

  const saveForLater = (item) => {
    setSavedItems(prev => [...prev, item])
    removeFromCart(item.product.id)
  }

  const moveToCart = (item) => {
    setCart(prev => [...prev, item])
    setSavedItems(prev => prev.filter(saved => saved.product.id !== item.product.id))
  }

  const calculateFreeShippingProgress = () => {
    if (!checkoutResult) return 0
    return Math.min((checkoutResult.subtotal / FREE_SHIPPING_THRESHOLD) * 100, 100)
  }

  const getRemainingForFreeShipping = () => {
    if (!checkoutResult) return FREE_SHIPPING_THRESHOLD
    return Math.max(FREE_SHIPPING_THRESHOLD - checkoutResult.subtotal, 0)
  }

  const calculateWalmartPoints = () => {
    if (!checkoutResult) return 0
    return Math.floor(checkoutResult.total / 100)
  }

  const handleCheckout = async () => {
    setCheckoutLoading(true)
    try {
      const result = await apiService.confirmCheckout(cart, selectedPayment)
      setTimeout(() => {
        setCheckoutLoading(false)
        setConfirmation(result)
      }, 1500)
    } catch (err) {
      console.error('Error confirming checkout:', err)
      setCheckoutLoading(false)
    }
  }

  const closeConfirmation = () => {
    setConfirmation(null)
    setCart([])
    setCheckoutResult(null)
  }

  const formatPrice = (price) => {
    return new Intl.NumberFormat('es-CL', {
      style: 'currency',
      currency: 'CLP',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(price)
  }

  if (loading) {
    return <LoadingSpinner />
  }

  return (
    <>
      <Header />
      <PromotionsBanner promotions={promotions} />

      <main className="main-container">
        <ProductGrid
          products={products}
          onAddToCart={addToCart}
          formatPrice={formatPrice}
        />

        <Cart
          cart={cart}
          savedItems={savedItems}
          paymentMethods={paymentMethods}
          selectedPayment={selectedPayment}
          checkoutResult={checkoutResult}
          onUpdateQuantity={updateQuantity}
          onRemoveFromCart={removeFromCart}
          onSaveForLater={saveForLater}
          onMoveToCart={moveToCart}
          onPaymentChange={setSelectedPayment}
          onCheckout={handleCheckout}
          formatPrice={formatPrice}
          calculateFreeShippingProgress={calculateFreeShippingProgress}
          getRemainingForFreeShipping={getRemainingForFreeShipping}
          calculateWalmartPoints={calculateWalmartPoints}
        />
      </main>

      {checkoutLoading && <CheckoutLoading />}

      {confirmation && (
        <Voucher
          confirmation={confirmation}
          cart={cart}
          onClose={closeConfirmation}
          formatPrice={formatPrice}
          calculateWalmartPoints={calculateWalmartPoints}
        />
      )}
    </>
  )
}

export default App
