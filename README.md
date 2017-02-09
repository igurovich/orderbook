# GDAX Order Buy/Sell API


### Prerequisites

Java 1.8 needs to be installed. Might work with Java 1.7, but I haven't tested.


### Installing

Download orderbook. Then:

```
cd orderbook
./gradlew clean build
```

## Running the tests

Run

```
./gradlew test

```
### Testing with curl

Start application in one terminal window:

```
./gradlew bootRun
```

Run the following curl commands in another window:
```
curl -H "Content-Type: application/json" -X POST -d '{"action": "sell", 
 "base_currency": "BTC",
 "quote_currency": "USD",
 "amount": "1.00000000"
}' http://localhost:8080/order

```

```
curl -H "Content-Type: application/json" -X POST -d '{"action": "buy", 
 "base_currency": "BTC",
 "quote_currency": "USD",
 "amount": "10.00000000"
}' http://localhost:8080/order

```

```
curl -H "Content-Type: application/json" -X POST -d '{"action": "buy", 
 "base_currency": "USD",
 "quote_currency": "BTC",
 "amount": "1000.0"
}' http://localhost:8080/order

```

## Built With

* [Spring Boot](http://www.spring.io/) - The web framework 


## Author

* **Igor Gurovich** 

