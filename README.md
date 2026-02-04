# Walmart Checkout Service

Sistema de checkout para retail con promociones y medios de pago.

## Arquitectura Hexagonal (Ports & Adapters)

```
                    ┌─────────────────────────────────────┐
                    │           INFRASTRUCTURE            │
                    │  ┌─────────────┐ ┌───────────────┐  │
   HTTP Request ───►│  │ Controllers │ │  JPA Adapters │  │◄─── Database
                    │  │ (Adapter In)│ │ (Adapter Out) │  │
                    │  └──────┬──────┘ └───────▲───────┘  │
                    └─────────┼────────────────┼──────────┘
                              │ Port In        │ Port Out
                    ┌─────────▼────────────────┼──────────┐
                    │              DOMAIN                  │
                    │  ┌─────────────────────────────────┐ │
                    │  │   Services (Use Case Impl)      │ │
                    │  │   - CheckoutService             │ │
                    │  │   - PromotionDiscountStrategy   │ │
                    │  │   - PaymentMethodDiscountStrategy│ │
                    │  └─────────────────────────────────┘ │
                    │  ┌─────────────────────────────────┐ │
                    │  │   Model (Pure Domain Objects)   │ │
                    │  │   Cart, Product, Promotion...   │ │
                    │  └─────────────────────────────────┘ │
                    └──────────────────────────────────────┘
```

### Estructura del Proyecto

```
backend/src/main/java/com/walmart/checkout/
├── domain/                          # Núcleo - Sin dependencias externas
│   ├── model/                       # Entidades puras (POJO)
│   │   ├── Product.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Promotion.java
│   │   ├── PaymentMethod.java
│   │   ├── AppliedDiscount.java
│   │   └── CheckoutResult.java
│   ├── port/
│   │   ├── in/                      # Puertos de entrada (casos de uso)
│   │   │   ├── CheckoutUseCase.java
│   │   │   ├── ProductQueryUseCase.java
│   │   │   ├── PaymentMethodQueryUseCase.java
│   │   │   └── PromotionQueryUseCase.java
│   │   └── out/                     # Puertos de salida (repositorios)
│   │       ├── ProductRepository.java
│   │       ├── PaymentMethodRepository.java
│   │       └── PromotionRepository.java
│   └── service/                     # Servicios de dominio
│       ├── CheckoutService.java
│       ├── DiscountStrategy.java          # Strategy Pattern
│       ├── PromotionDiscountStrategy.java
│       ├── PaymentMethodDiscountStrategy.java
│       ├── ProductQueryService.java
│       ├── PaymentMethodQueryService.java
│       └── PromotionQueryService.java
│
└── infrastructure/                  # Adaptadores técnicos
    ├── adapter/
    │   ├── in/rest/                 # Adaptadores de entrada (REST)
    │   │   ├── CheckoutController.java
    │   │   ├── ProductController.java
    │   │   ├── PaymentMethodController.java
    │   │   ├── PromotionController.java
    │   │   └── dto/
    │   │       ├── CheckoutRequest.java
    │   │       └── CartItemRequest.java
    │   └── out/persistence/         # Adaptadores de salida (JPA)
    │       ├── entity/              # Entidades JPA
    │       ├── repository/          # Spring Data JPA
    │       ├── mapper/              # Domain <-> Entity
    │       ├── ProductRepositoryAdapter.java
    │       ├── PaymentMethodRepositoryAdapter.java
    │       └── PromotionRepositoryAdapter.java
    └── config/
        ├── BeanConfiguration.java   # Wiring de beans
        └── GlobalExceptionHandler.java
```

### Patrones de Diseño

| Patrón | Implementación | Beneficio |
|--------|---------------|-----------|
| **Hexagonal Architecture** | Separación domain/infrastructure | El dominio no depende de frameworks |
| **Ports & Adapters** | Interfaces en domain/port | Fácil cambiar implementaciones |
| **Strategy** | `DiscountStrategy` | Extensible para nuevos descuentos |
| **Dependency Inversion** | Puertos definidos por dominio | Inversión de dependencias |

### Trade-offs

