package twintro.minecraft.modbuilder.data;

import net.minecraft.client.resources.data.IMetadataSection;
import twintro.minecraft.modbuilder.data.resources.meta.ModbuilderResource;

/**
 * Wrapper to return a <code>ModbuilderResource</code> through the
 * <code>MetadataSerializer</code>.
 */
public class MetadataSection implements IMetadataSection {
	public ModbuilderResource modbuilder;
}
