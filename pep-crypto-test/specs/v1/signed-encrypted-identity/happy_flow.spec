Successfully validate and decrypt SignedEncryptedIdentity - Happy Flow
======================================================================
Tags: signed-encrypted-identity,happy-flow,@bsnkeiddo-4602@

* Load dataset from "/v1/signed-encrypted-identity/happy_flow.yaml"
* Target default endpoint "/signed-encrypted-identity"

### Given

A SignedEncryptedIdentity, SP-key and schemekey is provided as input for the decryption component

### When

The SignedEncryptedIdentity and keys are validated, the SignedEncryptedIdentity is succesfully decrypted

### Then

The decrypted identity (BSN) and the additional fields from EI/SignedEI are returned in the response message

### Description

happy flow
-- different OINs
-- different schemeKeySetVersion
-- different recipientKeySetVersions
-- ECSchnorr signed

NB: Diagnostic information will be tested in the specific logging story BSNKEIDDO-4533

The *auditElement* and *signatureValue* have no static values and will change when new test data is generated.
when new test data is generated by Logius, the corresponding expected test results are produced as well.


Scenario 1: SchemeKeySetVersion 1 for OIN A, RKSV A
---------------------------------------------------
Tags: @bsnkeiddo-4618@


* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ scenario_1.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ scenario_1.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ scenario_1.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_1.expectations.statusCode         | string          }}      |
    | body                                    | {{ scenario_1.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ scenario_1.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ scenario_1.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met




Scenario 2: SchemeKeySetVersion 1 for OIN B, RKSV B
---------------------------------------------------
Tags: @bsnkeiddo-4806@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ scenario_2.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ scenario_2.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ scenario_2.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_2.expectations.statusCode         | string          }}      |
    | body                                    | {{ scenario_2.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ scenario_2.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ scenario_2.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met




Scenario 3: Additional key material supplied for inapplicable SchemeKeySetVersion or RecipientKeySetVersion
-----------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-4807@

Decryption selects the applicable key material from the key material present in the request.
Supplying key material for other SchemeKeySetVersion or RecipientKeySetVersion is allowed as
long as they have the same recipient.

The decrypted identity consists of 8 characters and is 0-padden from the left to a 9-character string.

* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ scenario_3.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ scenario_3.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ scenario_3.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_3.expectations.statusCode         | string          }}      |
    | body                                    | {{ scenario_3.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ scenario_3.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ scenario_3.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met

