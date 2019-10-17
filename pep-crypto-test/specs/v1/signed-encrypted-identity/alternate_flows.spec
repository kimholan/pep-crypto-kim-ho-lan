Validation and/or decryption SignedEncryptedIdentity fails - Alternate Flow
===========================================================================
Tags: signed-encrypted-identity,alternate-flow,@bsnkeiddo-4603@

* Load dataset from "/v1/signed-encrypted-identity/alternate_flows.yaml"
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

NB: Diagnostic information will be tested in the specific logging story BSNKEIDDO-4533


Scenario 1: Unknown schemeVersion of SignedEncryptedIdentity
------------------------------------------------------------
Tags: @bsnkeiddo-4808@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_1.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_1.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_1.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_1.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 3: Mismatch between idd schemeKeySetVersion and SignedEncryptedIdentity schemeKeySetVersion
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-4809@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_3.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_3.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_3.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_3.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 4: Mismatch between ipp schemeKeySetVersion and SignedEncryptedIdentity schemeKeySetVersion
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-4810@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_4.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_4.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_4.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_4.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met




Scenario 5: Input is an EncryptedPseudonym instead of SignedEncryptedIdentity
-----------------------------------------------------------------------
Tags: @bsnkeiddo-4811@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_5.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_5.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_5.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_5.expectations.statusCode                            | string          }}   |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met




Scenario 6: Unknown structure of SignedEncryptedIdentity
--------------------------------------------------
Tags: @bsnkeiddo-4812@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_6.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_6.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_6.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_6.expectations.statusCode                            | string          }}   |
* Expect response body contains "ASN1_SEQUENCE_DECODER"
* Fail if expectations are not met





Scenario 7: Missing idd-key input
---------------------------------
Tags: @bsnkeiddo-4813@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_7.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_7.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_7.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_7.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met





Scenario 8: Missing ipp-key input
---------------------------------
Tags: @bsnkeiddo-4814@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_8.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_8.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_8.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_8.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met





Scenario 9: Invalid signature of SignedEncryptedIdentity
--------------------------------------------------
Tags: @bsnkeiddo-4815@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_9.request.signedEncryptedIdentity                             | binary              }}  |
    | schemeKeys                    | {{ scenario_9.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_9.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_9.expectations.statusCode                            | string          }}   |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met




Scenario 10: Mismatch between idd recipientKeySetVersion  and SignedEncryptedIdentity recipientKeySetVersion 
------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-4816@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedIdentity       | {{ scenario_10.request.signedEncryptedIdentity                            | binary              }}  |
    | schemeKeys                    | {{ scenario_10.request.schemeKeys                                         | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_10.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_10.request.targetClosingKey                                   | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_10.expectations.statusCode                           | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met

