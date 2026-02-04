import './PromotionsBanner.css'

export default function PromotionsBanner({ promotions }) {
  if (promotions.length === 0) return null

  return (
    <div className="promotions-banner">
      Promociones Activas: {promotions.map(p => p.name).join(' | ')}
    </div>
  )
}
