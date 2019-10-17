Pseudonym migration: export to target OIN with diversifier - Happy Flow
=======================================================================
Tags: pseudonym-migration-export,happy-flow,diversifier,@bsnkeiddo-5241@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-diversifier-hf.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A OASPseudonymRequest containing a DecryptedPseudonym with diversifier, a source migration key with diversifier
and a migrationID matching the provided migration key

### When

The request is sent to pseudonym migration export endpoint

### Then

The response contains the decoded DecryptedPseudonym from the request, and the MigrationIntermediaryPseudonym
with diversifier for the export

### Description

happy flow:
-- Migration export with diversifiers
--- diversifier with 1 Key-value pair
--- diversifier with multiple1 Key-value pairs


PTC_5241_1: Pseudonym (type B, SKSV 1, diversifier) migration export - source SP pseudonym B -> Intermediary SP pseudonym B
---------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5245@

Migrate the incoming pseudonym(BSN) containing a Diversifier with 1 Key-Value pair to a different OIN

* Create OASPseudonymMigrationExportRequest
    | propertyName                        | propertyValue                                               | propertyFilter          |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | pseudonym                           | {{ PTC_5241_1.request.pseudonym                             | binary              }}  |
    | serviceProviderKeys                 | {{ PTC_5241_1.request.serviceProviderKeys                   | serviceProviderKeys }}  |
    | migrationID                         | {{ PTC_5241_1.request.migrationID                           | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5241_1.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5241_1.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5241_1.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met




PTC_5241_2: Pseudonym (type E, SKSV 10, diversifier) migration export - source SP pseudonym E -> Intermediary SP pseudonym E
-----------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5246@

Migrate the incoming pseudonym (eIDAS) containing a Diversifier with multiple Key-Value pairs to a different OIN

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                     | propertyFilter          |
    |-------------------------------|-------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5241_2.request.pseudonym                                   | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5241_2.request.serviceProviderKeys                         | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5241_2.request.migrationID                                 | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                         | expectedValue                                               | expectedValueType       |
    |-------------------------------------|-------------------------------------------------------------|-------------------------|
    | statusCode                          | {{ PTC_5241_2.expectations.statusCode                       | string            }}    |
    | json.migrationIntermediaryPseudonym | {{ PTC_5241_2.expectations.migrationIntermediaryPseudonym   | binary            }}    |
    | body                                | {{ PTC_5241_2.expectations.responseBody                     | json              }}    |
* Fail if expectations are not met
