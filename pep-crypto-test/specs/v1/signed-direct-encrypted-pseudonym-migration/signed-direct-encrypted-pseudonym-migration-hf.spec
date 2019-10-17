SignedDirectEncryptedPseudonym: OIN migration - Happy Flow
==========================================================
Tags: signed-direct-encrypted-pseudonym-migration,happy-flow,@bsnkeiddo-5401@

* Load dataset from "/v1/signed-direct-encrypted-pseudonym-migration/signed-direct-encrypted-pseudonym-migration-hf.yaml"
* Target default endpoint "/signed-direct-encrypted-pseudonym-migration"
* Specification properties
    | propertyName                             | propertyValue                                              |
    |------------------------------------------|------------------------------------------------------------|
    | OASSignedDirectEncryptedPseudonymRequest | /signed-direct-encrypted-pseudonym                         |
    | OASPseudonymMigrationExportRequest       | /pseudonym-migration-export                                |
    | OASPseudonymMigrationImportRequest       | /pseudonym-migration-import                                |


### Given

An OASSignedDirectEncryptedPseudonymMigrationRequest valid for migration of a SignedDirectEncryptedPseudonym.

### When

The request is sent to migration endpoint

### Then

The response contains the converted DecryptedPseudonym from the request migrated according to the input
parameters.

### Description

--- happy flow
---- different OIN combinations (source, target)
---- different schemeKeySetVersion
---- with / without diversifier
---- different recipientKeySetVersions


PTC_5401_1: SDEP (0 diversifier, sksv 1, type B), targetMigrant,targetMigrantKeySetVersion) not required
--------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5402@

SignedDirectEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).
Matching key inferred from the uniquely matching 'EP migration source'-key.


* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_1.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_1.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_1.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_1.request.migrationID                          | string              }}  |
    | authorizedParty                         | {{ PTC_5401_1.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_1.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_1.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_1.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_1.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_1.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_1.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_1.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_1.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met


PTC_5401_2: SDEP (0 diversifier, sksv 10, type E), (targetMigrant,targetMigrantKeySetVersion) not required
----------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5403@

SignedDirectEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).
Matching key inferred from the uniquely matching 'EP migration source'-key.


* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_2.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_2.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_2.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_2.request.migrationID                          | string              }}  |
    | authorizedParty                         | {{ PTC_5401_2.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_2.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_2.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_2.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_2.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_2.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_2.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_2.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_2.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5401_3: SDEP (0 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) required
-----------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5404@

SignedDirectEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).

[1] Matching key inferred from the uniquely matching 'EP migration source'-key:
although there a multiple keys matching the source SignedDirectEncryptedPseudonym, the diversifier
leads to an unique selection.

[2] Scheme key set version is relevant for both the 'EP migration source'- and 'EP migration target'-keys
without leading to ambiguities requiring specifying ofthe (targetMigrant,targetKeySetVersion).

* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_3.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_3.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_3.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_3.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5401_3.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5401_3.request.targetMigrantKeySetVersion           | string              }}  |
    | authorizedParty                         | {{ PTC_5401_3.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_3.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_3.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_3.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_3.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_3.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_3.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_3.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_3.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5401_4: SDEP (0 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) required
-----------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5405@

SignedDirectEncryptedPseudonym without diversifier migration requiring specifying a (targetMigrant,targetKeySetVersion),
without which there would be multiple matching 'EP migration source'- and 'EP migration target'-keys
for the given migrationID, that differ only in [1] sourceMigrant, [2] targetMigrant [2] the targetKeySetVersion.

* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_4.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_4.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_4.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_4.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5401_4.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5401_4.request.targetMigrantKeySetVersion           | string              }}  |
    | authorizedParty                         | {{ PTC_5401_4.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_4.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_4.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_4.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_4.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_4.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_4.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_4.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_4.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met





PTC_5401_5: SDEP (1 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) not required
---------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5409@

SignedDirectEncryptedPseudonym with diversifier migrated with multiple matching keys supplied that differ only in the
diversifier count.

Keys are matched based on the diversifier content besides the absence or presence of diversifiers.

* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_5.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_5.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_5.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_5.request.migrationID                          | string              }}  |
    | authorizedParty                         | {{ PTC_5401_5.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_5.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_5.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_5.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_5.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_5.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_5.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_5.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_5.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5401_6: SDEP (2 diversifier, sksv 1, type E), (targetMigrant,targetMigrantKeySetVersion) specified, but not required
------------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5410@

SignedDirectEncryptedPseudonym with diversifier migrated with multiple matching keys supplied that differ only in the
diversifier count.

The (targetMigrant,targetKeySetVersion) is specified, although supplied keys lead to an unique match by means of the
diversifier count and content.

* Create OASSignedDirectEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedDirectEncryptedPseudonym          | {{ PTC_5401_6.request.signedDirectEncryptedPseudonym       | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5401_6.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5401_6.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5401_6.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5401_6.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5401_6.request.targetMigrantKeySetVersion           | string              }}  |
    | authorizedParty                         | {{ PTC_5401_6.request.authorizedParty                      | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedDirectEncryptedPseudonymRequest
* Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5401_6.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5401_6.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5401_6.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedDirectEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                              | expectedValue                                              | expectedValueType       |
    |------------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                               | {{ PTC_5401_6.expectations.statusCode                      | string              }}  |
    | body                                     | {{ PTC_5401_6.expectations.responseBody                    | json                }}  |
    | json.pseudonym                           | {{ PTC_5401_6.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedDEP.auditElement | {{ PTC_5401_6.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue         | {{ PTC_5401_6.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met

