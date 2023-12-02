package com.paul.paulxdaggertweaks.asm.patcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatchMethod {

    /**
     * 
     * @return The name and obfuscated name of the method. The first element is the
     *         unobfuscated name and the second the obfuscated name. i.e
     *         (renderItemOverlayIntoGUI, func_180453_a)
     */
    String[] name();

    String desc();
}