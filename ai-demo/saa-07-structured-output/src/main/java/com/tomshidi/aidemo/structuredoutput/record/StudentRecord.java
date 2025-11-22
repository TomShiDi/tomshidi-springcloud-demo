package com.tomshidi.aidemo.structuredoutput.record;

/**
 * JDK14之后的新特性，record = entity + lombok
 * @author tomshidi
 * @version 1.0
 * @since 2025-11-22
 */
public record StudentRecord(Integer id,String name,String major, String email) {

}
