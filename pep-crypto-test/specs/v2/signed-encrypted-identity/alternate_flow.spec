Validation and/or decryption SignedEncryptedIdentity-v2 fails - Alternate Flow
==============================================================================
Tags: signed-encrypted-identity,happy-flow,@bsnkeiddo-5616@

* Load dataset from "/v2/signed-encrypted-identity/alternate_flow.yaml"
* Target default endpoint "/signed-encrypted-identity"

### Given

A SignedEncryptedIdentity, SP-key and schemekey is provided as input for the decryption component

### When

The SignedEncryptedIdentity and/or keys are invalid or the combination does not match

### Then

No decrypted identity (BSN) is returned in the response message with corresponding error status

### Description

Alternate flows:
-- invalid signed EI (signature won't validate)
-- mismatch between key-material and EI:
--- different OINs
--- different schemeKeySetVersion
--- different recipientKeySetVersions
-- input not EI
-- input EI, unknown structure
-- input EI schemeVersion  <> '1'
-- input missing required key



PTC_5616_1: Unkown schemeVersion of SignedEncryptedIdentity-v2
--------------------------------------------------------------
Tags: @bsnkeiddo-5621@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_1.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_1.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_1.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_1.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met



PTC_5616_2: Mismatch between idd OIN and SignedEncryptedIdentity-v2 OIN
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5622@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_2.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_2.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_2.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_2.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"




PTC_5616_3: Mismatch between idd schemeKeySetVersion and SignedEncryptedIdentity-v2 schemeKeySetVersion
-------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5623@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_3.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_3.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_3.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_3.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5616_4: Mismatch between ipp schemeKeySetVersion and SignedEncryptedIdentity-v2 schemeKeySetVersion
-------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5624@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_4.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_4.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_4.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_4.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met




PTC_5616_5: Input is an EncryptedPseudonym instead of SignedEncryptedIdentity-v2
--------------------------------------------------------------------------------
Tags: @bsnkeiddo-5625@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_5.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_5.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_5.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_5.expectations.statusCode                                     | string          }}      |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met




PTC_5616_6: Unknown structure of SignedEncryptedIdentity-v2
-----------------------------------------------------------
Tags: @bsnkeiddo-5626@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_6.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_6.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_6.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_6.expectations.statusCode                                     | string          }}      |
* Expect response body contains "ASN1_SEQUENCE_DECODER"
* Fail if expectations are not met




PTC_5616_7: Missing idd-key input
---------------------------------
Tags: @bsnkeiddo-5627@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_7.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_7.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_7.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_7.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met





PTC_5616_8: Missing ipp-key input
---------------------------------
Tags: @bsnkeiddo-5628@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_8.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_8.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_8.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_8.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met





PTC_5616_9: Invalid signature of SignedEncryptedIdentity-v2
-----------------------------------------------------------
Tags: @bsnkeiddo-5629@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_9.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_9.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_9.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_9.expectations.statusCode                                     | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met




PTC_5616_10: Mismatch between idd recipientKeySetVersion  and SignedEncryptedIdentity-v2 recipientKeySetVersion
---------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5630@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ PTC_5616_10.request.signedEncryptedIdentity                            | binary              }}  |
    | schemeKeys                    | {{ PTC_5616_10.request.schemeKeys                                         | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5616_10.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_5616_10.request.targetClosingKey                                   | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5616_10.expectations.statusCode                                    | string          }}      |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met


