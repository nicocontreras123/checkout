import './LoadingSpinner.css'

export default function LoadingSpinner() {
  return (
    <div className="loading" role="status" aria-label="Cargando productos">
      <div className="spinner"></div>
    </div>
  )
}
