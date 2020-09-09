# Restaurant Vote App

### Restrictions
 * All request except registration and list menus with dishes required authentication
 * Support only basic authentication

## Basic API
### User
#### Registration 
*Request:*

**POST** `/api/1.0/user`
```json
{
    "name" : "NAME",
    "email" : "EMAIL",
    "password" : "PASSWORD"
}
```

 * name - String (1-100), unique username
 * email - String, unique email
 * password - String (8-100)
 
*Response:*
 
204 NO CONTENT

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Ivan",
    "email" : "blablu@gmail.com",
    "password" : "blabla123"
}'
```
#### Profile
*Request:*

**GET** `/api/1.0/user/profile`

*Response:*
 
200 OK

```json
{
    "id": 9,
    "name": "Regina",
    "email": "bla@bla.com",
    "registered": "2020-09-04T15:19:40",
    "todayVote": {
        "id": 20,
        "date": "2020-09-09"
    }
}
```

todayVote can be null

*Example:*

```cmd
curl --location --request GET 'http://localhost:8080/api/1.0/user/profile' \
--header 'Authorization: Basic XXX' \
```
#### Create Vote
*Request:*

**POST** `/api/1.0/user/vote`
```json
{
    "menuId" : 1
}
```

 * menuId - Integer, Id menu
 
*Response:*

403 FORBIDDEN - if vote already exist
200 OK

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/user/vote' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "menuId" : 6
}'
```
#### Delete Vote
*Request:*

**DELETE** `/api/1.0/user/vote`
 
*Response:*
 
403 FORBIDDEN - if time already after 11 AM

204 NO CONTENT

*Example:*

```cmd
curl --location --request DELETE 'http://localhost:8080/api/1.0/user/vote' \
--header 'Authorization: Basic XXX'
```
#### List Of All Menus With Dist By Date
*Request:*

**GET** `/api/1.0/restaurant/menu/by-date/{date}`

 * date - LocalDate, date
 
*Response:*
 
200 OK

```json
[
    {
        "id": 6,
        "date": "2020-09-09",
        "restaurant": {
            "id": 8,
            "name": "Denny"
        },
        "dishes": [
            {
                "id": 4,
                "name": "Beef Burger",
                "price": 15.0
            },
            {
                "id": 3,
                "name": "Chicken Burger",
                "price": 10.0
            }
        ],
        "votes": 1
    },
    {
        "id": 14,
        "date": "2020-09-09",
        "restaurant": {
            "id": 7,
            "name": "Okayama"
        },
        "dishes": [
            {
                "id": 15,
                "name": "Alaska Roll",
                "price": 12.0
            },
            {
                "id": 16,
                "name": "Philadelphia Roll",
                "price": 15.0
            }
        ],
        "votes": 1
    }
]
```

*Example:*

```cmd
curl --location --request GET 'http://localhost:8080/api/1.0/restaurant/menu/by-date/2020-09-09' \
--header 'Authorization: Basic XXX'
```
#### Find All Restaurants
*Request:*

**GET** `/api/1.0/restaurant`
 
*Response:*
 
200 OK

```json
[
    {
        "id": 7,
        "name": "Okayama"
    },
    {
        "id": 8,
        "name": "Denny"
    },
    {
        "id": 19,
        "name": "Barbecue"
    }
]
```

*Example:*

```cmd
curl --location --request GET 'http://localhost:8080/api/1.0/restaurant' \
--header 'Authorization: Basic XXX'
```

### Admin
#### Create Restaurant
*Request:*

**POST** `/api/1.0/restaurant`
```json
{
    "name" : "RESTAURANT_NAME"
}
```

 * restaurant_name - String (1-100), restaurant name
 
*Response:*
 
200 OK

```json
{
    "id": 22,
    "name": "Regina's Barbecue"
}
```

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/restaurant' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Regina'\''s Barbecue"
}'
```
#### Create Menu
*Request:*

**POST** `/api/1.0/restaurant/{restaurant_id}/menu`

 * restaurant_id - Integer, restaurant id
 
```json
{
    "date" : "2020-09-09"
}
```

 * date - LocalDate, date for menu
 
*Response:*
 
400 BAD REQUEST - if menu already exist for this date

200 OK

```json
{
    "id": 23,
    "date": "2020-09-09"
}
```

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/restaurant/22/menu' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date" : "2020-09-09"
}'
```
#### Create Dish
*Request:*

**POST** `/api/1.0/menu/{menu_id}/dish`

 * menu_id - Integer, menu id

```json
{
    "name" : "NAME",
    "price" : 1
}
```

 * name - String (1-100), dish name
 * price - Double, dish price
 
*Response:*
 
200 OK

```json
{
    "id": 24,
    "name": "Noodle",
    "price": 12.0
}
```

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/menu/14/dish' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Noodle",
    "price" : 12
}'
```
#### Add Role To User
*Request:*

**POST** `/api/1.0/user/{user_id}/role`

 * user_id - Integer, user id

```json
{
    "role" : "ROLE_TYPE"
}
```

 * role_type - Enum, role for user
 
*Response:*
 
204 NO CONTENT

*Example:*

```cmd
curl --location --request POST 'http://localhost:8080/api/1.0/user/10/role' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "role" : "ADMIN"
}'
```
#### Delete Role From User
*Request:*

**DELETE** `/api/1.0/user/{user_id}/role`

 * user_id - Integer, user id

```json
{
    "role" : "ROLE_TYPE"
}
```

 * role_type - Enum, role for user
 
*Response:*

204 NO CONTENT

*Example:*

```cmd
curl --location --request DELETE 'http://localhost:8080/api/1.0/user/10/role' \
--header 'Authorization: Basic XXX' \
--header 'Content-Type: application/json' \
--data-raw '{
    "role" : "ADMIN"
}'
```
