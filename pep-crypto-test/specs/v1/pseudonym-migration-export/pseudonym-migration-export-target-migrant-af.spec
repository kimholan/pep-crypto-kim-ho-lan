Pseudonym migration: export to target OIN - target OIN + RKSV check - Alternate Flow
====================================================================================
Tags: pseudonym-migration-export,alternate-flow,@bsnkeiddo-5271@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-target-migrant-af.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A OASPseudonymMigrationExportRequest specifying values for one or both of (targetMigrant,targetMigrantKeySetVersion)
based on which the migration should be rejected.

### When

The OASPseudonymMigrationExportRequest is sent to service.

### Then

The request is rejected.

### Description

Error flow:
---input missing SP-key



PTC_5271_1: Pseudonym with 1 diversifier, 1 key with 2 diversifiers provided , matches (targetMigrant,targetMigrantKeySetVersion)
---------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5275@

Request contains a 'EP migration source'-key matching the pseudonym and the
optional (targetMigrant,targetMigrantKeySetVersion), but differs in diversifiers:
pseudonym has 1 diversifier, whilst key has 2 diversifiers.


* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_1.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_1.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_1.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_1.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met





PTC_5271_2: Pseudonym with 1 diversifier, 1 key with 0 diversifiers provided matching (targetMigrant,targetMigrantKeySetVersion)
-----------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5276@

Request contains a 'EP migration source'-key matching the pseudonym and the
optional (targetMigrant,targetMigrantKeySetVersion), but differs in diversifiers:
pseudonym has 1 diversifier, whilst key has 0 diversifiers.


* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_2.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_2.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_2.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_2.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met






PTC_5271_3: Pseudonym with no diversifier, 1 key with 1 diversifier provided, matching (targetMigrant,targetMigrantKeySetVersion)
---------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5277@

Request contains a 'EP migration source'-key matching the pseudonym and the
optional (targetMigrant,targetMigrantKeySetVersion), but differs in diversifiers:
pseudonym has 0 diversifier, whilst key has 1 diversifier.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_3.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_3.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_3.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_3.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met





PTC_5271_4: 1 Key provided, matches pseudonym, but differs from targetMigrant in (targetMigrant,targetMigrantKeySetVersion)
---------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5278@

Request contains one or more 'EP migration source'-keys matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, but none but matches the targetMigrant.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_4.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_4.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_4.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_4.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_4.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met






PTC_5271_5: 1 Key provided, matches pseudonym, but differs from targetMigrantKeySetVersion in (targetMigrant,targetMigrantKeySetVersion)
----------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5279@

Request contains one or more 'EP migration source'-keys matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, but none but matches the targetMigrantKeySetVersion.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_5.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_5.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_5.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_5.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_5.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met




PTC_5271_6: 1 Key provided, matches pseudonym, no targetMigrant specified for (targetMigrant,targetMigrantKeySetVersion)
------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5280@

Request contains one or more 'EP migration source'-keys matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant is null or missing, targetMigrantKeySetVersion matches.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_6.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_6.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_6.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_6.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_6.expectations.statusCode                                     | string          }}      |
* Expect response body contains "INVALID_MIGRATION_TARGET_KEY_SELECTION"
* Fail if expectations are not met


PTC_5271_7: 1 Key provided, matches pseudonym, no targetMigrantKeySetVersion specified for (targetMigrant,targetMigrantKeySetVersion)
-------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5281@

Request contains one or more 'EP migration source'-keys matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant matches, targetMigrantKeySetVersion is null or missing.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_7.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_7.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_7.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_7.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_7.expectations.statusCode                                     | string          }}      |
* Expect response body contains "INVALID_MIGRATION_TARGET_KEY_SELECTION"
* Fail if expectations are not met





PTC_5271_8: 2 Keys provided matching pseudonym for differing targetMigrant, no (targetMigrant,targetMigrantKeySetVersion) specified
------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5282@

