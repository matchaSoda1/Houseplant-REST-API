## Description

A Spring Boot REST API project to solidify concepts learned from a Udemy Spring Boot & Hibernate course through practical application. Helps keep my plants happy :-)

Supports CRUD operations, validation, exception handling and relational persistence using Hibernate ORM and MySQL. Includes water plant and overdue watering plant endpoints. 


## Sample Requests 

Adding a plant:

            {
                "name" : "Pilea",
                "wateringFrequency" : 7,
                "preferredBrightness" : "indirect bright",
                "soilMix" : "perlite, coco coir, soil potting mix"
            }

Watering a plant

        {
          plantId:11,
          dateWatered: "2026-01-01"
        }
