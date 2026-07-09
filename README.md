## Description

🪴A houseplant tracking Spring Boot REST API & JPA/Hibernate project. Add, update, water, delete plants, check which plants are due or overdue for watering using various API endpoints. Includes automatic update of plant watering status daily.

Made to solidify concepts learned from a Udemy Spring Boot & Hibernate course through practical application.

Supports CRUD operations, validation, exception handling and relational persistence using Hibernate ORM and MySQL. Includes water plant and overdue watering plant endpoints. 

## Sample Requests 

Adding a plant:
```json
{
  "name": "Pilea",
  "wateringFrequency": 7,
  "preferredBrightness": "indirect bright",
  "soilMix": "perlite, coco coir, soil potting mix"
}
```

Watering a plant:
```json
{
  "plantId": 11,
  "dateWatered": "2026-01-01"
}
```
