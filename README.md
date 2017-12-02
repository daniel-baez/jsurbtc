# This is a work in progress

## Features

- Idiomatic Java API
- Java 8 Compatible
- Lazy loading for paginated results
- Immutable, Thread-safe, Serializable objects
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
   <version>2.0.0</version>
</dependency>
```

```groovy
compile group: 'cl.daplay', name: 'jsurbtc', version: '2.0.0'
```
   
# Documentation

- [Javadoc](http://docs.daplay.cl/jsurbtc/cl/daplay/jsurbtc/JSurbtc.html)
- [Official API Docs](http://api.surbtc.com/)

# Testing

To run integration tests (those ended in _IT.java), you'll need to add the following settings to your $HOME/.gradle/gradle.properties

- `jsurbtc.key`
- `jsurbtc.secret`
- `jsurbtc.proxy.host` OPTIONAL
- `jsurbtc.proxy.port` OPTIONAL

# Todo

- Remove jackson dependency 
- Extract public API

# Errors

- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 405 Method Not Allowed
- 406 Not Acceptable
- 410 Gone
- 429 Too Many Requests
- 422 Unprocessable entity
- 500 Internal Server Error
- 503 Service Unavailable
