PEP Library modules
-------------------

Library functions are split in modules to enforce dependency structure.

Runtime dependencies for pep-crypto-lib-crypto are minimized to 
facilitate reuse without pulling a lot of dependencies in.

Projectstructure (Maven)
========================

| Module                   | Description                                       | 
|--------------------------|---------------------------------------------------|
| pep-crypto-lib-asn1      | PEP ASN.1 to Java binding                         |
| pep-crypto-lib-pem       | PEM-encoding handlers                             |
| pep-crypto-lib-crypto    | PEP crytopgrahy                                   |
| pep-crypto-lib-lang      | Language level utilities                          |

Runtime dependencies:

```mermaid

graph BT
  subgraph pep-crypto-lib
    pep-crypto-lib-lang
    pep-crypto-lib-asn1
    pep-crypto-lib-pem
    pep-crypto-lib-crypto
    
    pep-crypto-lib-crypto-->|depends on|pep-crypto-lib-lang
    
    pep-crypto-lib-pem-->|depends on|pep-crypto-lib-crypto
    pep-crypto-lib-pem-->|depends on|pep-crypto-lib-lang
        
    pep-crypto-lib-asn1-->|depends on|pep-crypto-lib-lang
    pep-crypto-lib-asn1-->|depends on|pep-crypto-lib-crypto
  end
````
---
