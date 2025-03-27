## Task 3
POST http://localhost:7070/api/trips/populate

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:15:23 GMT
Content-Type: text/plain
Content-Length: 0

<Response body is empty>

GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:15:59 GMT
Content-Type: application/json
Content-Length: 853

[
{
"id": 1,
"name": "Sunny Beach",
"category": "BEACH",
"price": 45.0,
"starttime": [
2024,
11,
5
],
"endtime": [
2024,
11,
5
],
"startposition": "Golden Sands"
},
{
"id": 2,
"name": "Downtown Exploration",
"category": "CITY",
"price": 30.0,
"starttime": [
2024,
11,
6
],
"endtime": [
2024,
11,
6
],
"startposition": "City Center"
},
{
"id": 3,
"name": "Forest Trekking",
"category": "FOREST",
"price": 35.0,
"starttime": [
2024,
11,
7
],
"endtime": [
2024,
11,
7
],
"startposition": "Evergreen Woods"
},
{
"id": 4,
"name": "Lake Camp",
"category": "LAKE",
"price": 40.0,
"starttime": [
2024,
11,
8
],
"endtime": [
2024,
11,
8
],
"startposition": "Crystal Lake"
},
{
"id": 5,
"name": "Sea Cruise",
"category": "SEA",
"price": 60.0,
"starttime": [
2024,
11,
9
],
"endtime": [
2024,
11,
9
],
"startposition": "Blue Horizon Port"
},
{
"id": 6,
"name": "Snowy Adventure",
"category": "SNOW",
"price": 70.0,
"starttime": [
2024,
11,
10
],
"endtime": [
2024,
11,
10
],
"startposition": "White Peaks"
}
]

GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:09 GMT
Content-Type: application/json
Content-Length: 271

{
"trip": {
"id": 1,
"name": "Sunny Beach",
"category": "BEACH",
"price": 45.0,
"starttime": [
2024,
11,
5
],
"endtime": [
2024,
11,
5
],
"startposition": "Golden Sands"
},
"guide": {
"id": 1,
"firstname": "John",
"lastname": "Doe",
"email": "john.doe@example.com",
"phone": 12345678,
"yearsOfExperience": 10
}
}

GET http://localhost:7070/api/trips/guide/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:19 GMT
Content-Type: application/json
Content-Length: 286

[
{
"id": 1,
"name": "Sunny Beach",
"category": "BEACH",
"price": 45.0,
"starttime": [
2024,
11,
5
],
"endtime": [
2024,
11,
5
],
"startposition": "Golden Sands"
},
{
"id": 2,
"name": "Downtown Exploration",
"category": "CITY",
"price": 30.0,
"starttime": [
2024,
11,
6
],
"endtime": [
2024,
11,
6
],
"startposition": "City Center"
}
]

POST http://localhost:7070/api/trips

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 10:16:32 GMT
Content-Type: application/json
Content-Length: 139

{
"id": 7,
"name": "Lake Lake",
"category": "LAKE",
"price": 25.0,
"starttime": [
2023,
12,
15
],
"endtime": [
2023,
12,
15
],
"startposition": "Roskildevej 32"
}

PUT http://localhost:7070/api/trips/7

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:40 GMT
Content-Type: application/json
Content-Length: 142

{
"id": 7,
"name": "Beach Beach",
"category": "BEACH",
"price": 25.0,
"starttime": [
2023,
12,
15
],
"endtime": [
2023,
12,
15
],
"startposition": "Roskildevej 32"
}

DELETE http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:49 GMT
Content-Type: application/json
Content-Length: 138

{
"id": 1,
"name": "Sunny Beach",
"category": "BEACH",
"price": 45.0,
"starttime": [
2024,
11,
5
],
"endtime": [
2024,
11,
5
],
"startposition": "Golden Sands"
}

PUT http://localhost:7070/api/trips/add/7/guides/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:16:59 GMT
Content-Type: application/json
Content-Length: 142

{
"id": 7,
"name": "Beach Beach",
"category": "BEACH",
"price": 25.0,
"starttime": [
2023,
12,
15
],
"endtime": [
2023,
12,
15
],
"startposition": "Roskildevej 32"
}

GET http://localhost:7070/api/trips/guide/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:17:08 GMT
Content-Type: application/json
Content-Length: 290

[
{
"id": 2,
"name": "Downtown Exploration",
"category": "CITY",
"price": 30.0,
"starttime": [
2024,
11,
6
],
"endtime": [
2024,
11,
6
],
"startposition": "City Center"
},
{
"id": 7,
"name": "Beach Beach",
"category": "BEACH",
"price": 25.0,
"starttime": [
2023,
12,
15
],
"endtime": [
2023,
12,
15
],
"startposition": "Roskildevej 32"
}
]

Why do we suggest a PUT method for adding a guide to a trip instead of a POST method?
Fordi vi opdaterer en eksisterende guide ved at tilføje et trip og bruge merge. Vi opretter altså ikke en ny. Hvis man oprettede en ny guide, så ville det give mening at bruge POST.

## Task 5

GET http://localhost:7070/api/trips/category/BEACH

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:25:49 GMT
Content-Type: application/json
Content-Length: 283

[
{
"id": 5,
"name": "Sunny Beach",
"category": "BEACH",
"price": 45.0,
"starttime": [
2024,
11,
5
],
"endtime": [
2024,
11,
5
],
"startposition": "Golden Sands"
},
{
"id": 7,
"name": "Beach Beach",
"category": "BEACH",
"price": 25.0,
"starttime": [
2023,
12,
15
],
"endtime": [
2023,
12,
15
],
"startposition": "Roskildevej 32"
}
]

GET http://localhost:7070/api/trips/guides/totalprice

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:42:59 GMT
Content-Type: application/json
Content-Length: 98

[
{
"guideId": 3,
"totalPrice": 115.0
},
{
"guideId": 1,
"totalPrice": 75.0
},
{
"guideId": 2,
"totalPrice": 90.0
}
]

## task 8
Man laver userToken og adminToken i test (kig i test)

## task 7
Jeg fik ikke testet alle metoder

## task 6
Jeg kom i gang med det i services mappen, men nåede ikke at lave det færdigt.