1. **Más archivos vs claridad**: La arquitectura hexagonal requiere más clases pero las responsabilidades están claramente separadas.

2. **Mappers manuales**: Se usan mappers manuales en lugar de MapStruct para mantener el dominio libre de dependencias.

3. **Sin Lombok en Domain**: Las entidades de dominio usan getters/setters explícitos para ser POJOs puros.

## Requisitos

**Opción A - Con Docker (Recomendado):**
- Docker y Docker Compose

**Opción B - Sin Docker:**
- Java 17+
- Maven 3.8+
- Node.js 18+

## Ejecución Local

### Opción A: Docker (Recomendado)

```bash
docker-compose up --build
```

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`

### Opción B: Sin Docker

**Backend:**

```bash
cd backend
mvn spring-boot:run
```

El API estará disponible en `http://localhost:8080`

**Frontend:**

```bash
cd frontend
npm install
npm run dev
```

La UI estará disponible en `http://localhost:3000`

## API Endpoints

### Productos
- `GET /api/products` - Lista todos los productos
- `GET /api/products/{id}` - Obtiene un producto
- `GET /api/products/category/{category}` - Productos por categoría

### Métodos de Pago
- `GET /api/payment-methods` - Lista métodos de pago activos

### Promociones
- `GET /api/promotions` - Lista promociones activas

### Checkout
- `POST /api/checkout/preview` - Calcula preview con descuentos
- `POST /api/checkout/confirm` - Confirma la compra

### Ejemplo de Request

```json
POST /api/checkout/preview
{
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 5, "quantity": 1 }
  ],
  "paymentMethodCode": "DEBIT"
}
```

### Ejemplo de Response

```json
{
  "items": [...],
  "totalItems": 3,
  "subtotal": 14.44,
  "discounts": [
    {
      "type": "PROMOTION",
      "description": "10% Off Dairy: 10% off",
      "amount": 0.70
    },
    {
      "type": "PAYMENT_METHOD",
      "description": "Debit Card: 10% off",
      "amount": 1.44
    }
  ],
  "totalDiscount": 2.14,
  "total": 12.30,
  "paymentMethod": "Debit Card",
  "status": "PREVIEW"
}
```

## Base de Datos

### Desarrollo Local (H2)

Consola: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:checkoutdb`
- User: `sa`

### Producción (Railway/PostgreSQL)

```bash
DATABASE_URL=jdbc:postgresql://host:port/database?user=xxx&password=xxx
SPRING_PROFILES_ACTIVE=prod
```

## Tests

```bash
cd backend
mvn test
```

## Extensibilidad

### Agregar nuevo tipo de descuento

```java
// 1. Crear nueva estrategia en domain/service
public class VolumeDiscountStrategy implements DiscountStrategy {
    @Override
    public List<AppliedDiscount> apply(Cart cart) {
        // Lógica de descuento por volumen
    }

    @Override
    public int getOrder() {
        return 3; // Se aplica después de promociones y pago
    }
}

// 2. Registrar en BeanConfiguration
@Bean
public DiscountStrategy volumeDiscountStrategy() {
    return new VolumeDiscountStrategy();
}
```

### Agregar nuevo medio de pago

```sql
INSERT INTO payment_methods (code, name, discount_percentage, description, active)
VALUES ('PAYPAL', 'PayPal', 3.00, '3% cashback', true);
```

## Datos de Prueba (Chile - CLP)

**Productos:**
- Leche Colun, Pan Marraqueta, Huevos Santa Marta
- Palta Hass, Coca-Cola, Papas Lays
- Arroz Tucapel, Aceite Maravilla, Café Nescafé
- Vino Casillero del Diablo

**Métodos de Pago:**
- Tarjeta de Débito (10% dcto)
- Tarjeta Lider MasterCard (5% dcto)
- Transferencia Bancaria (3% dcto)
- Tarjeta de Crédito, Efectivo

**Promociones:**
- 15% Dcto en Lácteos
- 10% Dcto en Snacks
- 20% Dcto en Bebidas
- $5.000 Dcto en compras sobre $30.000
