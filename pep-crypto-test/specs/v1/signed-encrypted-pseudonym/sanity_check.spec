Sanity check SEP decryption
===========================
Tags: signed-encrypted-pseudonym,sanity-check,@bsnkeiddo-4843@

* Load dataset from "/v1/signed-encrypted-pseudonym/sanity_check.yaml"
* Target default endpoint "/signed-encrypted-pseudonym"

### Given

A SignedEncryptedPseudonym, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedEncryptedPseudonym and/or keys have invalid content/invalid format or the input length is too big

### Then

No decrypted Pseudonym is returned in the response message with corresponding error status

### Description

Alternate flows:
-- Request with length >= 1MB
-- Schemekey-metadata in wrong format [metadata-error]
-- Schemekey in wrong format [key-error]
-- Duplicate key-metadata
-- SP-Key processing: keys from multiple OIN,
-- -BER-encoded EI/EP/DEP input-
-- -compressed ECPoint(s) in EI/EP/DEP-
-- Check if all SP-Key headers are present;
--- ignore non-specified headers
--- refuse duplicate headers
--- Case sensitive

The *auditElement* and *signatureValue* have no static values and will change when new
test data is generated. When new test data is generated by Logius, the corresponding expected
test results are produced as well.



Scenario 2: Scheme key metadata is not parsable according to scheme key format
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4870@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_2.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_2.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_2.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_2.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_2.expectations.statusCode              | string            }}    |
* Expect response body contains "SCHEME_KEY_METADATA_INVALID_STRUCTURE"
* Fail if expectations are not met


Scenario 3: Scheme key structure doesn't start with '0x04'
----------------------------------------------------------
Tags: @bsnkeiddo-4871@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_3.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_3.request.schemeKeys                   | base64ResourceMap   }}  |
    | serviceProviderKeys                       | {{ scenario_3.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_3.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_3.expectations.statusCode              | string            }}    |
* Expect response body contains "SCHEME_KEY_NOT_UNCOMPRESSED"
* Fail if expectations are not met

Scenario 4a: Scheme key total length < 81bytes
----------------------------------------------
Tags: @bsnkeiddo-4872@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_4a.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_4a.request.schemeKeys                  | base64ResourceMap   }}  |
    | serviceProviderKeys                       | {{ scenario_4a.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_4a.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_4a.expectations.statusCode             | string            }}    |
* Expect response body contains "SCHEME_KEY_INVALID_LENGTH"
* Fail if expectations are not met

Scenario 4b: Scheme key total length > 81bytes
----------------------------------------------
Tags: @bsnkeiddo-5042@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_4b.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_4b.request.schemeKeys                  | base64ResourceMap   }}  |
    | serviceProviderKeys                       | {{ scenario_4b.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_4b.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_4b.expectations.statusCode             | string            }}    |
* Expect response body contains "SCHEME_KEY_INVALID_LENGTH"
* Fail if expectations are not met


Scenario 5: Scheme key points not on curve - BrainpoolP320r1
------------------------------------------------------------
Tags: @bsnkeiddo-4873@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_5.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_5.request.schemeKeys                   | base64ResourceMap   }}  |
    | serviceProviderKeys                       | {{ scenario_5.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_5.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_5.expectations.statusCode              | string            }}    |
* Expect response body contains "SCHEME_KEY_NOT_DECODABLE"
* Fail if expectations are not met


Scenario 6: Scheme Key schemeVersion <> 1
------------------------------------------
Tags: @bsnkeiddo-4874@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_6.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_6.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_6.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_6.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_6.expectations.statusCode              | string            }}    |
* Expect response body contains "SCHEME_KEY_METADATA_INVALID_STRUCTURE"
* Fail if expectations are not met




Scenario 7: Scheme Key-metadata is not unique within the list of keys supplied.
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4875@

At most one key version for a given scheme key set version may be supplied for PP_P-scheme key.

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_7.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_7.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_7.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_7.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_7.expectations.statusCode              | string            }}    |
* Expect response body contains "UNIQUE_MATCHING_SCHEME_KEY_REQUIRED"
* Fail if expectations are not met




Scenario 8: Scheme Key-metadata environment is not identical for all supplied keys
----------------------------------------------------------------------------------
Tags: @bsnkeiddo-4876@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_8.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_8.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_8.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_8.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_8.expectations.statusCode              | string            }}    |
* Expect response body contains "SCHEME_KEYS_ENVIRONMENT_NON_UNIQUE"
* Fail if expectations are not met


