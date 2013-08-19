package edu.fudan.util.hash;

import java.io.Serializable;

public abstract class AbstractHashCode implements Serializable {
	private static final long serialVersionUID = -6803250687142456011L;

    public abstract int hashcode(String str);
}
