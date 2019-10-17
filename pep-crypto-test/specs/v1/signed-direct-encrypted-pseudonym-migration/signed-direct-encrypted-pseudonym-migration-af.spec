SignedDirectEncryptedPseudonym: OIN migration - Alternate Flow
==============================================================
Tags: signed-direct-encrypted-pseudonym-migration,alternate-flow,@bsnkeiddo-5413@

* Load dataset from "/v1/signed-direct-encrypted-pseudonym-migration/signed-direct-encrypted-pseudonym-migration-af.yaml"
* Target default endpoint "/signed-direct-encrypted-pseudonym-migration"

### Given

An OASSignedDirectEncryptedPseudonymMigrationRequest valid for migration of a SignedDirectEncryptedPseudonym.

### When

The request is sent to migration endpoint

### Then

The response contains the converted DecryptedPseudonym from the request migrated according to the input
parameters.

### Description

--- error flows
---- invalid signed EP
---- mismatch between key-material and SDEP:
----- different OINs
----- diversifier mismatch (EP migration keys)
----- different schemeKeySetVersions
----- different recipientKeySetVersions (EP migration source-key)
---- input no SDEP
---- input SDEP, unknown structure
---- input missing EP decryption key
---- input missing EP migration source key
---- input missing EP migration target key
---- input missing scheme key



PTC_5413_1,PTC_5413_2: SignedDirectEncryptedPseudonym migration, (targetMigrant,targetMigrantKeySetVersion) not required, partially specified
---------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5414@

Request for SignedDirectEncryptedPseudonym migration does not require specifying (targetMigrant, targetMigrantKeySetVersion),
and is valid without it.

Processing fails when only one of targetMigrant or targetMigrantKeySetVersion is specified, or both
are specified and one of targetMigrant or targetMigrantKeySetVersion leads not all required matches
being selected.


* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                             | propertyFilter          |
    |--------------------------------|---------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_1.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | serviceProviderKeys            | {{ PTC_5413_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | schemeKeys                     | {{ PTC_5413_1.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_1.request.migrationID                                         | string              }}  |
    | authorizedParty                | {{ PTC_5413_1.request.authorizedParty                                     | string              }}  |
    | targetMigrant                  | {{ PTC_5413_1.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion     | {{ PTC_5413_1.request.targetMigrantKeySetVersion                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                    | propertyFilter          | status | token                                          |
    |-------------------------------|--------------------------------------------------|-------------------------|--------|------------------------------------------------|
    | targetMigrant                 | {{ PTC_5413_1.param.a.targetMigrant              | string              }}  |   500  | INVALID_MIGRATION_TARGET_KEY_SELECTION         |
    | targetMigrantKeySetVersion    | {{ PTC_5413_1.param.b.targetMigrantKeySetVersion | string              }}  |   500  | INVALID_MIGRATION_TARGET_KEY_SELECTION         |
    | targetMigrant                 | {{ PTC_5413_1.param.c.targetMigrant              | string              }}  |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | targetMigrantKeySetVersion    | {{ PTC_5413_1.param.d.targetMigrantKeySetVersion | string              }}  |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
* Fail if expectations are not met



PTC_5413_3: SignedDirectEncryptedPseudonym migration, ambiguous targetMigrant for migrationID, (targetMigrant,targetMigrantKeySetVersion) required and partially specified
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5415@

Request for SignedDirectEncryptedPseudonym migration contains multiple valid keys for the migration based on the selection
by the source pseudonym and migrationID.

Multiple keys available for source and target differing only in either targetMigrant or targetMigrantKeySetVersion.

Processing should fail because  (targetMigrant,targetMigrantKeySetVersion) is required to uniquely identify
the 'EP migration target'-key.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_3.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | serviceProviderKeys            | {{ PTC_5413_3.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | schemeKeys                     | {{ PTC_5413_3.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_3.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_3.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_3.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY        |
    | serviceProviderKeys           | {{ PTC_5413_3.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY        |
* Fail if expectations are not met



PTC_5413_4: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP migration source'-key
----------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5417@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for
'EP migration source'-key is varied, making the supplied 'EP migration source'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_4.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_4.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_4.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_4.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_4.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.f.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.g.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_4.param.h.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_SOURCE_KEY                |
* Fail if expectations are not met



PTC_5413_5: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter for the 'EP migration target'-key
-----------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5418@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter is varied, making the
supplied 'EP migration target'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_5.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_5.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_5.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_5.request.authorizedParty                                      | string              }}  |
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_5.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.f.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.g.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
    | serviceProviderKeys           | {{ PTC_5413_5.param.h.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_MIGRATION_TARGET_KEY                |
* Fail if expectations are not met




PTC_5413_6: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP Decryption'-key
----------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5419@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for the 'EP Decryption'-key
is varied, making the supplied 'EP Decryption'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_6.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_6.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_6.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_6.request.authorizedParty                                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_6.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5413_6.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5413_6.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
    | serviceProviderKeys           | {{ PTC_5413_6.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DECRYPTION_KEY                      |
* Fail if expectations are not met


PTC_5413_7: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'EP Closing'-key
-------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5420@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for the 'EP Closing'-key
is varied, making the supplied 'EP Decryption'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_7.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_7.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_7.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_7.request.authorizedParty                                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_7.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5413_7.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5413_7.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
    | serviceProviderKeys           | {{ PTC_5413_7.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_CLOSING_KEY                         |
* Fail if expectations are not met


PTC_5413_8: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'DRKi'-key
-------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5421@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for the 'DRKi'-key
is varied, making the supplied 'DRKi'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_8.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_8.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_8.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_8.request.authorizedParty                                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_8.param.a.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DIRECT_RECEIVE_KEY                  |
    | serviceProviderKeys           | {{ PTC_5413_8.param.b.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DIRECT_RECEIVE_KEY                  |
    | serviceProviderKeys           | {{ PTC_5413_8.param.c.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DIRECT_RECEIVE_KEY                  |
    | serviceProviderKeys           | {{ PTC_5413_8.param.d.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DIRECT_RECEIVE_KEY                  |
    | serviceProviderKeys           | {{ PTC_5413_8.param.e.serviceProviderKeys         | serviceProviderKeys }} |   500  | NO_MATCHES_DIRECT_RECEIVE_KEY                  |
* Fail if expectations are not met

PTC_5413_9: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, except for 1 parameter in the 'U'-key
----------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5422@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for the 'U'-key
is varied, making the supplied 'U'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_9.request.signedDirectEncryptedPseudonym                       | binary              }}  |
    | serviceProviderKeys            | {{ PTC_5413_9.request.serviceProviderKeys                                  | serviceProviderKeys }}  |
    | migrationID                    | {{ PTC_5413_9.request.migrationID                                          | string              }}  |
    | authorizedParty                | {{ PTC_5413_9.request.authorizedParty                                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | schemeKeys                    | {{ PTC_5413_9.param.a.schemeKeys                  | schemeKeys          }} |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5413_9.param.b.schemeKeys                  | schemeKeys          }} |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5413_9.param.c.schemeKeys                  | schemeKeys          }} |   500  | MATCHING_SCHEME_KEY_NOT_FOUND                  |
    | schemeKeys                    | {{ PTC_5413_9.param.d.schemeKeys                  | schemeKeys          }} |   500  | SIGNATURE_VERIFICATION_FAILED                  |
* Fail if expectations are not met


PTC_5413_10: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, authorizedParty does not match
----------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5423@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym a single parameter for the 'PPp'-key
is varied, making the supplied 'PPp'-key a mismatch.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_10.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | serviceProviderKeys            | {{ PTC_5413_10.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | schemeKeys                     | {{ PTC_5413_10.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_10.request.migrationID                                         | string              }}  |
    | authorizedParty                | {{ PTC_5413_10.request.authorizedParty                                     | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | authorizedParty               | {{ PTC_5413_10.param.a.authorizedParty            | string              }} |   500  | DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED  |
    | authorizedParty               | {{ PTC_5413_10.param.b.authorizedParty            | string              }} |   500  | DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED  |
* Fail if expectations are not met


PTC_5413_11: SignedDirectEncryptedPseudonym migration, matching migrationID and all key parameters match, 'EP migration source'-key has invalid headers
-------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5424@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym, the 'EP migration source'-key is invalid
due to an invalid set of key headers.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_11.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_11.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_11.request.migrationID                                         | string              }}  |
    | authorizedParty                | {{ PTC_5413_11.request.authorizedParty                                     | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest-template
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_11.param.a.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_11.param.b.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_11.param.c.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_11.param.d.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
* Fail if expectations are not met




PTC_5413_12: SignedDirectEncryptedPseudonym migration, 'EP migration target'-key has invalid headers
----------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5425@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym, the 'EP migration target'-key is invalid
due to an invalid set of key headers.

No (targetMigrant,targetMigrantKeySetVersion) specified.


* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_12.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | schemeKeys                     | {{ PTC_5413_12.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_12.request.migrationID                                         | string              }}  |
    | authorizedParty                | {{ PTC_5413_12.request.authorizedParty                                     | string              }}  |
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                                          |
    |-------------------------------|---------------------------------------------------|------------------------|--------|------------------------------------------------|
    | serviceProviderKeys           | {{ PTC_5413_12.param.a.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_12.param.b.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_12.param.c.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
    | serviceProviderKeys           | {{ PTC_5413_12.param.d.serviceProviderKeys        | serviceProviderKeys }} |   500  | SERVICE_PROVIDER_KEY_PARSE_FAILED              |
* Fail if expectations are not met



PTC_5413_13: SignedDirectEncryptedPseudonym migration, invalid signature on SDEP
--------------------------------------------------------------------------------
Tags: @bsnkeiddo-5462@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym, the SignedDirectEncryptedPseudonym
is replaced by a version for which the signature verification fails.

No (targetMigrant,targetMigrantKeySetVersion) specified.


* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5413_13.request.signedDirectEncryptedPseudonym      | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5413_13.request.serviceProviderKeys                 | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5413_13.request.schemeKeys                          | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5413_13.request.migrationID                         | string              }}  |
    | authorizedParty                         | {{ PTC_5413_13.request.authorizedParty                     | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5413_13.expectations.statusCode                     | string          }}      |
* Expect response body contains "SIGNATURE_VERIFICATION_FAILED"
* Fail if expectations are not met


PTC_5413_14, PTC_5413_15: SignedDirectEncryptedPseudonym migration, invalid SDEP
--------------------------------------------------------------------------------
Tags: @bsnkeiddo-5426@

Based on a request for valid for migration of a SignedDirectEncryptedPseudonym, the provided
SignedDirectEncryptedPseudonym is invalid for several reasons.

No (targetMigrant,targetMigrantKeySetVersion) specified.

* Template OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                   | propertyValue                                                              | propertyFilter          |
    |--------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_14.request.signedDirectEncryptedPseudonym                      | binary              }}  |
    | serviceProviderKeys            | {{ PTC_5413_14.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | schemeKeys                     | {{ PTC_5413_14.request.schemeKeys                                          | schemeKeys          }}  |
    | migrationID                    | {{ PTC_5413_14.request.migrationID                                         | string              }}  |
    | authorizedParty                | {{ PTC_5413_14.request.authorizedParty                                     | string              }}  |
* Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                   | propertyValue                                         | propertyFilter | status | token                                          |
    |--------------------------------|-------------------------------------------------------|----------------|--------|------------------------------------------------|
    | signedDirectEncryptedPseudonym | {{ PTC_5413_14.param.a.signedDirectEncryptedPseudonym | binary }}      |   500  | ASN1_SEQUENCE_DECODER                          |
    | signedDirectEncryptedPseudonym | {{ PTC_5413_14.param.b.signedDirectEncryptedPseudonym | binary }}      |   500  | OID_NOT_SUPPORTED                              |
* Fail if expectations are not met
