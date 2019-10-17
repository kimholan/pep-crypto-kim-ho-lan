Pseudonym migration: export to target OIN fails - Alternate Flow
================================================================
Tags: pseudonym-migration-export,alternate-flow,@bsnkeiddo-5124@

* Load dataset from "/v1/pseudonym-migration-export/pseudonym-migration-export-af.yaml"
* Target default endpoint "/pseudonym-migration-export"

### Given

A OASPseudonymMigrationExportRequest contains one or more parameters based on which the migration
should be rejected.

### When

The request is sent to pseudonym migration export endpoint

### Then

The request is rejected and no migration is performed.


### Description

At least one 'serviceProviderKey'-element should be present and all must match
the schemeVersion, schemeKeySetVersion, and recipient of the incoming pseudonym
in addition to one element also matching the migrationID.

alternate flow:
-- mismatch between SP-key and Pseudonym:
--- different OINs
--- schemeKeySetVersion
--- recipientKeySetVersion (differs from SourceMigrantKeySetVersion)
-- input no pseudonym
-- input missing SP-key





PTC_5124_1: Pseudonym migration export - No migrationID
-------------------------------------------------------
Tags: @bsnkeiddo-5131@

The 'migrationID' must be specified.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_1.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_1.expectations.statusCode                                     | string          }}      |
* Expect response body contains "1 constraint violation"
* Expect response body contains "property path: processRequest.arg0.migrationID"
* Fail if expectations are not met




PTC_5124_2: Pseudonym migration export - different migrationID
--------------------------------------------------------------
Tags: @bsnkeiddo-5132@

No migrationID found in 'serviceProviderKeys' for specified 'migrationID'.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_2.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_2.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met






PTC_5124_3: Pseudonym migration export - No EP migration source key
-------------------------------------------------------------------
Tags: @bsnkeiddo-5133@

Migration fails due to missing EP migration source key

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_3.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_3.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met





PTC_5124_4: Pseudonym migration export - different source OIN
-------------------------------------------------------------
Tags: @bsnkeiddo-5134@

Migration fails due to none of the provided service provider keys matching the
pseudonym's OIN, whilst all other parameters do match.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_4.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_4.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_4.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met





PTC_5124_5: Pseudonym migration export - different source SKSV
--------------------------------------------------------------
Tags: @bsnkeiddo-5135@

Migration fails due to none of the provided service provider keys matching the
pseudonym's SchemeKeySetVersion, whilst all other parameters do match.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_5.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_5.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_5.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met







PTC_5124_6: Pseudonym migration export - different source-KSV
-------------------------------------------------------------
Tags: @bsnkeiddo-5136@

Migration fails due to none of the provided service provider keys matching the
pseudonym's RKSV, whilst all other parameters do match.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_6.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_6.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_6.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met






PTC_5124_7: Pseudonym migration export - No pseudonym as input
--------------------------------------------------------------
Tags: @bsnkeiddo-5137@

Migration fails due to no given Pseudonym as input.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_7.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_7.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_7.expectations.statusCode                                     | string          }}      |
* Expect response body contains "OID_NOT_SUPPORTED"
* Fail if expectations are not met





PTC_5124_8: Pseudonym migration export - Missing header
-------------------------------------------------------
Tags: @bsnkeiddo-5138@

Migration fails due to missing required header in migration key.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_8.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_8.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_8.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met





PTC_5124_9: Pseudonym migration export - Duplicate header
---------------------------------------------------------
Tags: @bsnkeiddo-5139@

Migration fails due to duplicate required header in migration key.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_9.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_9.request.migrationID                                         | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_9.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met



PTC_5124_10: Pseudonym migration export - Header doesn't match casing
---------------------------------------------------------------------
Tags: @bsnkeiddo-5140@

Migration fails due to required header in migration key is specified with incorrect case.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_10.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_10.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_10.request.migrationID                                        | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_10.expectations.statusCode                                    | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met



PTC_5124_11: Pseudonym migration export - Header 'Type' contains value which doesn't match casing
------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5141@

Migration fails due to required header 'type' in migration key contains a value with incorrect case.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_11.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_11.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_11.request.migrationID                                        | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_11.expectations.statusCode                                    | string          }}      |
* Expect response body contains "MATCHING_MIGRATION_SOURCE_KEY_REQUIRED"
* Fail if expectations are not met


PTC_5124_12: Pseudonym migration export - EP migration source keys 'SourceMigrant' not the same OIN
---------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5158@

Migration fails due to required header 'type' in migration key contains a value with incorrect case.

* Create OASPseudonymMigrationExportRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_5124_12.request.pseudonym                                          | binary              }}  |
    | serviceProviderKeys           | {{ PTC_5124_12.request.serviceProviderKeys                                | serviceProviderKeys }}  |
    | migrationID                   | {{ PTC_5124_12.request.migrationID                                        | string              }}  |
* Send OASPseudonymMigrationExportRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_5124_12.expectations.statusCode                                    | string          }}      |
* Expect response body contains "SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE"
* Fail if expectations are not met

