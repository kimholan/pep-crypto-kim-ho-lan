Pseudonym migration: export to target OIN - Happy Flow
======================================================
Tags: pseudonym-migration-export,happy-flow,@bsnkeiddo-5123@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-hf.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A OASPseudonymRequest containing a DecryptedPseudonym, a source migration key
and a migrationID matching the provided migration key

### When

The request is sent to pseudonym migration export endpoint

### Then

The response contains the decoded DecryptedPseudonym from the request, and the MigrationIntermediaryPseudonym
for the export

### Description

happy flow:
--- different source OINs
--- different target OINs
--- with/without diversifiers
--- different schemeKeySetVersion
--- different target closingKey


PTC_5123_1: Pseudonym (type B, SKSV 1) migration export - source SP pseudonym A -> Intermediary SP pseudonym A
---------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5127@

Migrate the incoming pseudonym(BSN) to a different OIN

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5123_1.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5123_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5123_1.request.migrationID                                 | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                     | expectedValueType       |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5123_1.expectations.statusCode                             | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5123_1.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                          | {{ PTC_5123_1.expectations.responseBody                           | json              }}    |
* Fail if expectations are not met




PTC_5123_2: Pseudonym (type E, SKSV 10, diversifier) migration export - source SP pseudonym B -> Intermediary SP pseudonym B
-----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5128@

Migrate the incoming pseudonym (eIDAS) to a different OIN

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5123_2.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5123_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5123_2.request.migrationID                                 | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5123_2.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5123_2.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5123_2.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met



PTC_5123_3: Pseudonym migration export - Extra header
------------------------------------------------------
Tags: @bsnkeiddo-5129@

Migrate the incoming pseudonym to a different OIN - extra non existing headers
are ignored

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5123_3.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5123_3.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5123_3.request.migrationID                                 | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5123_3.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5123_3.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5123_3.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met


PTC_5123_4: Pseudonym migration export - different OIN, same KSV
----------------------------------------------------------------
Tags: @bsnkeiddo-5130@

Migrate the incoming pseudonym to a different OIN, same (target)KSV

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5123_4.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5123_4.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5123_4.request.migrationID                                 | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5123_4.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5123_4.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5123_4.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met
