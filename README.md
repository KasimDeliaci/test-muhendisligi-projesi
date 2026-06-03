# Restful Booker API Regression Tests

Bu proje, Yazilim Test Muhendisligi dersi kapsaminda Java, Maven, JUnit 5 ve Rest Assured kullanilarak hazirlanmis bir servis otomatik regresyon testi projesidir.

## Test Edilen Servis

Proje, public bir rezervasyon API'si olan Restful Booker uzerinde calisir:

https://restful-booker.herokuapp.com

Restful Booker; booking olusturma, okuma, guncelleme ve silme islemleri icin kullanilabilen bir test API'sidir.

## Kapsam

Projede asagidaki otomatik API regresyon testleri bulunur:

- `GET /ping`
  - Servisin ayakta oldugu kontrol edilir.
  - Status code `201` kontrol edilir.
  - Cevabin `2000 ms` altinda dondugu kontrol edilir.

- Booking CRUD akisi
  - `POST /auth` ile token otomatik alinir.
  - `POST /booking` ile yeni booking olusturulur.
  - Response body icinde `bookingid` ve booking alanlari kontrol edilir.
  - `GET /booking/{id}` ile olusturulan booking dogrulanir.
  - `PATCH /booking/{id}` ile booking kismi olarak guncellenir.
  - `DELETE /booking/{id}` ile booking silinir.
  - Silinen booking icin `GET /booking/{id}` isteginde `404` dondugu kontrol edilir.

Tum ana isteklerde status code, response body ve response time kontrolleri yapilir.

## Auth Bilgisi

PATCH ve DELETE istekleri icin auth gerekir. Token alma islemi test icinde otomatik yapilir:

```json
{
  "username": "admin",
  "password": "password123"
}
```

Kullanicinin manuel token almasina veya environment variable tanimlamasina gerek yoktur.

## Proje Yapisi

```text
.
├── pom.xml
├── src
│   └── test
│       ├── java
│       │   └── com
│       │       └── testmuh
│       │           └── api
│       │               ├── BaseApiTest.java
│       │               ├── BookingCrudRegressionTest.java
│       │               └── HealthCheckTest.java
│       └── resources
│           └── payloads
│               ├── create-booking-request.json
│               └── patch-booking-request.json
```

## Gereksinimler

- Java 17 veya uzeri
- Maven 3.9 veya uzeri
- Internet baglantisi

## Testleri Calistirma

Terminalde proje klasorundeyken:

```bash
mvn test
```

Belirli bir test sinifini calistirmak icin:

```bash
mvn -Dtest=BookingCrudRegressionTest test
```

## Kullanilan Teknolojiler

- Java 17
- Maven
- JUnit 5
- Rest Assured
- Hamcrest Matchers
