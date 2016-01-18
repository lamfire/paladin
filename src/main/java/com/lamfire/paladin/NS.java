package com.lamfire.paladin;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-1-18
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface NS {
    public abstract String name();
}
