package com.bt.dm.fx.controls.chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bt.dm.core.model.ReferenceModel;
import com.bt.dm.core.utils.DMCollectionUtils;
import com.bt.dm.fx.controls.DMLabelBuilder;
import com.bt.dm.fx.controls.events.IconClickEvent;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon;
import com.bt.dm.fx.controls.labels.FXFontAwesomeIcon.FXFontAwesomeIconBuilder;
import com.bt.dm.fx.controls.labels.FXLabelCmp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * @author Ramalingesha ML
 *
 *         Created on Oct 23, 2023 7:30:07 PM
 */
public class DMChipCmp extends FlowPane {
	public static class DMChipLabelBuilder {
		private boolean i18n = true;
		private boolean englishFont;
		private IconClickEvent deleteEvent;
		private ReferenceModel labelRefModel;

		public DMChipLabelBuilder(ReferenceModel labelRefModel, IconClickEvent deleteEvent) {
			this.labelRefModel = labelRefModel;
			this.deleteEvent = deleteEvent;
		}

		public DMChipLabelBuilder i18n(boolean i18n) {
			this.i18n = i18n;
			return this;
		}

		public DMChipLabelBuilder englishFont(boolean englishFont) {
			this.englishFont = englishFont;
			return this;
		}
	}

	public static class DMChipCmpBuilder {
		private List<DMChipLabelBuilder> chipLabelBuilders;
		private DMChipActionEvent chipActionEvent;
		private int width = 200;

		public DMChipCmpBuilder width(int width) {
			this.width = width;
			return this;
		}

		public DMChipCmpBuilder chipLabelBuilders(List<DMChipLabelBuilder> chipLabelBuilders) {
			this.chipLabelBuilders = chipLabelBuilders;
			return this;
		}

		public DMChipCmpBuilder chipActionEvent(DMChipActionEvent chipActionEvent) {
			this.chipActionEvent = chipActionEvent;
			return this;
		}
	}

	private List<DMChipLabelBuilder> chipLabelBuilders;

	public DMChipCmp(DMChipCmpBuilder builder) {
		this.chipLabelBuilders = builder.chipLabelBuilders;
		this.initComponent(builder);
		this.addRootThemeStyleSheet();
	}

	private void initComponent(DMChipCmpBuilder builder) {
		this.getStyleClass().add("chip-container");
		this.setPrefWidth(builder.width);
		this.setHgap(10);
		this.setVgap(5);

		if (this.chipLabelBuilders == null) {
			this.chipLabelBuilders = new ArrayList<>();
		}

		FXFontAwesomeIcon addMobileNoButton = new FXFontAwesomeIcon(
				new FXFontAwesomeIconBuilder(FontAwesomeIcon.PLUS).faIconClickEvent(event -> {
					if (builder.chipActionEvent != null) {
						DMChipLabelBuilder newChipLabelBuilder = builder.chipActionEvent.addChip();

						if (newChipLabelBuilder != null) {
							boolean exists = this.chipLabelBuilders.stream()
									.filter(labelBuilder -> labelBuilder.labelRefModel.getValue()
											.equalsIgnoreCase(newChipLabelBuilder.labelRefModel.getValue()))
									.findFirst().isPresent();

							if (!exists) {
								this.addChipLabels(Arrays.asList(newChipLabelBuilder));
								this.chipLabelBuilders.add(newChipLabelBuilder);
							}
						}
					}
				}));

		this.getChildren().add(addMobileNoButton);

		this.addChipLabels(this.chipLabelBuilders);
	}

	private void addChipLabels(List<DMChipLabelBuilder> chipLabelBuilders) {
		if (DMCollectionUtils.isEmptyOrNull(chipLabelBuilders)) {
			return;
		}

		chipLabelBuilders.forEach(chipLabelBuilder -> {
			DMLabelBuilder labelBuilder = new DMLabelBuilder().label(chipLabelBuilder.labelRefModel.getValue())
					.i18n(chipLabelBuilder.i18n);

			if (chipLabelBuilder.englishFont) {
				labelBuilder.classNames(new String[] { "english-font" });
			}

			FXLabelCmp labelCmp = new FXLabelCmp(labelBuilder);
			FXFontAwesomeIcon deleteIcon = new FXFontAwesomeIcon(
					new FXFontAwesomeIconBuilder(FontAwesomeIcon.CLOSE).size("16px").faIconClickEvent(event -> {
						this.getChildren().remove(labelCmp.getParent());
						this.chipLabelBuilders.removeIf(builder -> builder.labelRefModel.getValue()
								.equalsIgnoreCase(chipLabelBuilder.labelRefModel.getValue()));

						if (chipLabelBuilder.deleteEvent != null) {
							chipLabelBuilder.deleteEvent.onClick(event);
						}
					}));

			HBox box = new HBox(2);
			box.setAlignment(Pos.CENTER);
			box.getChildren().addAll(labelCmp, deleteIcon);

			this.getChildren().add(box);
		});
	}

	public List<ReferenceModel> getChipLabels() {
		if (DMCollectionUtils.isEmptyOrNull(this.chipLabelBuilders)) {
			return null;
		}

		return this.chipLabelBuilders.stream().map(builder -> builder.labelRefModel).collect(Collectors.toList());
	}

	private void addRootThemeStyleSheet() {
		this.getStylesheets().clear();
		this.getStylesheets()
				.add(getClass().getClassLoader().getResource("com/bt/dm/fx/controls/DMControl.css").toExternalForm());
	}
}
