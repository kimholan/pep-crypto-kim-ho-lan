Validation and/or decryption SignedEncryptedPseudonym-v2 fails - Alternate Flow
===============================================================================
Tags: signed-encrypted-pseudonym,alternate-flow,@5635@

* Load dataset from "/v2/signed-encrypted-pseudonym/alternate_flow.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"

### Given

A SignedEncryptedPseudonym-v2, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedEncryptedPseudonym-v2 and/or keys are invalid or the combination does not match

### Then

No Pseudonym is returned in the response message with corresponding error status

### Description

Mapping AC tests:

Alternate flow
-- invalid signed EP (signature won't validate)
-- mismatch between key-material and EP:
--- different OINs
--- different schemeKeySetVersion
--- different recipientKeySetVersions
-- input not EP
-- input EP, unknown structure
-- input EP schemeVersion  <> '1'
-- input missing required key


Scenario 1: SignedEncryptedPseudonym invalid signed
----------------------------------------------------
Tags: @BSNKEIDDO-5640@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_1.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_1.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_1.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_1.expectations.statusCode                            | string          }}   |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met



Scenario 2: Mismatch between SignedEncryptedPseudonym OIN and pdd OIN
----------------------------------------------------------------------
Tags: @bsnkeiddo-5641@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_2.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_2.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_2.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_2.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE"
* Fail if expectations are not met



Scenario 3: Mismatch between SignedEncryptedPseudonym OIN and pcd OIN
---------------------------------------------------------------------
Tags: @bsnkeiddo-5642@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_3.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_3.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_3.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_3.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE"
* Fail if expectations are not met




Scenario 4: Mismatch between SignedEncryptedPseudonym SKSV and pdd SKSV
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5643@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_4.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_4.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_4.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_4.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 5: Mismatch between SignedEncryptedPseudonym SKSV and pcd SKSV
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5644@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_5.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_5.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_5.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_5.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 6: Mismatch between SignedEncryptedPseudonym SKSV and ipp SKSV
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5645@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_6.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_6.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_6.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_6.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met




Scenario 7: Mismatch between SignedEncryptedPseudonym RKSV and pdd RKSV
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5646@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_7.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_7.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_7.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_7.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 8: Mismatch between SignedEncryptedPseudonym RKSV and pcd RKSV
-----------------------------------------------------------------------
Tags: @bsnkeiddo-5647@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_8.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_8.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_8.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_8.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met


Scenario 9: Target closing key doesn't match the closing key
------------------------------------------------------------
Tags: @bsnkeiddo-5648@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_9.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_9.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_9.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_9.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met





Scenario 10: Input is a SignedEncryptedIdentity instead of a SignedEncryptedPseudonym
--------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5649@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_10.request.signedEncryptedPseudonym                           | binary              }}  |
    | schemeKeys                    | {{ scenario_10.request.schemeKeys                                         | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_10.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_10.request.targetClosingKey                                   | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_10.expectations.statusCode                           | string          }}   |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met





Scenario 11: Unknown structure of SignedEncryptedPseudonym
-----------------------------------------------------------
Tags: @bsnkeiddo-5650@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_11.request.signedEncryptedPseudonym                           | binary              }}  |
    | schemeKeys                    | {{ scenario_11.request.schemeKeys                                         | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_11.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_11.request.targetClosingKey                                   | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_11.expectations.statusCode                           | string          }}   |
* Expect response body contains "ASN1_SEQUENCE_DECODER"
* Fail if expectations are not met




Scenario 12: Input missing service provider key
-----------------------------------------------
Tags: @bsnkeiddo-5651@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_12.request.signedEncryptedPseudonym                           | binary              }}  |
    | schemeKeys                    | {{ scenario_12.request.schemeKeys                                         | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_12.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_12.request.targetClosingKey                                   | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_12.expectations.statusCode                           | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 13: Input missing closing key
--------------------------------------
Tags: @bsnkeiddo-5652@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_13.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_13.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_13.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_13.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_13.expectations.statusCode                           | string          }}   |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 14: Input missing scheme key
-------------------------------------
Tags: @bsnkeiddo-5653@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_14.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_14.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_14.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_14.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_14.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SCHEME_KEY_NOT_FOUND"
* Fail if expectations are not met



Scenario 15: SignedEncryptedPseudonym schemeVersion <> 1 (SignedEncryptedPseudonym-v2 with(out) extraElements)
--------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5654@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_15.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_15.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_15.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_15.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_15.expectations.statusCode                            | string          }}   |
* Expect response body contains "MATCHING_SERVICE_PROVIDER_KEY_REQUIRED"
* Fail if expectations are not met



Scenario 17: SignedEncryptedPseudonym invalid signed with diversifier
---------------------------------------------------------------------
Tags: @BSNKEIDDO-5707@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ scenario_17.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ scenario_17.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys           | {{ scenario_17.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ scenario_17.request.targetClosingKey                                    | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                                    | expectedValueType    |
    |-------------------------------------------------------|------------------------------------------------------------------|----------------------|
    | statusCode                                            | {{ scenario_17.expectations.statusCode                            | string          }}   |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met