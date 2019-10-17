Sanity check SEI decryption
===========================
Tags: signed-encrypted-identity,sanity-check,@bsnkeiddo-4842@

* Load dataset from "/v1/signed-encrypted-identity/sanity_check.yaml"
* Target default endpoint "/signed-encrypted-identity"

### Given

A SignedEncryptedIdentity, SP-key and schemekey is provided as input for the decryption component

### When

The SignedEncryptedIdentity and/or keys have invalid content/invalid format or the input length is to big

### Then

No decrypted identity (BSN) is returned in the response message with corresponding error status

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



Scenario 2: Scheme key metadata is not parsable according to scheme key format
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4851@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_2.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_2.request.schemeKeys                 | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_2.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_2.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_2.expectations.statusCode            | string          }}      |
* Expect response body contains "SCHEME_KEY_METADATA_INVALID_STRUCTURE"
* Fail if expectations are not met



Scenario 3: Scheme key structure doesn't start with '0x04'
----------------------------------------------------------
Tags: @bsnkeiddo-4853@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_3.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_3.request.schemeKeys                 | base64ResourceMap   }}  |
    | serviceProviderKeys                     | {{ scenario_3.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_3.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_3.expectations.statusCode            | string          }}      |
* Expect response body contains "SCHEME_KEY_NOT_UNCOMPRESSED"
* Fail if expectations are not met



Scenario 4a: Scheme key total length < 81bytes
----------------------------------------------
Tags: @bsnkeiddo-4853@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_4a.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_4a.request.schemeKeys                | base64ResourceMap   }}  |
    | serviceProviderKeys                     | {{ scenario_4a.request.serviceProviderKeys       | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_4a.request.targetClosingKey          | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_4a.expectations.statusCode           | string          }}      |
* Expect response body contains "SCHEME_KEY_INVALID_LENGTH"
* Fail if expectations are not met


Scenario 4b: Scheme key total length > 81bytes
----------------------------------------------
Tags: @bsnkeiddo-5041@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_4b.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_4b.request.schemeKeys                | base64ResourceMap   }}  |
    | serviceProviderKeys                     | {{ scenario_4b.request.serviceProviderKeys       | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_4b.request.targetClosingKey          | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_4b.expectations.statusCode           | string          }}      |
* Expect response body contains "SCHEME_KEY_INVALID_LENGTH"
* Fail if expectations are not met


Scenario 5: Scenario 5: Scheme key points not on curve - BrainpoolP320r1
------------------------------------------------------------------------
Tags: @bsnkeiddo-4854@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_5.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_5.request.schemeKeys                 | base64ResourceMap   }}  |
    | serviceProviderKeys                     | {{ scenario_5.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_5.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_5.expectations.statusCode            | string          }}      |
* Expect response body contains "SCHEME_KEY_NOT_DECODABLE"
* Fail if expectations are not met




Scenario 6: Scheme Key schemeVersion <> 1
-----------------------------------------
Tags: @bsnkeiddo-4855@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_6.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_6.request.schemeKeys                 | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_6.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_6.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_6.expectations.statusCode            | string          }}      |
* Expect response body contains "SCHEME_KEY_METADATA_INVALID_STRUCTURE"
* Fail if expectations are not met



Scenario 7: Scheme Key-metadata is not unique within the list of keys supplied
------------------------------------------------------------------------------
Tags: @bsnkeiddo-4856@

At most one key version for a given scheme key set version may be supplied for IP_P-scheme key.

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_7.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_7.request.schemeKeys                 | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_7.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_7.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_7.expectations.statusCode            | string          }}      |
* Expect response body contains "UNIQUE_MATCHING_SCHEME_KEY_REQUIRED"
* Fail if expectations are not met



Scenario 8: Scheme key-metadata environment is not identical for all supplied keys
----------------------------------------------------------------------------------
Tags: @bsnkeiddo-4857@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_8.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_8.request.schemeKeys                 | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_8.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_8.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_8.expectations.statusCode            | string          }}   |
* Expect response body contains "SCHEME_KEYS_ENVIRONMENT_NON_UNIQUE"
* Fail if expectations are not met

Scenario 9: SP-key Idd has duplicate headers
--------------------------------------------
Tags: @bsnkeiddo-4858@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_9.request.signedEncryptedIdentity    | binary              }}  |
    | schemeKeys                              | {{ scenario_9.request.schemeKeys                 | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_9.request.serviceProviderKeys        | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_9.request.targetClosingKey           | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_9.expectations.statusCode            | string          }}   |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


Scenario 10: SP-key Idd missing required header
-----------------------------------------------
Tags: @bsnkeiddo-4859@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_10.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_10.request.schemeKeys                | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_10.request.serviceProviderKeys       | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_10.request.targetClosingKey          | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_10.expectations.statusCode           | string          }}      |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


Scenario 13: Non-specified headers are present in Service provider key Idd
--------------------------------------------------------------------------
Tags: @bsnkeiddo-4862@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_13.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_13.request.schemeKeys                | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_13.request.serviceProviderKeys       | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_13.request.targetClosingKey          | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_13.expectations.statusCode           | string          }}      |
    | body                                    | {{ scenario_13.expectations.responseBody         | json            }}      |
    | json.decodedInput.signedEI.auditElement | {{ scenario_13.expectations.auditValue           | string_resource }}      |
    | json.decodedInput.signatureValue        | {{ scenario_13.expectations.signatureValue       | string_signature}}      |
* Fail if expectations are not met


Scenario 14: SP-key Idd header doesn't match casing
---------------------------------------------------
Tags: @bsnkeiddo-4863@

* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_14.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_14.request.schemeKeys                | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_14.request.serviceProviderKeys       | serviceProviderKeys }}  |
    | targetClosingKey                        | {{ scenario_14.request.targetClosingKey          | string              }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_14.expectations.statusCode           | string          }}      |
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


Scenario 15: Recipient OIN of supplied Idd keys not unique
----------------------------------------------------------
Tags: @bsnkeiddo-4929@


* Create OASSignedEncryptedIdentityRequest
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | signedEncryptedIdentity                 | {{ scenario_15.request.signedEncryptedIdentity   | binary              }}  |
    | schemeKeys                              | {{ scenario_15.request.schemeKeys                | schemeKeys          }}  |
    | serviceProviderKeys                     | {{ scenario_15.request.serviceProviderKeys       | serviceProviderKeys }}  |
* Send OASSignedEncryptedIdentityRequest
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_15.expectations.statusCode           | string          }}      |
* Expect response body contains "SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE"
* Fail if expectations are not met



Scenario 16: Key names for 'IP_P'-scheme keys must be unique
------------------------------------------------------------
Tags: @bsnkeiddo-4931@

The request is rejected if it contains duplicate object key names: uniqueness is determined by the object key name,
not the the value.

* Send payload
    | propertyName                            | propertyValue                                    | propertyFilter          |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | payload                                 | {{ scenario_16.request.payload                   | string          }}      |
* Expect response matches
    | actualValue                             | expectedValue                                    | expectedValueType       |
    |-----------------------------------------|--------------------------------------------------|-------------------------|
    | statusCode                              | {{ scenario_16.expectations.statusCode           | string          }}      |
* Expect response body contains "JsonMappingException"
* Expect response body contains "Duplicate field"
* Expect response body contains "SEI_DUP_KEY"
* Fail if expectations are not met



