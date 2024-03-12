# Implementation workflow to demonstrate the code execution

![image](https://github.com/DevShivmohan/distributed-transactions-microservice-saga/assets/72655528/6e39b9c2-ad99-4ab6-89c7-8b5f37ea5f63)
![image](https://github.com/DevShivmohan/distributed-transactions-microservice-saga/assets/72655528/550bb7a2-3175-4c9d-bd84-88de9853c45e)
![image](https://github.com/DevShivmohan/distributed-transactions-microservice-saga/assets/72655528/c2786fb1-66ab-4b76-aaa5-cb0e74fa7884)

# Introduction

Code example for the
article [Distributed Transactions in Microservices: implementing Saga with Temporal](https://techdozo.dev/distributed-transactions-in-microservices-implementing-saga-with-temporal/)
.

## Modules

### common

Contains interface for Activities. These Activities interface is used by order service and microservice that implements
Activity.

Also contains common object such as `OrderDTO` and error handling code (for now not used).

### order-svc (Order Microservice)

Entry point of Order Fulfillment workflow. Contains Workflow Definition `OrderWorkflow` and
implementation `OrderWorkflowImpl`

Code is organized in the package based on the clean/hexagonal architecture principles.

- application: contains business logic. There is no direct dependency on Temporal code.
- common: common configuration such as Spring bean definition
- infrastructure: Temporal specific code such as Workflow, Activity and Worker.
- persistence: Spring JPA related code.
- resource: REST API specific implementation.

### payment-svc (Payment Microservice)

Contains Activity implementation `PaymentActivitiesImpl` and worker `PaymentWorker`

Code is organized in the package based on the clean/hexagonal architecture principles.

- application: contains business logic. There is no direct dependency on Temporal code.
- common: common configuration such as Spring bean definition
- infrastructure: Temporal specific code such as Activity and Worker.
- persistence: Spring JPA related code.
- resource: REST API specific implementation.


### shipment-svc (Shipment Microservice)

Contains Activity implementation `ShipmentActivitiesImpl` and worker `ShipmentWorker`

Code is organized in the package based on the clean/hexagonal architecture principles.

- application: contains business logic. There is no direct dependency on Temporal code.
- common: common configuration such as Spring bean definition
- infrastructure: Temporal specific code such as Activity and Worker.
- persistence: Spring JPA related code.
- resource: REST API specific implementation.

## Installing Temporal Locally

Make sure you have Docker installed locally

``` commandline
git clone https://github.com/temporalio/docker-compose.git
cd  docker-compose
docker-compose up
```

# Live testing with success and failure case
- Live testing [Video link](https://drive.google.com/file/d/1KqO6jbDc9qZnAMMfK9K35LSdop-ypxOb/view?usp=drive_link).


**Add saga workflows like this, first add failure case and then business logic**
![image](https://github.com/DevShivmohan/distributed-transactions-microservice-saga/assets/72655528/039e12a7-0503-42ca-bbf0-25354bec5295)

