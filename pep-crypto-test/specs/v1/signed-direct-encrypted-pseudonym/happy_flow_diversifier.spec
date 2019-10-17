Successfully validate and decrypt SignedDirectEncryptedPseudonym with diversifier- Happy Flow
=============================================================================================
Tags: signed-direct-encrypted-pseudonym,diversifier,happy-flow,@bsnkeiddo-5078@

* Load dataset from "/v1/signed-direct-encrypted-pseudonym/happy_flow_diversifier.yaml"
* Target default endpoint "/signed-direct-encrypted-pseudonym"

### Given

A SignedDirectEncryptedPseudonym (with diversifier), SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedDirectEncryptedPseudonym and keys are validated, the SignedDirectEncryptedPseudonym is succesfully decrypted

### Then

The decrypted Pseudonym and the additional fields (including the diversifier) from DEP/SignedDEP are returned in the response message

### Description

Mapping AC tests:

happy flow
-- EP/DEP diversifier with 1 DiversifierKeyValuePair
-- EP/DEP diversifier with 2 DiversifierKeyValuePairs
-- EP/DEP diversifier with x DiversifierKeyValuePairs


Scenario 1: SignedDirectEncryptedPseudonym - diversifier with 1 DiversifierKeyValuePair
---------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5057@

Decryption applies the closing key for the recipient key set version matching the SignedDirectEncryptedPseudonym
with 1 DiversifierKeyValuePair.

* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                               | propertyValue                                        | propertyFilter            |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | signedDirectEncryptedPseudonym             | {{ scenario_1.request.signedDirectEncryptedPseudonym | binary                 }} |
    | schemeKeys                                 | {{ scenario_1.request.schemeKeys                     | schemeKeys             }} |
    | serviceProviderKeys                        | {{ scenario_1.request.serviceProviderKeys            | serviceProviderKeys    }} |
    | targetClosingKey                           | {{ scenario_1.request.targetClosingKey               | string                 }} |
    | authorizedParty                            | {{ scenario_1.request.authorizedParty                | string                 }} |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                | expectedValue                                        | expectedValueType         |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | statusCode                                 | {{ scenario_1.expectations.statusCode                | string                 }} |
    | body                                       | {{ scenario_1.expectations.responseBody              | json                   }} |
    | json.decodedInput.signedDEP.auditElement   | {{ scenario_1.expectations.auditValue                | string_resource        }} |
    | json.decodedInput.signatureValue           | {{ scenario_1.expectations.signatureValue            | string_signature       }} |
    | json.decodedPseudonym.pseudonymValue       | {{ scenario_1.expectations.pseudonymValue            | string_resource        }} |
* Fail if expectations are not met




Scenario 2: SignedDirectEncryptedPseudonym - diversifier with 2 DiversifierKeyValuePairs
----------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5058@

Decryption applies the closing key for the recipient key set version matching the SignedDirectEncryptedPseudonym
with 2 DiversifierKeyValuePairs.

* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                               | propertyValue                                        | propertyFilter            |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | signedDirectEncryptedPseudonym             | {{ scenario_2.request.signedDirectEncryptedPseudonym | binary                 }} |
    | schemeKeys                                 | {{ scenario_2.request.schemeKeys                     | schemeKeys             }} |
    | serviceProviderKeys                        | {{ scenario_2.request.serviceProviderKeys            | serviceProviderKeys    }} |
    | targetClosingKey                           | {{ scenario_2.request.targetClosingKey               | string                 }} |
    | authorizedParty                            | {{ scenario_2.request.authorizedParty                | string                 }} |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                | expectedValue                                        | expectedValueType         |
    |--------------------------------------------|------------------------------------------------------|---------------------------|
    | statusCode                                 | {{ scenario_2.expectations.statusCode                | string                 }} |
    | body                                       | {{ scenario_2.expectations.responseBody              | json                   }} |
    | json.decodedInput.signedDEP.auditElement   | {{ scenario_2.expectations.auditValue                | string_resource        }} |
    | json.decodedInput.signatureValue           | {{ scenario_2.expectations.signatureValue            | string_signature       }} |
    | json.decodedPseudonym.pseudonymValue       | {{ scenario_2.expectations.pseudonymValue            | string_resource        }} |
* Fail if expectations are not met




Scenario 3: SignedDirectEncryptedPseudonym - diversifier with 3 DiversifierKeyValuePairs, multiple keys provided as input
-------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5059@

Decryption applies the closing key for the recipient key set version matching the SignedDirectEncryptedPseudonym
with 3 DiversifierKeyValuePairs.

* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                             | propertyValue                                        | propertyFilter            |
    |------------------------------------------|------------------------------------------------------|---------------------------|
    | signedDirectEncryptedPseudonym           | {{ scenario_3.request.signedDirectEncryptedPseudonym | binary                 }} |
    | schemeKeys                               | {{ scenario_3.request.schemeKeys                     | schemeKeys             }} |
    | serviceProviderKeys                      | {{ scenario_3.request.serviceProviderKeys            | serviceProviderKeys    }} |
    | targetClosingKey                         | {{ scenario_3.request.targetClosingKey               | string                 }} |
    | authorizedParty                          | {{ scenario_3.request.authorizedParty                | string                 }} |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                              | expectedValue                                        | expectedValueType         |
    |------------------------------------------|------------------------------------------------------|---------------------------|
    | statusCode                               | {{ scenario_3.expectations.statusCode                | string                 }} |
    | body                                     | {{ scenario_3.expectations.responseBody              | json                   }} |
    | json.decodedInput.signedDEP.auditElement | {{ scenario_3.expectations.auditValue                | string_resource        }} |
    | json.decodedInput.signatureValue         | {{ scenario_3.expectations.signatureValue            | string_signature       }} |
    | json.decodedPseudonym.pseudonymValue     | {{ scenario_3.expectations.pseudonymValue            | string_resource        }} |
    | json.decodedPseudonym.pseudonymValue     | {{ scenario_3.expectations.pseudonymValue            | string_resource        }} |
* Fail if expectations are not met


