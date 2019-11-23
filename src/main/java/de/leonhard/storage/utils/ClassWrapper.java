package de.leonhard.storage.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@SuppressWarnings("unchecked")
@UtilityClass
public class ClassWrapper {

	/**
	 * Method to cast an object to a given datatype
	 * Used for example in {@link de.leonhard.storage.internal.IStorage}
	 * to cast the results of get() to for example a String
	 *
	 * @param obj Object to cast
	 * @param def type of result
	 * @return Casted object
	 */
	public <T> T getFromDef(Object obj, T def) {
		if (obj instanceof String && def instanceof Integer) {
			obj = Integer.parseInt((String) obj);
		} else if (obj instanceof String && def instanceof Double) {
			obj = Double.parseDouble((String) obj);
		} else if (obj instanceof String && def instanceof Float) {
			obj = Double.parseDouble((String) obj);
		} else if (obj instanceof String && def instanceof Boolean) {
			return (T) (Boolean) obj.equals("true"); // Mustn't be primitive
		}
		return (T) obj;
	}

	public <T> T getFromDef(Object obj, Class<T> clazz) {
		try {
			return getFromDef(obj, clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException ex) {
			System.err.println("Wasn't able to instantiate '" + clazz.getSimpleName() + "'");
			ex.printStackTrace();
			throw new IllegalStateException();
		}
	}

	@UtilityClass
	public class LONG {
		public long getLong(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).longValue();
			} else if (obj instanceof String) {
				return Long.parseLong((String) obj);
			} else {
				return Long.parseLong(obj.toString());
			}
		}
	}

	@UtilityClass
	public class DOUBLE {
		public double getDouble(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).longValue();
			} else if (obj instanceof String) {
				return Double.parseDouble((String) obj);
			} else {
				return Double.parseDouble(obj.toString());
			}

		}
	}

	@UtilityClass
	public class FLOAT {
		public float getFloat(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).floatValue();
			} else if (obj instanceof String) {
				return Float.parseFloat((String) obj);
			} else {
				return Float.parseFloat(obj.toString());
			}
		}
	}

	@UtilityClass
	public class INTEGER {
		public int getInt(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).intValue();
			} else if (obj instanceof String) {
				return Integer.parseInt((String) obj);
			} else {
				return Integer.parseInt(obj.toString());
			}
		}
	}

	@UtilityClass
	@SuppressWarnings("unused")
	public class SHORT {
		public short getShort(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).shortValue();
			} else if (obj instanceof String) {
				return Short.parseShort((String) obj);
			} else {
				return Short.parseShort(obj.toString());
			}
		}
	}

	@UtilityClass
	public class BYTE {
		public byte getByte(Object obj) {
			if (obj instanceof Number) {
				return ((Number) obj).byteValue();
			} else if (obj instanceof STRING) {
				return Byte.parseByte(obj.toString());
			} else {
				return Byte.parseByte(obj.toString());
			}
		}
	}

	@UtilityClass
	public class STRING {
		public String getString(Object obj) {
			if (obj instanceof List && ((List) obj).size() == 1) {
				return ((List) obj).get(0).toString();
			}
			return obj.toString();
		}
	}
}
