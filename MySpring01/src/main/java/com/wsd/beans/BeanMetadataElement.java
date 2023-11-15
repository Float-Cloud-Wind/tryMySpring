package com.wsd.beans;

/**
 * Interface to be implemented by bean metadata elements
 * that carry a configuration source object.
 */
public interface BeanMetadataElement {

    /**
     * Return the configuration source {@code Object} for this metadata element
     * (may be {@code null}).
     */
    default Object getSource() {
        return null;
    }

}
