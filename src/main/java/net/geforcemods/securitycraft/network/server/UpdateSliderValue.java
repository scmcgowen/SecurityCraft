package net.geforcemods.securitycraft.network.server;

import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.geforcemods.securitycraft.api.CustomizableTileEntity;
import net.geforcemods.securitycraft.api.ICustomizable;
import net.geforcemods.securitycraft.api.Option;
import net.geforcemods.securitycraft.api.Option.DoubleOption;
import net.geforcemods.securitycraft.api.Option.IntOption;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateSliderValue {

	private BlockPos pos;
	private int id;
	private double value;

	public UpdateSliderValue(){ }

	public UpdateSliderValue(BlockPos pos, int id, double v){
		this.pos = pos;
		this.id = id;
		value = v;
	}

	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
		buf.writeInt(id);
		buf.writeDouble(value);
	}

	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
		id = buf.readInt();
		value = buf.readDouble();
	}

	public static void encode(UpdateSliderValue message, PacketBuffer packet)
	{
		message.toBytes(packet);
	}

	public static UpdateSliderValue decode(PacketBuffer packet)
	{
		UpdateSliderValue message = new UpdateSliderValue();

		message.fromBytes(packet);
		return message;
	}

	public static void onMessage(UpdateSliderValue message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			BlockPos pos = message.pos;
			int id = message.id;
			double value = message.value;
			PlayerEntity player = ctx.get().getSender();
			TileEntity te = player.world.getTileEntity(pos);

			if(te instanceof ICustomizable) {
				Option<?> o = ((ICustomizable)te).customOptions()[id];

				if(o instanceof DoubleOption)
					((DoubleOption)o).setValue(value);
				else if(o instanceof IntOption)
					((IntOption)o).setValue((int)value);

				((ICustomizable)te).onOptionChanged(((ICustomizable)te).customOptions()[id]);

				if(te instanceof CustomizableTileEntity)
					((CustomizableTileEntity)te).sync();
			}
		});

		ctx.get().setPacketHandled(true);
	}
}
