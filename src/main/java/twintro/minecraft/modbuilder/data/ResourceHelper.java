package twintro.minecraft.modbuilder.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ResourceHelper<T> {
	public static final ResourceHelper<Material> materials = new ResourceHelper<Material>();
	public static final ResourceHelper<CreativeTabs> tabs = new ResourceHelper<CreativeTabs>();

	public static void init() {
		init(materials, Material.class);
		init(tabs, CreativeTabs.class);
	}

	private static void init(ResourceHelper helper, Class c) {
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			if (field.getType() == c && Modifier.isPublic(field.getModifiers())
					&& Modifier.isStatic(field.getModifiers())) {
				try {
					helper.addObject(field.getName(), field.get(null));
				} catch (IllegalArgumentException e) {
					// ignore
				} catch (IllegalAccessException e) {
					// ignore
				}
			}
		}
	}

	private Map<T, String> keys = new HashMap<T, String>();

	private Map<String, T> values = new HashMap<String, T>();

	public T get(String key) {
		return values.get(key);
	}

	public String getKey(T value) {
		return keys.get(value);
	}

	public T put(String key, T value) {
		keys.put(value, key);
		return values.put(key, value);
	}

	private void addObject(String key, Object value) {
		put(key, (T) value);
	}
}
