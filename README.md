Requirements
For building and running the application you need:

JDK 1.8+

To compile:  mvn clean compile
To run the tests: mvn test

Running the application locally :mvn spring-boot:run

-- NOTES --
Normally Sprig Data Rest would be a better choice for this scope but to be able
to demonstrate TDD, I decided to follow clasic aproach.

I did not complete the cucumber test because of the time constraint and
also I believe I demonstrated enough BDD skills.
---
API:

Create:
POST
URL : http://localhost:8080/products
{"name":"aspirin","price":1.2,"priceCurrency":"GBP"}

Response Content
{"productId":1,"name":"aspirin","price":1.2,"priceCurrency":"GBP","creationDate":2020-04-16,"active":true}

List:
Request Method - GET
URI - http://localhost:8080/products
Response Content
[{"productId":1,"name":"Aspirin","price":2.20,"priceCurrency":"GBP","creationDate":{"year":2020,"month":"APRIL","monthValue":4,"dayOfMonth":16,"dayOfWeek":"THURSDAY","leapYear":true,"dayOfYear":107,"era":"CE","chronology":{"id":"ISO","calendarType":"iso8601"}},"active":true},{"productId":2,"name":"Lempsip","price":1.20,"priceCurrency":"CHF","creationDate":{"year":2020,"month":"APRIL","monthValue":4,"dayOfMonth":16,"dayOfWeek":"THURSDAY","leapYear":true,"dayOfYear":107,"era":"CE","chronology":{"id":"ISO","calendarType":"iso8601"}},"active":true},{"productId":3,"name":"Newdrug","price":3.10,"priceCurrency":"CHF","creationDate":{"year":2020,"month":"APRIL","monthValue":4,"dayOfMonth":16,"dayOfWeek":"THURSDAY","leapYear":true,"dayOfYear":107,"era":"CE","chronology":{"id":"ISO","calendarType":"iso8601"}},"active":true}]

Update:
URI - http://localhost:8080/products/1
Request Method - PUT
Content-Type - application/json
Request Body Content

{
  "name": "Apple",
  "price": "2.2",
  "priceCurrency": "GBP"
}

Delete:
URI - http://localhost:8080/products/1/
Request Method - DELETE
