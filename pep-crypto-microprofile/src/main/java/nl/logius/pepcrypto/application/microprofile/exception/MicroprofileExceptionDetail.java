package nl.logius.pepcrypto.application.microprofile.exception;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;

public enum MicroprofileExceptionDetail
        implements PepExceptionDetail {

    /**
     * Decoding inbound bytes as an ASN.1-sequence having an OID.
     */
    ASN1_SEQUENCE_DECODER,

    /**
     * Failed to parse a scheme keys.
     */
    SCHEME_KEYS_PARSER,

    /**
     * Scheme key name structure not valid.
     */
    SCHEME_KEY_METADATA_INVALID_STRUCTURE,

    /**
     * Input requires scheme keys for only a single environment
     */
    SCHEME_KEYS_ENVIRONMENT_NON_UNIQUE,

    /**
     * Input requires scheme keys to be of the correct length to represent an uncompressed EC-Point
     */
    SCHEME_KEY_INVALID_LENGTH,

    /**
     * Input requires scheme keys to be an uncompressed EC-Point
     */
    SCHEME_KEY_NOT_UNCOMPRESSED,

    /**
     * Input requires scheme keys to be decodable as an EC-Point
     */
    SCHEME_KEY_NOT_DECODABLE,

    /**
     * Parsing of the service provider keys.
     */
    SERVICE_PROVIDER_KEY_PARSER,

    /**
     * Failed to parse a service provider key.
     */
    SERVICE_PROVIDER_KEY_PARSE_FAILED,

    /**
     * Input requires keys for only a single recipient
     */
    SERVICE_PROVIDER_KEYS_RECIPIENT_NON_UNIQUE,

    /**
     * Service provider keys in request must relate to source or target migrant for the migration.
     */
    SERVICE_PROVIDER_KEYS_UNRELATED_TO_MIGRATION,

    /**
     * Failed to matching the key versions from the SignedEncryptedPseudonym to one of the inbound
     * scheme keys.
     */
    MATCHING_SCHEME_KEY_NOT_FOUND,

    /**
     * Multiple key urns mapping the the key version from the SignedEncryptedPseudonym.
     */
    UNIQUE_MATCHING_SCHEME_KEY_REQUIRED,

    /**
     * Selected scheme key does not have a value.
     */
    NO_SCHEME_KEY_VALUE,

    /**
     * Failed to matching the key versions from the SignedEncryptedPseudonym to one of the inbound
     * service provider keys.
     */
    MATCHING_SERVICE_PROVIDER_KEY_REQUIRED,

    /**
     *
     */
    MATCHING_DIRECT_RECEIVE_KEY_REQUIRED,

    /**
     *
     */
    NO_MATCHES_DIRECT_RECEIVE_KEY,

    /**
     * No decryption keys were matched for the decryption operation.
     */
    NO_MATCHES_DECRYPTION_KEY,

    /**
     * Multiple decryption keys were matched for the decryption operation.
     */
    NON_UNIQUE_MATCHES_DECRYPTION_KEY,

    /**
     * Multiple decryption keys were matched for the decryption operation.
     */
    NON_UNIQUE_MATCHES_DIRECT_RECEIVE_KEY,

    /**
     * No closing keys were matched for the decryption operation.
     */
    NO_MATCHES_CLOSING_KEY,

    /**
     * Failed to match the target closing key version to one of the inbound service provider keys.
     */
    MATCHING_CLOSING_KEY_REQUIRED,

    /**
     * Multiple closing key were matches for the reshuffle operation.
     */
    NON_UNIQUE_MATCHES_CLOSING_KEY,

    /**
     * Failed to match the migration source key version to one of the inbound service provider keys.
     */
    MATCHING_MIGRATION_SOURCE_KEY_REQUIRED,

    /**
     * Multiple migration source key were matches for the reshuffle operation.
     */
    NON_UNIQUE_MATCHES_MIGRATION_SOURCE_KEY,

    /**
     * No migration source keys were matched for the decryption operation.
     */
    NO_MATCHES_MIGRATION_SOURCE_KEY,

    /**
     * Failed to match the migration target key version to one of the inbound service provider keys.
     */
    MATCHING_MIGRATION_TARGET_KEY_REQUIRED,

    /**
     * Multiple migration target key were matches for the reshuffle operation.
     */
    NON_UNIQUE_MATCHES_MIGRATION_TARGET_KEY,

    /**
     * No migration target keys were matched for the decryption operation.
     */
    NO_MATCHES_MIGRATION_TARGET_KEY,

    /**
     * Both targetMigrant and targetMigrantKeySetVersion when selecting a migration target key.
     */
    INVALID_MIGRATION_TARGET_KEY_SELECTION,

    /**
     * Failed to select the scheme key.
     */
    SCHEME_KEY_SELECTOR,

    /**
     * No mapping available for the OID identifying the ASN.1-sequence.
     */
    OID_NOT_SUPPORTED,

    /**
     * Failed to encode pseudonym as DER.
     */
    ENCODE_DECRYPTED_PSEUDONYM_AS_DER,

    /**
     * Failed to encode intermediary pseudonym as DER.
     */
    ENCODE_MIGRATION_INTERMEDIARY_PSEUDONYM_AS_DER,

    /**
     * Unspecified exception in this module.
     */
    MICROPROFILE_MODULE

}
