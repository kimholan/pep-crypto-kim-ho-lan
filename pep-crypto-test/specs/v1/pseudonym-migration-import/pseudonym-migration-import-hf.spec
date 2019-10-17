Pseudonym migration: import to target OIN - Happy Flow
======================================================
Tags: pseudonym-migration-import,happy-flow,@bsnkeiddo-5177@

* Load dataset from "/v1/pseudonym-migration-import/pseudonym-migration-import-hf.yaml"
* Target default endpoint "/pseudonym-migration-import"

### Given

A OASPseudonymMigrationImportRequest containing a MigrationIntermediaryPseudonym, a target migration key
and a migrationID matching the provided migration key

### When

The request is sent to pseudonym migration import endpoint

### Then

The response contains the MigrationIntermediaryPseudonym from the request, and the migrated Pseudonym for
the 'target' organization

### Description

happy flow:
--- different source OINs
--- different target OINs
--- different schemeKeySetVersion
--- different targetKeySetVersion
--- different sourceKeySetVersion
--- different migrationID


PTC_5177_1: MigrationIntermediaryPseudonym (type B, SKSV 1, without diversifier) migration import - Intermediary SP pseudonym A -> target SP pseudonym A
---------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5194@

Migrate the incoming MigrationIntermediaryPseudonym(BSN) to a different OIN.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5177_1.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5177_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                     | expectedValueType       |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5177_1.expectations.statusCode                             | string            }}    |
    | json.pseudonym                | {{ PTC_5177_1.expectations.pseudonym                              | binary            }}    |
    | body                          | {{ PTC_5177_1.expectations.responseBody                           | json              }}    |
* Fail if expectations are not met




PTC_5177_2: MigrationIntermediaryPseudonym (type E, SKSV 10, without diversifier) migration import - Intermediary SP pseudonym B -> target SP pseudonym B
---------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5195@

Migrate the incoming MigrationIntermediaryPseudonym (eIDAS) to a different OIN.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5177_2.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5177_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5177_2.expectations.statusCode                       | string            }}    |
    | json.pseudonym                      | {{ PTC_5177_2.expectations.pseudonym                        | binary            }}    |
    | body                                | {{ PTC_5177_2.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met



PTC_5177_3: MigrationIntermediaryPseudonym (type B, SKSV 1) migration import - Intermediary SP pseudonym -> target SP pseudonym (same KSV)
------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5196@

Migrate the incoming MigrationIntermediaryPseudonym to a different OIN, same (target)KSV.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5177_3.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5177_3.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5177_3.expectations.statusCode                       | string            }}    |
    | json.pseudonym                      | {{ PTC_5177_3.expectations.pseudonym                        | binary            }}    |
    | body                                | {{ PTC_5177_3.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met


PTC_5177_4: MigrationIntermediaryPseudonym (type B, SKSV 1) migration import - Intermediary SP pseudonym -> target SP pseudonym (same OIN, different target KSV)
----------------------------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5197@

Migrate the incoming MigrationIntermediaryPseudonym to a different OIN, same (target)OIN.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5177_4.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5177_4.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5177_4.expectations.statusCode                       | string            }}    |
    | json.pseudonym                      | {{ PTC_5177_4.expectations.pseudonym                        | binary            }}    |
    | body                                | {{ PTC_5177_4.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met



PTC_5177_5: MigrationIntermediaryPseudonym migration import - Extra header
--------------------------------------------------------------------------
Tags: @bsnkeiddo-5198@

Migrate the incoming MigrationIntermediaryPseudonym to a different OIN - extra non existing headers.
are ignored

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5177_5.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5177_5.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5177_5.expectations.statusCode                       | string            }}    |
    | json.pseudonym                      | {{ PTC_5177_5.expectations.pseudonym                        | binary            }}    |
    | body                                | {{ PTC_5177_5.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met
