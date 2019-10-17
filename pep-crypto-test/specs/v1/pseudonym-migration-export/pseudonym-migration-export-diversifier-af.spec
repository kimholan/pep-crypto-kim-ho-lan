Pseudonym migration with diversifier: export to target OIN fails - Alternate Flow
=================================================================================
Tags: pseudonym-migration-export,diversifier,alternate-flow,@bsnkeiddo-5243@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-diversifier-af.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A OASPseudonymMigrationExportRequest contains mismatch between diversifier(s) based on which the migration
should be rejected.

### When

The request is sent to pseudonym migration export endpoint

### Then

The request is rejected and no migration is performed.


### Description

At least one 'serviceProviderKey'-element should be present and all must match
the schemeVersion, schemeKeySetVersion, diversifier and recipient of the incoming pseudonym
in addition to one element also matching the migrationID.

alternate flow:
-- mismatch between SP-migration key and MigrationIntermediaryPseudonym values:
--- Diversifier in MigrationIntermediaryPseudonym doesn't match Diversifier in Target migration-key



PTC_5243_1: Pseudonym migration export - Pseudonym with diversifier, EP migration key without diversifier
---------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5249@

The 'diversifier' of the Pseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5243_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5243_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5243_1.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5243_1.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5243_2: Pseudonym migration export - Pseudonym without diversifier, EP migration key with diversifier
---------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5250@

The 'diversifier' of the Pseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5243_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5243_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5243_2.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5243_2.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5243_3: Pseudonym migration export - Pseudonym contains diversifier different from EP migration key
-------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5251@

The 'diversifier' of the Pseudonym must match the 'diversifier' of the EP migration key

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5243_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5243_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5243_3.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5243_3.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met
