ASN.1 binding
-------------

The ASN.1 binding classes are generated using an proprietary tool.

Care was taken to avoid direct dependencies on the generated
class by introducing a new type hierarchy using interfaces
to make dealing with the ASN.1 easier.

As a convention the interfaces should only contain getters/setters 
for properties mapping to elements from ASN.1 schema, so 
[1] the source of the data is easy traceable [2] the builders
will stop functioning if they don't.

Convenience methods to deal with the ASN.1 data should not
follow the JavaBean patterns ('getXXX','setXXX','is') to avoid 
it breaking code assuming them to be concrete properties, such
as the builders.


