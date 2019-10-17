Successfully validate and decrypt SignedDirectEncryptedIdentity - Happy Flow
============================================================================
Tags: signed-direct-encrypted-identity,happy-flow,@bsnkeiddo-5411@

* Load dataset from "/v2/signed-direct-encrypted-identity/happy_flow.yaml"
* Target default endpoint "/signed-direct-encrypted-identity"

### Given

A SignedDirectEncryptedIdentity, SP-key and schemekey are provided as input for the decryption component

### When

The SignedDirectEncryptedIdentity and keys are validated, the SignedDirectEncryptedIdentity is succesfully decrypted

### Then

The decrypted identity (BSN) and the additional fields from DEI/SignedDEI are returned in the response message

### Description

Mapping AC tests:

happy flow
-- different OINs
-- different schemeKeySetVersion
-- different recipientKeySetVersions
-- different AuthorizedParty
-- without/ with extraElements (1, multiple elements, all datatypes)


PTC_5411_1: SignedDirectEncryptedIdentity, no ExtraElements, 8 digit BSN [sv=1,sksv=1,oin=A,rksv=A,skv=1,ap=A]
--------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5449@

SignedDirectEncryptedIdentity without ExtraElements.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                             | propertyValue                                         | propertyFilter          |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity            | {{ PTC_5411_1.request.signedDirectEncryptedIdentity   | binary              }}  |
    | schemeKeys                               | {{ PTC_5411_1.request.schemeKeys                      | schemeKeys          }}  |
    | serviceProviderKeys                      | {{ PTC_5411_1.request.serviceProviderKeys             | serviceProviderKeys }}  |
    | authorizedParty                          | {{ PTC_5411_1.request.authorizedParty                 | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                              | expectedValue                                         | expectedValueType       |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5411_1.expectations.statusCode                 | string          }}      |
    | body                                     | {{ PTC_5411_1.expectations.responseBody               | json            }}      |
    | json.decodedInput.signedDEI.auditElement | {{ PTC_5411_1.expectations.auditValue                 | string_resource }}      |
    | json.decodedInput.signatureValue         | {{ PTC_5411_1.expectations.signatureValue             | string_signature}}      |
* Fail if expectations are not met



PTC_5411_2: SignedDirectEncryptedIdentity, 1 ExtraElements of each type, [sv=1,sksv=10,oin=B,rksv=B,skv=10,ap=B]
----------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5450@

SignedDirectEncryptedIdentity with ExtraElements-elements, with 1 element of each type allowed by the specification.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                             | propertyValue                                         | propertyFilter          |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity            | {{ PTC_5411_2.request.signedDirectEncryptedIdentity   | binary              }}  |
    | schemeKeys                               | {{ PTC_5411_2.request.schemeKeys                      | schemeKeys          }}  |
    | serviceProviderKeys                      | {{ PTC_5411_2.request.serviceProviderKeys             | serviceProviderKeys }}  |
    | authorizedParty                          | {{ PTC_5411_2.request.authorizedParty                 | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                              | expectedValue                                         | expectedValueType       |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5411_2.expectations.statusCode                 | string          }}      |
    | body                                     | {{ PTC_5411_2.expectations.responseBody               | json            }}      |
    | json.decodedInput.signedDEI.auditElement | {{ PTC_5411_2.expectations.auditValue                 | string_resource }}      |
    | json.decodedInput.signatureValue         | {{ PTC_5411_2.expectations.signatureValue             | string_signature}}      |
* Fail if expectations are not met




PTC_5411_3: SignedDirectEncryptedIdentity (BSN with 8 digits), multiple ExtraElements of each type, [sv=1,sksv=10,oin=B,rksv=B,skv=10,ap=B]
-------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5451@

SignedDirectEncryptedIdentity (BSN with 8 digits) contains multiple ExtraElements-elements, some of them having identical key values.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                             | propertyValue                                         | propertyFilter          |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity            | {{ PTC_5411_3.request.signedDirectEncryptedIdentity   | binary              }}  |
    | schemeKeys                               | {{ PTC_5411_3.request.schemeKeys                      | schemeKeys          }}  |
    | serviceProviderKeys                      | {{ PTC_5411_3.request.serviceProviderKeys             | serviceProviderKeys }}  |
    | authorizedParty                          | {{ PTC_5411_3.request.authorizedParty                 | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                              | expectedValue                                         | expectedValueType       |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5411_3.expectations.statusCode                 | string          }}      |
    | body                                     | {{ PTC_5411_3.expectations.responseBody               | json            }}      |
    | json.decodedInput.signedDEI.auditElement | {{ PTC_5411_3.expectations.auditValue                 | string_resource }}      |
    | json.decodedInput.signatureValue         | {{ PTC_5411_3.expectations.signatureValue             | string_signature}}      |
* Fail if expectations are not met



PTC_5411_4: SignedDirectEncryptedIdentity, 1 ExtraElements [sv=1,sksv=1,oin=A,rksv=A,skv=1,ap=A], redundant schemeKeys and serviceProviderKeys
----------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5452@

Decryption is successful, even if there are superfluous serviceProviderKeys and schemeKeys present in the request.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                             | propertyValue                                         | propertyFilter          |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity            | {{ PTC_5411_4.request.signedDirectEncryptedIdentity   | binary              }}  |
    | schemeKeys                               | {{ PTC_5411_4.request.schemeKeys                      | schemeKeys          }}  |
    | serviceProviderKeys                      | {{ PTC_5411_4.request.serviceProviderKeys             | serviceProviderKeys }}  |
    | authorizedParty                          | {{ PTC_5411_4.request.authorizedParty                 | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                              | expectedValue                                         | expectedValueType       |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5411_4.expectations.statusCode                 | string          }}      |
    | body                                     | {{ PTC_5411_4.expectations.responseBody               | json            }}      |
    | json.decodedInput.signedDEI.auditElement | {{ PTC_5411_4.expectations.auditValue                 | string_resource }}      |
    | json.decodedInput.signatureValue         | {{ PTC_5411_4.expectations.signatureValue             | string_signature}}      |
* Fail if expectations are not met



PTC_5411_5: SignedDirectEncryptedIdentity, multiple U-schemekeys supplied with identical schemeKeySetVersion
------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5453@

SignedDirectEncryptedIdentity verified and decrypted when supplying multiple U-keys for the same schemeKeySetVersion,
the signingKeyVersion is used to select the correct U-key.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                             | propertyValue                                         | propertyFilter          |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity            | {{ PTC_5411_5.request.signedDirectEncryptedIdentity   | binary              }}  |
    | schemeKeys                               | {{ PTC_5411_5.request.schemeKeys                      | schemeKeys          }}  |
    | serviceProviderKeys                      | {{ PTC_5411_5.request.serviceProviderKeys             | serviceProviderKeys }}  |
    | authorizedParty                          | {{ PTC_5411_5.request.authorizedParty                 | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                              | expectedValue                                         | expectedValueType       |
    |------------------------------------------|-------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5411_5.expectations.statusCode                 | string          }}      |
    | body                                     | {{ PTC_5411_5.expectations.responseBody               | json            }}      |
    | json.decodedInput.signedDEI.auditElement | {{ PTC_5411_5.expectations.auditValue                 | string_resource }}      |
    | json.decodedInput.signatureValue         | {{ PTC_5411_5.expectations.signatureValue             | string_signature}}      |
* Fail if expectations are not met

