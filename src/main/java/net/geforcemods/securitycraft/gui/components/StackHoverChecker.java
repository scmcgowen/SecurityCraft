package net.geforcemods.securitycraft.gui.components;

import net.minecraft.item.ItemStack;

public class StackHoverChecker extends HoverChecker
{
	private ItemStack stack;

	public StackHoverChecker(int top, int bottom, int left, int right, ItemStack stack)
	{
		super(top, bottom, left, right);
		this.stack = stack;
	}

	public ItemStack getStack()
	{
		return stack;
	}
}
