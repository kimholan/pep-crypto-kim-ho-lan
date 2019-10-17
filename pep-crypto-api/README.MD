PEP Decryption Component  - API (CDI)
=====================================

### Actions 

Decryption can be described as a series of actions. 

Each of these actions is a set of interfaces:
[1] One interface for the processing the action
[2] Another interface to carry the input and output for the action.

This leads to quite a number of interfaces, but will make reuse in another CDI-application easier,
because:
- Theres is not strict requirement to inherit for a specific base class
- Specific actions can be reimplemented

### Exceptions

All action methods are intercepted so any exception will be rewrapped/rethrown with a specific
exception message. Details of any initial cause could be combined with the rethrown exception. 

### Prototype

Decryption comes in a number of variants, each specific for a ASN.1-structure identified by an OID, 
while being identical most of the part, they should be handled separately.

- Identity : PEP Decryption without a reshuffle
- Pseudonym:  PEP Decryption with a reshuffle 
- Direct: Decrption with a direct key followed by a regulier Identity or Pseudonym decryption 

The shared implementation is placed in the prototype package.

### OIDs

Duplication is partially caused by supporting multiple versions of the ASN.1-specification at once without
introducing another abstraction layer to support the minimal processing specific for an OID.

The OID-specific service is quite generic and could be replaced by a producer method.

### Reuse

Reuse should be possible by implementating the exchange interface and injecting the right OID-based
service to process it. 


