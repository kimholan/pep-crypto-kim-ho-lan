Successfully validate and decrypt SignedEncryptedIdentity-v2 - Happy Flow
=========================================================================
Tags: signed-encrypted-identity,happy-flow,@bsnkeiddo-5615@

* Load dataset from "/v2/signed-encrypted-identity/happy_flow.yaml"
* Target default endpoint "/signed-encrypted-identity"

### Given

A SignedEncryptedIdentity-v2, SP-key and schemekey is provided as input for the decryption component

### When

The SignedEncryptedIdentity-v2 and keys are validated, the SignedEncryptedIdentity is succesfully decrypted

### Then

The decrypted identity (BSN) and the additional fields from EI/SignedEI are returned in the response message

### Description

happy flow
-- different OINs
-- different schemeKeySetVersion
-- different recipientKeySetVersions
-- ECSDSA signed
-- With/without extraElements


PTC_5615_1: SchemeKeySetVersion 1 for OIN A, RKSV A - SignedEncryptedIdentity-v2 without extraElements
------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5617@


* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ PTC_5615_1.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ PTC_5615_1.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ PTC_5615_1.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5615_1.expectations.statusCode         | string          }}      |
    | body                                    | {{ PTC_5615_1.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ PTC_5615_1.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ PTC_5615_1.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met


PTC_5615_2: SchemeKeySetVersion 10 for OIN B, RKSV B - SignedEncryptedIdentity-v2, 1 extraElement
-------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5618@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ PTC_5615_2.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ PTC_5615_2.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ PTC_5615_2.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5615_2.expectations.statusCode         | string          }}      |
    | body                                    | {{ PTC_5615_2.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ PTC_5615_2.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ PTC_5615_2.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met



PTC_5615_3: Additional key material supplied for inapplicable SchemeKeySetVersion or RecipientKeySetVersion - SignedEncryptedIdentity-v2, 1 extraElements of each type
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5619@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ PTC_5615_3.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ PTC_5615_3.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ PTC_5615_3.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5615_3.expectations.statusCode         | string          }}      |
    | body                                    | {{ PTC_5615_3.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ PTC_5615_3.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ PTC_5615_3.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met



PTC_5615_4: SchemeKeySetVersion 1 for OIN A, RKSV A - SignedEncryptedIdentity-v2, multiple extraElements of each type
----------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5620@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                           | propertyValue                                  | propertyFilter          |
    |----------------------------------------|------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                | {{ PTC_5615_4.request.signedEncryptedIdentity  | binary              }}  |
    | schemeKeys                             | {{ PTC_5615_4.request.schemeKeys               | schemeKeys          }}  |
    | serviceProviderKeys                    | {{ PTC_5615_4.request.serviceProviderKeys      | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                 | expectedValueType       |
    |-----------------------------------------|-----------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5615_4.expectations.statusCode         | string          }}      |
    | body                                    | {{ PTC_5615_4.expectations.responseBody       | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ PTC_5615_4.expectations.auditValue         | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ PTC_5615_4.expectations.signatureValue     | string_signature}}      |
* Fail if expectations are not met
