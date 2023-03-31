package io.github.djtpj.trait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells a developer that an SimpleTrait is to be used in a general manner (typically) from the {@code origins.json} file.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface UtilityTrait {
}
