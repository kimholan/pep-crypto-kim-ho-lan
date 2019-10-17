SignedEncryptedPseudonym: OIN migration - Happy Flow
====================================================
Tags: signed-encrypted-pseudonym-migration,happy-flow,@bsnkeiddo-5290@

* Load dataset from "/v1/signed-encrypted-pseudonym-migration/signed-encrypted-pseudonym-migration-hf.yaml"
* Target default endpoint "/signed-encrypted-pseudonym-migration"
* Specification properties
    | propertyName                            | propertyValue                                              |
    |-----------------------------------------|------------------------------------------------------------|
    | OASSignedEncryptedPseudonymRequest      | /signed-encrypted-pseudonym                                |
    | OASPseudonymMigrationExportRequest      | /pseudonym-migration-export                                |
    | OASPseudonymMigrationImportRequest      | /pseudonym-migration-import                                |


### Given

An OASSignedEncryptedPseudonymMigrationRequest valid for migration of a SignedEncryptedPseudonym.

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


PTC_5290_1: SEP (0 diversifier, sksv 1, type B), targetMigrant,targetMigrantKeySetVersion) not required
-------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5292@

SignedEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).
Matching key inferred from the uniquely matching 'EP migration source'-key.


* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_1.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_1.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_1.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_1.request.migrationID                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_1.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_1.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_1.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_1.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_1.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_1.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_1.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_1.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5290_2: SEP (0 diversifier, sksv 10, type E), (targetMigrant,targetMigrantKeySetVersion) not required
---------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5293@

SignedEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).
Matching key inferred from the uniquely matching 'EP migration source'-key.


* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_2.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_2.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_2.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_2.request.migrationID                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_2.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_2.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_2.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_2.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_2.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_2.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_2.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_2.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5290_3: SEP (0 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) required
----------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5294@

SignedEncryptedPseudonym without diversifier migration not requiring specifying a (targetMigrant,targetKeySetVersion).

[1] Matching key inferred from the uniquely matching 'EP migration source'-key:
although there a multiple keys matching the source SignedEncryptedPseudonym, the diversifier
leads to an unique selection.

[2] Scheme key set version is relevant for both the 'EP migration source'- and 'EP migration target'-keys
without leading to ambiguities requiring specifying ofthe (targetMigrant,targetKeySetVersion).

* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_3.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_3.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_3.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_3.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5290_3.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5290_3.request.targetMigrantKeySetVersion           | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_3.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_3.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_3.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_3.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_3.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_3.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_3.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_3.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5290_4: SEP (0 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) required
----------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5295@

SignedEncryptedPseudonym without diversifier migration requiring specifying a (targetMigrant,targetKeySetVersion),
without which there would be multiple matching 'EP migration source'- and 'EP migration target'-keys
for the given migrationID, that differ only in [1] sourceMigrant, [2] targetMigrant [2] the targetKeySetVersion.

* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_4.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_4.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_4.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_4.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5290_4.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5290_4.request.targetMigrantKeySetVersion           | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_4.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_4.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_4.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_4.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_4.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_4.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_4.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_4.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met





PTC_5290_5: SEP (1 diversifier, sksv 1, type B), (targetMigrant,targetMigrantKeySetVersion) not required
--------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5296@

SignedEncryptedPseudonym with diversifier migrated with multiple matching keys supplied that differ only in the
diversifier count.

Keys are matched based on the diversifier content besides the absence or presence of diversifiers.

* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_5.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_5.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_5.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_5.request.migrationID                          | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_5.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_5.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_5.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_5.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_5.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_5.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_5.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_5.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met




PTC_5290_6: SEP (2 diversifier, sksv 1, type E), (targetMigrant,targetMigrantKeySetVersion) specified, but not required
-----------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5297@

SignedEncryptedPseudonym with diversifier migrated with multiple matching keys supplied that differ only in the
diversifier count.

The (targetMigrant,targetKeySetVersion) is specified, although supplied keys lead to an unique match by means of the
diversifier count and content.

