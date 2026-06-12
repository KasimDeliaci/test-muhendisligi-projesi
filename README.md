# Restful Booker API Regression Tests

## Öğrenci ve Sunum Bilgisi

**Öğrenci:** Kasım Deliacı | Bursa Teknik Üniversitesi
**Öğrenci No:** 21360859021
**Ders:** Yazılım Test Mühendisliği

Bu proje, Yazılım Test Mühendisliği dersi kapsamında hazırlanmıştır. Projenin kod tarafında Restful Booker API üzerinde otomatik regresyon testleri bulunmaktadır.

Sunum tarafında ise **“AI sistemleri nasıl test edilir?”** sorusu ele alınmıştır. Sunumda özellikle AI tabanlı sistemlerde beklenen sonucun her zaman net olmaması, test oracle problemi, CT-AI yaklaşımı, metamorphic testing, golden dataset, back-to-back testing ve AI sistemlerinde test mühendisinin değişen rolü anlatılmıştır.

## Proje Özeti

Bu proje, Yazılım Test Mühendisliği dersi kapsamında Java, Maven, JUnit 5 ve Rest Assured kullanılarak hazırlanmış bir servis otomatik regresyon testi projesidir.

## Test Edilen Servis

Proje, public bir rezervasyon API'si olan Restful Booker uzerinde calisir:

https://restful-booker.herokuapp.com

Restful Booker; booking oluşturma, okuma, güncelleme ve silme işlemleri için kullanılabilen bir test API'sidir.

## Kapsam

Projede aşağıdaki otomatik API regresyon testleri bulunur:

- `GET /ping`
  - Servisin ayakta olduğu kontrol edilir.
  - Status code `201` kontrol edilir.
  - Cevabın `2000 ms` altında döndüğü kontrol edilir.

- Booking CRUD akışı
  - `POST /auth` ile token otomatik alınır.
  - `POST /booking` ile yeni booking oluşturulur.
  - Response body içinde `bookingid` ve booking alanları kontrol edilir.
  - `GET /booking/{id}` ile oluşturulan booking doğrulanır.
  - `PATCH /booking/{id}` ile booking kısmi olarak güncellenir.
  - `DELETE /booking/{id}` ile booking silinir.
  - Silinen booking için `GET /booking/{id}` isteğinde `404` döndüğü kontrol edilir.

Tüm ana isteklerde status code, response body ve response time kontrolleri yapılır.

## Auth Bilgisi

PATCH ve DELETE istekleri için auth gerekir. Token alma işlemi test içinde otomatik yapılır:

```json
{
  "username": "admin",
  "password": "password123"
}
```

Kullanıcının manuel token almasına veya environment variable tanımlamasına gerek yoktur.

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
- İnternet bağlantısı

## Testleri Çalıştırma

Terminalde proje klasöründeyken:

```bash
mvn test
```

Belirli bir test sınıfını çalıştırmak için:

```bash
mvn -Dtest=BookingCrudRegressionTest test
```

Tek bir adımı göstermek için (örnek: sadece DELETE):

```bash
mvn -Dtest=BookingCrudRegressionTest#deleteBookingShouldRemoveBooking test
```

Diğer örnekler:

```bash
mvn -Dtest=BookingCrudRegressionTest#authShouldReturnValidToken test
mvn -Dtest=BookingCrudRegressionTest#createBookingShouldReturnBookingWithExpectedFields test
mvn -Dtest=BookingCrudRegressionTest#getBookingShouldReturnCreatedBooking test
mvn -Dtest=BookingCrudRegressionTest#patchBookingShouldUpdateSelectedFields test
mvn -Dtest=BookingCrudRegressionTest#bookingCrudFlowShouldWorkSuccessfully test
mvn -Dtest=HealthCheckTest#pingShouldReturnCreatedStatus test
```

Her adım testi kendi ön koşulunu kurar (örneğin DELETE testi önce booking oluşturur ve token alır), bu yüzden tek başına çalıştırılabilir. Tam CRUD akışı için `bookingCrudFlowShouldWorkSuccessfully` testini kullanın.

## Kullanılan Teknolojiler

- Java 17
- Maven
- JUnit 5
- Rest Assured
- Hamcrest Matchers
