# Solution example for AutoScout tech task'

## Start server
```
sbt run
```

## List all adverts
```
curl localhost:8080
```

## List all sorted by field (ascending order)
```
curl localhost:8080?sortBy=price
```
If the field named by sortBy is not a valid CarAdvert-field, the data will be sorted by id rather than failing the request 

## Add advert
```
curl localhost:8080 -XPOST -H 'Content-type: application/json' -d '{ "title": "A car", "fuel": "Gasoline", "price": 1234 }'
```


## Remove advert
```
curl localhost:8080/1 -XDELETE 
```

## Update advert
```
curl localhost:8080/123 -XPUT -H 'Content-type: application/json' -d '{ "title": "A car", "fuel": "Gasoline", "price": 1234 }'
```
Updating is more of an upsert — To honor the idempotency of PUT, the data is always written to the requested ID, no matter if it already exists or not
   