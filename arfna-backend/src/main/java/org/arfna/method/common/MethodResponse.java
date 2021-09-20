package org.arfna.method.common;

import java.io.Serializable;

/**
 * We add this empty class as a parent for all API responses to extend
 * This allows for serialization while still maintaining type safety
 */
public class MethodResponse implements Serializable {
    private static final long serialVersionUID = -6864423760419443911L;
}
