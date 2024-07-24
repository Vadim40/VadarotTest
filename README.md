# Created by Vadim Grishin


## Endpoints

### 1. Load Currency Rates

#### URL
`POST /api/cur-rate/load`

#### Description
Loads currency rates for the specified date from an 
`https://www.nbrb.by/apihelp/exrates`
. If the data for the specified date is already loaded, it will not be loaded again.

#### Request Body RequestDate:
```json
{
  "date": "YYYY-MM-DD"
}
```
-  date  : The date for which to load currency rates (in YYYY-MM-DD format).  
     Validation:  
   Must be a past or present date (not a future date).  

#### Responses
200 OK:

```json
"Data successfully loaded for date: YYYY-MM-DD"
```  
The data has been successfully loaded for the specified date.

#### 400 Bad Request:

```json
{
  "errors": [
    {
      "field": "date",
      "message": "must be a valid date"
    }
  ]
}
``` 
Validation error. The date parameter is missing or has an incorrect format.

#### 404 Not Found:

```json
"Currency rate not found for date: YYYY-MM-DD"
```
Currency rates for the specified date were not found.

### 2. Get Currency Rate
#### URL
`GET /api/cur-rate/rate`

#### Description
Returns the currency rate for the specified currency code and date from application database .

#### Request Body RequestCodeDate:
```json
{
  "code": "USD",
  "date": "YYYY-MM-DD"
}
```
- code: The currency code (e.g., USD, EUR).  
Validation:  
Must not be null.  
Must be exactly 3 characters long. 
  
- date: The date for which to get the currency rate (in YYYY-MM-DD format).
  Validation:  
Must be a past or present date (not a future date).

#### Responses  
####  200 OK: 

```json
{
  "currencyCode": "USD",
  "date": "YYYY-MM-DD",
  "rate": 2.2
}
```
Successful response with the currency rate.

#### 400 Bad Request: 

```json
{
  "errors": [
    {
      "field": "code",
      "message": "must not be empty"
    },
    {
      "field": "date",
      "message": "must be a valid date"
    }
  ]
}
``` 
Validation error. One or both parameters code and date are missing or have an incorrect format.

### 404 Not Found: 

``` json
"Currency rate not found for code: USD and date: YYYY-MM-DD"
``` 
The currency rate for the specified currency code and date was not found.

