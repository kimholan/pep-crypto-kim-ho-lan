Validation and/or decryption SignedEncryptedPseudonym fails - Alternate Flow
============================================================================
Tags: signed-encrypted-pseudonym,alternate-flow,@bsnkeiddo-4657@

* Load dataset from "/v1/signed-encrypted-pseudonym/alternate_flows.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"

### Given

A SignedEncryptedPseudonym, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedEncryptedPseudonym and/or keys are invalid or the combination does not match

### Then

No Pseudonym is returned in the response message with corresponding error status

### Description

error flows
-- invalid signed EP
-- mismatch between key-material and EP:
--- different OINs
--- different schemeKeySetVersions
--- different recipientKeySetVersions
--- different closingKeySetVersions
-- input no EP
-- input EP, unknown structure
-- input missing decryption key
-- input missing closing key
-- input missing scheme key

NB: Diagnostic information will be tested in the specific logging story BSNKEIDDO-4533

The *auditElement* and *signatureValue* have no static values and will change when new
test data is generated. When new test data is generated by Logius, the corresponding expected
test results are produced as well.


Scenario 1: SignedEncryptedPseudonym invalid signed
----------------------------------------------------
Tags: @BSNKEIDDO-4821@

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
Tags: @bsnkeiddo-4822@

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
Tags: @bsnkeiddo-4823@

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
Tags: @bsnkeiddo-4824@

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
Tags: @bsnkeiddo-4825@

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
Tags: @bsnkeiddo-4826@

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
Tags: @bsnkeiddo-4827@

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
Tags: @bsnkeiddo-4828@

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
Tags: @bsnkeiddo-4829@

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
Tags: @bsnkeiddo-4830@

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
Tags: @bsnkeiddo-4831@

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
Tags: @bsnkeiddo-4832@

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
Tags: @bsnkeiddo-4833@

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
Tags: @bsnkeiddo-4834@

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

