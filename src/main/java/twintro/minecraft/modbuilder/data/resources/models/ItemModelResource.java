package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

public class ItemModelResource {
	public String parent;
	public Map<String, String> textures;
	public Display display;
	
	public enum Display {
		regular(0),
		tool(1),
		block(2);
		
		public Perspective thirdperson;
		public Perspective firstperson;
		
		private Display(int type){
			switch(type){
			case 0:
				firstperson = Perspective.first_person;
				thirdperson = Perspective.regular_third_person;
				break;
			case 1:
				firstperson = Perspective.first_person;
				thirdperson = Perspective.tool_third_person;
				break;
			case 2:
				thirdperson = Perspective.block_third_person;
			}
		}
	}
	
	public enum Perspective {
		first_person(new double[]{0, -135, 25},
				new double[]{0, 4, 2},
				new double[]{1.7, 1.7, 1.7}),
		regular_third_person(new double[]{-90, 0, 0},
				new double[]{0, 1, -3},
				new double[]{0.55, 0.55, 0.55}),
		tool_third_person(new double[]{0, 90, -35},
				new double[]{0, 1.25, -3.5},
				new double[]{0.85, 0.85, 0.85}),
		block_third_person(new double[]{10, -45, 170},
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
