# Queue producer

## V 1.x - Simple producer
Very simple producer with two exchangers, two queues;
Problem: Waste of beans to create resources even when it already exists;

### How to put messages in queues
#### String Message in first queue
```curl
 curl -X POST "http://localhost:8080/exchanges/DIRECT-EXCHANGE-BASIC/TO-FIRST-QUEUE" -H "accept: */*" -H "Content-Type: application/json" -d 'any text in here'
```

#### Person in json queue
```curl
 curl -X POST "http://localhost:8080/exchanges/json/DIRECT-EXCHANGE-BASIC/TO-JSON-QUEUE" -H "accept: */*" -H "Content-Type: application/json" -d '{"name":"Iundarigun","collageCompletedYear":2005,"bornAt":"1980-08-07","active":true}'
```
## V 2.x - Advanced producer
Create exchanges and queues with rabbitAdmin and add DLQ queues

### How to put messages in queues
```curl
 curl -X POST "http://localhost:8080/exchanges/persons/:exchangeName/:routingKey" -H "accept: */*" -H "Content-Type: application/json" -d '{'name': 'Iundarigun', 'collageCompletedYear': 2005, 'bornAt': '1980-08-07', 'active': true}'
```

Example:
```curl
curl --location --request POST 'localhost:8080/exchanges/persons/DIRECT-EXCHANGE-ADVANCED/TO-FIRST-QUEUE' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Iundarigun",
    "collageCompletedYear": 2005,
    "bornAt": "1980-08-07",
    "active": true
}'
```
