Pseudonym migration: export to target OIN - target OIN and target KSV - Happy Flow
==================================================================================
Tags: pseudonym-migration-export-target-migrant,happy-flow,@bsnkeiddo-5270@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-target-migrant-hf.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A valid OASPseudonymMigrationExportRequest specifying values for targetMigrant and targetMigrantKeySetVersion.

### When

The OASPseudonymMigrationExportRequest is sent to service.

### Then

The service responds with a OASMigrationIntermediaryPseudonymResponse containing the
MigrationIntermediaryPseudonym converted from the inbound DecryptedPseudonym using the
key material specified by the selection criteria in the request.

### Description

happy flow:
---different target OIN
---different target RKSV


PTC_5270_1: Type B, 1 diversifier, Single key provided, targetMigrant and targetMigrantKeySetVersion specified
--------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5272@

Request contains a single 'EP migration source'-key and specifies the optional values for
'targetMigrant' and 'targetMigrantKeySetVersion' matching the provided key.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5270_1.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5270_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5270_1.request.migrationID                                 | string              }}  |
    | targetMigrant                 | {{ PTC_5270_1.request.targetMigrant                               | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5270_1.request.targetMigrantKeySetVersion                  | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5270_1.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5270_1.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5270_1.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met






PTC_5270_2: Type E, 2 diversifiers, Single key provided, targetMigrant and targetMigrantKeySetVersion specified
---------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5273@

Request contains a single 'EP migration source'-key and specifies the optional values for
'targetMigrant' and 'targetMigrantKeySetVersion' matching the provided key.


* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5270_2.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5270_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5270_2.request.migrationID                                 | string              }}  |
    | targetMigrant                 | {{ PTC_5270_2.request.targetMigrant                               | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5270_2.request.targetMigrantKeySetVersion                  | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5270_2.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5270_2.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5270_2.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met






PTC_5270_3: Type B, no diversifier, Multiple keys provided, targetMigrant and targetMigrantKeySetVersion specified
------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5274@

Request contains multiple 'EP migration source'-keys, with multiple keys matching the pseudonym based on
(Source,SourceKeySetVersion,MigrationID). Specifying the optional (targetMigrant,targetMigrantKeySetVersion)
is required to enable the unique selection, without which the request would be an error.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5270_3.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5270_3.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5270_3.request.migrationID                                 | string              }}  |
    | targetMigrant                 | {{ PTC_5270_3.request.targetMigrant                               | string              }}  |
    | targetMigrantKeySetVersion    | {{ PTC_5270_3.request.targetMigrantKeySetVersion                  | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5270_3.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5270_3.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5270_3.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met

