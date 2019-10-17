Pseudonym conversion: closing key change - Happy Flow
=====================================================
Tags: pseudonym-closing-key-change,happy-flow,@bsnkeiddo-4952@

* Load dataset from "/v1/pseudonym-conversion/pseudonym-conversion-hf.yaml"
* Target default endpoint "/pseudonym-conversion"

### Given

A OASPseudonymRequest containing a DecryptedPseudonym, multiple service provider keys of the 'EP Closing'-type
and a targetClosingKey matching one of the provided service provider keys

### When

The request is sent to pseudonym closing key conversion endpoint

### Then

The response contains the decoded DecryptedPseudonym from the request, and the conversion result
for the target closing key

### Description

happy flow:
--- different OINs
--- different schemeKeySetVersion (pseudonyms)
--- different recipientKeySetVersions


PTC_4952_1: Pseudonym (type B, SKSV 1)  conversion A -> A
---------------------------------------------------------
Tags: @bsnkeiddo-4953@

Converting the incoming pseudonym to the same recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4952_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4952_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4952_1.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4952_1.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4952_1.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4952_1.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met




PTC_4952_2: Pseudonym (type B, SKSV 1) conversion A -> B
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4955@

Converting the incoming pseudonym to the a differing recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4952_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4952_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4952_2.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4952_2.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4952_2.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4952_2.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met



PTC_4952_3: Pseudonym (type E, SKSV 10) conversion A -> B
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4956@

Converting the incoming pseudonym to the a differing recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4952_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4952_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4952_3.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4952_3.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4952_3.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4952_3.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met


