package com.manning.liveproject.simplysend.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 42009L;
}
