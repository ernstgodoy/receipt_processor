# receipt_processor

## dependencies
- java 23
- spring boot 3.2.1
- maven

## steps to run with docker
1. clone repository
2. run `mvn clean package`
3. run `docker build -t receipt-processor .`
4. run `docker run -p 8080:8080 receipt-processor`

## endpoints available

### GET:

`/receipts/{id}`
#### example:
```
request path: /receipts/someid

response:
    {
        "retailer": "M&M Corner Market",
        "purchaseDate": "2022-03-20",
        "purchaseTime": "14:33",
        "items": [
            {
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            }
        ],
        "total": "9.00"
    }
```

`/receipts/{id}/points`
#### example:
```
request path: /receipts/someid/points

response: 
    {
        "points": 100
    }
```

`/receipts/all`
#### example:
```
response: 
    {
        someid1: {
            "retailer": "M&M Corner Market",
            "purchaseDate": "2022-03-20",
            "purchaseTime": "14:33",
            "items": [
                {"shortDescription": "Gatorade","price": "2.25"},
                {"shortDescription": "Gatorade","price": "2.25"},
                {"shortDescription": "Gatorade","price": "2.25"},
                {"shortDescription": "Gatorade","price": "2.25"}
            ],
            "total": "9.00"
        },
        someid2: {
            {
                "retailer": "Walgreens",
                "purchaseDate": "2022-01-02",
                "purchaseTime": "08:13",
                "total": "2.65",
                "items": [
                    {"shortDescription": "Pepsi - 12-oz", "price": "1.25"},
                    {"shortDescription": "Dasani", "price": "1.40"}
                ]
            }
        }
    }
```

### POST 

`/receipts/process`
#### example:
```
resuest body:
    {
        "retailer": "M&M Corner Market",
        "purchaseDate": "2022-03-20",
        "purchaseTime": "14:33",
        "items": [
            {
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            },{
            "shortDescription": "Gatorade",
            "price": "2.25"
            }
        ],
        "total": "9.00"
    }

response:
    {
        "id": someid
    }
```