Scenario 9: SP-key Pdd has duplicate header
-------------------------------------------
Tags: @bsnkeiddo-4877@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_9.request.signedEncryptedPseudonym     | binary              }}  |
    | schemeKeys                                | {{ scenario_9.request.schemeKeys                   | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_9.request.serviceProviderKeys          | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_9.request.targetClosingKey             | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_9.expectations.statusCode              | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


Scenario 10: SP-key Pcd has duplicate header
--------------------------------------------
Tags: @bsnkeiddo-4878@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_10.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_10.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_10.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_10.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_10.expectations.statusCode             | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met

Scenario 13: Non-specified headers are present in Service provider key Pdd
--------------------------------------------------------------------------
Tags: @bsnkeiddo-4881@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_13.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_13.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_13.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_13.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_13.expectations.statusCode             | string            }}    |
    | body                                      | {{ scenario_13.expectations.responseBody           | json              }}    |
    | json.decodedInput.signedEP.auditElement   | {{ scenario_13.expectations.auditValue             | string_resource   }}    |
    | json.decodedInput.signatureValue          | {{ scenario_13.expectations.signatureValue         | string_signature  }}    |
    | json.decodedPseudonym.pseudonymValue      | {{ scenario_13.expectations.pseudonymValue         | string_resource   }}    |
* Fail if expectations are not met


Scenario 14: Non-specified headers are present in Closing key
-------------------------------------------------------------
Tags: @bsnkeiddo-4882@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_14.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_14.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_14.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_14.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_14.expectations.statusCode             | string            }}    |
    | body                                      | {{ scenario_14.expectations.responseBody           | json              }}    |
    | json.decodedInput.signedEP.auditElement   | {{ scenario_14.expectations.auditValue             | string_resource   }}    |
    | json.decodedInput.signatureValue          | {{ scenario_14.expectations.signatureValue         | string_signature  }}    |
    | json.decodedPseudonym.pseudonymValue      | {{ scenario_14.expectations.pseudonymValue         | string_resource   }}    |
* Fail if expectations are not met


Scenario 15: SP-key Pdd header doesn't match casing
---------------------------------------------------
Tags: @bsnkeiddo-4883@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_15.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_15.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_15.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_15.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_15.expectations.statusCode             | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met

Scenario 16: SP-key Pcd header doesn't match casing
---------------------------------------------------
Tags: @bsnkeiddo-4884@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_16.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_16.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_16.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_16.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_16.expectations.statusCode             | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


Scenario 17: Key names for 'PP_P'-scheme keys must be unique
------------------------------------------------------------
Tags: @bsnkeiddo-4930@

The request is rejected if it contains duplicate object key names: uniqueness is determined by the object key name,
not the the value.

* Send payload
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | payload                                   | {{ scenario_17.request.payload                     | string          }}      |
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_17.expectations.statusCode             | string          }}      |
* Expect response body contains "JsonMappingException"
* Expect response body contains "Duplicate field"
* Expect response body contains "SEP_DUP_KEY"
* Fail if expectations are not met

Scenario 18: SP-key Pdd missing required header
-----------------------------------------------
Tags: @bsnkeiddo-5009@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_16.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_16.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_16.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_16.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_16.expectations.statusCode             | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met

Scenario 19: SP-key Pcd missing required header
-----------------------------------------------
Tags: @bsnkeiddo-5010@

* Create OASSignedEncryptedPseudonymRequest
    | propertyName                              | propertyValue                                      | propertyFilter          |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                  | {{ scenario_16.request.signedEncryptedPseudonym    | binary              }}  |
    | schemeKeys                                | {{ scenario_16.request.schemeKeys                  | schemeKeys          }}  |
    | serviceProviderKeys                       | {{ scenario_16.request.serviceProviderKeys         | serviceProviderKeys }}  |
    | targetClosingKey                          | {{ scenario_16.request.targetClosingKey            | string              }}  |
* Send OASSignedEncryptedPseudonymRequest
* Expect response matches
    | actualValue                               | expectedValue                                      | expectedValueType       |
    |-------------------------------------------|----------------------------------------------------|-------------------------|
    | statusCode                                | {{ scenario_16.expectations.statusCode             | string            }}    |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met

