Successfully validate and decrypt SignedEncryptedPseudonym-v2 -Happy Flow
=========================================================================
Tags: signed-encrypted-pseudonym,happy-flow,@5634@

* Load dataset from "/v2/signed-encrypted-pseudonym/happy_flow.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"

### Given

A SignedEncryptedPseudonym-v2, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedEncryptedPseudonym-v2 and keys are validated, the SignedEncryptedPseudonym is succesfully decrypted

### Then

The decrypted Pseudonym and the additional fields from EP/SignedEP are returned in the response message

### Description

Mapping AC tests:

happy flow
-- different OINs
-- different schemeKeySetVersion
-- different recipientKeySetVersions
-- different target recipientKeySetVersion for closingKey
-- ECSDSA signed
-- With/without extraElements


Scenario 1: SchemeKeySetVersion 1 for OIN A, RKSV A, CKSV A - target closingKey = empty - (SignedEncryptedIdentity-v2 without extraElements)
--------------------------------------------------------------------------------------------------------------------------------------------
Tags: @5636@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                            | propertyValue                                     | propertyFilter       |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | signedEncryptedPseudonym                | {{ scenario_1.request.signedEncryptedPseudonym    | binary             }}|
    | schemeKeys                              | {{ scenario_1.request.schemeKeys                  | schemeKeys         }}|
    | serviceProviderKeys                     | {{ scenario_1.request.serviceProviderKeys         | serviceProviderKeys}}|
    | targetClosingKey                        | {{ scenario_1.request.targetClosingKey            | string             }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                             | expectedValue                                     | expectedValueType    |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | statusCode                              | {{ scenario_1.expectations.statusCode             | string           }}  |
    | body                                    | {{ scenario_1.expectations.responseBody           | json             }}  |
    | json.decodedInput.signedEP.auditElement | {{ scenario_1.expectations.auditValue             | string_resource  }}  |
    | json.decodedInput.signatureValue        | {{ scenario_1.expectations.signatureValue         | string_signature }}  |
    | json.decodedPseudonym.pseudonymValue    | {{ scenario_1.expectations.pseudonymValue         | string_resource  }}  |
* Fail if expectations are not met


Scenario 2: SchemeKeySetVersion 10 for OIN B, RKSV B, CKSV B - target closingKey = RKSV B - (SignedEncryptedIdentity-v2 with 1 extraElement)
--------------------------------------------------------------------------------------------------------------------------------------------
Tags: @5637@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                            | propertyValue                                     | propertyFilter       |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | signedEncryptedPseudonym                | {{ scenario_2.request.signedEncryptedPseudonym    | binary             }}|
    | schemeKeys                              | {{ scenario_2.request.schemeKeys                  | schemeKeys         }}|
    | serviceProviderKeys                     | {{ scenario_2.request.serviceProviderKeys         | serviceProviderKeys}}|
    | targetClosingKey                        | {{ scenario_2.request.targetClosingKey            | string             }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                             | expectedValue                                     | expectedValueType    |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | statusCode                              | {{ scenario_2.expectations.statusCode             | string           }}  |
    | body                                    | {{ scenario_2.expectations.responseBody           | json             }}  |
    | json.decodedInput.signedEP.auditElement | {{ scenario_2.expectations.auditValue             | string_resource  }}  |
    | json.decodedInput.signatureValue        | {{ scenario_2.expectations.signatureValue         | string_signature }}  |
    | json.decodedPseudonym.pseudonymValue    | {{ scenario_2.expectations.pseudonymValue         | string_resource  }}  |
* Fail if expectations are not met


Scenario 3: Additional key material supplied for inapplicable SchemeKeySetVersion or RecipientKeySetVersion - target closingKey = empty (SignedEncryptedIdentity-v2 multiple extraElements)
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @5638@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                            | propertyValue                                     | propertyFilter       |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | signedEncryptedPseudonym                | {{ scenario_3.request.signedEncryptedPseudonym    | binary             }}|
    | schemeKeys                              | {{ scenario_3.request.schemeKeys                  | schemeKeys         }}|
    | serviceProviderKeys                     | {{ scenario_3.request.serviceProviderKeys         | serviceProviderKeys}}|
    | targetClosingKey                        | {{ scenario_3.request.targetClosingKey            | string             }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                             | expectedValue                                     | expectedValueType    |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | statusCode                              | {{ scenario_3.expectations.statusCode             | string           }}  |
    | body                                    | {{ scenario_3.expectations.responseBody           | json             }}  |
    | json.decodedInput.signedEP.auditElement | {{ scenario_3.expectations.auditValue             | string_resource  }}  |
    | json.decodedInput.signatureValue        | {{ scenario_3.expectations.signatureValue         | string_signature }}  |
    | json.decodedPseudonym.pseudonymValue    | {{ scenario_3.expectations.pseudonymValue         | string_resource  }}  |
* Fail if expectations are not met


Scenario 4: RKSV C, CKSV B - target closingKey = RKSV B + 1 diversifierKeyValuePair
--------------------------------------------------------------------------------------------------------------------------------------------
Tags: @5639@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                            | propertyValue                                     | propertyFilter       |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | signedEncryptedPseudonym                | {{ scenario_4.request.signedEncryptedPseudonym    | binary             }}|
    | schemeKeys                              | {{ scenario_4.request.schemeKeys                  | schemeKeys         }}|
    | serviceProviderKeys                     | {{ scenario_4.request.serviceProviderKeys         | serviceProviderKeys}}|
    | targetClosingKey                        | {{ scenario_4.request.targetClosingKey            | string             }}|
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                             | expectedValue                                     | expectedValueType    |
    |-----------------------------------------|---------------------------------------------------|----------------------|
    | statusCode                              | {{ scenario_4.expectations.statusCode             | string           }}  |
    | body                                    | {{ scenario_4.expectations.responseBody           | json             }}  |
    | json.decodedInput.signedEP.auditElement | {{ scenario_4.expectations.auditValue             | string_resource  }}  |
    | json.decodedInput.signatureValue        | {{ scenario_4.expectations.signatureValue         | string_signature }}  |
    | json.decodedPseudonym.pseudonymValue    | {{ scenario_4.expectations.pseudonymValue         | string_resource  }}  |
* Fail if expectations are not met