* Create OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                            | propertyValue                                              | propertyFilter          |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym                | {{ PTC_5290_6.request.signedEncryptedPseudonym             | binary              }}  |
    | serviceProviderKeys                     | {{ PTC_5290_6.request.serviceProviderKeys                  | serviceProviderKeys }}  |
    | schemeKeys                              | {{ PTC_5290_6.request.schemeKeys                           | schemeKeys          }}  |
    | migrationID                             | {{ PTC_5290_6.request.migrationID                          | string              }}  |
    | targetMigrant                           | {{ PTC_5290_6.request.targetMigrant                        | string              }}  |
    | targetMigrantKeySetVersion              | {{ PTC_5290_6.request.targetMigrantKeySetVersion           | string              }}  |
* Require identical MigrationID for all EP migration keys in OASSignedEncryptedPseudonymRequest
* Migrate SignedEncryptedPseudonym using OASSignedEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | sourcePseudonym                         | {{ PTC_5290_6.expectations.sourcePseudonym                 | binary  }}              |
    | migrationIntermediaryPseudonym          | {{ PTC_5290_6.expectations.migrationIntermediaryPseudonym  | binary  }}              |
    | targetPseudonym                         | {{ PTC_5290_6.expectations.targetPseudonym                 | binary  }}              |
* Fail if expectations are not met
* Send OASSignedEncryptedPseudonymMigrationRequest
* Expect response matches
    | actualValue                             | expectedValue                                              | expectedValueType       |
    |-----------------------------------------|------------------------------------------------------------|-------------------------|
    | statusCode                              | {{ PTC_5290_6.expectations.statusCode                      | string              }}  |
    | body                                    | {{ PTC_5290_6.expectations.responseBody                    | json                }}  |
    | json.pseudonym                          | {{ PTC_5290_6.expectations.targetPseudonym                 | binary              }}  |
    | json.decodedInput.signedEP.auditElement | {{ PTC_5290_6.expectations.auditValue                      | string_resource     }}  |
    | json.decodedInput.signatureValue        | {{ PTC_5290_6.expectations.signatureValue                  | string_signature    }}  |
* Fail if expectations are not met



 

PTC_5290_7: SignedEncryptedPseudonym migration, SP-keys for recipients related to source or target of migration allowed
-----------------------------------------------------------------------------------------------------------------------
Tags: @bsnkeiddo-5428@


Given a migration of an SignedEncryptedPseudonym from service provider (source migrant) A to
service provider (target migrant) B,, and an service provider X unrelated to the current migration,
it is allowed to supply migration keys for:
--- 'EP migration source' / 'EP migration target' for source A to target X
--- 'EP migration source' / 'EP migration target' for source B to target X
--- 'EP migration source' / 'EP migration target' for source X to target A
--- 'EP migration source' / 'EP migration target' for source X to target B

And any single party service provider key having A or B as the recipient not required for the current
migration, such as:
--- 'EP Decryption' for B
--- 'EP Closing' for B

* Template OASSignedEncryptedPseudonymMigrationRequest
    | propertyName                  | propertyValue                                                              | propertyFilter          |
    |-------------------------------|----------------------------------------------------------------------------|-------------------------|
    | signedEncryptedPseudonym      | {{ PTC_5290_7.request.signedEncryptedPseudonym                             | binary              }}  |
    | schemeKeys                    | {{ PTC_5290_7.request.schemeKeys                                           | schemeKeys          }}  |
    | migrationID                   | {{ PTC_5290_7.request.migrationID                                          | string              }}  |
* Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions
    | propertyName                  | propertyValue                                     | propertyFilter         | status | token                    |
    |-------------------------------|---------------------------------------------------|------------------------|--------|--------------------------|
    | serviceProviderKeys           | {{ PTC_5290_7.param.a.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.b.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.c.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.d.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.e.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.f.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.g.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.h.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.i.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
    | serviceProviderKeys           | {{ PTC_5290_7.param.j.serviceProviderKeys         | serviceProviderKeys }} |   200  | 2.16.528.1.1003.10.1.3.2 |
* Fail if expectations are not met
