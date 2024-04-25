package com.tomshidi.demo.controller;

import com.tomshidi.demo.dto.SystemVersionInfo;

/**
 * @author tomshidi
 * @since 2023/3/29 16:54
 */
public abstract class BaseController {

    public abstract SystemVersionInfo getSystemVersion();
}
