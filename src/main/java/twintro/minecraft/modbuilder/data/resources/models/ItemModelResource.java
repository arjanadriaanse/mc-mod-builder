package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

public class ItemModelResource {
	public String parent;
	public Map<String, String> textures;
	public Display display;
	
	public enum Display {
		REGULAR(0),
		TOOL(1),
		BLOCK(2);
		
		public Perspective thirdperson;
		public Perspective firstperson;
		
		private Display(int type){
			switch(type){
			case 0:
				firstperson = Perspective.FIRST_PERSON;
				thirdperson = Perspective.REGULAR_THIRD_PERSON;
				break;
			case 1:
				firstperson = Perspective.FIRST_PERSON;
				thirdperson = Perspective.TOOL_THIRD_PERSON;
				break;
			case 2:
				thirdperson = Perspective.BLOCK_THIRD_PERSON;
			}
		}
	}
	
	public enum Perspective {
		FIRST_PERSON(new double[]{0, -135, 25},
				new double[]{0, 4, 2},
				new double[]{1.7, 1.7, 1.7}),
		REGULAR_THIRD_PERSON(new double[]{-90, 0, 0},
				new double[]{0, 1, -3},
				new double[]{0.55, 0.55, 0.55}),
		TOOL_THIRD_PERSON(new double[]{0, 90, -35},
				new double[]{0, 1.25, -3.5},
				new double[]{0.85, 0.85, 0.85}),
		BLOCK_THIRD_PERSON(new double[]{10, -45, 170},
				new double[]{0, 1.5, -2.75},
				new double[]{0.375, 0.375, 0.375});
		
		public double[] rotation;
		public double[] translation;
		public double[] scale;

		private Perspective(double[] r, double[] t, double[] s){
			rotation = r;
			translation = t;
			scale = s;
		}
	}
}
