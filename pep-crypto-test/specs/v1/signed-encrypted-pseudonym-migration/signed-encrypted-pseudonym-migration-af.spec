SignedEncryptedPseudonym: OIN migration - Alternate Flow
========================================================
Tags: signed-encrypted-pseudonym-migration,alternate-flow,@bsnkeiddo-5291@

* Load dataset from "/v1/signed-encrypted-pseudonym-migration/signed-encrypted-pseudonym-migration-af.yaml"
* Target default endpoint "/signed-encrypted-pseudonym-migration"

### Given

An OASSignedEncryptedPseudonymMigrationRequest valid for migration of a SignedEncryptedPseudonym.

### When

The request is sent to migration endpoint

### Then

The response contains the converted DecryptedPseudonym from the request migrated according to the input
parameters.

### Description

--- error flows
---- invalid signed EP
---- mismatch between key-material and SEP:
----- different OINs
----- diversifier mismatch (EP migration keys)
----- different schemeKeySetVersions
----- different recipientKeySetVersions (EP migration source-key)
---- input no SEP
---- input SEP, unknown structure
---- input missing EP decryption key
---- input missing EP migration source key
---- input missing EP migration target key
---- input missing scheme key



PTC_5291_1,PTC_5291_2,PTC_5291_9,PTC_5291_10: SignedEncryptedPseudonym migration, (targetMigrant,targetMigrantKeySetVersion) not required, partially specified
--------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5320@

Request for SignedEncryptedPseudonym migration does not require specifying (targetMigrant, targetMigrantKeySetVersion),
and is valid without it.

Processing fails when only one of targetMigrant or targetMigrantKeySetVersion is specified, or both
are specified and one of targetMigrant or targetMigrantKeySetVersion leads not all required matches
being selected.


* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_1.request.signedEncryptedPseudonym                            | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5291_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | schemeKeys                    | {{ PTC_5291_1.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_1.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5291_1.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5291_1.request.targetMigrantKeySetVersion                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                    | propertyFilter          | status | token                                          |
    |-------------------------------|--------------------------------------------------|-------------------------|--------|------------------------------------------------|
    | targetMigrant                 | {{ PTC_5291_1.param.a.targetMigrant              | string              }}  |   500  | INVALID_MIGRATION_TARGET_KEY_SELECTION         |
    | targetMigrantKeySetVersion    | {{ PTC_5291_1.param.b.targetMigrantKeySetVersion | string              }}  |   500  | INVALID_MIGRATION_TARGET_KEY_SELECTION         |
    | targetMigrant                 | {{ PTC_5291_1.param.c.targetMigrant              | string              }}  |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | targetMigrantKeySetVersion    | {{ PTC_5291_1.param.d.targetMigrantKeySetVersion | string              }}  |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
* Fail if expectations are not met



PTC_5291_3: SignedEncryptedPseudonym migration, ambiguous targetMigrant for migrationID, (targetMigrant,targetMigrantKeySetVersion) required
--------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5321@

Request for SignedEncryptedPseudonym migration contains multiple valid keys for the migration based on the selection
by the source pseudonym and migrationID.

Multiple keys available for source and target differing only in either targetMigrant or targetMigrantKeySetVersion.

Processing should fail because  (targetMigrant,targetMigrantKeySetVersion) is required to uniquely identify
the 'EP migration target'-key.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_3.request.signedEncryptedPseudonym                             | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5291_3.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | schemeKeys                    | {{ PTC_5291_3.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_3.request.migrationID                                          | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_3.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY        |
    | serviceProviderKeys           | {{ PTC_5291_3.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY        |
* Fail if expectations are not met



PTC_5291_4: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP migration source'-key
----------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5322@

Based on a request for valid for migration of a SignedEncryptedPseudonym a single parameter for
'EP migration source'-key is varied, making the supplied 'EP migration source'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_4.request.signedEncryptedPseudonym                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_4.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_4.request.migrationID                                          | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_4.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.f.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.g.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_4.param.h.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
* Fail if expectations are not met



PTC_5291_5: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter for the 'EP migration target'-key
-----------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5323@

Based on a request for valid for migration of a SignedEncryptedPseudonym a single parameter is varied, making the
supplied 'EP migration target'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_5.request.signedEncryptedPseudonym                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_5.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_5.request.migrationID                                          | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_5.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.f.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.g.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5291_5.param.h.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
* Fail if expectations are not met




PTC_5291_6: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP Decryption'-key
----------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5324@

Based on a request for valid for migration of a SignedEncryptedPseudonym a single parameter for the 'EP Decryption'-key
is varied, making the supplied 'EP Decryption'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_6.request.signedEncryptedPseudonym                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_6.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_6.request.migrationID                                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_6.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5291_6.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5291_6.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5291_6.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
* Fail if expectations are not met


PTC_5291_7: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP Closing'-key
-------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5325@

Based on a request for valid for migration of a SignedEncryptedPseudonym a single parameter for the 'EP Closing'-key
is varied, making the supplied 'EP Decryption'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_7.request.signedEncryptedPseudonym                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_7.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_7.request.migrationID                                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_7.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5291_7.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5291_7.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5291_7.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
* Fail if expectations are not met



PTC_5291_8: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'PPp'-key
-------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5326@

Based on a request for valid for migration of a SignedEncryptedPseudonym a single parameter for the 'PPp'-key
is varied, making the supplied 'PPp'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_8.request.signedEncryptedPseudonym                             | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5291_8.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5291_8.request.migrationID                                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | schemeKeys                    | {{ PTC_5291_8.param.a.schemeKeys                  | schemeKeys          }} |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5291_8.param.b.schemeKeys                  | schemeKeys          }} |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
* Fail if expectations are not met




PTC_5291_11: SignedEncryptedPseudonym migration, matching migrationID and all key parameters match, 'EP migration source'-key has invalid headers
-------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5327@

Based on a request for valid for migration of a SignedEncryptedPseudonym, the 'EP migration source'-key is invalid
due to an invalid set of key headers.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_11.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_11.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_11.request.migrationID                                         | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest-template
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_11.param.a.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_11.param.b.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_11.param.c.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_11.param.d.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
* Fail if expectations are not met




PTC_5291_12: SignedEncryptedPseudonym migration, 'EP migration target'-key has invalid headers
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5328@

Based on a request for valid for migration of a SignedEncryptedPseudonym, the 'EP migration target'-key is invalid
due to an invalid set of key headers.

No (targetMigrant,targetMigrantKeySetVersion) specified.


* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_12.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_12.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_12.request.migrationID                                         | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_12.param.a.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_12.param.b.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_12.param.c.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5291_12.param.d.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
* Fail if expectations are not met


PTC_5291_13: SignedEncryptedPseudonym migration, invalid signature on SEP
-------------------------------------------------------------------------
Tags: @bsnkeiddo-5406@

Based on a request for valid for migration of a SignedEncryptedPseudonym, the SignedEnycryptedPseudonym
is replaced by a version for which the signature verification fails.

No (targetMigrant,targetMigrantKeySetVersion) specified.


* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5291_13.request.signedEncryptedPseudonym            | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5291_13.request.serviceProviderKeys                 | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5291_13.request.schemeKeys                          | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5291_13.request.migrationID                         | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5291_13.expectations.statusCode                     | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met



PTC_5291_14: SignedEncryptedPseudonym migration, invalid SEP input
------------------------------------------------------------------
Tags: @bsnkeiddo-5407@

Based on a request for valid for migration of a SignedEncryptedPseudonym, the SignedEncryptedPseudonym is
replaced with a malformed or or another ASN.1-structure.

No (targetMigrant,targetMigrantKeySetVersion) specified.


* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_14.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_14.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_14.request.migrationID                                         | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_14.param.a.signedEncryptedPseudonym   | binary              }} |   500  | ASN1_SEQUENCE_DECODER                          |
    | signedEncryptedPseudonym      | {{ PTC_5291_14.param.b.signedEncryptedPseudonym   | binary              }} |   500  | OID_NOT_SUPPORTED                              |
* Fail if expectations are not met




PTC_5291_15: SignedEncryptedPseudonym migration, SP-keys for recipients unrelated to source disallowed
------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5408@

Based on a request for valid for migration of a SignedEncryptedPseudonym, an additional key unrelated
to the source party is added causing the otherwise valid request to fail.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5291_15.request.signedEncryptedPseudonym                            | binary              }}  |
    | schemeKeys                    | {{ PTC_5291_15.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5291_15.request.migrationID                                         | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5291_15.param.a.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION   |
    | serviceProviderKeys           | {{ PTC_5291_15.param.b.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION   |
    | serviceProviderKeys           | {{ PTC_5291_15.param.c.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION   |
    | serviceProviderKeys           | {{ PTC_5291_15.param.d.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION   |
* Fail if expectations are not met
