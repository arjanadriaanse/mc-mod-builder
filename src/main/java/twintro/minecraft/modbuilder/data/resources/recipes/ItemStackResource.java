package twintro.minecraft.modbuilder.data.resources.recipes;

import java.util.HashMap;
import java.util.Map;

public class ItemStackResource {
	public String item;
	public String container; //wat je terugkrijgt als je met dit item craft. Werkt niet bij blocks.
	                         //maak hier een lege string van als je wilt dat items die normaal een
	                         //container hebben (zoals water buckets) die nu niet gebruiken.
	public String block;
	public Integer amount;
	public Integer meta;
	public Map<String, Integer> enchantments = new HashMap<String, Integer>();
}
