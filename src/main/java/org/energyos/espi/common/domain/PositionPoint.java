package org.energyos.espi.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PositionPoint {

	@Column(name = "positionPoint_x")
	private String xPosition;

	@Column(name = "positionPoint_y")
	private String yPosition;

	@Column(name = "positionPoint_z")
	private String zPosition;

	public String getxPosition() {
		return xPosition;
	}

	public void setxPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	public String getyPosition() {
		return yPosition;
	}

	public void setyPosition(String yPosition) {
		this.yPosition = yPosition;
	}

	public String getzPosition() {
		return zPosition;
	}

	public void setzPosition(String zPosition) {
		this.zPosition = zPosition;
	}
}
