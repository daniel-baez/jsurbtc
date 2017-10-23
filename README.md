# this is a work in progress

## Features

- Idiomatic Java API
- Java 8 Compatible
- Lazy loading for paginated results
- Immutable, Thread-safe, Jackson Ready, Serializable objects
- Error handling

## Usage

```java
import cl.daplay.jsurbtc.JSurbtc;

final JSurbtc surbtc = new JSurbtc(apiKey, apiSecret);

// create order
final Order order = surbtc.createOrder(MarketID.BTC_CLP, OrderType.BID, OrderPriceType.LIMIT, BigDecimal.ONE, BigDecimal.ONE);

// cancel order
surbtc.cancelOrder(order.getId());

// get all orders
final List<Order> orders = surbtc.getOrders(MarketID.BTC_CLP);

// GET /markets
final List<Market> markets = surbtc.getMarkets();
```

## Installation

```xml
<dependency>
   <groupId>cl.daplay</groupId>
   <artifactId>jsurbtc</artifactId>
   <version>1.0.0</version>
</dependency>
```

```groovy
compile group: 'cl.daplay', name: 'jsurbtc', version: '1.0.0'
```
   
# Documentation

Please see [Javadoc](http://docs.daplay.cl/jsurbtc/cl/daplay/jsurbtc/JSurbtc.html)

# On Test

To run integration tests you'll need to have 

# Errors

- 400 Bad Request – La solicitud contiene sintaxis errónea y no debería repetirse
- 401 Unauthorized – Tu API Key es inválida
- 403 Forbidden – La solicitud fue legal, pero no cuenta con los privilegios para hacerla
- 404 Not Found – Recurso no encontrado
- 405 Method Not Allowed – Has intentado acceder a un endpoint con el metodo incorrecto (GET/POST)
- 406 Not Acceptable – Has solicitado un formato no soportado por el servidor (Header “Accept”)
- 410 Gone – El recurso seleccionado fue removido del servidor
- 429 Too Many Requests – Has superado el límite de solicitudes por segundo
- `422 Unprocessable entity` Request in somehow invalid
- 500 Internal Server Error – Ha habido un problema con nuestro servidor, intentalo más tarde
- 503 Service Unavailable – Estamos temporalmente indisponibles por mantenimiento, intentalo más tarde
