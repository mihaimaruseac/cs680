public enum FSPermissionType implements Permission {
	PERMISSION_NONE,
	PERMISSION_READ,
	PERMISSION_WRITE,
	PERMISSION_READ_WRITE;

	public boolean compatible(FSPermissionType p) {
		if (p == PERMISSION_READ_WRITE)
			return this != PERMISSION_NONE;

		if ((p == PERMISSION_READ) || (p == PERMISSION_WRITE))
			return ((this == p) || (this == PERMISSION_READ_WRITE));
		return false;
	}
}
