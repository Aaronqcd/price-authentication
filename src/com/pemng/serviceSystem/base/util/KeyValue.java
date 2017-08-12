/**    
 * description: 
 * Create on 2010-10-14 上午10:15:15    
 *      
 */
package com.pemng.serviceSystem.base.util;

import java.io.Serializable;

/**
 * @author luohanbin</a>
 * @version 1.0
 */

public class KeyValue implements Serializable {

	private static final long serialVersionUID = 5349818866304043832L;

	private Object key;

	private Object value;

	public KeyValue() {

	}

	public KeyValue(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final KeyValue other = (KeyValue) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
