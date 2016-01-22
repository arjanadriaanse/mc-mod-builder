package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import javax.swing.JLabel;

import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class MaterialLabel extends TooltipLabel {
	private String material;
	
	public MaterialLabel(String string, String tooltip) {
		super(string, tooltip);
	}
	
	@Override
	public void setText(String text) {
		material = text;
		super.setText(MaterialResources.simplifyItemStackName(text));
	}
	
	public String getMaterial(){
		return material;
	}
}
