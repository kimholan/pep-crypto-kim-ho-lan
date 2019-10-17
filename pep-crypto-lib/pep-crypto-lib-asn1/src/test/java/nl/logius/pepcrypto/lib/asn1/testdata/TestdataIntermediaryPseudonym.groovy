package nl.logius.pepcrypto.lib.asn1.testdata

import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym

class TestdataIntermediaryPseudonym {

    static Map pseudonymValue(File file, byte[] fileBytes) {
        def asn1 = Asn1MigrationIntermediaryPseudonym.fromByteArray(fileBytes)
        def item = [:]

        item << [
                fileName           : file.name,
                oid                : asn1.asn1Oid(),
                schemeVersion      : asn1.schemeVersion as String,
                schemeKeySetVersion: asn1.schemeKeySetVersion as String,
                source             : asn1.source as String,
                sourceKeySetVersion: asn1.sourceKeySetVersion as String,
                target             : asn1.target as String,
                targetKeySetVersion: asn1.targetKeySetVersion as String,
                type               : asn1.type.intValue() as char,
                pseudonymValue     : asn1.pseudonymValue as String,
        ]

        // Add additional fields if present
        if (asn1.hasProperty('diversifier')) {
            item << [diversifier: asn1.diversifier?.diversifierkeyvaluepair?.collect {
                "${it.key}=${it.value}"
            }?.join(',')]
        }

        return item
    }

}
