package nl.logius.pepcrypto.api.event;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventPhaseType.BEFORE;
import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.UNSPECIFIED;

@Qualifier
@Retention(RUNTIME)
@Target({PARAMETER})
public @interface ApiEvent {

    ApiEventType value() default UNSPECIFIED;

    ApiEventPhaseType phase() default BEFORE;

    enum ApiEventType {
        /**
         * Unmarshal raw input as ASN.1-schema objects.
         */
        ASN1_MAPPING,

        /**
         * Signature verification.
         */
        SIGNATURE_VERIFICATION,

        /**
         * Decryption.
         */
        DECRYPTION,

        /**
         *
         */
        UNSPECIFIED
    }

    enum ApiEventPhaseType {
        /**
         * Intercepted method about to be invoked.
         */
        BEFORE,

        /**
         * Intercepted method invoked without exceptions.
         */
        AFTER,

        /**
         * Intercepted has thrown an exception.
         */
        EXCEPTION
    }

    final class Literal
            extends AnnotationLiteral<ApiEvent>
            implements ApiEvent {

        private static final long serialVersionUID = -8796229925200350790L;

        private final ApiEventType value;

        private final ApiEventPhaseType phase;

        private Literal(ApiEventType value, ApiEventPhaseType phase) {
            this.value = value;
            this.phase = phase;
        }

        public static Literal of(ApiEventType type, ApiEventPhaseType phase) {
            return new Literal(type, phase);
        }

        @Override
        public ApiEventType value() {
            return value;
        }

        @Override
        public ApiEventPhaseType phase() {
            return phase;
        }

    }

}

