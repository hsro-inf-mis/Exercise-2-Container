package de.fhro.mis.dockerCompose.repository;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface CDIRepository {
}
