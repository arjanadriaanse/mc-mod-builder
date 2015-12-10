package twintro.minecraft.modbuilder.data.resources.items;

public abstract class BaseItemResource {
	public ItemType type;
	public String model;
	public Integer stacksize; //hoeveel items er maximaal in een stack kunnen
	public String container; //naam van het item waar het in verandert als je ermee craft
}
