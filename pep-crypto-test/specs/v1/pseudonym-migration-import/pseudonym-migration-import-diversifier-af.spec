Pseudonym migration with diversifier: import to target OIN - Alternate Flow
===========================================================================
Tags: pseudonym-migration-import,alternate-flow,@bsnkeiddo-5244@

* Load dataset from "/v1/pseudonym-migration-import/pseudonym-migration-import-diversifier-af.yaml"
* Target default endpoint "/pseudonym-migration-import"

### Given

A OASPseudonymMigrationImportRequest contains mismatch between diversifier(s) based on which the migration
should be rejected.

### When

The request is sent to pseudonym migration import endpoint

### Then

The request is reject and no migration is performed.


### Description

Alternate flow:
--  mismatch between SP-migration key and Pseudonym values:
--- Diversifier in Pseudonym doesn't match Diversifier in Source migration-key


PTC_5244_1: Pseudonym migration import - MigrationIntermediaryPseudonym with diversifier, EP migration key without diversifier
------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5252@

The 'diversifier' of the MigrationIntermediaryPseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5244_1.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5244_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                     | expectedValueType       |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5244_1.expectations.statusCode                             | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5244_2: Pseudonym migration import - MigrationIntermediaryPseudonym without diversifier, EP migration key with diversifier
------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5253@

The 'diversifier' of the MigrationIntermediaryPseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5244_2.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5244_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5244_2.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5244_3: Pseudonym migration import - MigrationIntermediaryPseudonym contains diversifier different from EP migration key
----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5254@

The 'diversifier' of the MigrationIntermediaryPseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5244_3.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5244_3.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5244_3.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met
