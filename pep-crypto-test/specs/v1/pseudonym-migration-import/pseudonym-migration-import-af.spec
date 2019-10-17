Pseudonym migration: import to target OIN - Alternate Flow
==========================================================
Tags: pseudonym-migration-import,alternate-flow,@bsnkeiddo-5181@

* Load dataset from "/v1/pseudonym-migration-import/pseudonym-migration-import-af.yaml"
* Target default endpoint "/pseudonym-migration-import"

### Given

A OASPseudonymMigrationImportRequest contains one or more parameters based on which the migration
should be rejected.

### When

The request is sent to pseudonym migration import endpoint

### Then

The request is reject and no migration is performed.


### Description

Alternate flow:
--  mismatch between SP-migration key and MigrationIntermediaryPseudonym values:
--- source in MigrationIntermediaryPseudonym doesn't match SourceMigrant in SP-key
--- target in MigrationIntermediaryPseudonym doesn't match TargetMigrant in SP-key
--- schemeKeySetVersion
--- sourceKeySetVersion doesn't matchSourceMigrantKeySetVersion in SP-key
--- targetKeySetVersion doesn't match TargetMigrantKeySetVersion in SP-key
--  input no MigrationIntermediaryPseudonym
--  input missing SP-key


PTC_5181_1: Pseudonym migration import - EP migration source instead of EP migration target
--------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5182@

Migration import contains source migration key instead of target

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_1.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                     | expectedValueType       |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5181_1.expectations.statusCode                             | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5181_2: Pseudonym migration import - EP migration target 'migrationID' doesn't match with MigrationIntermediaryPseudonym
----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5183@

Migration import contains MigrationIntermediaryPseudonym which doesn't match de migrationID

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_2.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_2.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met




PTC_5181_4: Pseudonym migration import - EP migration target 'source oin' doesn't match with MigrationIntermediaryPseudonym
----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5185@

Migration import contains MigrationIntermediaryPseudonym 'source oin' which doesn't match the migration key 'source oin'

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_4.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_4.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_4.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met



PTC_5181_5: Pseudonym migration import - EP migration target 'target oin' doesn't match with MigrationIntermediaryPseudonym
----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5186@

Migration import contains MigrationIntermediaryPseudonym 'target oin' which doesn't match the migration key 'target oin'

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_5.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_5.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_5.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met


PTC_5181_6: Pseudonym migration import - EP migration target 'source ksv' doesn't match with MigrationIntermediaryPseudonym
---------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5187@

Migration import contains MigrationIntermediaryPseudonym 'source ksv' which doesn't match the migration key 'source ksv'

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_6.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_6.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_6.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met


PTC_5181_7: Pseudonym migration import - EP migration target 'target ksv' doesn't match with MigrationIntermediaryPseudonym
----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5188@

Migration import contains MigrationIntermediaryPseudonym 'target ksv' which doesn't match the migration key 'target ksv'

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_7.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_7.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_7.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Fail if expectations are not met


PTC_5181_8: Pseudonym migration import - No MigrationIntermediaryPseudonym as input
------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5189@

Migration import contains encryptedPseudonym instead of MigrationIntermediaryPseudonym source

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_8.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_8.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_8.expectations.statusCode                       | string            }}    |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met


PTC_5181_9: Pseudonym migration import - Missing header
-------------------------------------------------------
Tags: @bsnkeiddo-5190@

Migration import contains target migration key which missing a required header

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_9.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_9.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_9.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met



PTC_5181_10: Pseudonym migration import - Duplicate header
----------------------------------------------------------
Tags: @bsnkeiddo-5191@

Migration import contains target migration key which has duplicate required header

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_10.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_10.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_10.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met


PTC_5181_11: Pseudonym migration import - Header doesn't match casing
---------------------------------------------------------------------
Tags: @bsnkeiddo-5192@

Migration import contains target migration key which has a required header not conform the specified case

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_11.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_11.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_11.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met



PTC_5181_12: Pseudonym migration import - Header 'Type' contains value which doesn't match casing
------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5193@

Migration import contains target migration key with headervalue not conform specified case for header 'type'


* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5181_12.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5181_12.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5181_12.expectations.statusCode                       | string            }}    |
* Expect response body contains "MATCHING_MIGRATION_TARGET_KEY_REQUIRED"
* Expect response body contains "SERVICE_PROVIDER_KEY_PARSE_FAILED"
* Fail if expectations are not met
