package me.rabrg.abyss.node;

import org.dreambot.api.wrappers.widgets.WidgetChild;

import me.rabrg.abyss.RabrgAbyssPro;

public final class ActivateRunNode extends Node {

	private WidgetChild runningWidget;

	public ActivateRunNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return !script.getWalking().isRunEnabled() && (runningWidget = script.getWidgets().getChildWidget(160, 27)) != null && runningWidget.getTextureId() == 1092;
	}

	@Override
	public void execute() {
		script.getWalking().toggleRun();
	}

	@Override
	public String toString() {
		return "Activate run";
	}

}
