package twintro.minecraft.modbuilder.data;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.resources.data.IMetadataSection;

public class MetadataSection implements IMetadataSection {
	public Set<String> blocks = new HashSet<String>();
	public Set<String> items = new HashSet<String>();
	public Set<String> recipes = new HashSet<String>();
}
