package com.guerra.enrico.navis_processor

import java.lang.Exception
import javax.lang.model.element.Element

/**
 * Created by enrico
 * on 23/06/2020.
 */
class ErrorTypeException(message: String, val element: Element) : Exception(message)