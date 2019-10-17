Pseudonym conversion: closing key change with diversifer - Happy Flow
=====================================================================
Tags: pseudonym-closing-key-change,happy-flow,diversifier,@bsnkeiddo-5077@

* Load dataset from "/v1/pseudonym-conversion/pseudonym-conversion-div-hf.yaml"
* Target default endpoint "/pseudonym-conversion"

### Given

A OASPseudonymRequest containing a DecryptedPseudonym with diversifier, multiple service provider keys
of the 'EP Closing'-type and a targetClosingKey matching one of the provided service provider keys

### When

The request is sent to pseudonym closing key conversion endpoint

### Then

The response contains the decoded DecryptedPseudonym from the request, and the conversion result
for the target closing key

### Description

happy flow:
--- Pseudonym conversion with Diversifier


Scenario 1: Pseudonym conversion - diversifier with 1 DiversifierKeyValuePair
-----------------------------------------------------------------------------
Tags: @bsnkeiddo-5066@

Converting the incoming pseudonym to the same recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4986_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4986_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4986_1.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4986_1.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4986_1.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4986_1.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met




Scenario 2: Pseudonym conversion - diversifier with 2 DiversifierKeyValuePairs
------------------------------------------------------------------------------
Tags: @bsnkeiddo-5067@

Converting the incoming pseudonym to the a differing recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4986_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4986_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4986_2.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4986_2.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4986_2.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4986_2.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met



Scenario 3: Pseudonym conversion - diversifier with 3 DiversifierKeyValuePairs
------------------------------------------------------------------------------
Tags: @bsnkeiddo-5068@

Converting the incoming pseudonym to the a differing recipient key set version
of the closing key is allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4986_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4986_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4986_3.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4986_3.expectations.statusCode                                     | string            }}    |
    | body                          | {{ PTC_4986_3.expectations.responseBody                                   | json              }}    |
    | json.pseudonym                | {{ PTC_4986_3.expectations.pseudonym                                      | binary            }}    |
* Fail if expectations are not met


