package de.applegreen.household.business;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author [ATE] Alexander Tepe | alexander.tepe@lmis.de
 **/

public class AdviceAnnotations {

    /**
     * Annotation to apply to Controller method which commits a grocerylist
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BillCommit {}
}
