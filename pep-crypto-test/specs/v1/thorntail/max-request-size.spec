Services enforces a configurable maximum input size
===================================================
Tags: thorntail,sanity-check,@bsnkeiddo-4839@

### Given

The service is configured to enforce a maximum on incoming data

### When

A request is sent to the service

### Then

The request is rejected if it exceeds the configured maximum

### Description

Sending a request of a specified size leads to rejection if it exceeds the configured maximum.





PTC_4839_1: SignedEncryptedPseudonym - Maximum size set to 1B
-------------------------------------------------------------
Tags: @bsnkeiddo-4917@

Send a request to different server instances differing in server configuration.

One of the instance is configured to reject messages exceeding a certain limit
and will reject the otherwise valid request.

* Load dataset from "/v1/thorntail/max-request-size.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"
* Create OASSignedEncryptedPseudonymRequest
    | propertyName                             | propertyValue                                    | propertyFilter         |
    |------------------------------------------|--------------------------------------------------|------------------------|
    | signedEncryptedPseudonym                 | {{ PTC_4839_1.request.signedEncryptedPseudonym   | binary              }} |
    | schemeKeys                               | {{ PTC_4839_1.request.schemeKeys                 | schemeKeys          }} |
    | serviceProviderKeys                      | {{ PTC_4839_1.request.serviceProviderKeys        | serviceProviderKeys }} |
    | targetClosingKey                         | {{ PTC_4839_1.request.targetClosingKey           | string              }} |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                              | expectedValue                                    | expectedValueType      |
    |------------------------------------------|--------------------------------------------------|------------------------|
    | statusCode                               | {{ PTC_4839_1.expectations.statusCode            | string              }} |
    | json.decodedInput.signedEP.auditElement  | {{ PTC_4839_1.expectations.auditValue            | string_resource     }} |
    | json.decodedInput.signatureValue         | {{ PTC_4839_1.expectations.signatureValue        | string_signature    }} |
* Target size restricting endpoint "/signed-encrypted-pseudonym"
* Send OASSignedEncryptedPseudonymRequest
* Expect response body contains "RequestTooBigException"
* Expect response body contains "Connection terminated as request was larger than 1"
* Fail if expectations are not met





PTC_4839_2: SignedEncryptedIdentity - Maximum size set to 1B
------------------------------------------------------------
Tags: @bsnkeiddo-4918@

Send a request to different server instances differing in server configuration.

One of the instance is configured to reject messages exceeding a certain limit
and will reject the otherwise valid request.

* Load dataset from "/v1/thorntail/max-request-size.yaml"
* Target default endpoint "/signed-encrypted-identity"
* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                      | propertyFilter          |
    |----------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ PTC_4839_2.request.signedEncryptedIdentity      | binary              }}  |
    | schemeKeys                             | {{ PTC_4839_2.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ PTC_4839_2.request.serviceProviderKeys          | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                     | expectedValueType       |
    |-----------------------------------------|---------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_4839_2.expectations.statusCode             | string          }}      |
    | json.decodedInput.signedEI.auditElement | {{ PTC_4839_2.expectations.auditValue             | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ PTC_4839_2.expectations.signatureValue         | string_signature}}      |
* Target size restricting endpoint "/signed-encrypted-identity"
* Send OASSignedEncryptedIdentityRequest
* Expect response body contains "RequestTooBigException"
* Expect response body contains "Connection terminated as request was larger than 1"
* Fail if expectations are not met





PTC_4839_3: SignedDirectEncryptedPseudonym - Maximum size set to 1B
-------------------------------------------------------------------
Tags: @bsnkeiddo-4919@

Send a request to different server instances differing in server configuration.

One of the instance is configured to reject messages exceeding a certain limit
and will reject the otherwise valid request.

* Load dataset from "/v1/thorntail/max-request-size.yaml"
* Target default endpoint "/signed-direct-encrypted-pseudonym"
* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                               | propertyValue                                        | propertyFilter            |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | signedDirectEncryptedPseudonym             | {{ PTC_4839_3.request.signedDirectEncryptedPseudonym | binary                 }} |
    | schemeKeys                                 | {{ PTC_4839_3.request.schemeKeys                     | schemeKeys             }} |
    | serviceProviderKeys                        | {{ PTC_4839_3.request.serviceProviderKeys            | serviceProviderKeys    }} |
    | targetClosingKey                           | {{ PTC_4839_3.request.targetClosingKey               | string                 }} |
    | authorizedParty                            | {{ PTC_4839_3.request.authorizedParty                | string                 }} |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                | expectedValue                                        | expectedValueType         |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | statusCode                                 | {{ PTC_4839_3.expectations.statusCode                | string                 }} |
    | json.decodedInput.signedDEP.auditElement   | {{ PTC_4839_3.expectations.auditValue                | string_resource        }} |
    | json.decodedInput.signatureValue           | {{ PTC_4839_3.expectations.signatureValue            | string_signature       }} |
* Target size restricting endpoint "/signed-direct-encrypted-pseudonym"
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response body contains "RequestTooBigException"
* Expect response body contains "Connection terminated as request was larger than 1"
* Fail if expectations are not met

