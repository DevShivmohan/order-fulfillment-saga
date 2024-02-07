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

````commandline
git clone https://github.com/temporalio/docker-compose.git
cd  docker-compose
docker-compose up
```
