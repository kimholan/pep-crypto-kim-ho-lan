Pseudonym conversion: closing key change - Alternate Flow
=========================================================
Tags: pseudonym-conversion-closing-key-change,alternate-flow,@bsnkeiddo-4954@

* Load dataset from "/v1/pseudonym-conversion/pseudonym-conversion-af.yaml"
* Target default endpoint "/pseudonym-conversion"

### Given

A OASPseudonymRequest contains one or more parameters based on which the conversion
should be rejected.

### When

The request is sent to pseudonym closing key conversion endpoint

### Then

The request is reject and no conversion is performed.


### Description

At least two 'serviceProviderKey'-element should be present and all must match
the schemeVersion, schemeKeySetVersion, and recipient of the incoming pseudonym
in addition to one element also matching the recipientKeySetVersion.

alternate flow:
--- missing keys (source, target)





PTC_4954_1: Different targetClosingKey
--------------------------------------
Tags: @bsnkeiddo-4957@

No closing key found in 'serviceProviderKeys' for specified 'targetClosingKey'.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_1.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_1.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_1.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_1.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met






PTC_4954_2: No targetClosingKey
-------------------------------
Tags: @bsnkeiddo-4958@

The 'targetClosingKey' must be specified.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_2.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_2.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_2.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_2.expectations.statusCode                                     | string          }}      |
* Expect response body contains "1 constraint violation"
* Expect response body contains "property path: processRequest.arg0.targetClosingKey"
* Fail if expectations are not met






PTC_4954_3: No ClosingKey (with source RKSV)
--------------------------------------------
Tags: @bsnkeiddo-4959@

Conversion fails due to none of the provided service provider keys matching the
pseudonym's recipientKeySetVersion, whilst all other parameters do match.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_3.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_3.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_3.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_3.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met





PTC_4954_4: Source closing key unavailable
------------------------------------------
Tags: @bsnkeiddo-4960@

Conversion fails due to providing only 1 serviceProviderKey-element,
with a recipientKeySetVersion differing from the pseudonym and matching all
other parameters.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_4.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_4.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_4.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_4.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met





PTC_4954_5: Target closing key missing
--------------------------------------
Tags: @bsnkeiddo-4961@

The 'serviceProviderKey' must match the incoming schemeVersion, schemeKeySetVersion,and OIN.
Provided service provider keys matches the incoming pseudonym's key parameters.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_5.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_5.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_5.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_5.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met







PTC_4954_6: different SKSV for ClosingKey (with target RKSV)
------------------------------------------------------------
Tags: @bsnkeiddo-4962@

Conversion to schemeKeySetVersion differing from the incoming pseudonym is not allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_6.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_6.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_6.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_6.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met






PTC_4954_7: different SKSV for ClosingKey (with source RKSV)
------------------------------------------------------------
Tags: @bsnkeiddo-4963@

Key selection should enforce a matching schemeKeySetVersion for the source closing key.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_7.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_7.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_7.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_7.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met





PTC_4954_8: different OIN for ClosingKey (with target RKSV)
-----------------------------------------------------------
Tags: @bsnkeiddo-4964@

Conversion to using key for recipient differing from the incoming pseudonym is not allowed.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_8.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_8.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_8.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_8.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met





PTC_4954_9: Different OIN for ClosingKey (with source RKSV)
-----------------------------------------------------------
Tags: @bsnkeiddo-4965@

Key selection should enforce a matching OIN for the source closing key.

* Create OASPseudonymRequest
    | propertyName                  | propertyValue                                                             | propertyFilter          |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | pseudonym                     | {{ PTC_4954_9.request.pseudonym                                           | binary              }}  |
    | serviceProviderKeys           | {{ PTC_4954_9.request.serviceProviderKeys                                 | serviceProviderKeys }}  |
    | targetClosingKey              | {{ PTC_4954_9.request.targetClosingKey                                    | string              }}  |
* Send OASPseudonymRequest
* Expect response matches
    | actualValue                   | expectedValue                                                             | expectedValueType       |
    |-------------------------------|---------------------------------------------------------------------------|-------------------------|
    | statusCode                    | {{ PTC_4954_9.expectations.statusCode                                     | string          }}      |
* Expect response body contains "MATCHING_CLOSING_KEY_REQUIRED"
* Fail if expectations are not met


