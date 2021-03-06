Successfully validate and decrypt SignedEncryptedPseudonym with diversifier - Happy Flow
========================================================================================
Tags: signed-encrypted-pseudonym,diversifier,happy-flow,@bsnkeiddo-4786@

* Load dataset from "/v1/signed-encrypted-pseudonym/happy_flow_diversifier.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"

### Given

A SignedEncryptedPseudonym with diversifier, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedEncryptedPseudonym and keys are validated, the SignedEncryptedPseudonym is succesfully decrypted

### Then

The decrypted Pseudonym and the additional fields from EP/SignedEP are returned in the response message

### Description

Mapping AC tests:

happy flow
-- EP diversifier with 1 DiversifierKeyValuePair
-- EP diversifier with 2 DiversifierKeyValuePairs
-- EP diversifier with x DiversifierKeyValuePairs

The *auditElement* and *signatureValue* have no static values and will change when new test data is generated. 
When new test data is generated by Logius, the corresponding expected test results are produced as well.



Scenario 1: SignedEncryptedPseudonym - diversifier with 1 DiversifierKeyValuePair
---------------------------------------------------------------------------------
Tags: @bsnkeiddo-4803@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                     | propertyFilter        |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | signedEncryptedPseudonym                  | {{ scenario_1.request.signedEncryptedPseudonym    | binary              }}|
    | schemeKeys                                | {{ scenario_1.request.schemeKeys                  | schemeKeys          }}|
    | serviceProviderKeys                       | {{ scenario_1.request.serviceProviderKeys         | serviceProviderKeys }}|
    | targetClosingKey                          | {{ scenario_1.request.targetClosingKey            | string              }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                     | expectedValueType     |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | statusCode                                | {{ scenario_1.expectations.statusCode             | string           }}   |
    | body                                      | {{ scenario_1.expectations.responseBody           | json             }}   |
    | json.decodedInput.signedEP.auditElement   | {{ scenario_1.expectations.auditValue             | string_resource  }}   |
    | json.decodedInput.signatureValue          | {{ scenario_1.expectations.signatureValue         | string_signature }}   |
    | json.decodedPseudonym.pseudonymValue      | {{ scenario_1.expectations.pseudonymValue         | string_resource  }}   |
* Fail if expectations are not met


Scenario 2: SignedEncryptedPseudonym - diversifier with 2 DiversifierKeyValuePairs
----------------------------------------------------------------------------------
Tags: @bsnkeiddo-4804@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                     | propertyFilter        |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | signedEncryptedPseudonym                  | {{ scenario_2.request.signedEncryptedPseudonym    | binary              }}|
    | schemeKeys                                | {{ scenario_2.request.schemeKeys                  | schemeKeys          }}|
    | serviceProviderKeys                       | {{ scenario_2.request.serviceProviderKeys         | serviceProviderKeys }}|
    | targetClosingKey                          | {{ scenario_2.request.targetClosingKey            | string              }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                     | expectedValueType     |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | statusCode                                | {{ scenario_2.expectations.statusCode             | string           }}   |
    | body                                      | {{ scenario_2.expectations.responseBody           | json             }}   |
    | json.decodedInput.signedEP.auditElement   | {{ scenario_2.expectations.auditValue             | string_resource  }}   |
    | json.decodedInput.signatureValue          | {{ scenario_2.expectations.signatureValue         | string_signature }}   |
    | json.decodedPseudonym.pseudonymValue      | {{ scenario_2.expectations.pseudonymValue         | string_resource  }}   |
* Fail if expectations are not met


Scenario 3: SignedEncryptedPseudonym - diversifier with 3 DiversifierKeyValuePairs
----------------------------------------------------------------------------------
Tags: @bsnkeiddo-4805@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                     | propertyFilter        |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | signedEncryptedPseudonym                  | {{ scenario_3.request.signedEncryptedPseudonym    | binary              }}|
    | schemeKeys                                | {{ scenario_3.request.schemeKeys                  | schemeKeys          }}|
    | serviceProviderKeys                       | {{ scenario_3.request.serviceProviderKeys         | serviceProviderKeys }}|
    | targetClosingKey                          | {{ scenario_3.request.targetClosingKey            | string              }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                     | expectedValueType     |
    |-------------------------------------------|---------------------------------------------------|-----------------------|
    | statusCode                                | {{ scenario_3.expectations.statusCode             | string            }}  |
    | body                                      | {{ scenario_3.expectations.responseBody           | json              }}  |
    | json.decodedInput.signedEP.auditElement   | {{ scenario_3.expectations.auditValue             | string_resource   }}  |
    | json.decodedInput.signatureValue          | {{ scenario_3.expectations.signatureValue         | string_signature  }}  |
    | json.decodedPseudonym.pseudonymValue      | {{ scenario_3.expectations.pseudonymValue         | string_resource   }}  |
* Fail if expectations are not met


