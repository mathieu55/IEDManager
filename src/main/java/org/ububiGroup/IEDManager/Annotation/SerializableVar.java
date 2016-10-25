package org.ububiGroup.IEDManager.Annotation;

import java.lang.annotation.*;
/**
 * Created by mathieu on 8/19/2016.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializableVar
{
	int Order();
	String Name();
}