Request contains 2 'EP migration source'-keys for different targetMigrant matching the pseudonym, targetMigrant and targetMigrantKeySetVersion is null or missing.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_8.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_8.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_8.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_8.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_8.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met





PTC_5271_9: 2 Keys provided matching pseudonym for differing targetMigrantKeySetVersion, no (targetMigrant,targetMigrantKeySetVersion) specified
------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5283@

Request contains 2 'EP migration source'-keys for different targetMigrantKeySetVersion matching the pseudonym, targetMigrant and targetMigrantKeySetVersion is null or missing.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_9.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_9.request.migrationID                                         | string              }}  |
    | targetMigrant                 | {{ PTC_5271_9.request.targetMigrant                                       | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_9.request.targetMigrantKeySetVersion                          | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_9.expectations.statusCode                                     | string          }}      |
* Expect response body contains "NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met




PTC_5271_10: 1 Key provided matching pseudonym for (targetMigrant,targetMigrantKeySetVersion) specified, sourceMigrant differs
------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5284@

Request contains one or more 'EP migration source'-keys, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant and  targetMigrantKeySetVersion matches, no matching sourceMigrant for the pseudonym.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_10.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_10.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_10.request.migrationID                                        | string              }}  |
    | targetMigrant                 | {{ PTC_5271_10.request.targetMigrant                                      | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_10.request.targetMigrantKeySetVersion                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_10.expectations.statusCode                                    | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met



PTC_5271_11: Pseudonym (type B, SKSV 1) migration export - matching target OIN and target RKSV - no matching source-KSV
-----------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5285@

Request contains one or more 'EP migration source'-keys, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant and  targetMigrantKeySetVersion matches, no matching sourceMigrantKeySetVersion for the pseudonym.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_11.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_11.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_11.request.migrationID                                        | string              }}  |
    | targetMigrant                 | {{ PTC_5271_11.request.targetMigrant                                      | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_11.request.targetMigrantKeySetVersion                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_11.expectations.statusCode                                    | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met



PTC_5271_12: Pseudonym (type B, SKSV 1) migration export - matching target OIN and target RKSV - no matching SKSV
-----------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5286@

Request contains one or more 'EP migration source'-keys, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant and  targetMigrantKeySetVersion matches, no matching schemeKeySetVersion.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_12.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_12.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_12.request.migrationID                                        | string              }}  |
    | targetMigrant                 | {{ PTC_5271_12.request.targetMigrant                                      | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_12.request.targetMigrantKeySetVersion                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_12.expectations.statusCode                                    | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met



PTC_5271_13: 'EP migration target'-key instead of 'EP migration source', matches pseudonym and (targetMigrant,targetMigrantKeySetVersion) specified
---------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5298@

Request contains one 'EP migration target'-key matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant and targetMigrantKeySetVersion matches.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_13.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_13.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_13.request.migrationID                                        | string              }}  |
    | targetMigrant                 | {{ PTC_5271_13.request.targetMigrant                                      | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_13.request.targetMigrantKeySetVersion                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_13.expectations.statusCode                                    | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met




PTC_5271_14: 1 Key provided matching pseudonym specified for (targetMigrant,targetMigrantKeySetVersion), but differs in migrationID
-----------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5299@

Request contains one or more 'EP migration source'-keys matching the pseudonym, optional (targetMigrant,targetMigrantKeySetVersion)
is specified, targetMigrant and targetMigrantKeySetVersion matched, no matching migrationID.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5271_14.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5271_14.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5271_14.request.migrationID                                        | string              }}  |
    | targetMigrant                 | {{ PTC_5271_14.request.targetMigrant                                      | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5271_14.request.targetMigrantKeySetVersion                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5271_14.expectations.statusCode                                    | string          }}      |
* Expect response body contains "NO_MATCHES_MIGRATION_SOURCE_KEY"
* Fail if expectations are not met
