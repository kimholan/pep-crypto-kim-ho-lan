Validation and/or decryption SignedDirectEncryptedIdentity fails - Alternate Flow
=================================================================================
Tags: signed-direct-encrypted-identity,alternate-flow,@bsnkeiddo-5412@

* Load dataset from "/v2/signed-direct-encrypted-identity/alternate_flows.yaml"
* Target default endpoint "/signed-direct-encrypted-identity"

### Given

A SignedDirectEncryptedIdentity, SP-key and schemekey is provided as input for the decryption component

### When

The SignedDirectEncryptedIdentity and/or keys are invalid or the combination does not match

### Then

No decrypted identity (BSN) is returned in the response message with corresponding error status

### Description

Alternate flows:
-- invalid signed DEI (signature won't validate)
-- mismatch between key-material and DEI:
--- different OINs
--- different schemeKeySetVersion
--- different recipientKeySetVersions
--- different AuthorizedParty
-- input not DEI
-- input DEI schemeVersion  <> '1'
-- input missing required schemekey
-- invalid structure DEI


PTC_5412_1: Signature verification fails
----------------------------------------
Tags: @bsnkeiddo-5454@

Externally modified SignedDirectEncryptedIdentity causes the signature verification to fail.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_1.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_1.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_1.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_1.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                              | expectedValueType       |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5412_1.expectations.statusCode                                      | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION"
* Fail if expectations are not met




PTC_5412_2: No matching 'EI Decryption'-key (ID_D)
--------------------------------------------------
Tags: @bsnkeiddo-5455@

Decryption attempt fails, because no matching 'EI Decryption'-key is supplied.
The matching key is modified in a single parameter to cause the mismatch.

* Template OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_2.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_2.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_2.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_2.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedIdentityRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5412_2.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5412_2.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5412_2.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5412_2.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5412_2.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5412_2.param.f.serviceProviderKeys         | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5412_2.param.g.serviceProviderKeys         | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
* Fail if expectations are not met




PTC_5412_4: No matching 'ECDSA verification'-key (U)
----------------------------------------------------
Tags: @bsnkeiddo-5456@

Decryption attempt fails, because no matching 'ECDSA verification'-key (U) is supplied.
The matching URN/key-value is modified in a single parameter to cause the mismatch.

* Template OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_4.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_4.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_4.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_4.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedIdentityRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | schemeKeys                    | {{ PTC_5412_4.param.a.schemeKeys                  | schemeKeys }}          |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5412_4.param.b.schemeKeys                  | schemeKeys }}          |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5412_4.param.c.schemeKeys                  | schemeKeys }}          |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5412_4.param.d.schemeKeys                  | schemeKeys }}          |   500  | SIGNATURE_VERIFICATION_FAILED                  |
* Fail if expectations are not met





PTC_5412_5: No matching authorizedParty
---------------------------------------
Tags: @bsnkeiddo-5457@

Decryption attempt fails, because the authorizedParty is missing from the request
or does not match the value in the SignedDirectEncryptedIdentity.

* Template OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_5.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_5.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_5.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_5.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedIdentityRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | authorizedParty               | {{ PTC_5412_5.param.a.authorizedParty             | string     }}          |   500  | DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED  |
    | authorizedParty               | {{ PTC_5412_5.param.b.authorizedParty             | string     }}          |   500  | DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED  |
* Fail if expectations are not met




PTC_5412_6: Wrong PP ASN.1-structure
------------------------------------
Tags: @bsnkeiddo-5467@

Decryption attempt fails, because the endpoint is supplied with a SignedEncryptedIdentity instead of a
SignedDirectEncryptedIdentity.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_6.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_6.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_6.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_6.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                              | expectedValueType       |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5412_6.expectations.statusCode                                      | string          }}      |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met





PTC_5412_7: Wrong scheme version
--------------------------------
Tags: @bsnkeiddo-5468@

Decryption attempt fails, because the SignedDirectEncryptedIdentity contains an unsupported schemeVersion.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_7.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_7.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_7.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_7.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                              | expectedValueType       |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5412_7.expectations.statusCode                                      | string          }}      |
* Expect response body contains "NO_MATCHES_DECRYPTION_KEY"
* Fail if expectations are not met





PTC_5412_8: Invalid PP ASN.1-structure
------------------------------------
Tags: @bsnkeiddo-5469@

The supplied 'signedDirectEncryptedIdentity' contains an unparsable/illegal ASN.1-structure.

* Create OASSignedDirectEncryptedIdentityRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedIdentity | {{ PTC_5412_8.request.signedDirectEncryptedIdentity                        | binary              }}  |
    | schemeKeys                    | {{ PTC_5412_8.request.schemeKeys                                           | schemeKeys          }}  |
    | serviceProviderKeys           | {{ PTC_5412_8.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | authorizedParty               | {{ PTC_5412_8.request.authorizedParty                                      | string              }}  |
* Send OASSignedDirectEncryptedIdentityRequest
* Expect response matches
    | actualValue                   | expectedValue                                                              | expectedValueType       |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5412_8.expectations.statusCode                                      | string          }}      |
* Expect response body contains "ASN1_SEQUENCE_DECODER"
* Fail if expectations are not met

