Pseudonym migration with diversifier: import to target OIN - Happy Flow
=======================================================================
Tags: pseudonym-migration-import,diversifier,happy-flow,@bsnkeiddo-5242@

* Load dataset from "/v1/pseudonym-migration-import/pseudonym-migration-import-diversifier-hf.yaml"
* Target default endpoint "/pseudonym-migration-import"

### Given

A OASPseudonymMigrationImportRequest containing a MigrationIntermediaryPseudonym with diversifier and a target migration key
with diversifier matching the provided migration key

### When

The request is sent to pseudonym migration import endpoint

### Then

The response contains the MigrationIntermediaryPseudonym from the request, and the migrated Pseudonym for
the 'target' organization with diversifier

### Description

happy flow:
-- Migration import with diversifiers
--- diversifier with 1 Key-value pair
--- diversifier with multiple Key-value pairs


PTC_5242_1: MigrationIntermediaryPseudonym (type B, SKSV 1, with diversifier) conversion Intermediary SP pseudonym A -> target SP pseudonym A
----------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5247@

Migrate the incoming MigrationIntermediaryPseudonym(BSN) with diversifier to a different OIN.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5242_1.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5242_1.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                     | expectedValueType       |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5242_1.expectations.statusCode                             | string            }}    |
    | json.pseudonym                | {{ PTC_5242_1.expectations.pseudonym                              | binary            }}    |
    | body                          | {{ PTC_5242_1.expectations.responseBody                           | json              }}    |
* Fail if expectations are not met




PTC_5242_2: MigrationIntermediaryPseudonym (type E, SKSV 10, with diversifier) conversion Intermediary SP pseudonym B -> target SP pseudonym B
----------------------------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5248@

Migrate the incoming MigrationIntermediaryPseudonym (eIDAS) with diversifier to a different OIN.

* Create OASPseudonymMigrationImportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | migrationIntermediaryPseudonym| {{ PTC_5242_2.request.migrationIntermediaryPseudonym              | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5242_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
* Send OASPseudonymMigrationImportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5242_2.expectations.statusCode                       | string            }}    |
    | json.pseudonym                      | {{ PTC_5242_2.expectations.pseudonym                        | binary            }}    |
    | body                                | {{ PTC_5242_2.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met
