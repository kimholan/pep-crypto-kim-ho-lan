Validation and/or decryption SignedDirectEncryptedPseudonym with diversifier fails - Alternate Flow
===================================================================================================
Tags: signed-direct-encrypted-pseudonym,pseudonym,diversifier,alternate-flow,@bsnkeiddo-5079@

* Load dataset from "/v1/signed-direct-encrypted-pseudonym/alternate_flows_diversifier.yaml"
* Target default endpoint "/signed-direct-encrypted-pseudonym"

### Given

A SignedDirectEncryptedPseudonym with diversifier, SP-key, schemekey and closingkey is provided as input for the decryption component

### When

The SignedDirectEncryptedPseudonym and/or keys are invalid or the combination does not match

### Then

No Pseudonym is returned in the response message with corresponding error status

### Description

Alternate flows
-- EP/DEP with diversifier - invalid signature
-- DEP with no matching DRKi (Only DEP or only DRKi with diversifier)


Scenario 1: SignedDirectEncryptedPseudonym with 1 DiversifierKeyValuePair - invalid signed
------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5060@

U key content is invalid
* Create OASSignedDirectEncryptedPseudonymRequest
     | propertyName                        | propertyValue                                                              | propertyFilter          |
     |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
     | signedDirectEncryptedPseudonym      | {{ scenario_1.request.signedDirectEncryptedPseudonym                       | binary              }}  |
     | schemeKeys                          | {{ scenario_1.request.schemeKeys                                           | schemeKeys          }}  |
     | serviceProviderKeys                 | {{ scenario_1.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
     | targetClosingKey                    | {{ scenario_1.request.targetClosingKey                                     | string              }}  |
     | authorizedParty                     | {{ scenario_1.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
     | actualValue                                           | expectedValue                                            | expectedValueType       |
     |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
     | statusCode                                            | {{ scenario_1.expectations.statusCode                    | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met



Scenario 2: SignedDirectEncryptedPseudonym with 2 DiversifierKeyValuePairs - invalid signed
-------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5061@

U key content is invalid
* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                        | propertyValue                                                              | propertyFilter          |
    |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym      | {{ scenario_2.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                          | {{ scenario_2.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys                 | {{ scenario_2.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | targetClosingKey                    | {{ scenario_2.request.targetClosingKey                                     | string              }}  |
    | authorizedParty                     | {{ scenario_2.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                            | expectedValueType       |
    |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
    | statusCode                                            | {{ scenario_2.expectations.statusCode                    | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met



Scenario 3: SignedDire  ctEncryptedPseudonym with 3 DiversifierKeyValuePairs - invalid signed
-------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5062@

U key content is invalid
* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                        | propertyValue                                                              | propertyFilter          |
    |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym      | {{ scenario_3.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | schemeKeys                          | {{ scenario_3.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys                 | {{ scenario_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey                    | {{ scenario_3.request.targetClosingKey                                    | string              }}  |
    | authorizedParty                     | {{ scenario_3.request.authorizedParty                                     | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                            | expectedValueType       |
    |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
    | statusCode                                            | {{ scenario_3.expectations.statusCode                   | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met



Scenario 4: SignedDirectEncryptedPseudonym - DEP contains diversifier, DRKi has no diversifier
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5063@

* Create OASSignedDirectEncryptedPseudonymRequest
    | propertyName                        | propertyValue                                                              | propertyFilter          |
    |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym      | {{ scenario_4.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | schemeKeys                          | {{ scenario_4.request.schemeKeys                                          | schemeKeys          }}  |
    | serviceProviderKeys                 | {{ scenario_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey                    | {{ scenario_4.request.targetClosingKey                                    | string              }}  |
    | authorizedParty                     | {{ scenario_4.request.authorizedParty                                     | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
    | actualValue                                           | expectedValue                                            | expectedValueType       |
    |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
    | statusCode                                            | {{ scenario_4.expectations.statusCode                   | string          }}      |
* Expect response body contains "MATCHING_DIRECT_RECEIVE_KEY_REQUIRED"
* Fail if expectations are not met



Scenario 5: SignedDirectEncryptedPseudonym - DRKi contains diversifier, DEP has no diversifier
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5064@

* Create OASSignedDirectEncryptedPseudonymRequest
     | propertyName                        | propertyValue                                                              | propertyFilter          |
     |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
     | signedDirectEncryptedPseudonym      | {{ scenario_5.request.signedDirectEncryptedPseudonym                      | binary              }}  |
     | schemeKeys                          | {{ scenario_5.request.schemeKeys                                          | schemeKeys          }}  |
     | serviceProviderKeys                 | {{ scenario_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
     | targetClosingKey                    | {{ scenario_5.request.targetClosingKey                                    | string              }}  |
     | authorizedParty                     | {{ scenario_5.request.authorizedParty                                     | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
     | actualValue                                           | expectedValue                                            | expectedValueType       |
     |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
     | statusCode                                            | {{ scenario_5.expectations.statusCode                   | string          }}      |
* Expect response body contains "MATCHING_DIRECT_RECEIVE_KEY_REQUIRED"
* Fail if expectations are not met



Scenario 6: SignedDirectEncryptedPseudonym - DRKi contains diversifier different from DEP
-----------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5065@

Diversifier in SDEP is different from DRKi diversifier
* Create OASSignedDirectEncryptedPseudonymRequest
     | propertyName                        | propertyValue                                                              | propertyFilter          |
     |-------------------------------------|----------------------------------------------------------------------------|-------------------------|
     | signedDirectEncryptedPseudonym      | {{ scenario_6.request.signedDirectEncryptedPseudonym                      | binary              }}  |
     | schemeKeys                          | {{ scenario_6.request.schemeKeys                                          | schemeKeys          }}  |
     | serviceProviderKeys                 | {{ scenario_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
     | targetClosingKey                    | {{ scenario_6.request.targetClosingKey                                    | string              }}  |
     | authorizedParty                     | {{ scenario_6.request.authorizedParty                                     | string              }}  |
* Send OASSignedDirectEncryptedPseudonymRequest
* Expect response matches
     | actualValue                                           | expectedValue                                            | expectedValueType       |
     |-------------------------------------------------------|----------------------------------------------------------|-------------------------|
     | statusCode                                            | {{ scenario_6.expectations.statusCode                   | string          }}      |
* Expect response body contains "MATCHING_DIRECT_RECEIVE_KEY_REQUIRED"
* Fail if expectations are not met
