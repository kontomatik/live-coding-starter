This is the repository used in Kontomatik live coding.

To work with the code `java 17` and `docker` are required.

To verify if the requirements are met run `./gradlew check` - tests should pass.

The code here loosely resembles Kontomatik API (feel free to check out [documentation of the real one](https://developer.kontomatik.com/api-doc/)) the main purpose of which is to serve *data of bank account owners* from various bank targets in a unified format over HTTP.
In this version owner data consists of basic account information (account name, iban, balance, currency) with personal details (name, address).

To import data API user has to:
1) Create session providing **owner external id** (identifier of an actual person) and selecting **target** (abstraction for a banking system) - you can think of this step like of *specific person* entering *specific bank*'s web page. Owner external id is created by API user (thus *external* from the API perspective) and allows for aggregation of one's person data across multiple import sessions (and so multiple targets). As a result of this step user receives session id.  
2) Using session id sign in to the banking system - by entering credentials.
3) Using session id invoke default import command. 
4) After default import has finished user can access imported data using *owner external id* in a dedicated endpoint.

Application consists of two modules: `service` and `lib`.
Library module contains integrations with specific banking interfaces and is responsible for the data fetching.
Service module wraps library module and exposes its capabilities via HTTP web API, it also persists and aggregates owner data. 
Abstractions of *session*, *entering credentials*, *executing default import command* are present in both modules.

File `./service/src/main/java/com/kontomatik/WebApi.java` can be considered `service`'s entrypoint.
File `./lib/src/main/java/com/kontomatik/KontoX.java` can be considered `lib`'s entrypoint.

See `./service/src/test/java/com/kontomatik/KxServiceTest.java` and `./lib/src/test/java/com/kontomatik/KontoXTest.java` for tests.

Some important naming conventions:
* 'UseCase' suffixes are similar to classes typically named '…Service'
* 'Gateway' suffixes can be interpreted as repository-like abstractions