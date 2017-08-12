package com.pemng.serviceSystem.base.pojo;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
/**
 * 基础PO，所有业务用PO都需继承
 * @author shaojie
 *
 * @param <T>
 */
@MappedSuperclass
public abstract class BasePO<T> implements Comparable<T>, Serializable {


	@Transient
	public abstract Long getId();
//    public abstract boolean equals(Object o );
//
//    public abstract int hashCode();
//	
//    public abstract String toString();

	public int compareTo(T o) {
		return 0;
	}

}
